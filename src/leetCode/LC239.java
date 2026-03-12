package leetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LC239 - 滑动窗口最大值
 *
 * 题目（概要）：给定数组 nums 和窗口大小 k，求每个长度为 k 的滑动窗口中的最大值。
 *
 * 解法说明：
 * - 解法一（单调队列）：双端队列存下标，队首到队尾对应值单调减。当前数入队前弹出队尾小于它的；队首超出窗口时弹出；窗口形成时队首即为最大值。
 * - 解法二（分块+前后缀）：按 k 分块，prefixMax[i] 为块内到 i 的最大值，suffixMax[i] 为 i 到块末的最大值；窗口 [i,i+k-1] 的最大值 = max(suffixMax[i], prefixMax[i+k-1])。
 *
 * 时间复杂度：单调队列 O(n)；分块 O(n)
 * 空间复杂度：O(k) / O(n)
 *
 * 示例：nums=[1,3,-1,-3,5,3,6,7], k=3 → [3,3,5,5,6,7]
 */
public class LC239 {

    /**
     * 单调队列：队首为窗口内最大值下标，队内下标对应值单调减
     *
     * 关键点：peek 过期时 poll；队尾小于当前则 pollLast（不可能再成为后续窗口最大值）
     */
    public int[] maxSlidingWindoe(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }

    /**
     * 分块 + 前后缀最大值
     *
     * 关键点：窗口可能跨两块，max(suffixMax[i], prefixMax[i+k-1]) 覆盖跨块情况
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] prefixMax = new int[n];
        int[] suffixMax = new int[n];

        for (int i = 0; i < n; i++) {
            if (i % k == 0) {
                prefixMax[i] = nums[i];
            } else {
                prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1 || (i + 1) % k == 0) {
                suffixMax[i] = nums[i];
            } else {
                suffixMax[i] = Math.max(suffixMax[i + 1], nums[i]);
            }
        }

        int[] ans = new int[n - k + 1];
        for (int i = 0; i <= n - k; i++) {
            ans[i] = Math.max(suffixMax[i], prefixMax[i + k - 1]);
        }
        return ans;
    }
}
