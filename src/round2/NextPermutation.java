package round2;

import java.util.Arrays;

/**
 * NextPermutation - 生成下一个字典序排列的实现
 *
 * 算法思路（标准三步法）：
 * 1. 从右向左找到第一个 nums[i] < nums[i+1] 的位置 i（称为 "pivot"），
 *    如果没有这样的 i，说明当前序列是降序排列（最大的排列），下一个排列是翻转整个数组（变为最小排列）。
 * 2. 如果找到了 i，从右向左找到第一个 nums[j] > nums[i] 的位置 j，交换 nums[i] 与 nums[j]，
 *    这样保证交换后生成的序列比原序列大，但尽可能小。
 * 3. 最后把 i 右边的子数组（原来是降序）全部反转（变为升序），以得到在第 2 步生成的前缀下的最小后缀，
 *    从而得到下一个最小的字典序排列。
 *
 * 本实现要点：
 * - 处理空数组和长度 1 的特殊情况（直接返回）。
 * - 辅助方法 swap 和 reverse 均为私有静态方法。
 * - 方法行为是就地修改传入的数组（in-place），并不返回新数组。
 *
 * 时间复杂度：O(n)（一次从右向左扫描 + 最坏情况下一次完整反转）
 * 空间复杂度：O(1)（原地修改）
 */
public class NextPermutation {

    /**
     * 将数组 nums 修改为其下一个字典序排列（就地修改）
     *
     * 逐行说明：
     * - 若数组为 null 或长度小于 2，直接返回（没有下一个排列或无需改变）。
     * - 从右向左找到第一个满足 nums[i] < nums[i+1] 的 i（pivot）。
     * - 若找到了这样的 i，从右向左找到第一个 nums[j] > nums[i] 的 j 并交换；
     *   若没有找到 i（即整个数组非递增从左到右，即整体为降序），直接翻转整个数组。
     * - 最后翻转 i+1 到 n-1 的区间，使后缀升序，得到最小的下一个排列。
     */
    public static void nextPermutation(int[] nums) {
        // 空/小数组直接返回
        if (nums == null || nums.length < 2) {
            return;
        }

        int n = nums.length;
        // i 指向第一个需要被替换的位置，初始化为倒数第二个下标
        int i = n - 2;

        // 从右向左找到第一个 nums[i] < nums[i+1]
        // 这一步定位的是：从右边开始的非递增前缀的结束位置的前一位
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // 如果找到了这样的 i，说明存在比当前排列更大的排列
        if (i >= 0) {
            // 从右向左找到第一个 nums[j] > nums[i]
            int j = n - 1;
            // 注意条件是 nums[j] <= nums[i] 时继续左移 j，直到找到第一个严格大于 nums[i] 的位置
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }
            // 交换 i 与 j 的值，使排列更大一点但尽量小
            swap(nums, i, j);
        }

        // 将 i 后面的元素全部翻转（将原来的降序变为升序），以构造最小的后缀
        reverse(nums, i + 1, n - 1);
    }

    // 交换数组中两个下标的元素（私有工具方法）
    private static void swap(int[] nums, int i, int j) {
        // 额外的边界检查（理论上 i 和 j 都应当是有效下标）
        if (nums == null || i < 0 || j < 0 || i >= nums.length || j >= nums.length) {
            return;
        }
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 反转数组区间 [l, r]（私有工具方法）
    private static void reverse(int[] nums, int l, int r) {
        if (nums == null || l >= r) {
            return;
        }
        while (l < r) {
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l++;
            r--;
        }
    }

    // 逐步演示 nextPermutation 的内部流程：打印 pivot、j、交换前后、反转前后等中间状态
    // 该方法不会改变原始数组的副本，而是在副本上演示（以便保留原始输入用于对比）
    public static void nextPermutationVerbose(int[] nums) {
        System.out.println("--- verbose demo start ---");
        if (nums == null) {
            System.out.println("input is null");
            System.out.println("--- verbose demo end ---\n");
            return;
        }
        if (nums.length < 2) {
            System.out.println("input has length < 2, no change: " + Arrays.toString(nums));
            System.out.println("--- verbose demo end ---\n");
            return;
        }

        // 使用数组副本以便演示时可以同时显示原始输入
        int[] a = Arrays.copyOf(nums, nums.length);
        System.out.println("original: " + Arrays.toString(a));

        int n = a.length;
        int i = n - 2;
        // 找 pivot
        System.out.println("searching pivot (first i from right where a[i] < a[i+1])...");
        while (i >= 0 && a[i] >= a[i + 1]) {
            System.out.printf("a[%d]=%d >= a[%d]=%d -> move left%n", i, a[i], i + 1, a[i + 1]);
            i--;
        }
        if (i < 0) {
            System.out.println("no pivot found: array is in descending order -> will reverse entire array");
            reverse(a, 0, n - 1);
            System.out.println("after reverse: " + Arrays.toString(a));
            System.out.println("--- verbose demo end ---\n");
            return;
        }

        System.out.printf("found pivot i=%d, a[i]=%d%n", i, a[i]);

        // 找 j
        int j = n - 1;
        System.out.println("searching j from right where a[j] > a[i]...");
        while (j > i && a[j] <= a[i]) {
            System.out.printf("a[%d]=%d <= a[%d]=%d -> move left%n", j, a[j], i, a[i]);
            j--;
        }
        System.out.printf("found j=%d, a[j]=%d%n", j, a[j]);

        // 交换并显示
        System.out.println("swap a[i] and a[j]");
        swap(a, i, j);
        System.out.println("after swap: " + Arrays.toString(a));

        // 反转后缀并显示
        System.out.printf("reverse suffix from index %d to %d%n", i + 1, n - 1);
        reverse(a, i + 1, n - 1);
        System.out.println("after reverse: " + Arrays.toString(a));
        System.out.println("--- verbose demo end ---\n");
    }

    /**
     * 简单的 main 测试示例，展示若干典型用例的变换结果
     * 输出示例用于手动或自动验证算法正确性
     */
    public static void main(String[] args) {
        int[][] tests = new int[][]{
            {},
            {1},
            {1, 2, 3},
            {3, 2, 1},
            {1, 1, 5},
            {1, 3, 2},
            {2, 3, 1},
            {2, 2, 0, 4, 3}
        };

        System.out.println("=== quick results ===");
        for (int[] t : tests) {
            // 复制用于打印原始数组
            int[] original = t == null ? null : Arrays.copyOf(t, t.length);
            nextPermutation(t);
            System.out.println("original: " + Arrays.toString(original) + " -> next: " + Arrays.toString(t));
        }

        // 追加更详细的逐步演示（选取若干典型用例）
        System.out.println();
        System.out.println("=== verbose demos ===");
        nextPermutationVerbose(new int[]{2, 2, 0, 4, 3});
        nextPermutationVerbose(new int[]{1, 3, 2});
        nextPermutationVerbose(new int[]{3, 2, 1});
        nextPermutationVerbose(new int[]{1, 1, 5});
    }
}
