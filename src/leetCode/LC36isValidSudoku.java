package leetCode;

public class LC36isValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        //用hash表进行一次遍历；
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)  {
                if (board[i][j] == '.') continue;
                int num = board[i][j] - '1';
                int boxIndex = (i/3) * 3 + j / 3;
                if (rows[i][num] || cols[j][num] || boxes[boxIndex][num]) {
                    return false;
                } else {
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }
        return true;
    }

}
