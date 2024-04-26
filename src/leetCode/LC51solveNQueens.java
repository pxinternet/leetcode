package leetCode;

import sun.security.x509.AttributeNameEnumeration;

import java.util.*;

public class LC51solveNQueens {
    //是否在同一列
    Set<Integer> colSet = new HashSet<>();
    Set<Integer> diagonal1 = new HashSet<>();
    Set<Integer> diagonal2 = new HashSet<>();
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
                dfs(board, row+1, res);
                colSet.remove(col);
                diagonal1.remove(row + col);
                diagonal2.remove(row - col);

                board[row][col] = '.';
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
