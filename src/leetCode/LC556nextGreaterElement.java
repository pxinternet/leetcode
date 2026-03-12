package leetCode;

/**
 * LC556 - 下一个更大元素 III
 *
 * 题目（概要）：给定正整数 n，用其各位数字重排得到大于 n 的最小整数；若不存在则返回 -1。
 *
 * 解法说明：
 * - 核心思想：从右往左找第一个 nums[i] < nums[i+1] 的 i；再从右找第一个 nums[j] > nums[i] 的 j；交换 i、j；将 i+1 到末尾反转（变为最小字典序）。
 * - 即「下一个排列」算法。
 *
 * 时间复杂度：O(d)，d 为位数
 * 空间复杂度：O(d)
 *
 * 边界与注意事项：
 * - 结果可能超 int，用 long 解析后与 Integer.MAX_VALUE 比较
 *
 * 示例：n=12 → 21；n=21 → -1
 */
public class LC556nextGreaterElement {

    /**
     * 下一个排列：找第一处可增大的位置，交换后反转后缀
     *
     * @param n 正整数
     * @return 大于 n 的最小重排数，不存在则 -1
     */
    public int nextGreaterElement(int n) {
        char[] array = String.valueOf(n).toCharArray();
        int i = array.length - 2;

        while (i >= 0 && array[i + 1] <= array[i]) i--;
        if (i < 0) return -1;

        int j = array.length - 1;
        while (array[j] <= array[i]) j--;

        swap(array, i, j);
        reverse(array, i + 1);

        long res = Long.parseLong(new String(array));
        return res <= Integer.MAX_VALUE ? (int) res : -1;
    }

    private void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void reverse(char[] array, int left) {
        int right = array.length - 1;
        while (left < right) {
            swap(array, left, right);
            left++;
            right--;
        }
    }
}
