package leetCode;

import com.sun.scenario.effect.impl.state.LinearConvolveKernel;

import java.util.Deque;
import java.util.LinkedList;

public class LC581findUnsortedSubarray {


    //两次遍历,什么时候出栈，谁出栈的问题
    public int findUnsortedSubarray(int[] nums) {
        int left = nums.length;
        int right = 0;

        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                left = Math.min(left, stack.pop());
            }

            stack.push(i);
        }
        stack.clear();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                right = Math.max(right, stack.pop());
            }
            stack.push(i);
        }
        return left > right ? 0 : right - left + 1;
    }

    public static void main(String[] args) {
        LC581findUnsortedSubarray lc581findUnsortedSubarray = new LC581findUnsortedSubarray();
        int[] nums = new int[]{2,6,4,8,10,9,15};

        int res = lc581findUnsortedSubarray.findUnsortedSubarray(nums);
        System.out.println(res);
    }
}
