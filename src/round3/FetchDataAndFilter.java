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
