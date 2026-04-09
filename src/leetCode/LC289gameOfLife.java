package leetCode;

/**
 * LC289 - 生命游戏
 *
 * 题目概要：根据细胞当前状态和 8 邻域存活数，按 Conway 规则同步更新到下一状态。
 * 规则：活细胞 2~3 个邻居存活则存活；死细胞恰 3 个邻居存活则复活；其余死亡。
 *
 * 解法说明：使用辅助矩阵 aliveMatrix 存储下一状态，避免原地更新破坏当前状态。
 * 原因：每个格子依赖邻居的「当前」状态，若原地写会干扰尚未计算的格子。
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 */
public class LC289gameOfLife {

    private static final int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
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
                board[i][j] = aliveMatrix[i][j] ? 1 : 0;
            }
        }
    }

    private boolean isAlive(int[][] board, int i, int j) {
        int m = board.length;
        int n = board[0].length;
        int aliveCount = 0;

        for (int[] d : directions) {
            int r = i + d[0], c = j + d[1];
            if (r >= 0 && r < m && c >= 0 && c < n && board[r][c] == 1) aliveCount++;
        }

        if (aliveCount == 3) return true;
        if (aliveCount == 2 && board[i][j] == 1) return true;
        return false;
    }
}
