package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistanceTree {

    private int[] count;

    private int[] answer;

    private List<List<Integer>> tree;

    public int[] sumOfDistancesInTree(int n, int[][] edges) {

        tree = new ArrayList<>();

        count = new int[n];

        answer = new int[n];

        Arrays.fill(count, 1);

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            tree.get(edge[0]).add(edge[1]);
            tree.get(edge[1]).add(edge[0]);
        }

        dfs(0, -1);

        dfs2(0, -1);

        return answer;
    }

    private void dfs(int node, int parent) {

        for (int neighbor : tree.get(node)) {
            if (neighbor != parent) {
                dfs(neighbor, node);
                count[node] += count[neighbor];
                answer[node] += answer[neighbor] + count[neighbor];
            }
        }
    }

    private void dfs2(int node, int parent) {
        for (int neighbor : tree.get(node)) {
            if (neighbor != parent) {
                answer[neighbor] = answer[node] - count[neighbor] + (count.length - count[neighbor]);
                dfs2(neighbor, node);
            }
        }
    }

}
