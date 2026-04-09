package leetCode;

/**
 * LC74 - 搜索二维矩阵
 *
 * 题目（概要）：矩阵每行升序，且下一行首元素大于上一行末元素。判断 target 是否在矩阵中。
 *
 * 解法说明：
 * - 解法一：二分。将二维展平为一维 [0, m*n-1]，mid 对应 matrix[mid/n][mid%n]。
 * - 解法二：从右上角出发，当前>target 则左移，<target 则下移。
 *
 * 时间复杂度：O(log(m*n)) / O(m+n)
 * 空间复杂度：O(1)
 *
 * 示例：matrix=[[1,3,5,7],[10,11,16,20],[23,30,34,60]], target=3 → true
 */
public class LC74searchMatrix {

    /**
     * 二分：展平为一维有序数组
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        int left = 0;
        int right = m * n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midElement = matrix[mid / n][mid % n];

            if (midElement == target) {
                return true;
            } else if (midElement < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * 从右上角搜索：当前>target 则左移，<target 则下移
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        int row = 0;
        int col = n -1;

        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }
}
