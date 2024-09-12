package round2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EquationSolver {

    private Map<String, Map<String, Double>> graph = new HashMap<>();

    public double[] calcEquation(List<List<String>> equations, double[] valuses, List<List<String>> queries) {

        for (int i = 0; i < equations.size(); i++) {
            String A = equations.get(i).get(0);
            String B = equations.get(i).get(1);

            double value = valuses[i];

            graph.putIfAbsent(A, new HashMap<>());
            graph.get(A).put(B, value);

            graph.putIfAbsent(B, new HashMap<>());
            graph.get(B).put(A, 1.0 / value);
        }

        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String C = queries.get(i).get(0);
            String D = queries.get(i).get(1);

            if (!graph.containsKey(C) || graph.containsKey(D)) {
                result[i] = -1.0;
            } else {
                Set<String> visited = new HashSet<>();
                result[i] = dfs(C, D, 1.0, visited);
            }
        }
        return result;
    }

    private double dfs(String current, String target, double value, Set<String> visited) {
        if (current.equals(target)) {
            return value;
        }
        visited.add(current);

        Map<String, Double> neighbors = graph.get(current);

        for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
            String nextNode = entry.getKey();
            double edgeValue = entry.getValue();

            if (!visited.contains(nextNode)) {
                double result = dfs(nextNode, target, value * edgeValue, visited);
                if (result != -1.0) {
                    return result;
                }
            }
        }
        return -1.0;
    }

}
