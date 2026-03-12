package leetCode;

/**
 * LC209 - 长度最小的子数组（注：类名为 LC207，题为 209）
 *
 * 题目概要：找和 >= target 的最短连续子数组长度。
 *
 * 解法说明：滑动窗口。右指针扩展，当和 >= target 时收缩左指针并更新最短长度。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC207minSubArrayLen {

    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int left = 0, right = 0;
        int n = nums.length;
        int ans = n + 1;
        int tmp = nums[0];

        while (right < n) {
            if (tmp >= target) {
                ans = Math.min(ans, right - left + 1);
                if (ans == 1) return 1;
                tmp -= nums[left];
                left++;
            } else {
                right++;
                if (right < n) tmp += nums[right];
            }
        }
        return ans > n ? 0 : ans;
    }

    public static void main(String[] args) {
        LC207minSubArrayLen lc207minSubArrayLen = new LC207minSubArrayLen();

        int[] nums = new int[]{2,3,1,2,4,3};
        int target = 7;

        int res = lc207minSubArrayLen.minSubArrayLen(7, nums);

        System.out.println(res);
    }

}
