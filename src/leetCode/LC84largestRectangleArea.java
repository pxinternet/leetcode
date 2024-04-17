package leetCode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LC84largestRectangleArea {

    public int largestRectangleArea(int[] heights) {


        //单调增的栈，所有栈顶元素一定是第一个比小的
        int n = heights.length;
        int ans = 0;

        Deque<Integer> monoStack = new LinkedList<>();
        monoStack.push(-1);
        for (int i = 0; i < n; i++) {
            System.out.println(" i = " + i);
            while (monoStack.peek() != -1 && heights[monoStack.peek()] >= heights[i]) {
                int pop = monoStack.pop();
                int peek = monoStack.peek();
                int area = heights[pop] * (i - monoStack.peek() - 1);
                System.out.println("pop = " + pop + " peek = " + peek + " area = " + area);
                ans = Math.max(ans, area);
            }
            monoStack.push(i);
        }
        System.out.println("============================================================================");
        while(monoStack.peek() != -1) {
            int pop = monoStack.pop();
            int peek = monoStack.peek();
            int area = heights[pop] * (heights.length - monoStack.peek() - 1);
            System.out.println("pop = " + pop + " peek = " + peek + " area = " + area);
            ans = Math.max(ans, area);
        }
        return ans;

//        int[] left = new int[n];
//        int[] right = new int[n];
//
//        Arrays.fill(right,n);

//        Deque<Integer> monoStack = new LinkedList<>();
//
//        for (int i = 0; i < n; i++) {
//            while(!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
//                right[monoStack.peek()] = i;
//                monoStack.pop();
//            }
//            left[i] = (monoStack.isEmpty() ? - 1: monoStack.peek());
//            monoStack.push(i);
//        }
//        int ans = 0;
//        for (int i = 0; i < n; ++i) {
//            ans = Math.max(ans, (right[i] - left[i] - 1)) * heights[i];
//        }
//        return ans;
    }

    public static void main(String[] args) {

        LC84largestRectangleArea lc84largestRectangleArea = new LC84largestRectangleArea();
        int[] heights = new int[]{2,1,5,6,2,3};
        lc84largestRectangleArea.largestRectangleArea(heights);

    }




}
