package leetCode;

public class LC289gameOfLife {

    int[][] directions = new int[][]{
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}
    };


    public void gameOfLife(int[][] board) {

        int m = board.length;
        int n = board[0].length;

        boolean[][] aliveMatrix = new boolean[m][n];


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                aliveMatrix[i][j] = isAlive(board, i, j);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (aliveMatrix[i][j]) {
                    board[i][j] = 1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    private boolean isAlive(int[][] board, int i, int j) {

        int m = board.length;
        int n = board[0].length;
        int aliveCount = 0;

        for (int[] direction : directions) {
            int r = i + direction[0];
            int c = j + direction[1];

            if ((r >= 0 && r < m) && (c >= 0 && c < n) && board[r][c] == 1) {
                aliveCount++;
            }
        }

        if (aliveCount == 3) {
            return true;
        }

        if (aliveCount == 2 && board[i][j] == 1) {
            return true;
        }

        return false;

    }
}
