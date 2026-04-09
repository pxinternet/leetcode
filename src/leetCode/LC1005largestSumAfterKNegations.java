package leetCode;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * LC1005 - K 次取反后最大化的数组和
 *
 * 题目（概要）：可多次选择元素取反（每次算一次操作），最多 k 次，求最大数组和。
 *
 * 解法说明：按绝对值降序排序，优先对负数取反；k 有剩余且为奇则在最小绝对值上再取反一次。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 */
public class LC1005largestSumAfterKNegations {

    /** 最多 k 次取反，返回最大数组和 */
    public int largestSumAfterKNegations(int[] nums, int k) {
        nums = IntStream.of(nums)
                .boxed()
                .sorted((o1, o2) -> Math.abs(o2) - Math.abs(o1))
                .mapToInt(Integer::intValue)
                .toArray();

        int len = nums.length;

        for (int i = 0; i < len; i++) {
            if (nums[i] < 0 && k > 0) {
                nums[i] = -nums[i];
                k--;
            }
        }

        if ( k % 2 == 1) nums[len - 1] = -nums[len - 1];

        return Arrays.stream(nums).sum();
    }


}
