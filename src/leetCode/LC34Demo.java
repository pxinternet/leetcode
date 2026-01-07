package leetCode;

import java.util.Arrays;

/**
 * LC34Demo: 一个独立的演示类，复制 LC34 的逻辑并避免潜在 BOM 干扰，
 * 用于编译并展示 verbose 二分过程的输出。
 */
public class LC34Demo {

    public int[] searchRange(int[] nums, int target) {
        if (nums == null) return new int[]{-1, -1};
        int[] result = {-1, -1};
        int leftIndex = extremeInsertionIndex(nums, target, true);
        if (leftIndex == nums.length || nums[leftIndex] != target) return result;
        result[0] = leftIndex;
        result[1] = extremeInsertionIndex(nums, target, false) - 1;
        return result;
    }

    private int extremeInsertionIndex(int[] nums, int target, boolean first) {
        int low = 0, high = nums.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > target || (first && nums[mid] == target)) high = mid; else low = mid + 1;
        }
        return low;
    }

    private int extremeInsertionIndexVerbose(int[] nums, int target, boolean first) {
        if (nums == null) return 0;
        int low = 0, high = nums.length;
        System.out.printf("\n[verbose] first=%b target=%d nums=%s\n", first, target, Arrays.toString(nums));
        System.out.println("iter\tlow\thigh\tmid\tnums[mid]\taction");
        int iter = 0;
        while (low < high) {
            int mid = low + (high - low) / 2;
            String action;
            if (nums[mid] > target || (first && nums[mid] == target)) {
                action = String.format("nums[%d]=%d >=? target -> high=mid(%d)", mid, nums[mid], mid);
                System.out.printf("%d\t%d\t%d\t%d\t%d\t%s\n", iter++, low, high, mid, nums[mid], action);
                high = mid;
            } else {
                action = String.format("nums[%d]=%d <? target -> low=mid+1(%d)", mid, nums[mid], mid + 1);
                System.out.printf("%d\t%d\t%d\t%d\t%d\t%s\n", iter++, low, high, mid, nums[mid], action);
                low = mid + 1;
            }
        }
        System.out.printf("finish: low==high==%d\n", low);
        return low;
    }

    public static void main(String[] args) {
        LC34Demo solver = new LC34Demo();
        int[][] tests = new int[][]{
                {5,7,7,8,8,10},
                {5,7,7,8,8,10},
                {},
                {2},
                {1,2,3,4,5}
        };
        int[] targets = new int[]{8, 6, 1, 2, 3};
        System.out.println("==== LC34Demo 演示: searchRange & verbose 二分 ====");
        for (int i = 0; i < tests.length; i++) {
            int[] arr = tests[i];
            int t = targets[i];
            int[] range = solver.searchRange(arr, t);
            System.out.printf("nums=%s, target=%d => range=%s\n", Arrays.toString(arr), t, Arrays.toString(range));
            if (arr.length > 0) {
                solver.extremeInsertionIndexVerbose(arr, t, true);
                solver.extremeInsertionIndexVerbose(arr, t, false);
            }
            System.out.println();
        }
        int[] demo = new int[]{5,7,7,8,8,10};
        System.out.println("\n--- 详细跟踪示例: nums=[5,7,7,8,8,10], target=8 ---");
        solver.extremeInsertionIndexVerbose(demo, 8, true);
        solver.extremeInsertionIndexVerbose(demo, 8, false);
        System.out.println("==== 演示结束 ====");
    }
}

