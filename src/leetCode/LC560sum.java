package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC560sum - 和为 K 的子数组
 *
 * 题目（概要）：给定整数数组 nums 和整数 k，返回连续子数组中元素和等于 k 的子数组数量。
 *
 * 解法说明：
 * - 核心思想：前缀和 + 哈希表。pre[i] 表示 nums[0..i] 的和，则 nums[j..i] 的和 = pre[i] - pre[j-1]
 * - 要使 nums[j..i] = k，即 pre[i] - pre[j-1] = k，即 pre[j-1] = pre[i] - k
 * - 用 map 记录每个前缀和出现的次数，遍历时累加 map.get(pre - k)
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - map 初始 put(0, 1)，表示前缀和为 0 出现 1 次（空前缀，用于子数组从 0 开始的情况）
 *
 * 示例：nums=[1,-1,0], k=0 → 3（[1,-1],[0],[1,-1,0]）
 */
public class LC560sum {

    /**
     * 返回和为 k 的连续子数组数量
     *
     * 关键点逐行说明：
     * - pre 为当前前缀和，即 nums[0..i] 的和
     * - pre - k 若在 map 中存在，说明存在 j 使得 nums[j..i] 的和为 k
     * - map 记录每个前缀和出现的次数，因为可能有多个位置的前缀和相同
     *
     * @param nums 整数数组
     * @param k    目标和
     * @return 和为 k 的子数组数量
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int pre = 0;
        Map<Integer, Integer> map = new HashMap<>();
        // 前缀和为 0 出现 1 次，用于子数组从索引 0 开始的情况
        // 例如：nums=[1,-1,0], k=0，当 i=2 时 pre=0，需要 count += map.get(0-0)=1
        map.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            // 若存在前缀和 pre-k，则存在以 i 结尾、和为 k 的子数组
            if (map.containsKey(pre - k)) {
                count += map.get(pre - k);
            }
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        LC560sum lC560sum = new LC560sum();
        int[] nums = new int[]{1, -1, 0};
        int res = lC560sum.subarraySum(nums, 0);
        System.out.println(res);
    }
}
