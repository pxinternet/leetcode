package leetCode;

import javafx.scene.layout.Priority;

import java.util.Arrays;

public class LC16threeSumClosest {

    public int threeSumClosest(int[] nums, int target) {
        int minGap = Integer.MAX_VALUE;
        Arrays.sort(nums);
        int result = 0;


        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                int gap = Math.abs(sum - target);

                if (gap < minGap) {
                    minGap = gap;
                    result = sum;
                }

                if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }


        return result;

    }
}
