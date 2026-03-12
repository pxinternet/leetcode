package leetCode;

import java.util.Arrays;

/**
 * LC34 - 在排序数组中查找元素的第一个和最后一个位置
 *
 * 题目（概要）：给定升序数组 nums 和目标值 target，找出 target 在数组中的起始和结束位置索引。若不存在返回 [-1,-1]。
 *
 * 解法说明：
 * - 使用两次二分查找：lower_bound（第一个 >= target）和 upper_bound（第一个 > target）
 * - 左边界 = lower_bound 结果（若该位置值等于 target）；右边界 = upper_bound - 1
 * - 采用 [low, high) 半开区间二分模板
 *
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - nums 为 null 返回 [-1,-1]
 *
 * 示例：nums = [5,7,7,8,8,10], target = 8 → [3,4]
 */
public class LC34 {

    /**
     * 在有序数组 nums 中查找 target 的起始与结束索引。
     * 返回长度为 2 的数组 [leftIndex, rightIndex]，若不存在则返回 [-1, -1]。
     *
     * 保障：如果传入 null，会返回 [-1, -1]（避免 NPE）。
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null) return new int[]{-1, -1};

        int[] result = {-1, -1};

        int leftIndex = extremeInsertionIndex(nums, target, true);
        if (leftIndex == nums.length || nums[leftIndex] != target) {
            return result;  // lower_bound 越界或指向更大值，说明 target 不存在
        }
        result[0] = leftIndex;
        // 找到第一个 > target 的位置，减一得到右边界
        result[1] = extremeInsertionIndex(nums, target, false) - 1;

        return result;
    }

    /**
     * 二分查找插入位置的变体。
     * 参数：
     * - nums: 已排序数组（升序）
     * - target: 目标值
     * - first: 若为 true，则在 nums[mid] == target 时向左收缩（查找 lower_bound）
     *          若为 false，则在 nums[mid] == target 时向右移动（查找 upper_bound）
     *
     * 返回值：若 first==true 返回第一个 >= target 的索引；若 first==false 返回第一个 > target 的索引。
     */
    private int extremeInsertionIndex(int[] nums, int target, boolean first) {
        int low = 0;
        int high = nums.length; // 半开区间 [low, high)

        // 循环不变式：答案始终位于 [low, high)
        while (low < high) {
            int mid = low + (high - low) / 2;
            // 若 nums[mid] > target，答案在左侧（包含 mid）
            // 若 nums[mid] == target 且 first == true，我们也向左收缩以找到最左侧的等于位置
            if (nums[mid] > target || (first && nums[mid] == target)) {
                high = mid;
            } else {
                // nums[mid] < target 或者 nums[mid] == target 且 first == false
                low = mid + 1;
            }
        }
        // low == high 为插入点
        return low;
    }

    // ----------------- 下面为演示/测试代码 -----------------

    /**
     * verbose 版本：打印每次迭代的 low/high/mid 以便教学演示
     */
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

    /**
     * main 演示：包含多个用例，并对部分用例打印 verbose 二分过程
     */
    public static void main(String[] args) {
        LC34 solver = new LC34();

        int[][] tests = new int[][]{
                {5,7,7,8,8,10},
                {5,7,7,8,8,10},
                {},
                {2},
                {1,2,3,4,5}
        };
        int[] targets = new int[]{8, 6, 1, 2, 3};

        System.out.println("==== LC34 演示: searchRange & verbose 二分 ====");
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

        // 额外示例：详细跟踪 nums=[5,7,7,8,8,10], target=8
        int[] demo = new int[]{5,7,7,8,8,10};
        System.out.println("\n--- 详细跟踪示例: nums=[5,7,7,8,8,10], target=8 ---");
        solver.extremeInsertionIndexVerbose(demo, 8, true);
        solver.extremeInsertionIndexVerbose(demo, 8, false);

        System.out.println("==== 演示结束 ====");
    }

}
