package leetCode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LC496nextGreaterElement {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        //记录nums2的下一个更大元素
        Map<Integer, Integer> nextMap = new HashMap<>();

        //单调递减栈
        Deque<Integer> stack = new LinkedList<>();
        for(int num : nums2) {
            while(!stack.isEmpty() && num > stack.peek()) {
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
