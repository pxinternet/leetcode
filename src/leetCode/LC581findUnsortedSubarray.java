package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC581 - 最短无序连续子数组
 *
 * 题目（概要）：给定整数数组 nums，找一段最短的连续子数组，使得将这段子数组排序后，整个数组升序。
 * 返回该子数组的长度。
 *
 * 算法原理：
 * - 无序区间的定义：左边界 = 最左侧「其右侧存在更小元素」的位置；右边界 = 最右侧「其左侧存在更大元素」的位置。
 * - 单调栈作用：从左扫时，栈单调增；nums[i]<栈顶表示当前元素比前面某元素小，前面元素「违反升序」，其下标可能成为左边界。弹栈时 left=min(left, 弹栈下标)。
 * - 从右扫同理：栈单调减；nums[i]>栈顶表示当前元素比后面某元素大，后面元素可能成为右边界。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：从左到右，栈存下标保持单调增。nums[i]<栈顶时弹栈，left=min(left, 弹栈下标)。
 * - 步骤 2：从右到左，栈存下标保持单调减。nums[i]>栈顶时弹栈，right=max(right, 弹栈下标)。
 * - 步骤 3：若 left>right 则已有序返回 0；否则返回 right-left+1。
 *
 * 关键洞察：
 * - 左边界：从左扫时，被 nums[i] 顶出的栈顶对应「前面比当前大的元素」，其下标是左边界候选（因为该位置需要参与重排）。
 * - 右边界：从右扫时，被 nums[i] 顶出的栈顶对应「后面比当前小的元素」，其下标是右边界候选。
 * - 也可用「从左找最右的下降沿、从右找最左的上升沿」的纯遍历方法，但单调栈更清晰地表达「违反顺序」的语义。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：nums=[2,6,4,8,10,9,15] → 5（需排序 [6,4,8,10,9]）
 */
public class LC581findUnsortedSubarray {

    /**
     * 单调栈求无序区间的左右边界
     *
     * 关键点：从左扫找左边界（被更小元素顶出的最小下标）；从右扫找右边界（被更大元素顶出的最大下标）
     *
     * @param nums 整数数组
     * @return 需排序的最短子数组长度
     */
    public int findUnsortedSubarray(int[] nums) {
        int left = nums.length;
        int right = 0;

        Deque<Integer> stack = new LinkedList<>();

        // 从左到右：栈单调增，遇到更小则出栈，left 取最小出栈下标
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                left = Math.min(left, stack.pop());
            }
            stack.push(i);
        }

        stack.clear();

        // 从右到左：栈单调减，遇到更大则出栈，right 取最大出栈下标
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                right = Math.max(right, stack.pop());
            }
            stack.push(i);
        }

        return left > right ? 0 : right - left + 1;
    }

    public static void main(String[] args) {
        LC581findUnsortedSubarray solver = new LC581findUnsortedSubarray();
        int[] nums = new int[]{2,6,4,8,10,9,15};
        System.out.println(solver.findUnsortedSubarray(nums));
    }
}
