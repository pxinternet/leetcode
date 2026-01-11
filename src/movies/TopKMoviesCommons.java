package movies;

/*
 * 使用 Apache Commons CSV 的 Top-K 实现示例
 *
 * 注意：该文件依赖 org.apache.commons:commons-csv 库（在编译和运行时需要将其放到 classpath）。
 * 下面的实现使用 Commons CSV 的 API 来解析 CSV（支持 RFC4180），替代自定义解析器。
 *
 * 在无库时该文件不会在本地环境编译通过 —— 请参考同目录下的 README_TOPK_COMMONS.md
 * 关于如何添加依赖并编译运行的说明。
 */

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;

/**
 * TopKMoviesCommons: 基于 Apache Commons CSV 的实现。解析由库负责，代码更简洁且稳健。
 */
public class TopKMoviesCommons {

    /**
     * 从 CSV 文件读取并合并播放量，使用 Apache Commons CSV 进行解析。
     */
    public static List<Map.Entry<String, Long>> topKFromCsv(String csvPath, int k) throws IOException {
        Map<String, Long> counts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
            // 使用默认的 CSVFormat（可根据需要改为 CSVFormat.RFC4180 或自定义配置）
            CSVParser parser = CSVFormat.RFC4180.parse(reader);
            for (CSVRecord record : parser) {
                if (record.size() < 2) continue;
                String name = record.get(0).trim();
                String cnt = record.get(1).trim();
                try {
                    long plays = Long.parseLong(cnt);
                    counts.merge(name, plays, Long::sum);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid play count for movie '" + name + "': '" + cnt + "' - skip");
                }
            }
        }

        // Top-K
        PriorityQueue<Map.Entry<String, Long>> heap = new PriorityQueue<>(Comparator
                .comparingLong((Map.Entry<String, Long> e) -> e.getValue())
                .thenComparing(Map.Entry::getKey, Comparator.reverseOrder()));

        for (Map.Entry<String, Long> e : counts.entrySet()) {
            heap.offer(e);
            if (heap.size() > k) heap.poll();
        }

        List<Map.Entry<String, Long>> res = new ArrayList<>(heap);
        res.sort((a, b) -> {
            int cmp = Long.compare(b.getValue(), a.getValue());
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey());
        });
        return res;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java -cp <classpath including commons-csv.jar> movies.TopKMoviesCommons <csvPath> <k>");
            return;
        }
        String csvPath = args[0];
        int k = Integer.parseInt(args[1]);
        topKFromCsv(csvPath, k).forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
    }
}

