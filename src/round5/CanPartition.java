package round5;

/**
 * 子集划分（Partition Equal Subset Sum）问题的解法实现
 * 题目描述（简要）：给定一个正整数数组 nums，判断是否可以将数组划分为两个子集，使得两个子集的和相等。
 *
 * 主要思想：将问题转换为 0/1 背包的子集和问题
 * - 令 sum 为数组所有元素和。如果 sum 为奇数，则不能等分，直接返回 false。
 * - 目标子集和 target = sum / 2。问题等价于：是否存在数组的一个子集，其和恰好等于 target。
 * - 使用一维布尔 DP 数组 dp[j] 表示是否存在子集和为 j（0 <= j <= target）。
 * - 初始化 dp[0] = true（空集可以组成和为 0）。
 * - 对于每个数 num，逆序遍历 j 从 target 到 num：dp[j] = dp[j] || dp[j - num]
 *   逆序遍历可以保证每个数只被使用一次（0/1 背包），而不是无限次使用（完全背包）。
 *
 * 时间复杂度：O(n * target)，空间复杂度：O(target)
 *
 * 下面的注释较为详细，行级说明了每一步的含义与正确性。
 */
public class CanPartition {

    /**
     * 判断是否可以将数组划分为两个和相等的子集
     * 注意：方法名保留为 "CanPartition"（首字母大写）以匹配原文件中的签名，
     * 但按 Java 习惯建议改为小写开头的 "canPartition"。
     *
     * @param nums 非空或可空的整数数组（元素为非负整数）
     * @return 如果可以划分返回 true，否则返回 false
     */
    public boolean CanPartition(int[] nums) {
        // 1. 计算数组元素之和 sum
        int sum = 0;
        for (int num : nums) {
            sum += num; // 将每个数累加到 sum
        }

        // 2. 如果 sum 是奇数，则不可能分为两个相等的子集，直接返回 false
        if (sum % 2 != 0) {
            return false;
        }

        // 3. 目标子集和 target，即其中一个子集应达到的和
        int target = sum / 2;

        // 4. dp[j] 表示是否存在某个子集，其和恰好为 j（0 <= j <= target）
        //    使用 boolean 数组节省空间；默认初始值为 false
        boolean[] dp = new boolean[target + 1];

        // 5. 空集可以组成和为 0，因此 dp[0] = true
        dp[0] = true;

        /*
         * 从这里开始是关键循环（逐行解释）
         *
         * for (int num : nums) {
         *     for (int j = target; j >= num; j--) {
         *         dp[j] = dp[j] || dp[j - num];
         *     }
         * }
         *
         * 每一行的含义：
         * - 外层 for (int num : nums)
         *   表示把数组中的每个元素作为当前要考虑是否加入子集的候选元素。
         *   每一轮外循环的完成应当将 dp 更新为“考虑到当前这个元素为止”的可达状态。
         *
         * - 内层 for (int j = target; j >= num; j--)
         *   枚举所有可能的子集和 j，从目标值 target 开始向下到 num（包括 num）。
         *   只枚举 j >= num 是因为只有在 j >= num 时，当前元素 num 才有可能用于构成 j。
         *   注意：这里使用的是逆序（从大到小），这是关键点，下面详细解释为什么。
         *
         * - dp[j] = dp[j] || dp[j - num]
         *   表示在处理当前元素后，是否能组成和 j：
         *     要么已经能组成 j（dp[j] 为 true），
         *     要么之前能组成 j - num（dp[j - num] 为 true），再加上当前元素 num 就能得到 j。
         *
         * 为什么必须逆序（从 target 向下）遍历 j？
         * ------------------------------------------
         * 目标是实现 0/1 背包的「每个元素只能被使用一次」的语义。
         * 当我们在同一外循环（处理同一个 num）中更新 dp 时，必须保证读取到的 dp[j - num]
         * 是在加入当前 num 之前的状态（即上一轮外循环的状态）。如果内层使用正序（从小到大）
         * 遍历 j，则当我们计算较大的 j 时，dp[j - num] 可能已经在当前外循环轮次中被更新为 true（
         * 基于同一 num），从而导致把当前 num 重复计入多次（等价于无限使用当前 num），这会把问题
         * 退化为完全背包（unbounded knapsack），产生错误结果。
         *
         * 直观反例（说明正序会出错）：
         * - 假设 nums = {2}，target = 4（我们想验证子集是否能组成和 4）。
         * - 初始 dp: [dp[0]=true, dp[1]=false, dp[2]=false, dp[3]=false, dp[4]=false]
         * - 若使用正序（j 从 2 到 4）：
         *     j=2: dp[2] |= dp[0] -> dp[2] = true
         *     j=3: dp[3] |= dp[1] -> dp[3] = false
         *     j=4: dp[4] |= dp[2] -> 这里读取到的 dp[2] 已经在当前轮次被设置为 true（基于同一个 num），
         *           所以 dp[4] 会被错误地置为 true —— 但实际上只有一个 2，不能构成 4。
         *   因此正序会错误地允许重复使用同一个元素。
         *
         * 逆序（正确）演示同一例子：
         * - 初始 dp 同上
         * - 逆序 j 从 4 到 2：
         *     j=4: dp[4] |= dp[2] (旧的 dp[2] 为 false) -> dp[4] = false
         *     j=3: dp[3] |= dp[1] (旧的 dp[1] 为 false) -> dp[3] = false
         *     j=2: dp[2] |= dp[0] -> dp[2] = true
         *   处理结束后 dp[4] 仍为 false，正确地表示不能用单个 2 得到 4。
         *
         * 一个带两个元素的示例（说明逆序是如何构造组合的）：
         * - nums = [2, 3]，target = 5，目标是检测是否能组成和 5。
         * - 初始 dp: [T, F, F, F, F, F]
         * - 处理 num=2（逆序 j=5..2）：会把 dp[2] 置为 true，其他保持 false -> [T,F,T,F,F,F]
         * - 处理 num=3（逆序 j=5..3）：读取到 dp[2]=true（这是上一轮的结果），因此 dp[5] |= dp[2] -> dp[5]=true，表示 2+3=5。
         *
         * 形式化（归纳）证明思路：
         * - 定义不变量：令 dp^{(i)}[j] 表示考虑完前 i 个元素后能否构成和 j（i 从 0 开始）。
         * - 归纳基础：i=0 时，只有 dp^{(0)}[0] = true，其余 false（空集合）。
         * - 归纳步：假设在开始处理第 i 个元素（数值 num_i）之前，数组 dp 表示 dp^{(i-1)}（仅使用前 i-1 个元素的可达性）。
         *   当我们按 j 从 target 到 num_i 的顺序更新时，计算 dp[j] = dp[j] || dp[j - num_i] 中的 dp[j - num_i]
         *   对于任何 j，j - num_i < j，因此在逆序下 dp[j - num_i] 在本轮更新中尚未被修改，仍然保持 dp^{(i-1)}[j - num_i]。
         *   因此更新等价于：dp^{(i)}[j] = dp^{(i-1)}[j] || dp^{(i-1)}[j - num_i]，这正是 0/1 背包的状态转移。
         * - 由归纳可得：在处理完第 i 个元素后，dp 等于 dp^{(i)}，于是当处理完所有元素后 dp 等于 dp^{(n)}，
         *   dp[target] 正确反映是否存在子集和为 target。
         *
         * 实用注意点：
         * - 如果 num > target，则内层循环不会执行任何一次（因为 j >= num 条件无法满足），即该元素被跳过。
         * - dp 的长度为 target+1，且必须初始化 dp[0]=true，否则无法从空集出发累加。
         * - 该逆序技巧是 0/1 背包的一维空间压缩标准方法，既保证正确性又节省空间。
         */
        for (int num : nums) {
            // 如果 num 大于 target，则不可能用于构成 j <= target 的子集和
            // 因此 j 从 target 开始，如果 j < num，则循环会被跳过
            for (int j = target; j >= num; j--) {
                // 关键转移：要么原来能组成和 j（dp[j] 为 true），
                // 要么能通过添加当前元素 num，从以前能组成的和 j - num 转移过来（dp[j - num] 为 true）
                // 一旦 dp[target] 变为 true，就表示存在一个子集和为 target，问题有解
                dp[j] = dp[j] || dp[j - num];
            }
        }

        // 7. 返回 dp[target]，表示是否存在子集和为 target
        return dp[target];
    }

    /**
     * 简单的 main 测试入口，包含若干示例，便于本文件独立运行验证
     * 示例包括：典型的有解/无解/边界情况
     */
    public static void main(String[] args) {
        CanPartition solver = new CanPartition();

        // 示例 1：有解
        int[] nums1 = {1, 5, 11, 5};
        // 解释：可以分成 {11} 与 {1,5,5}，两边和均为 11
        System.out.println("nums1 = {1,5,11,5} -> " + solver.CanPartition(nums1)); // 期望 true

        // 示例 2：无解
        int[] nums2 = {1, 2, 3, 5};
        // 解释：总和为 11，不是偶数，因此不可能等分
        System.out.println("nums2 = {1,2,3,5} -> " + solver.CanPartition(nums2)); // 期望 false

        // 示例 3：边界，空数组（视作可以分为两个空集，和均为 0）
        int[] nums3 = {};
        System.out.println("nums3 = {} -> " + solver.CanPartition(nums3)); // 期望 true

        // 示例 4：单个元素
        int[] nums4 = {2};
        // 总和为 2，target = 1，不存在子集和为 1
        System.out.println("nums4 = {2} -> " + solver.CanPartition(nums4)); // 期望 false

        // 示例 5：重复元素组合
        int[] nums5 = {2, 2, 3, 5};
        // 总和为 12，target = 6，存在子集 {2,3,1}? 这里实际无 1，但 {2,2,2} 也不存在，期望 false
        System.out.println("nums5 = {2,2,3,5} -> " + solver.CanPartition(nums5)); // 期望 false

        // 示例 6：更多示例，能被划分
        int[] nums6 = {3, 3, 3, 4, 5};
        // 总和 18，target = 9，可以分为 {4,5} 与 {3,3,3}
        System.out.println("nums6 = {3,3,3,4,5} -> " + solver.CanPartition(nums6)); // 期望 true
    }

}
