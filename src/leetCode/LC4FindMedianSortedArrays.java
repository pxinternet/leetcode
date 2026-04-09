package leetCode;

/**
 * LC4FindMedianSortedArrays - 寻找两个有序数组的中位数
 *
 * 题目（概要）：给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2，
 * 找出并返回这两个正序数组的中位数。算法的时间复杂度应该为 O(log(m + n))。
 *
 * 算法原理：
 * - 二分 cut 的对称性：另一种思路是在 nums1 上二分割点 i，nums2 上对应割点 j=(m+n+1)/2-i，
 *   使得 left1<=right2 且 left2<=right1，则中位数由 max(left1,left2) 与 min(right1,right2) 决定。
 * - 第 k 小思路：中位数=第 (m+n+1)/2 小与第 (m+n+2)/2 小的平均。每次比较两数组的第 k/2 元素，
 *   较小者前 k/2 必不包含第 k 小，可排除，递归规模减半。
 * - 边界不越界：保证 len1<=len2，则 len1==0 时直接返 nums2[start2+k-1]；i=start1+min(len1,k/2)-1 确保不越界。
 *
 * 解法一（递归二分查找 - 找第 k 小的数）说明：
 * - 核心思想：中位数就是第 (m+n+1)/2 小的数和第 (m+n+2)/2 小的数的平均值
 * - 对于奇数长度：中位数是第 (m+n+1)/2 小的数
 * - 对于偶数长度：中位数是第 (m+n+1)/2 小的数和第 (m+n+2)/2 小的数的平均值
 * - 使用递归二分查找，每次排除 k/2 个元素，将问题规模缩小一半
 * - 关键优化：始终让第一个数组较短，这样可以更快地处理边界情况
 *
 * 时间复杂度：O(log(m + n))
 * - 每次递归排除 k/2 个元素，k 从 (m+n)/2 开始，最多递归 log(m+n) 次
 *
 * 空间复杂度：O(log(m + n))（递归调用栈）
 *
 * 边界与注意事项：
 * - 如果某个数组为空，直接返回另一个数组的第 k 小元素
 * - 如果 k == 1，返回两个数组当前起始位置的最小值
 * - 每次比较两个数组的第 k/2 个元素，排除较小元素所在数组的前 k/2 个元素
 * - 需要处理数组越界的情况（当某个数组长度小于 k/2 时）
 *
 * 示例：
 * - nums1 = [1, 3], nums2 = [2] → 中位数 = 2.0
 * - nums1 = [1, 2], nums2 = [3, 4] → 中位数 = (2 + 3) / 2 = 2.5
 */
public class LC4FindMedianSortedArrays {

    /**
     * 方法一：使用递归二分查找找第 k 小的数
     *
     * 关键点逐行说明：
     * - 计算两个数组的总长度，确定中位数的位置
     * - left = (m+n+1)/2：对于奇数长度，这是中位数位置；对于偶数长度，这是左中位数位置
     * - right = (m+n+2)/2：对于偶数长度，这是右中位数位置；对于奇数长度，等于 left
     * - 调用 getKth 找第 left 小和第 right 小的数，取平均值
     *
     * @param nums1 第一个有序数组
     * @param nums2 第二个有序数组
     * @return 两个数组的中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        // 计算中位数的位置
        // left: 对于奇数长度是唯一中位数位置，对于偶数长度是左中位数位置
        // right: 对于偶数长度是右中位数位置，对于奇数长度等于 left
        // 例如：m+n=5 时，left=3, right=3（奇数，中位数是第3小的数）
        // m+n=6 时，left=3, right=4（偶数，中位数是第3小和第4小的数的平均值）
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;

        // 找第 left 小和第 right 小的数，取平均值
        // 对于奇数长度，left == right，所以实际上是同一个数除以 2.0
        return (getKth(nums1, 0, m - 1, nums2, 0, n - 1, left) +
                getKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;
    }

    /**
     * 递归查找两个有序数组合并后的第 k 小的元素
     *
     * 核心思想：
     * - 比较两个数组的第 k/2 个元素（如果存在）
     * - 较小元素所在数组的前 k/2 个元素一定不包含第 k 小的元素，可以排除
     * - 递归查找剩余部分中的第 (k - 排除数量) 小的元素
     *
     * 关键点逐行说明：
     * - 计算两个数组当前区间的长度
     * - 保证 len1 <= len2，这样边界处理更简单（如果 len1 > len2，交换两个数组）
     * - 如果 len1 == 0，说明第一个数组已全部排除，直接返回第二个数组的第 k 小元素
     * - 如果 k == 1，返回两个数组当前起始位置的最小值
     * - 计算两个数组的第 k/2 个元素的位置（注意处理数组越界）
     * - 比较两个元素，排除较小元素所在数组的前 k/2 个元素，递归查找
     *
     * @param nums1  第一个有序数组
     * @param start1 第一个数组的起始索引
     * @param end1   第一个数组的结束索引
     * @param nums2  第二个有序数组
     * @param start2 第二个数组的起始索引
     * @param end2   第二个数组的结束索引
     * @param k      要找的第 k 小的元素（k 从 1 开始计数）
     * @return 第 k 小的元素值
     */
    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        // 计算两个数组当前区间的长度
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        // 关键优化：始终让 len1 <= len2
        // 这样当 len1 == 0 时，可以直接返回 nums2 的第 k 小元素
        // 如果 len1 > len2，交换两个数组的角色
        if (len1 > len2) {
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
        }

        // 边界条件 1：第一个数组为空
        // 此时第 k 小的元素一定在第二个数组中，直接返回
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }

        // 边界条件 2：k == 1，找最小的元素
        // 直接返回两个数组当前起始位置的最小值
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        // 计算两个数组的第 k/2 个元素的位置
        // 注意：如果数组长度小于 k/2，则取数组的最后一个元素
        // i 和 j 是索引位置，不是偏移量
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        // 比较两个数组的第 k/2 个元素
        if (nums1[i] > nums2[j]) {
            // nums2[j] 较小，说明 nums2 的前 (j - start2 + 1) 个元素都不可能是第 k 小的元素
            // 排除这些元素，在剩余部分中找第 (k - 排除数量) 小的元素
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            // nums1[i] 较小或相等，说明 nums1 的前 (i - start1 + 1) 个元素都不可能是第 k 小的元素
            // 排除这些元素，在剩余部分中找第 (k - 排除数量) 小的元素
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

    /**
     * 方法二：使用二分查找切割点（更优的解法）
     *
     * 核心思想：
     * - 将两个数组分别从某个位置切割，使得：
     * 1. 左半部分的所有元素 <= 右半部分的所有元素
     * 2. 左半部分的元素数量 = 右半部分的元素数量（或比右半部分多 1 个）
     * - 中位数就是：max(左半部分最大值) 和 min(右半部分最小值) 的平均值（偶数长度）
     * 或 max(左半部分最大值)（奇数长度）
     *
     * 关键点说明：
     * - 在较短的数组上进行二分查找，确定切割点 i
     * - 切割点 j 由公式 j = (m + n + 1) / 2 - i 确定，保证左右两部分元素数量平衡
     * - 需要满足：nums1[i-1] <= nums2[j] 且 nums2[j-1] <= nums1[i]
     * - 如果不满足，调整二分查找的范围
     *
     * 时间复杂度：O(log(min(m, n)))
     * - 只在较短的数组上进行二分查找，最多 log(min(m, n)) 次
     *
     * 空间复杂度：O(1)
     * - 只使用常数额外空间
     *
     * 边界与注意事项：
     * - 如果 m > n，交换两个数组，保证在较短的数组上二分
     * - 需要处理边界情况：i == 0, i == m, j == 0, j == n
     * - 当找到正确的切割点时，计算左半部分的最大值和右半部分的最小值
     *
     * 示例：
     * - nums1 = [1, 3], nums2 = [2]
     * 切割：nums1 在位置 1 切割，nums2 在位置 0 切割
     * 左半部分：[1], [2] → max = 2
     * 右半部分：[3], [] → min = 3
     * 中位数 = 2（奇数长度）
     */
    public double findMedianSortedArraysCut(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        // 关键优化：保证在较短的数组上进行二分查找
        // 这样时间复杂度是 O(log(min(m, n))) 而不是 O(log(m + n))
        if (m > n) {
            return findMedianSortedArraysCut(nums2, nums1);
        }

        // 在较短的数组 nums1 上进行二分查找，确定切割点 i
        // i 表示 nums1 左半部分的元素数量（0 <= i <= m）
        int iMin = 0, iMax = m;

        while (iMin <= iMax) {
            // 在 nums1 的中间位置切割
            int i = (iMin + iMax) / 2;

            // 根据切割点 i 计算 nums2 的切割点 j
            // 公式推导：
            // - 左半部分元素数量 = i + j
            // - 右半部分元素数量 = (m - i) + (n - j)
            // - 对于奇数长度：左半部分比右半部分多 1 个，即 i + j = (m - i) + (n - j) + 1
            // - 对于偶数长度：左右两部分相等，即 i + j = (m - i) + (n - j)
            // - 两种情况都可以统一为：i + j = (m + n + 1) / 2
            // 因为 (m+n+1)/2 对于奇数等于 (m+n)/2 + 1，对于偶数等于 (m+n)/2
            int j = (m + n + 1) / 2 - i;

            // 检查切割点是否满足条件
            // 条件：nums1[i-1] <= nums2[j] 且 nums2[j-1] <= nums1[i]
            // 如果不满足，需要调整切割点

            if (i != m && j != 0 && nums1[i] < nums2[j - 1]) {
                // 情况 1：nums1[i] < nums2[j-1]
                // 说明 nums1 的右半部分最小值小于 nums2 的左半部分最大值
                // 切割点 i 太小，需要增大 i（向右移动切割点）
                // 例如：nums1 = [1, 3], nums2 = [2, 4], i=0, j=2
                // nums1[0]=1 < nums2[1]=2，说明应该让更多 nums1 的元素在左半部分
                iMin = i + 1;
            } else if (i != 0 && j != n && nums1[i - 1] > nums2[j]) {
                // 情况 2：nums1[i-1] > nums2[j]
                // 说明 nums1 的左半部分最大值大于 nums2 的右半部分最小值
                // 切割点 i 太大，需要减小 i（向左移动切割点）
                // 例如：nums1 = [1, 3], nums2 = [2, 4], i=2, j=0
                // nums1[1]=3 > nums2[0]=2，说明应该让更少 nums1 的元素在左半部分
                iMax = i - 1;
            } else {
                // 情况 3：找到了正确的切割点！
                // 此时满足：nums1[i-1] <= nums2[j] 且 nums2[j-1] <= nums1[i]
                // 左半部分的最大值就是中位数的候选值

                // 计算左半部分的最大值
                int maxLeft;
                if (i == 0) {
                    // nums1 的左半部分为空，最大值来自 nums2
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    // nums2 的左半部分为空，最大值来自 nums1
                    maxLeft = nums1[i - 1];
                } else {
                    // 两个数组的左半部分都不为空，取两者最大值
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }

                // 如果是奇数长度，中位数就是左半部分的最大值
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                // 如果是偶数长度，还需要计算右半部分的最小值
                int minRight;
                if (i == m) {
                    // nums1 的右半部分为空，最小值来自 nums2
                    minRight = nums2[j];
                } else if (j == n) {
                    // nums2 的右半部分为空，最小值来自 nums1
                    minRight = nums1[i];
                } else {
                    // 两个数组的右半部分都不为空，取两者最小值
                    minRight = Math.min(nums1[i], nums2[j]);
                }

                // 偶数长度：中位数是左半部分最大值和右半部分最小值的平均值
                return (maxLeft + minRight) / 2.0;
            }
        }

        // 理论上不会执行到这里，但为了编译通过需要返回值
        return 0.0;
    }

    public static void main(String[] args) {
        LC4FindMedianSortedArrays lc4FindMedianSortedArrays = new LC4FindMedianSortedArrays();
        int[] nums1 = new int[] { 1, 2 };
        int[] nums2 = new int[] { 3, 4 };

        double mid = lc4FindMedianSortedArrays.findMedianSortedArraysCut(nums1, nums2);
        System.out.println(mid);
    }

}
