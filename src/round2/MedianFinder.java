package round2;

import java.util.Random;

public class MedianFinder {

    public static double findMedian(int[] nums) {

        int n = nums.length;

        if (n % 2 == 1) {
            return quickSelect(nums, 0, n - 1, n / 2);
        } else {
            return (quickSelect(nums, 0, n - 1, n / 2 - 1) + quickSelect(nums, 0, n - 1, n / 2)) / 2.0;
        }

    }

    private static int quickSelect(int[] nums, int left, int right, int k) {

        if (left == right) {
            return nums[left];
        }

        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);

        pivotIndex = partition(nums, left, right, pivotIndex);

        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {

        int pivotValue = nums[pivotIndex];

        swap(nums, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }
        swap(nums, storeIndex, right);
        return storeIndex;

    }

    private static int partition2(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];

        swap(nums, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }

        swap(nums, right, storeIndex);
        return storeIndex;
    }

}
