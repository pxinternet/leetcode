package matrix;

public class LLC329LongestIncreasingPath {

    int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        int[][] cache = new int[m][n];
        int maxLength = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxLength = Math.max(maxLength, dfs(matrix, i, j, cache));
            }
        }
        return maxLength;
    }

    private int dfs(int[][] matrix, int i, int j, int[][] cache) {
        if (cache[i][j] != 0) {
            return cache[i][j];
        }

        int max = 1;

        for (int[] dir : directions) {

            int x = i + dir[0];
            int y = j + dir[1];

            if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && matrix[x][y] > matrix[i][j]) {
                max = Math.max(max, 1 + dfs(matrix, x, y, cache));
            }
        }

        cache[i][j] = max;
        return max;

    }

}
