package leetCode;

import java.util.Arrays;

/**
 * LC403 - 青蛙过河
 *
 * 题目（概要）：青蛙从位置 0 出发，第 1 步跳 1 单位。若上步跳 k 单位，则下一步可跳 k-1、k 或 k+1。
 * 给定石头位置数组 stones（升序），判断青蛙能否到达最后一块石头。
 *
 * 解法一（记忆化 DFS - canCross）：
 * - 状态 (i, lastDis)：当前在 stones[i]，上步跳了 lastDis 单位
 * - 递归尝试 lastDis-1、lastDis、lastDis+1 三种步长，用二分查找下一块石头
 *
 * 解法二（DP - canCrossdp）：
 * - dp[i][k] 表示能否从某位置以步长 k 跳到 stones[i]
 * - 若 stones[i]-stones[j]=k 且 dp[j][k-1]||dp[j][k]||dp[j][k+1] 为真，则 dp[i][k]=true
 *
 * 时间复杂度：O(n^2)（DFS 状态数 × 二分）
 * 空间复杂度：O(n^2)
 *
 * 示例：stones=[0,1,3,5,6,8,12,17] → true
 */
public class LC403 {

    private Boolean[][] rec;

    /**
     * 记忆化 DFS：从 stones[0] 出发，上步跳 0（第一步跳 1）
     *
     * @param stones 石头位置数组（升序）
     * @return 能否到达最后一块石头
     */
    public boolean canCross(int[] stones) {
        int n = stones.length;
        rec = new Boolean[n][n];
        return dfs(stones, 0, 0);
    }

    /**
     * DFS：当前在 stones[i]，上步跳了 lastDis，能否到达终点
     *
     * 关键点：下一步只能跳 lastDis-1、lastDis、lastDis+1；用二分查 stones 中是否存在对应位置
     */
    private boolean dfs(int[] stones, int i, int lastDis) {
        if (i == stones.length - 1) {
            return true;
        }

        if (rec[i][lastDis] != null) {
            return rec[i][lastDis];
        }

        for (int curDis = lastDis - 1; curDis <= lastDis + 1; curDis++) {
            if (curDis > 0) {  // 步长必须为正，否则无法前进
                int j = Arrays.binarySearch(stones, i + 1, stones.length, curDis + stones[i]);
                if (j >= 0 && dfs(stones, j, curDis)) {
                    return rec[i][lastDis] = true;
                }
            }
        }

        return rec[i][lastDis] = false;
    }

    /**
     * DP 解法
     */
    public boolean canCrossdp(int[] stones) {
        int n = stones.length;
        boolean[][] dp = new boolean[n][n];
        dp[0][0] = true;

        for (int i = 1; i < n; i++) {
            if (stones[i] - stones[i - 1] > i) {
                return false;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int k = stones[i] - stones[j];
                if (k > j + 1) {
                    break;
                }
                dp[i][k] = dp[j][k - 1] || dp[j][k] || dp[j][k + 1];
                if (i == n - 1 && dp[i][k]) {
                    return true;
                }
            }
        }
        return false;
    }
}
