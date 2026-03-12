package leetCode;

/**
 * LC304 - 二维区域和检索 - 矩阵不可变
 *
 * 题目（概要）：给定二维矩阵，多次查询子矩阵 (row1,col1) 到 (row2,col2) 的元素和。
 *
 * 解法说明：
 * - 核心思想：二维前缀和。sum[i][j] 表示 (0,0) 到 (i-1,j-1) 的和。
 * - sumRegion = sum[r2+1][c2+1] - sum[r2+1][c1] - sum[r1][c2+1] + sum[r1][c1]
 *
 * 时间复杂度：构造 O(m*n)，查询 O(1)
 * 空间复杂度：O(m*n)
 */
public class LC304NumMatrix {

    private final int[][] sum;

    public LC304NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        sum = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
    }

    /** 返回 (row1,col1) 到 (row2,col2) 的子矩阵和 */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum[row2 + 1][col2 + 1] - sum[row2 + 1][col1] - sum[row1][col2 + 1] + sum[row1][col1];
    }

    public static void main(String[] args) {
        int[][] m = new int[][] {
                {3,0,1,4,2},
                {5,6,3,2,1},
                {1,2,0,1,5},
                {4,1,0,1,7},
                {1,0,3,0,5}
        };

        LC304NumMatrix numMatrix = new LC304NumMatrix(m);

        Tools.printMatrix(numMatrix.sum);


    }


}
