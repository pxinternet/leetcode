package leetCode;

/**
 * LC36isValidSudoku - 有效的数独
 *
 * 题目（概要）：判断 9×9 数独是否有效。有效指：每行、每列、每个 3×3 宫格内数字 1-9 不重复。
 *
 * 解法说明：用三个布尔数组 rows、cols、boxes 分别记录每行/列/宫格中数字是否已出现；boxIndex = (i/3)*3 + j/3
 *
 * 时间复杂度：O(81) = O(1)
 * 空间复杂度：O(1)
 */
public class LC36isValidSudoku {

    /**
     * 判断数独是否有效
     *
     * @param board 9×9 数独棋盘，'.' 表示空
     * @return 有效返回 true
     */
    public boolean isValidSudoku(char[][] board) {
        //用hash表进行一次遍历；
        //9行每行 9个数
        boolean[][] rows = new boolean[9][9];
        //9列每行9个数
        boolean[][] cols = new boolean[9][9];
        //9个BOX区间，每个区间九个数
        boolean[][] boxes = new boolean[9][9];


        //循环遍历矩阵
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)  {
                if (board[i][j] == '.') continue;
                int num = board[i][j] - '1';

                //计算区块索引，区块索引这个要消化一下
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
