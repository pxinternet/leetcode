package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC834 - 树中距离之和
 *
 * 题目（概要）：给定 n 个节点、n-1 条边的无向树，求每个节点到其他所有节点的距离之和。
 *
 * 解法说明：
 * - 核心思想：换根 DP。第一次 DFS 以 0 为根，计算 dp[u]（u 子树内所有点到 u 的距离和）、sz[u]（子树大小）。
 * - 第二次 DFS 换根：从父 u 到子 v 时，将根从 u 换到 v，更新 dp、sz，递归后恢复。
 * - 换根公式：dp[v] += dp[u] - (dp[v]+sz[v]) + (sz[u]-sz[v]) = dp[u] + sz[u] - 2*sz[v]；sz[v] += sz[u] - sz[v]。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：n=6, edges=[[0,1],[0,2],[2,3],[2,4],[2,5]] → [8,12,6,10,10,10]
 */
public class LC834TreeDistanceSum {

    private int[] ans;
    private int[] sz;
    private int[] dp;
    private List<List<Integer>> graph;

    /**
     * 求每个节点到其他所有节点的距离之和
     *
     * @param n     节点数
     * @param edges 边集
     * @return 每节点的距离和数组
     */
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        ans = new int[n];
        sz = new int[n];
        dp = new int[n];
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        dfs(0, -1);
        dfs2(0, -1);

        return ans;
    }

    /** 第一次 DFS：计算以 u 为根时，dp[u]（子树内到 u 的距离和）与 sz[u]（子树大小） */
    private void dfs(int u, int f) {
        sz[u] = 1;
        dp[u] = 0;

        for (int v : graph.get(u)) {
            if (v == f) continue;
            dfs(v, u);
            dp[u] += dp[v] + sz[v];
            sz[u] += sz[v];
        }
    }

    /** 第二次 DFS：换根，将根从 u 换到 v，更新 dp、sz 后递归，再恢复 */
    private void dfs2(int u, int f) {
        ans[u] = dp[u];

        for (int v : graph.get(u)) {
            if (v == f) continue;

            int pu = dp[u], pv = dp[v];
            int su = sz[u], sv = sz[v];

            dp[u] -= dp[v] + sz[v];
            sz[u] -= sz[v];
            dp[v] += dp[u] + sz[u];
            sz[v] += sz[u];

            dfs2(v, u);

            dp[u] = pu;
            dp[v] = pv;
            sz[u] = su;
            sz[v] = sv;
        }
    }
}
