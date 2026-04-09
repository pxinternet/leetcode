package leetCode;

/**
 * LC70climbStairs - 爬楼梯
 *
 * 题目（概要）：假设每次可以爬 1 或 2 个台阶，到达第 n 阶有多少种不同的爬法。
 *
 * 解法说明：
 * - 核心思想：动态规划，f[i] 表示到达第 i 阶的方法数
 * - 到达第 i 阶可从第 i-1 阶跨一步，或从第 i-2 阶跨两步，故 f[i] = f[i-1] + f[i-2]
 * - 初始：f[1]=1（1 阶一种），f[2]=2（2 阶两种：1+1 或 2）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，可优化为 O(1) 滚动变量
 *
 * 边界与注意事项：
 * - n=1 返回 1，n=2 返回 2
 *
 * 示例：n=3 → 3（1+1+1, 1+2, 2+1）
 */
public class LC70climbStairs {

    /**
     * 计算到达第 n 阶的不同爬法数量
     *
     * @param n 台阶总数，n >= 1
     * @return 不同爬法数量
     */
    public int climbStairs(int n) {
        // n=1 时 1 种，n=2 时 2 种
        if (n < 3) return n;

        // f[i] 表示到达第 i 阶的方法数
        int[] f = new int[n + 1];
        f[1] = 1;
        f[2] = 2;
        for (int i = 3; i <= n; i++) {
            // 第 i 阶可从 i-1 跨 1 步或从 i-2 跨 2 步到达
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }
}
