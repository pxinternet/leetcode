package leetCode;

import java.util.Arrays;

/**
 * LC73 - 矩阵置零
 *
 * 题目概要：若 matrix[i][j]==0，则将第 i 行和第 j 列全部置 0。要求 O(1) 额外空间。
 *
 * 解法说明：用第一行、第一列作为标记位。先记录第一行、第一列是否有 0；再遍历 [1..m)[1..n)，
 * 若 (i,j) 为 0 则置 matrix[i][0]=0、matrix[0][j]=0；最后根据首行首列对行列置零。
 * 原因：首行首列在标记阶段会被覆盖，故需预先记录。
 *
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(1)
 */
public class LC73setZeros {

    public void setZeroes(int[][] matrix) {
        boolean isFirstRowZero = false;
        boolean isFirstColZero = false;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) isFirstColZero = true;
        }
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) isFirstRowZero = true;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[0].length; j++) matrix[i][j] = 0;
            }
        }
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < matrix.length; i++) matrix[i][j] = 0;
            }
        }

        if (isFirstColZero) {
            for (int i = 0; i < matrix.length; i++) matrix[i][0] = 0;
        }
        if (isFirstRowZero) Arrays.fill(matrix[0], 0);
    }
}
