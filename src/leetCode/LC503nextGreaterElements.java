package leetCode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LC503nextGreaterElements {

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;

        int[] res = new int[n];
        Arrays.fill(res, -1);

        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < 2*n; i++) {
            while(!stack.isEmpty() && nums[i % n] > nums[stack.peek()]) {
                res[stack.pop()] = nums[i % n];
            }
            stack.push(i % n);
        }

        return res;
    }


}
