package round2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * EquationSolver - 除法求值（LeetCode 399）
 *
 * 题目（概要）：给定等式 A/B=k、B/C=m 等，以及若干查询 C/D，求 C/D 的值。若无法确定则返回 -1。
 *
 * 算法原理：
 * - 有向带权图：变量为节点，A->B 权 k 表示 A/B=k，B->A 权 1/k。
 * - DFS 求路径积：从 C 到 D 的路径上边权相乘即为 C/D。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：建图，equations 中 A,B 与 values 对应，双向边权 k 与 1/k。
 * - 步骤 2：对每个查询 (C,D)，若 C 或 D 不在图中返回 -1；否则 DFS(C,D,1.0,visited)。
 * - 步骤 3：DFS 中若 current==target 返回 value；否则遍历邻居，未访问则递归 value*edgeValue。
 *
 * 关键洞察：双向边保证可沿任意方向传递比值；visited 避免环。
 *
 * 时间复杂度：O(E + Q×(V+E))，E 为等式数，Q 为查询数，V 为变量数
 * 空间复杂度：O(V+E)
 *
 * 示例：equations=[["a","b"],["b","c"]], values=[2,3], queries=[["a","c"]] → [6]
 */
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

            if (!graph.containsKey(C) || !graph.containsKey(D)) {
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
