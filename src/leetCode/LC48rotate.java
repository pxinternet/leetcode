package leetCode;

/**
 * LC48rotate - 旋转图像
 *
 * 题目（概要）：给定 n×n 二维矩阵 matrix，将矩阵顺时针旋转 90 度。必须原地修改。
 *
 * 解法说明：
 * - 核心思想：先沿主对角线转置，再每行左右翻转
 * - 转置：matrix[i][j] 与 matrix[j][i] 交换（i < j）
 * - 每行翻转：左右对调
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - n 为矩阵边长
 *
 * 示例：[[1,2,3],[4,5,6],[7,8,9]] → [[7,4,1],[8,5,2],[9,6,3]]
 */
public class LC48rotate {

    /**
     * 原地顺时针旋转矩阵 90 度
     *
     * @param matrix n×n 矩阵，会被原地修改
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        for (int i = 0; i < n; i++) {
            int start = 0, end = n - 1;
            while (start < end) {
                int tmp = matrix[i][start];
                matrix[i][start] = matrix[i][end];
                matrix[i][end] = tmp;
                start++;
                end--;
            }
        }
    }

}
