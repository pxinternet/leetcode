package leetCode;

public class MaxValueIndex {

    public static int findPeakElement(int[] nums) {
        int low = 0, high = nusms.length - 1;

        while(low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[mid + 1]) {
                high = mid;
            } else if (nums[mid] < nums[mid + 1]){
                low = mid + 1;
            } else {
                int temp = mid;
                while (temp < high && nums[temp] == nums[temp + 1]) {
                    temp++;
                }
                if (temp == high || nums[temp] > nums[temp + 1]) {
                    high = mid;
                }    else {
                    low = temp + 1;
                }

            }
        }
        return low;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 3, 8, 8, 8, 12, 12, 12, 5, 2};
        System.out.println(findPeakElement(nums));
    }
}
