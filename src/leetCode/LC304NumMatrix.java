package leetCode;

public class LC304NumMatrix {
    int[][] sum;
    public LC304NumMatrix(int[][] matrix) {
        int m = matrix.length; //行
        int n = matrix[0].length; //列
        sum = new int[m+1][n+1];

        for(int i = 0; i < m; i++) {
            sum[i][0] = 0;
        }

        for(int i = 0; i < n; i++) {
            sum[0][i] = 0;
        }

        for (int i = 1; i <=m; i++) {
            for (int j = 1; j <=n; j++) {
                sum[i][j] = sum[i-1][j] + sum[i][j -1] - sum[i-1][j-1] + matrix[i-1][j-1];
             }
        }
    }

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
