package leetCode;

import java.util.Arrays;

public class LC16threeSumClosest {

    /**
     * threeSumClosest：找到数组中三个数之和最接近 target 的和
     * 算法概要：先对数组排序，然后固定一个元素 nums[i]，对剩下部分使用双指针（left/right）求
     * 两数之和使得与 (target - nums[i]) 的差最小。对每个 i 枚举并维护最小差值即可。
     * 时间复杂度：O(n^2)（排序 O(n log n) + 双指针枚举 O(n^2)）
     * 空间复杂度：O(1)（忽略排序使用的栈/额外空间）
     */
    public int threeSumClosest(int[] nums, int target) {
        // 边界检查：若 nums 为空或长度小于 3，则无法选出三个数，抛出异常或者返回 0（此处抛异常以明确调用者错误）
        if (nums == null || nums.length < 3) {
            throw new IllegalArgumentException("nums must have at least 3 numbers");
        }

        // 将数组排序，排序后可以使用双指针在 O(n) 时间内查找两数之和
        Arrays.sort(nums);

        // minGap 用来维护当前发现的最小绝对差（|sum - target|），初始设为最大整数
        int minGap = Integer.MAX_VALUE;
        // result 保存当前最接近 target 的三数之和
        int result = 0;

        // 外层循环固定第一个元素 i（注意 i 最多到 nums.length - 3）
        for (int i = 0; i < nums.length - 2; i++) {
            // left 和 right 分别为 i 右侧的左右指针，开始时指向 i+1 与末尾
            int left = i + 1;
            int right = nums.length - 1;

            // 当 left < right 时，(i,left,right) 三元组有效
            while (left < right) {
                // 计算当前三数之和
                int sum = nums[i] + nums[left] + nums[right];
                // 计算与 target 的差值的绝对值
                int gap = Math.abs(sum - target);

                // 如果找到了更小的 gap，则更新 minGap 与 result
                if (gap < minGap) {
                    minGap = gap;
                    result = sum;
                }

                // 双指针移动策略：
                // - 如果 sum < target，说明需要更大的和（因为数组已排序，向右移动 left 会增加 sum）
                // - 如果 sum > target，说明需要更小的和，向左移动 right 会减小 sum
                // - 如果 sum == target，则已经达到最优（差为 0），可以直接返回 target
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    // 精确匹配，最优解
                    return sum;
                }
            }
        }

        // 循环结束后 result 即为最接近 target 的三数之和
        return result;
    }

    /**
     * threeSumClosestVerbose：与 threeSumClosest 相同逻辑，但打印每一步的详细状态，便于理解
     */
    public int threeSumClosestVerbose(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            throw new IllegalArgumentException("nums must have at least 3 numbers");
        }
        Arrays.sort(nums);
        int minGap = Integer.MAX_VALUE;
        int result = 0;

        System.out.println("数组(排序后)=" + Arrays.toString(nums) + ", target=" + target);

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            System.out.println("固定 i=" + i + ", nums[i]=" + nums[i]);
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                int gap = Math.abs(sum - target);
                System.out.printf("  left=%d (v=%d), right=%d (v=%d), sum=%d, gap=%d, currentMinGap=%d, result=%d\n",
                        left, nums[left], right, nums[right], sum, gap, minGap, result);

                if (gap < minGap) {
                    minGap = gap;
                    result = sum;
                    System.out.println("    => 更新：minGap=" + minGap + ", result=" + result);
                }

                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    System.out.println("精确匹配，直接返回 " + sum);
                    return sum;
                }
            }
        }
        System.out.println("最终 result=" + result + ", 最小差=" + minGap);
        return result;
    }

    // main：演示若干示例用法（包含普通用例与 verbose 输出）
    public static void main(String[] args) {
        LC16threeSumClosest solver = new LC16threeSumClosest();

        int[] nums1 = {-1, 2, 1, -4};
        int target1 = 1;
        System.out.println("示例1: nums=" + Arrays.toString(nums1) + ", target=" + target1);
        System.out.println("结果=" + solver.threeSumClosest(nums1, target1));
        System.out.println("详细过程：");
        solver.threeSumClosestVerbose(nums1, target1);
        System.out.println();

        int[] nums2 = {0, 0, 0};
        int target2 = 1;
        System.out.println("示例2: nums=" + Arrays.toString(nums2) + ", target=" + target2);
        System.out.println("结果=" + solver.threeSumClosest(nums2, target2));
        solver.threeSumClosestVerbose(nums2, target2);
        System.out.println();

        int[] nums3 = {1, 1, -1, -1, 3};
        int target3 = -1;
        System.out.println("示例3: nums=" + Arrays.toString(nums3) + ", target=" + target3);
        System.out.println("结果=" + solver.threeSumClosest(nums3, target3));
        solver.threeSumClosestVerbose(nums3, target3);
    }
}
