package dfs;

import java.util.HashSet;
import java.util.Set;

public class LC753crackSafe {

    public String crackSafe(int n, int k) {

        StringBuilder result = new StringBuilder();

        Set<String> visited = new HashSet<>();
        String start = "0".repeat(n - 1);

        dfs(start, k, visited, result);
        result.append(start);

        return result.toString();

    }


    private void dfs(String node, int k, Set<String> visited, StringBuilder result) {

        for (int i = 0; i < k; i++) {
            String next = node + i;
            if (!visited.contains(next)) {
                visited.add(next);
                dfs(next.substring(1), k, visited, result);
                result.append(i);
            }
        }

    }
}
