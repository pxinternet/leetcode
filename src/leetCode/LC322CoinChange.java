package leetCode;

import java.util.Arrays;

/**
 * LC322CoinChange - 计算凑出目标金额所需的最少硬币数（零钱兑换）
 *
 * 题目（概要）：给定一个目标金额 amount 和若干种面额 coins，求凑出金额 amount 所需的最少硬币数。
 * 如果无法凑出目标金额，返回 -1。每种面额的硬币数量无限。
 *
 * 解法（动态规划 - 一维滚动数组）说明：
 * - 定义状态：dp[i] 表示凑出金额 i 所需的最少硬币数。
 * - 初始状态：
 * - dp[0] = 0，表示凑出 0 元需要 0 枚硬币（base case）。
 * - dp[i] = amount + 1（i > 0），初始化为一个不可能达到的大值，用于表示"不可达"状态。
 * - 状态转移：对于每个金额 i（从 1 到 amount），遍历所有硬币 coin：
 * - 如果 i - coin >= 0（即可以使用当前硬币），则：
 * dp[i] = min(dp[i], dp[i - coin] + 1)
 * - 含义：凑出金额 i 的最少硬币数 = min(当前已知的最少硬币数, 凑出金额 i-coin 的最少硬币数 + 1)
 * - 为什么先枚举金额再枚举硬币？
 * - 本题求的是"最少硬币数"，不涉及组合/排列的区别，两种循环顺序都可以。
 * - 但先枚举金额再枚举硬币的方式更直观：对于每个目标金额，尝试所有可能的硬币。
 *
 * 时间复杂度：O(amount * number_of_coins)
 * 空间复杂度：O(amount)（一维数组）
 *
 * 边界与注意事项：
 * - amount = 0 时，返回 0（不需要任何硬币）。
 * - 如果 dp[amount] > amount，说明无法凑出目标金额，返回 -1。
 * （因为最多只需要 amount 枚 1 元硬币，如果 dp[amount] > amount，说明初始值 max 未被更新）
 * - coins 为空且 amount > 0 时，返回 -1。
 * - coins 中可能包含大于 amount 的面额，但通过 i - coin >= 0 的判断可以自然过滤。
 *
 * 示例：amount = 11, coins = [1,2,5]
 * - 最优方案：5 + 5 + 1 = 11，需要 3 枚硬币。
 * - 如果 amount = 3, coins = [2]，则无法凑出，返回 -1。
 */
public class LC322CoinChange {

    /**
     * 返回凑出 amount 所需的最少硬币数，如果无法凑出则返回 -1
     *
     * 关键点逐行说明：
     * - 使用长度为 amount+1 的数组 dp，dp[i] 表示金额 i 的最少硬币数
     * - dp[0] = 0：凑出 0 元需要 0 枚硬币（base case）
     * - 其他位置初始化为 max = amount + 1，表示"不可达"状态
     * - 外层按金额从 1 到 amount 遍历，内层遍历所有硬币
     * - 对于每个金额 i，尝试使用每个硬币 coin，更新 dp[i] 为更小的值
     * - 最后检查 dp[amount] 是否被更新过，如果仍为初始值则返回 -1
     *
     * @param coins  硬币面额数组，每种面额数量无限
     * @param amount 目标金额
     * @return 凑出 amount 所需的最少硬币数，无法凑出则返回 -1
     */
    public int coinChange(int[] coins, int amount) {
        // 边界情况：amount = 0 时，不需要任何硬币
        if (amount == 0)
            return 0;

        // max 用于表示"不可达"状态，初始化为一个不可能达到的大值
        // 选择 amount + 1 是因为即使全部使用 1 元硬币，最多也只需要 amount 枚
        int max = amount + 1;
        int n = coins.length;

        // dp[i] 表示凑出金额 i 所需的最少硬币数
        int[] dp = new int[amount + 1];

        // 初始化：除了 dp[0] = 0 外，其他位置都设为 max（表示不可达）
        Arrays.fill(dp, max);

        // base case：凑出 0 元需要 0 枚硬币
        dp[0] = 0;

        // 外层循环：按金额从 1 到 amount 逐个计算
        for (int i = 1; i <= amount; i++) {
            // 内层循环：尝试使用每种面额的硬币
            for (int coin : coins) {
                // 只有当 i >= coin 时，才能使用当前硬币
                if (i - coin >= 0) {
                    // 状态转移：dp[i] = min(当前已知的最少硬币数, 使用当前硬币后的硬币数)
                    // dp[i - coin] + 1 表示：先凑出 i-coin 的最少硬币数，再加上一枚 coin 面额的硬币
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        // 如果 dp[amount] 仍为初始值 max，说明无法凑出目标金额，返回 -1
        // 否则返回 dp[amount]（即凑出 amount 所需的最少硬币数）
        return dp[amount] > amount ? -1 : dp[amount];
    }

}
