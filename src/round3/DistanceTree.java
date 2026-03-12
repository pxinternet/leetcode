package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DistanceTree - 树中所有节点到其他节点的距离和（LeetCode 834）
 *
 * 题目（概要）：n 个节点的树，求每个节点到其他所有节点的距离之和，返回 answer[0..n-1]。
 *
 * 算法原理：
 * - 换根 DP：先以 0 为根 DFS 求 count（子树节点数）、answer[0]；再 DFS2 从父传子，利用 answer[parent] 推导 answer[child]。
 * - answer[child]=answer[parent]-count[child]+(n-count[child])：从父到子，child 子树内节点少走 1，子树外多走 1。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：建树，dfs(0,-1) 求 count、answer（基于子树）。
 * - 步骤 2：dfs2(0,-1) 换根：answer[neighbor]=answer[node]-count[neighbor]+(n-count[neighbor])。
 *
 * 关键洞察：换根公式 O(1) 转移；count 为子树大小。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：n=2, edges=[[0,1]] → answer=[1,1]
 */
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
