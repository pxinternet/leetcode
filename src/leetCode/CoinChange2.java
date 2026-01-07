package leetCode;

/**
 * CoinChange2 - 计算不同硬币组合的数量（零钱兑换 II）
 *
 * 题目（概要）：给定一个目标金额 amount 和若干种面额 coins，求有多少种组合可以凑出金额 amount。
 * 每种面额的硬币数量无限，且组合中硬币顺序不计（即 [1,2] 与 [2,1] 视为同一种组合）。
 *
 * 解法（动态规划 - 一维滚动数组）说明：
 * - 定义状态：dp[j] 表示凑出金额 j 的组合数（注意是组合而非排列）。
 * - 初始状态：dp[0] = 1，表示凑出 0 元有 1 种方案（什么都不取）。
 * - 状态转移（按每种硬币枚举）：当枚举到某个硬币 coin 时，
 *   对于 j 从 coin 到 amount：dp[j] += dp[j - coin]
 *   含义：把此前已经能凑出 j-coin 的每一种方案，加上一个 coin，就构成了新的凑出 j 的方案。
 * - 为什么先枚举硬币再枚举金额可以统计“组合”而不是“排列”？
 *   - 先按 coin 外层，内层按金额递增进行累加，保证了对于不同硬币的选择，顺序不会被重复计数。
 *   - 若把循环顺序反过来（先金额再硬币），会统计到不同顺序的组合（即排列），这不是本题要求。
 *
 * 时间复杂度：O(amount * number_of_coins)
 * 空间复杂度：O(amount)（一维数组）
 *
 * 边界与注意事项：
 * - amount = 0 时，返回 1（空集合是一种合法组合）。
 * - coins 为空且 amount > 0 时，返回 0。
 * - 返回值为 int，题目保证结果在 int 范围内（如存在更大需求可考虑 long 或 BigInteger）。
 *
 * 示例： amount = 5, coins = [1,2,5]
 * - 方案有： [1x5], [1x3 + 2x1], [1x1 + 2x2], [5x1] 总共 4 种。
 */
public class CoinChange2 {

    /**
     * 返回可以凑出 amount 的不同组合数（硬币顺序不计）
     *
     * 关键点逐行说明：
     * - 使用长度为 amount+1 的数组 dp，dp[j] 表示金额 j 的组合数
     * - dp[0] = 1：凑出 0 的方法只有 "不选任何硬币"
     * - 外层按 coin 遍历以保证组合的无序性，内层按金额从 coin 到 amount 递增
     */
    public int change(int amount, int[] coins) {

        int[] dp = new int[amount + 1];

        // base case：凑出 0 元有 1 种方案（什么都不取）
        dp[0] = 1;

        // 按硬币枚举（外层）确保每个组合只计数一次
        for (int coin : coins) {
            // 对于当前面额 coin，更新所有大于等于 coin 的金额
            // 从 coin 开始而不是从 1 开始，是因为 j-coin 必须为非负
            for (int j = coin; j <= amount; j++) {
                // 核心转移：针对每一种已经能组成 j-coin 的方案，加上当前 coin，可组成 j
                dp[j] += dp[j - coin];
            }

        }

        // dp[amount] 即为所求：凑成 amount 的不同组合数
        return dp[amount];
    }

}
