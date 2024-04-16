package leetCode;

public class LC37solveSudoku {


    //直接回溯超时了
    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }

    private void backtrack(char[][] board, int i, int j) {
        if (j == 9) {
            //处理下一列
            backtrack(board, i + 1, 0);
            return;
        }

        if (i == 9) {
            //所有行的处理完了
            return;
        }

        if (board[i][j] != '.') {
            //有数字，处理下一列
            backtrack(board, i, j +1);
        }


        //如果不然，开始处理1 - 9;
        for (char c = '1'; c <= '9'; c++) {

            if (!isValid(board, i, j, c))
                continue;

            board[i][j] = c;
            backtrack(board, i, j + 1);
            board[i][j] = '.';
        }
    }

    private boolean isValid(char[][] board, int r, int c, char ch) {
        for (int i = 0; i < 9; i++) {
            if (board[r][i] == ch) return false;
            if (board[i][c] == ch) return false;
            if (board[(r/3) * 3 + i /3][(c / 3) * 3 + i % 3] == ch) return false;
        }
        return true;
    }


    //第一种改进方式
    public void solveSudokuBetter(char[][] board) {
        //行
        boolean[][] rows = new boolean[9][9];
        //列
        boolean[][] cols = new boolean[9][9];
        //box
        boolean[][] boxes = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int boxIndex = (i / 3) * 3 + (j / 3);
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }
        backtrack(board, 0, 0, rows, cols, boxes);
    }

    private boolean backtrack(char[][] board, int i, int j, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        if (j == 9) {
            return backtrack(board, i+1, 0, rows, cols, boxes);
        }
        if (i == 9) {
            return true;
        }

        if (board[i][j] != '.') {
            return backtrack(board, i, j + 1, rows, cols, boxes);
        }

        int boxIndex = (i / 3) *3 + j / 3;

        for (char ch = '1'; ch <= '9'; ch++) {
            int num = ch - '1';
            if (rows[i][num] || cols[j][num] || boxes[boxIndex][num]) {
                continue;
            }
            board[i][j] = ch;
            rows[i][num] = true;
            cols[j][num] = true;
            boxes[boxIndex][num] = true;
            if (backtrack(board, i, j +1, rows, cols, boxes)) {
                return true;
            }
            board[i][j] = '.';
            rows[i][num] = false;
            cols[j][num] = false;
            boxes[boxIndex][num] = false;
        }
        return false;
    }

}
