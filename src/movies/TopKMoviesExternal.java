package movies;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Base64;

/**
 * TopKMoviesExternal: 处理海量数据的 Top-K 实现（外部分桶聚合）
 *
 * 思路（两阶段）：
 * 1) 分桶（hash partition）：逐条读取原始 CSV（使用已有的 readNextRecord），将记录写入 numBuckets 个临时文件之一。
 *    为了避免转义复杂性，电影名使用 Base64 编码写入，格式为: base64(name) + '\t' + count + '\n'.
 * 2) 聚合与合并：逐个读取每个桶文件，在内存中对该桶内的电影名累计播放量（HashMap），然后用一个全局大小为 K 的最小堆
 *    更新 Top-K（因为同名电影都在同一桶，所以不会跨桶分散）。
 *
 * 该算法的优点：内存需求可由 numBuckets 控制，适合单机处理无法全部装入内存的场景（将大量数据分散到多个磁盘文件）。
 */
public class TopKMoviesExternal {

    /**
     * 执行外部分桶 Top-K 计算
     * @param csvPath 源 CSV 文件路径
     * @param k Top K
     * @param numBuckets 分桶数（建议根据内存与数据规模设置）
     * @return Top-K 结果，按播放数降序
     * @throws Exception
     */
    public static List<Map.Entry<String, Long>> topKExternal(String csvPath, int k, int numBuckets) throws Exception {
        if (k <= 0) return Collections.emptyList();
        if (numBuckets <= 0) numBuckets = 1024;

        // 创建临时目录用于存放桶文件
        Path tmpDir = Files.createTempDirectory("topk_buckets_");
        tmpDir.toFile().deleteOnExit();

        // 创建 BufferedWriter 数组，每个桶对应一个文件
        BufferedWriter[] writers = new BufferedWriter[numBuckets];
        File[] bucketFiles = new File[numBuckets];
        try {
            for (int i = 0; i < numBuckets; i++) {
                Path p = tmpDir.resolve("bucket_" + i + ".tmp");
                bucketFiles[i] = p.toFile();
                writers[i] = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(bucketFiles[i], false), StandardCharsets.UTF_8));
            }

            // 阶段一：分桶写入（使用 TopKMovies.readNextRecord 解析 CSV）
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
                List<String> rec;
                while ((rec = TopKMovies.readNextRecord(reader)) != null) {
                    if (rec.size() < 2) continue; // skip invalid
                    String name = rec.get(0).trim();
                    String cntStr = rec.get(1).trim();
                    long cnt;
                    try {
                        cnt = Long.parseLong(cntStr);
                    } catch (NumberFormatException ex) {
                        continue; // skip invalid number
                    }
                    int idx = (name.hashCode() & 0x7fffffff) % numBuckets;
                    String b64 = Base64.getEncoder().encodeToString(name.getBytes(StandardCharsets.UTF_8));
                    // 写入格式：base64(tab)count\n
                    writers[idx].write(b64);
                    writers[idx].write('\t');
                    writers[idx].write(Long.toString(cnt));
                    writers[idx].write('\n');
                }
            }
        } finally {
            // 关闭所有 writer
            for (BufferedWriter w : writers) {
                if (w != null) try { w.close(); } catch (IOException ignored) {}
            }
        }

        // 阶段二：逐桶聚合并更新全局 Top-K
        PriorityQueue<Map.Entry<String, Long>> minHeap = new PriorityQueue<>(Comparator
                .comparingLong((Map.Entry<String, Long> e) -> e.getValue())
                .thenComparing(Map.Entry::getKey, Comparator.reverseOrder())
        );

        for (int i = 0; i < numBuckets; i++) {
            File f = bucketFiles[i];
            if (!f.exists() || f.length() == 0) continue;

            // 在内存中聚合该桶
            Map<String, Long> bucketCounts = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    int tab = line.indexOf('\t');
                    if (tab <= 0) continue;
                    String b64 = line.substring(0, tab);
                    String numStr = line.substring(tab + 1);
                    long val;
                    try { val = Long.parseLong(numStr); } catch (NumberFormatException ex) { continue; }
                    String name = new String(Base64.getDecoder().decode(b64), StandardCharsets.UTF_8);
                    bucketCounts.merge(name, val, Long::sum);
                }
            }

            // 对该桶的聚合结果更新全局 Heap
            for (Map.Entry<String, Long> e : bucketCounts.entrySet()) {
                minHeap.offer(e);
                if (minHeap.size() > k) minHeap.poll();
            }

            // 删除桶文件以释放空间
            try { Files.deleteIfExists(f.toPath()); } catch (IOException ignored) {}
        }

        // 删除临时目录
        try { Files.deleteIfExists(tmpDir); } catch (IOException ignored) {}

        // 提取并按最终顺序排序输出
        List<Map.Entry<String, Long>> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> {
            int cmp = Long.compare(b.getValue(), a.getValue());
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey());
        });
        return result;
    }

    private static void printUsage() {
        System.out.println("Usage: java movies.TopKMoviesExternal <csvPath> <k> [numBuckets]");
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) { printUsage(); return; }
        String csvPath = args[0];
        int k = Integer.parseInt(args[1]);
        int buckets = args.length >= 3 ? Integer.parseInt(args[2]) : 1024;
        List<Map.Entry<String, Long>> res = topKExternal(csvPath, k, buckets);
        res.forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
    }
}

