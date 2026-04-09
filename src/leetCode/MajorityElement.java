package leetCode;

import java.util.Arrays;

/**
 * LC169 多数元素 - Boyer-Moore 投票算法示例
 *
 * 题目（概要）：找出数组中出现次数超过 n/2 的元素（保证存在）。
 *
 * 解法说明：候选+计数，相同+1、不同-1，计数为0换候选。另一解法：排序后取中位数。
 */
public class MajorityElement {

    /** Boyer-Moore 投票，O(n) 时间 O(1) 空间 */
    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }


    /** 排序取中位数，O(n log n) */
    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
