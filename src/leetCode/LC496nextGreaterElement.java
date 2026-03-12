package leetCode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * LC496 - 下一个更大元素 I
 *
 * 题目（概要）：nums1 是 nums2 的子集。对于 nums1 中每个元素，在 nums2 中找到其右侧第一个更大的元素，若不存在则为 -1。
 *
 * 解法说明：
 * - 核心思想：单调栈。对 nums2 从左到右遍历，栈维护单调递减下标序列；当当前数大于栈顶时，栈顶的「下一个更大」即为当前数，弹出并记录。
 * - 用 nextMap 记录每个数的下一个更大值，最后按 nums1 顺序查表。
 *
 * 时间复杂度：O(n + m)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - nums2 中可能无下一个更大元素，用 getOrDefault(key, -1)
 *
 * 示例：nums1=[4,1,2], nums2=[1,3,4,2] → [-1,3,-1]
 */
public class LC496nextGreaterElement {

    /**
     * 单调栈求 nums2 中每个数的下一个更大元素，再映射到 nums1
     *
     * 关键点：栈单调递减，当前数 > 栈顶 时栈顶找到答案并出栈
     *
     * @param nums1 查询数组
     * @param nums2 参考数组（nums1 为其子集）
     * @return nums1 中各元素在 nums2 中的下一个更大元素
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextMap = new HashMap<>();

        Deque<Integer> stack = new LinkedList<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                nextMap.put(stack.pop(), num);
            }
            stack.push(num);
        }

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = nextMap.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }
}
