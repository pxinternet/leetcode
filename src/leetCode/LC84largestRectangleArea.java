package leetCode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * LC84largestRectangleArea - 柱状图中最大的矩形
 *
 * 题目（概要）：给定非负整数数组 heights 表示柱状图每根柱的高度，求其中能勾勒出的最大矩形面积。
 *
 * 解法说明：
 * - 单调栈：维护单调递增栈。对每个位置 i，left[i] 为左侧第一个比 heights[i] 小的下标，right[i] 为右侧第一个比其小的下标
 * - 以 heights[i] 为高的矩形宽度 = right[i] - left[i] - 1
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：heights=[2,1,5,6,2,3] → 10（高度 5 和 6 的矩形 2*5=10）
 */
public class LC84largestRectangleArea {

    /**
     * 单调栈求每根柱向两侧扩展的边界
     *
     * @param heights 每根柱的高度
     * @return 最大矩形面积
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);
        Deque<Integer> mono_stack = new LinkedList<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                right[mono_stack.peek()] = i;
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }
        Tools.printArray("left ", left);
        Tools.printArray("height", heights);
        Tools.printArray("right", right);
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

    public static void main(String[] args) {

        LC84largestRectangleArea lc84largestRectangleArea = new LC84largestRectangleArea();
        int[] heights = new int[]{2,1,5,6,2,3};
        lc84largestRectangleArea.largestRectangleArea(heights);

    }




}
