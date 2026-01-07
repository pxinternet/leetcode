package round2;

import java.util.Random;

public class MedianFinder {

    // 返回数组 nums 的中位数（若长度为偶数，返回中间两个数的平均值）
    public static double findMedian(int[] nums) {

        int n = nums.length;

        // 若元素个数为奇数，直接找到第 n/2 小元素
        if (n % 2 == 1) {
            return quickSelect(nums, 0, n - 1, n / 2);
        } else {
            // 若为偶数，找到第 n/2-1 与 n/2 两个秩，然后取平均
            // 注意：quickSelect 会就地修改数组，但返回的秩元素值仍然是正确的
            return (quickSelect(nums, 0, n - 1, n / 2 - 1) + quickSelect(nums, 0, n - 1, n / 2)) / 2.0;
        }

    }

    // Quickselect：在 nums[left..right] 区间中寻找秩为 k (0-based) 的元素
    private static int quickSelect(int[] nums, int left, int right, int k) {

        // 终止条件：区间内只有一个元素，则直接返回
        if (left == right) {
            return nums[left];
        }

        // 随机选择 pivotIndex，随机化可以降低最坏情况（已排序/逆序）出现的概率
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);

        // 将 pivot 放到它在正确排序后的索引位置（partition 返回这个位置），并在此基础上继续递归
        pivotIndex = partition(nums, left, right, pivotIndex);

        // pivotIndex 对比 k 来决定递归到左半部还是右半部（或正好命中）
        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    // 简单的交换工具函数
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /*
     * partition 的目标：以 pivotValue 为基准，把区间 [left, right] 重排为：
     *    nums[left..storeIndex-1] < pivotValue
     *    nums[storeIndex] == pivotValue （最终位置）
     *    nums[storeIndex+1..right] >= pivotValue
     * 返回值是 pivot 最终被放置的位置（storeIndex）。
     *
     * 实现思路（Lomuto partition scheme 的变体）：
     * 1. 取出 pivotValue，并把 pivot 元素交换到区间末尾（right）作为暂存
     * 2. 使用 storeIndex 指针从 left 开始，扫描 i 从 left 到 right-1：
     *    - 若 nums[i] < pivotValue，则把 nums[i] 与 nums[storeIndex] 交换，并把 storeIndex++。
     *    - 这样不变式始终成立：所有被交换到左侧的元素都 < pivotValue。
     * 3. 扫描结束后，把保存在 right 的 pivotValue 与 storeIndex 位置交换，
     *    使 pivotValue 回到其正确位置（storeIndex），并返回该索引。
     *
     * 重要细节与正确性理由：
     * - 在循环中只比较 "< pivotValue"，因此等于 pivotValue 的元素会被保留在右区间，
     *   这会导致重复值被放到 pivot 右边（不影响选择正确的秩，但会影响具体分布），
     *   当数组中有大量重复值时，这种策略仍然是正确的，但可能降低分区的平衡性。
     * - Lomuto scheme 的不变式容易证明：在任一时刻，
     *     所有索引 < storeIndex 的元素都 < pivotValue；
     *     索引 i 在遍历过程中表示当前考察元素，区间 (storeIndex..i-1) 包含 >= pivotValue 的元素。
     * - 最终交换后，pivotValue 位于 storeIndex，左侧元素都 < pivot，右侧元素都 >= pivot，满足 partition 合约。
     *
     * 时间复杂度：O(right-left+1)（线性扫描）；对 quickSelect 的总体复杂度为平均 O(n)，最坏 O(n^2)。
     */
    private static int partition(int[] nums, int left, int right, int pivotIndex) {

        int pivotValue = nums[pivotIndex]; // 记录 pivot 的值（后面用来比较）

        // 把 pivot 元素暂时放到区间末尾，避免在扫描时干扰
        swap(nums, pivotIndex, right);

        int storeIndex = left; // storeIndex 指向下一个小于 pivot 的元素应放的位置

        // 遍历每个元素，如果小于 pivot 就放到左侧区域
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, i, storeIndex); // 把较小元素调到左侧
                storeIndex++;
            }
        }
        // 把 pivotValue 放回它的最终位置（第一个不小于 pivot 的位置）
        swap(nums, storeIndex, right);
        return storeIndex; // 返回 pivot 的最终索引

    }

    /*
     * partition2 是与 partition 实现几乎等价的另一个版本。
     * 对比可见：
     * - 两者都将 pivot 移到 right 做暂存
     * - 都用 storeIndex 从 left 开始
     * - 在循环中，两者交换参数的顺序略有不同，但 swap(i, storeIndex) 与 swap(storeIndex, i) 等价（swap 内部无序依赖）
     * - 最后的交换在参数顺序上也互为等价（swap(nums, right, storeIndex) 与 swap(nums, storeIndex, right) 是一样的）
     *
     * 因此 partition2 与 partition 在语义和行为上是等价的；保留第二个实现可能是为了调试或比较不同写法，
     * 但在生产代码中应避免重复实现以减少维护成本。
     */
    private static int partition2(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];

        swap(nums, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i); // 与 partition 中 swap(nums,i,storeIndex) 等价
                storeIndex++;
            }
        }

        swap(nums, right, storeIndex); // 与 partition 中 swap(nums,storeIndex,right) 等价
        return storeIndex;
    }

}
