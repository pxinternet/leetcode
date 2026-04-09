package leetCode;

/**
 * LC130solve - 被围绕的区域
 *
 * 题目（概要）：给定 m×n 矩阵 board，'X' 和 'O' 表示单元格。将被 'X' 围绕的 'O' 全部改为 'X'。
 * 与边界相连的 'O' 不会被围绕。
 *
 * 解法说明：
 * - 核心思想：从四条边的 'O' 出发 DFS/BFS，将所有能到达的 'O' 标记为临时字符（如 'A'）
 * - 这些 'A' 即与边界连通的 'O'，不会被改为 'X'
 * - 最后遍历：'O' 改为 'X'（被围绕的），'A' 恢复为 'O'
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)，递归栈最坏情况
 *
 * 边界与注意事项：
 * - board 为空直接返回
 *
 * 示例：board=[["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * → [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 */
public class LC130solve {

    private int m, n;
    // 四个方向：右、左、下、上
    private int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private char[][] board;

    /**
     * 将被围绕的 'O' 改为 'X'
     *
     * 关键点逐行说明：
     * - 从四条边界的 'O' 出发 DFS，标记所有连通区域为 'A'
     * - 最后遍历：'O'→'X'，'A'→'O'
     *
     * @param board 字符矩阵，会被原地修改
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        this.board = board;
        m = board.length;
        n = board[0].length;

        // 从左右两列的边界 'O' 出发 DFS
        for (int i = 0; i < m; i++) {
            dfs(i, 0);
            dfs(i, n - 1);
        }

        // 从上下两行的边界 'O' 出发 DFS
        for (int i = 0; i < n; i++) {
            dfs(0, i);
            dfs(m - 1, i);
        }

        // 遍历整个矩阵：被围绕的 'O' 改为 'X'，与边界连通的 'A' 恢复为 'O'
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

    /**
     * 从 (x,y) 出发 DFS，将与边界连通的 'O' 标记为 'A'
     *
     * 关键点：
     * - 越界或非 'O' 则返回
     * - 将 'O' 改为 'A' 避免重复访问
     * - 向四方向递归
     *
     * @param x 行坐标
     * @param y 列坐标
     */
    private void dfs(int x, int y) {
        // 边界条件：越界或不是 'O'（已是 'X' 或 'A'）则返回
        if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != 'O') {
            return;
        }
        // 标记为 'A'，表示与边界连通，后续会恢复为 'O'
        board[x][y] = 'A';
        for (int[] d : direction) {
            dfs(x + d[0], y + d[1]);
        }
    }
}
