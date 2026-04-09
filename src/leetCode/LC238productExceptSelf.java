package leetCode;

/**
 * LC238productExceptSelf - 除自身以外数组的乘积
 *
 * 题目（概要）：给定数组 nums，返回数组 answer，其中 answer[i] 等于 nums 中除 nums[i] 外所有元素的乘积。
 * 不能使用除法，时间复杂度需 O(n)。
 *
 * 解法说明：
 * - 核心思想：answer[i] = (nums[0]*...*nums[i-1]) * (nums[i+1]*...*nums[n-1])
 * - 方法一：用 left[i]、right[i] 分别存左右前缀积，ans[i]=left[i]*right[i]
 * - 方法二（Space）：用 ans 存左前缀积，再一趟从右往左乘右前缀，O(1) 额外空间
 *
 * 时间复杂度：O(n)
 * 空间复杂度：方法一 O(n)，方法二 O(1)（不计返回数组）
 *
 * 示例：nums = [1,2,3,4] → [24,12,8,6]
 */
public class LC238productExceptSelf {

    /**
     * 使用左右前缀积数组
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int[] left = new int[n];
        int[] right = new int[n];

        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < n; i++) {
            ans[i] = left[i] * right[i];
        }
        return ans;
    }

    /**
     * O(1) 额外空间：ans 先存左前缀积，再从右往左乘右前缀
     */
    public int[] productExceptSelfSpace(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        ans[0] = 1;

        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            ans[i] =right * ans[i];
            right *= nums[i];
        }
        return ans;
    }

}
