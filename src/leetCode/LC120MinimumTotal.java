package leetCode;

import java.util.List;

/**
 * LC120MinimumTotal - 三角形最小路径和
 *
 * 题目（概要）：给定三角形，从顶到底找一条路径使路径上数字和最小。每步可走到下一行相邻位置。
 *
 * 解法说明：dp[i][j] = min(dp[i-1][j], dp[i-1][j-1]) + triangle[i][j]；首尾列单独处理。可滚动数组优化为 O(n)。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2) 或 O(n) 滚动
 *
 * 示例：triangle=[[2],[3,4],[6,5,7],[4,1,8,3]] → 11（2+3+5+1）
 */
public class LC120MinimumTotal {

    /**
     * 二维 DP 求最小路径和
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] dp = new int[n][n];

        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i-1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i-1][j-1]) + triangle.get(i).get(j);
            }
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }

        int minRes = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[n-1][i]);
        }
        return minRes;
    }

    public int minimumTotalON(List<List<Integer>> triangle) {

        int n = triangle.size();

        int[] dp = new int[n];

        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i-1] + triangle.get(i).get(i);

            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j], dp[j-1]) + triangle.get(i).get(j);
            }

            dp[0] = dp[0] + triangle.get(i).get(0);
        }

        int minRes = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[i]);
        }
        return minRes;

    }




}
