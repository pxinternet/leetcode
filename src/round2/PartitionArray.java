package round2;

import java.util.Arrays;

/**
 * PartitionArray - 划分数组使两子集和差最小
 *
 * 题目（概要）：将数组划分为两个子集，使两子集和的差的绝对值最小。求该最小差。
 *
 * 算法原理：
 * - 0/1 背包思想：求是否存在和为 j 的子集，dp[j] 表示能否凑出和 j。
 * - 最优划分：一个子集和越接近 totalSum/2，两子集差越小；目标为最接近 totalSum/2 的可达和。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：totalSum=数组和，target=totalSum/2；dp[0..target] 布尔数组。
 * - 步骤 2：dp[0]=true；对每个 num，从 target 到 num 逆序更新 dp[i]=dp[i]||dp[i-num]。
 * - 步骤 3：从 target 向下找最大的 i 使 dp[i]=true，sumA=i，sumB=totalSum-i，返回 |sumA-sumB|。
 *
 * 关键洞察：逆序更新避免同一元素多次使用；target 取半可保证最优解在 [0,target] 内。
 *
 * 时间复杂度：O(n × target)
 * 空间复杂度：O(target)
 *
 * 示例：[1,2,3,4,5] → 最小差 1（如 {1,2,4} 与 {3,5}）
 */
public class PartitionArray {

    public int minDifferencePartition(int[] nums) {
        int totalSum = Arrays.stream(nums).sum();
        int target = totalSum / 2;

        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        for (int i = target; i >= 0; i--) {
            if (dp[i]) {
                int sumA = i;
                int sumB = totalSum - i;
                return Math.abs(sumA - sumB);
            }
        }
        return -1;
    }

}
