package round3;

public class RotateSearch {

    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] < nums[mid]) {
                if (target >= nums[mid] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }


    public int findMin2(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                right--;
            }
        }
        return nums[left];
    }

    public int findFLInSequence(int[] nums, int start, int end, int target) {

        int left = start, right = end;

        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                result = nums[mid];
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;

    }

    public int findFL(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;

        if (target < nums[0] && target >= nums[right]) {
            return nums[0];
        }

        if (nums[0] > nums[right]) {

            while (left < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] > nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            if (target < nums[left]) {
                return nums[left];
            }

            if (target >= nums[left] && target < nums[nums.length - 1]) {
                return findFLInSequence(nums, left, nums.length - 1, target);
            } else {
                return findFLInSequence(nums, 0, left - 1, target);
            }
        } else {
            return findFLInSequence(nums, 0, right, target);
        }
    }
}
