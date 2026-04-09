package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LC51 - N 皇后
 *
 * 题目（概要）：在 n×n 棋盘放 n 个皇后，使任意两个不在同一行、列、对角线。返回所有解。
 *
 * 算法原理：
 * - 行约束：按行放置，每行放一个，天然满足「不同行」。
 * - 列约束：colSet 记录已占用的列，同一列不能放两个。
 * - 对角线约束：同一主对角线（左上到右下）上 row-col 相同；同一副对角线（右上到左下）上 row+col 相同。故 diagonal1=row+col，diagonal2=row-col。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：dfs(row)，若 row==n 则构造解并加入 res。
 * - 步骤 2：枚举 col，若 validate（列、两条对角线均未占用）则放置皇后，标记约束，递归 row+1，回溯恢复。
 * - 步骤 3：validate 检查 colSet、diagonal1、diagonal2 是否包含。
 *
 * 关键洞察：
 * - row+col 与 row-col 将两条对角线唯一映射到整数，便于 HashSet 判重。
 * - 回溯时务必 remove 与 board 恢复，否则会污染后续分支。
 *
 * 时间复杂度：O(n!)
 * 空间复杂度：O(n)
 *
 * 示例：n=4 → [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 */
public class LC51solveNQueens {

    private Set<Integer> colSet = new HashSet<>();
    private Set<Integer> diagonal1 = new HashSet<>();   // row+col 主对角线
    private Set<Integer> diagonal2 = new HashSet<>();  // row-col 副对角线
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (char[] chars : board) {
            Arrays.fill(chars, '.');
        }
        List<List<String>> res = new ArrayList<>();
        dfs(board, 0, res);
        return res;
    }

    private void dfs(char[][] board, int row, List<List<String>> res) {
        if (row == board.length) {
            res.add(construct(board));
            return;
        }
        for (int col = 0; col < board.length; col++) {
            if (validate(board, row, col)) {
                board[row][col] = 'Q';
                colSet.add(col);
                diagonal1.add(row + col);
                diagonal2.add(row - col);
                dfs(board, row + 1, res);
                colSet.remove(col);
                diagonal1.remove(row + col);
                diagonal2.remove(row - col);
                board[row][col] = '.';  // 回溯恢复
            }
        }
    }
    private boolean validate(char[][] board, int row, int col) {
        if (colSet.contains(col) || diagonal1.contains(row + col) || diagonal2.contains(row - col)) {
            return false;
        }
        return true;
    }
    private List<String> construct(char[][] board) {
        List<String> res = new ArrayList<String>();
        for (char[] chars : board) {
            res.add(String.valueOf(chars));
        }
        return res;
    }
}
