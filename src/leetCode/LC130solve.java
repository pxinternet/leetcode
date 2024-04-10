package leetCode;

public class LC130solve {

    private int m, n;
    private int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private char[][] board;

    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        this.board = board;
        m = board.length;
        n = board[0].length;

        for (int i = 0; i < m; i++) {
            dfs(i, 0);
            dfs(i, n - 1);
        }

        for (int i = 0; i < n; i++) {
            dfs(0, i);
            dfs(m - 1, i);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(int x, int y) {
        if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != 'O') {
            return;
        }
        board[x][y] = 'A';
        for (int[] d : direction) {
            dfs(x + d[0], y + d[1]);
        }
    }
}
