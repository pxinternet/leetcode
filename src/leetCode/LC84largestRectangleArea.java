package leetCode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LC84largestRectangleArea {

    public int largestRectangleArea(int[] heights) {


        //单调增的栈，所有栈顶元素一定是第一个比小的
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
