package leetCode;

public class LC36isValidSudoku {

    /**
     * 这个问题的解法是基于数独的规则来的。数独的规则是每行、每列和每个 3x3 的子数独中的所有数字都必须是 1-9，且不能重复。  在这个解法中，我们使用了三个二维布尔数组 rows、cols 和 boxes 来分别记录每行、每列和每个 3x3 的子数独中的数字是否已经出现过。数组的第一维表示行、列或子数独的索引，第二维表示数字（减去 1 后的结果）。  然后我们遍历数独的每个单元格。对于每个单元格，我们首先检查它是否为空。如果它为空，我们就跳过它。如果它不为空，我们就获取它的数字（减去 1 后的结果），并计算它所在的子数独的索引。  然后我们检查这个数字在当前行、当前列和当前子数独中是否已经出现过。如果它已经出现过，那么我们就返回 false，因为这违反了数独的规则。如果它没有出现过，我们就在 rows、cols 和 boxes 中标记这个数字已经出现过。  在遍历完所有的单元格后，我们返回 true，表示这个数独是有效的。  这个解法的正确性是基于数独的规则的。我们通过记录每行、每列和每个子数独中的数字是否已经出现过，来确保数独的有效性。如果我们在遍历过程中发现了任何违反数独规则的情况，我们就立即返回 false。如果我们遍历完所有的单元格都没有发现违反数独规则的情况，我们就返回 true。因此，这个解法是正确的。
     * @param board
     * @return
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

                //计算区块索引
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
