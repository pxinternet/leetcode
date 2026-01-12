package movies;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;

/**
 * TopKMoviesV2: 优雅的流式 Top-K 实现
 * 
 * 设计亮点：
 * - 使用 TreeSet 替代 PriorityQueue，支持高效的删除和更新操作
 * - 使用 Map 维护堆中元素的引用，实现 O(log K) 的更新操作
 * - 代码简洁清晰，无需延迟删除策略
 */
public class TopKMoviesV2 {

    // Comparator 用于最小堆（保留 Top-K）——按播放数升序，播放数相同时按名字降序
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
     * 从 reader 读取下一条记录，并处理 BOM（若存在于行首）。
     */
    private static List<String> readNextRecord(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null) return null;
        if (line.startsWith("\uFEFF")) line = line.substring(1);
        return parseCsvRecord(reader, line);
    }

    /**
     * 解析一条 CSV 记录（从 initialLine 开始），支持双引号包裹字段并允许字段内含换行与转义引号。
     */
    private static List<String> parseCsvRecord(BufferedReader reader, String initialLine) throws IOException {
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
                        if (i + 1 < buffer.length() && buffer.charAt(i + 1) == '"') {
                            cur.append('"');
                            i += 2;
                            continue;
                        } else {
                            inQuotes = false;
                            i++;
                            continue;
                        }
                    } else {
                        inQuotes = true;
                        i++;
                        continue;
                    }
                }

                if (!inQuotes && ch == ',') {
                    fields.add(cur.toString());
                    cur.setLength(0);
                    i++;
                    continue;
                }

                cur.append(ch);
                i++;
            }

            if (inQuotes) {
                String next = reader.readLine();
                if (next == null) {
                    fields.add(cur.toString());
                    break;
                }
                buffer.append('\n').append(next);
                continue;
            } else {
                fields.add(cur.toString());
                break;
            }
        }

        return fields;
    }

    // ------------------------- Top-K 统计相关 -------------------------

    /**
     * 从 CSV 文件流式读取并实时维护 Top-K，返回播放量最高的 k 个电影。
     * 
     * 核心思路：
     * 1. 使用 HashMap 记录每个电影的累计播放量
     * 2. 使用 TreeSet 维护 Top-K（支持高效的删除和插入）
     * 3. 使用 Map 维护堆中元素的引用，实现 O(log K) 的更新操作
     * 
     * 时间复杂度：O(N log K)，其中 N 是记录数，K 是 Top-K 的大小
     * 空间复杂度：O(M + K)，其中 M 是不同电影的数量，K 是 Top-K 的大小
     */
    public static List<Map.Entry<String, Long>> topKFromCsv(String csvPath, int k) throws IOException {
        if (k <= 0) return Collections.emptyList();

        // 记录每个电影的累计播放量
        Map<String, Long> counts = new HashMap<>();
        // TreeSet 维护 Top-K（堆顶是最小值），支持高效的删除和插入
        TreeSet<Map.Entry<String, Long>> heap = new TreeSet<>(MIN_HEAP_COMPARATOR);
        // 维护堆中每个电影对应的 Entry 引用，用于快速删除旧值
        Map<String, Map.Entry<String, Long>> heapEntries = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
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
                    
                    // 创建新的 Entry
                    Map.Entry<String, Long> newEntry = new AbstractMap.SimpleEntry<>(name, newCount);
                    
                    // 如果该电影已在堆中，先删除旧的 Entry
                    Map.Entry<String, Long> oldEntry = heapEntries.get(name);
                    if (oldEntry != null) {
                        heap.remove(oldEntry);
                    }
                    
                    // 判断新值是否应该进入 Top-K
                    if (heap.size() < k) {
                        // 堆未满，直接加入
                        heap.add(newEntry);
                        heapEntries.put(name, newEntry);
                    } else {
                        // 堆已满，检查新值是否大于堆顶（最小值）
                        Map.Entry<String, Long> minEntry = heap.first();
                        if (MIN_HEAP_COMPARATOR.compare(newEntry, minEntry) > 0) {
                            // 新值大于堆顶，替换堆顶
                            heap.remove(minEntry);
                            heapEntries.remove(minEntry.getKey());
                            heap.add(newEntry);
                            heapEntries.put(name, newEntry);
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid play count for movie '" + name + "': '" + cnt + "' - skip");
                }
            }
        }

        // 将堆中元素转换为列表并排序
        List<Map.Entry<String, Long>> res = new ArrayList<>(heap);
        res.sort(RESULT_COMPARATOR);
        return res;
    }

    /**
     * 简单演示
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java movies.TopKMoviesV2 <csvPath> <k>");
            // 使用示例文件
            String example = "example_movies.csv";
            if (new java.io.File(example).exists()) {
                List<Map.Entry<String, Long>> res = topKFromCsv(example, 3);
                res.forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
            }
            return;
        }

        String csvPath = args[0];
        int k = Integer.parseInt(args[1]);
        topKFromCsv(csvPath, k).forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
    }
}
