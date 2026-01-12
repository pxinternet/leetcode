package movies;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;

/**
 * TopKMovies: 从 CSV 文件中读取电影播放量数据，统计播放量最高的 K 部电影。
 *
 * 设计目标：代码优雅清晰、模块化、带充分中文注释，且功能与之前一致。
 */
public class TopKMovies {

    // Comparator 用于最小堆（保留 Top-K）——按播放数升序，播放数相同时按名字降序（便于弹出较差的项）
    private static final Comparator<Map.Entry<String, Long>> MIN_HEAP_COMPARATOR =
            Comparator.comparingLong((Map.Entry<String, Long> e) -> e.getValue())
                      .thenComparing(e -> e.getKey(), Comparator.reverseOrder());

    // Comparator 用于最终结果排序：按播放数降序，播放数相同时按名字升序
    private static final Comparator<Map.Entry<String, Long>> RESULT_COMPARATOR = (a, b) -> {
        int cmp = Long.compare(b.getValue(), a.getValue());
        if (cmp != 0) return cmp;
        return a.getKey().compareTo(b.getKey());
    };

    // ------------------------- CSV 解析相关 -------------------------

    /**
     * 解析一条 CSV 记录（从 initialLine 开始），支持双引号包裹字段并允许字段内含换行与转义引号。
     *
     * 解析规则（RFC4180 风格的简化实现）：
     * - 未被双引号包裹的字段以逗号分隔。
     * - 被双引号包裹的字段可包含逗号和换行；内部使用 "" 表示一个 " 字符。
     *
     * 返回值：字段列表（外围引号被去除，内部的双引号转义已处理）。
     */
    public static List<String> parseCsvRecord(BufferedReader reader, String initialLine) throws IOException {
        if (initialLine == null) return null;

        StringBuilder buffer = new StringBuilder(initialLine);
        List<String> fields = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        int i = 0;
        while (true) {
            while (i < buffer.length()) {
                char ch = buffer.charAt(i);
                if (ch == '"') {
                    if (inQuotes) {
                        // 双引号内：若下一个也是双引号，则是转义，写入一个双引号字符
                        if (i + 1 < buffer.length() && buffer.charAt(i + 1) == '"') {
                            cur.append('"');
                            i += 2;
                            continue;
                        } else {
                            // 结束引号段
                            inQuotes = false;
                            i++;
                            continue;
                        }
                    } else {
                        // 进入引号段（不向 cur 输出外围引号）
                        inQuotes = true;
                        i++;
                        continue;
                    }
                }

                if (!inQuotes && ch == ',') {
                    // 分隔字段
                    fields.add(cur.toString());
                    cur.setLength(0);
                    i++;
                    continue;
                }

                // 普通字符或在引号内的字符
                cur.append(ch);
                i++;
            }

            if (inQuotes) {
                // 引号未闭合：读取下一行并保留换行
                String next = reader.readLine();
                if (next == null) {
                    // 文件结束但引号未闭合，尽量收尾
                    fields.add(cur.toString());
                    break;
                }
                buffer.append('\n').append(next);
                // 继续解析新增内容
                continue;
            } else {
                // 当前记录解析完毕，加入最后一个字段
                fields.add(cur.toString());
                break;
            }
        }

        return fields;
    }

    /**
     * 从 reader 读取下一条记录，并处理 BOM（若存在于行首）。
     */
    public static List<String> readNextRecord(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null) return null;
        if (line.startsWith("\uFEFF")) line = line.substring(1);
        return parseCsvRecord(reader, line);
    }

    // ------------------------- Top-K 统计相关 -------------------------

    /**
     * 从 CSV 文件流式读取并实时维护 Top-K，返回播放量最高的 k 个电影（按播放量降序，名称升序）。
     *
     * 优化实现要点：
     * - 流式处理：每处理一条记录就更新 Top-K，而不是先统计全部数据
     * - 使用 HashMap 记录每个电影的累计播放量
     * - 使用最小堆维护 Top-K，采用延迟删除策略处理堆中可能过期的元素
     * - 若播放数解析失败（非数字），会跳过该行并在 stderr 打印警告。
     *
     * 时间复杂度：O(N log K)，其中 N 是记录数，K 是 Top-K 的大小
     * 空间复杂度：O(M + K)，其中 M 是不同电影的数量，K 是 Top-K 的大小
     */
    public static List<Map.Entry<String, Long>> topKFromCsv(String csvPath, int k) throws IOException {
        if (k <= 0) return Collections.emptyList();

        // 记录每个电影的累计播放量
        Map<String, Long> counts = new HashMap<>();
        // 最小堆维护 Top-K（堆顶是最小值）
        PriorityQueue<Map.Entry<String, Long>> heap = new PriorityQueue<>(MIN_HEAP_COMPARATOR);
        // 记录哪些电影名在堆中（用于快速判断）
        Set<String> inHeap = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
            List<String> rec;
            while ((rec = readNextRecord(reader)) != null) {
                if (rec.size() < 2) {
                    System.err.println("Skipping invalid record: " + rec);
                    continue;
                }
                String name = rec.get(0).trim();
                String cnt = rec.get(1).trim();
                try {
                    long plays = Long.parseLong(cnt);
                    // 更新累计播放量
                    long newCount = counts.merge(name, plays, Long::sum);
                    
                    // 创建新的 Entry（使用最新的播放量）
                    Map.Entry<String, Long> entry = new AbstractMap.SimpleEntry<>(name, newCount);
                    
                    if (inHeap.contains(name)) {
                        // 该电影已在堆中，但由于 PriorityQueue 不支持更新操作，
                        // 我们采用延迟删除策略：先加入新值，后续清理时再删除旧值
                        heap.offer(entry);
                    } else {
                        // 该电影不在堆中
                        if (heap.size() < k) {
                            // 堆未满，直接加入
                            heap.offer(entry);
                            inHeap.add(name);
                        } else {
                            // 堆已满，检查新值是否大于堆顶
                            Map.Entry<String, Long> top = heap.peek();
                            if (MIN_HEAP_COMPARATOR.compare(entry, top) > 0) {
                                // 新值大于堆顶，替换堆顶
                                Map.Entry<String, Long> removed = heap.poll();
                                inHeap.remove(removed.getKey());
                                heap.offer(entry);
                                inHeap.add(name);
                            }
                        }
                    }
                    
                    // 定期清理堆中的过期元素（延迟删除策略）
                    // 当堆大小超过 k 的 2 倍时进行清理，避免堆中积累过多过期元素
                    if (heap.size() > k * 2) {
                        cleanExpiredEntries(heap, counts, inHeap, k);
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid play count for movie '" + name + "': '" + cnt + "' - skip");
                }
            }
        }

        // 最终清理所有过期元素
        cleanExpiredEntries(heap, counts, inHeap, k);

        // 将堆中元素转换为列表并排序
        List<Map.Entry<String, Long>> res = new ArrayList<>(heap);
        res.sort(RESULT_COMPARATOR);
        return res;
    }

    /**
     * 清理堆中的过期元素（延迟删除策略）。
     * 堆中可能包含同一个电影的多个 Entry（旧值），需要删除过期的，只保留最新的。
     *
     * @param heap 最小堆
     * @param counts 最新的播放量映射
     * @param inHeap 记录哪些电影在堆中
     * @param k Top-K 的大小
     */
    private static void cleanExpiredEntries(PriorityQueue<Map.Entry<String, Long>> heap,
                                            Map<String, Long> counts,
                                            Set<String> inHeap,
                                            int k) {
        // 临时存储有效的 Entry
        PriorityQueue<Map.Entry<String, Long>> validHeap = new PriorityQueue<>(MIN_HEAP_COMPARATOR);
        inHeap.clear();

        while (!heap.isEmpty()) {
            Map.Entry<String, Long> entry = heap.poll();
            String name = entry.getKey();
            Long currentCount = counts.get(name);
            
            // 检查 Entry 的值是否是最新的
            if (currentCount != null && currentCount.equals(entry.getValue())) {
                // 值是最新的，保留
                validHeap.offer(entry);
                inHeap.add(name);
            }
            // 否则，该 Entry 已过期，丢弃
        }

        // 只保留 Top-K
        while (validHeap.size() > k) {
            Map.Entry<String, Long> removed = validHeap.poll();
            inHeap.remove(removed.getKey());
        }

        // 将有效的 Entry 放回原堆
        heap.clear();
        heap.addAll(validHeap);
    }

    /**
     * 给定 movie->count 映射，返回 Top-K 项（按播放数降序，播放数相同时按名称升序）。
     * 使用最小堆保持容量为 k 的候选集合，最终将堆中元素排序为最终输出顺序。
     * 
     * 注意：此方法保留用于向后兼容，新代码建议使用 topKFromCsv 的流式处理版本。
     */
    public static List<Map.Entry<String, Long>> topKFromCounts(Map<String, Long> counts, int k) {
        if (k <= 0) return Collections.emptyList();

        PriorityQueue<Map.Entry<String, Long>> heap = new PriorityQueue<>(MIN_HEAP_COMPARATOR);
        for (Map.Entry<String, Long> e : counts.entrySet()) {
            heap.offer(e);
            if (heap.size() > k) heap.poll();
        }

        List<Map.Entry<String, Long>> res = new ArrayList<>(heap);
        res.sort(RESULT_COMPARATOR);
        return res;
    }

    // ------------------------- 辅助：示例文件写入 & main -------------------------

    /**
     * 写入示例 CSV：包含含逗号、含换行、含引号的电影名，用于演示解析能力。
     */
    private static void writeExampleCsv(String path) throws IOException {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(path, "UTF-8")) {
            pw.println("\"The Good, the Bad and the Ugly\",100");
            pw.println("Simple Movie,50");
            // 使用单个 println 包含换行符，便于构造一个被引号包裹的、内部含换行的字段
            pw.println("\"Multi\nLine Movie\",200");
            // 字段内部的双引号通过两个双引号转义
            pw.println("\"He said \"\"Hi\"\"\",300");
            pw.println("Normal,150");
        }
    }

    /**
     * 简单演示：当未提供参数时，生成示例 CSV 并输出 Top-3；若提供参数则按用户 CSV 运行。
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java movies.TopKMovies <csvPath> <k>");
            String example = "example_movies.csv";
            writeExampleCsv(example);
            System.out.println("Created example file: " + example);
            List<Map.Entry<String, Long>> res = topKFromCsv(example, 3);
            res.forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
            return;
        }

        String csvPath = args[0];
        int k = Integer.parseInt(args[1]);
        topKFromCsv(csvPath, k).forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
    }
}
