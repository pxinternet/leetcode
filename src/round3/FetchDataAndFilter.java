package round3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * FetchDataAndFilter - HTTP 拉取数据并按正则过滤
 *
 * 题目（概要）：fetchData 从 URL 拉取 HTTP 响应正文；filterData 用正则 schema 从 data 中提取匹配项，去空后返回。
 *
 * 算法原理：
 * - fetchData：GET 请求，BufferedReader 逐行读取并拼接。
 * - filterData：Pattern.matcher(data)，matcher.find() 收集 group()，过滤空串。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：HttpURLConnection GET，InputStream 包装为 BufferedReader。
 * - 步骤 2：每行 append 到 result。
 * - 步骤 3：filterData 中 pattern.matcher(data)，while(find) 收集，stream 过滤非空。
 *
 * 关键洞察：正则可提取多组；空匹配需过滤。
 *
 * 时间复杂度：fetch O(响应大小)；filter O(data 长度)
 * 空间复杂度：O(响应大小 + 匹配结果)
 */
public class FetchDataAndFilter {

    public static String fetchData(String urlString) throws Exception {
        StringBuilder result = new StringBuilder();

        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();

    }

    public static List<String> filterData(String data, String schema) {
        Pattern pattern = Pattern.compile(schema);
        Matcher matcher = pattern.matcher(data);

        List<String> filteredData = new ArrayList<>();

        while (matcher.find()) {
            filteredData.add(matcher.group());
        }

        return filteredData.stream()
                .filter(item -> !item.isEmpty()).collect(Collectors.toList());
    }

}
