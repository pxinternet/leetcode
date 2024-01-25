package leetCode;

public class LC48rotate {

    public void rotate(int[][] matrix) {
        //分两步，沿着对角线互换
        //然后每行互换

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
            while(start < end) {
                int tmp = matrix[i][start];
                matrix[i][start] = matrix[i][end];
                matrix[i][end] = tmp;
                start++;
                end--;
            }
        }



    }



}
