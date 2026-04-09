package leetCode;

/**
 * LC189 - 轮转数组
 *
 * 题目（概要）：将数组 nums 向右轮转 k 个位置。要求 O(1) 额外空间（原地）。
 *
 * 解法说明：
 * - 核心思想：三次反转。向右轮转 k 等价于先反转前 n-k、再反转后 k、最后整体反转；
 *   等价地，k = n - k%n 表示「向左移 k%n」的偏移，然后 reverse(0,k-1)、reverse(k,n-1)、reverse(0,n-1)。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - k 可能大于 n，故用 k % n；k=0 时不变
 *
 * 示例：nums=[1,2,3,4,5,6,7], k=3 → [5,6,7,1,2,3,4]
 */
public class LC189rotate {

    /**
     * 三次反转实现原地轮转
     *
     * 关键点：k=n-k%n 将「右移 k」转为「左移」的等价操作，便于用反转实现
     *
     * @param nums 待轮转数组
     * @param k    右移位数
     */
    public void rotate(int[] nums, int k) {
        int n = nums.length;

        k = k % n;
        if (k == 0) return;
        k = n - k;   // 转为「左移」等效偏移

        reverse(nums, 0, k - 1);
        reverse(nums, k, n -1);
        reverse(nums, 0, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        int n = nums.length;
        if (start < 0 || end >= n) {
            return;
        }
        while (end > start) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            end--;
            start++;
        }
    }
}
