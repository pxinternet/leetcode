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
     * 从 CSV 文件读取并合并每个电影的播放量，返回播放量最高的 k 个电影（按播放量降序，名称升序）。
     *
     * 实现要点：
     * - 使用 HashMap 合并相同电影名的播放数（counts），然后用容量为 k 的最小堆筛选 Top-K。
     * - 若播放数解析失败（非数字），会跳过该行并在 stderr 打印警告。
     */
    public static List<Map.Entry<String, Long>> topKFromCsv(String csvPath, int k) throws IOException {
        Map<String, Long> counts = new HashMap<>();

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
                    counts.merge(name, plays, Long::sum);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid play count for movie '" + name + "': '" + cnt + "' - skip");
                }
            }
        }

        return topKFromCounts(counts, k);
    }

    /**
     * 给定 movie->count 映射，返回 Top-K 项（按播放数降序，播放数相同时按名称升序）。
     * 使用最小堆保持容量为 k 的候选集合，最终将堆中元素排序为最终输出顺序。
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
