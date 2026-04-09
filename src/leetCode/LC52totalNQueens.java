package leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * LC52 - N 皇后 II
 *
 * 题目（概要）：在 n×n 棋盘放 n 个皇后，使任意两个不在同一行、列、对角线。返回解的个数。
 *
 * 解法说明：
 * - 核心思想：同 LC51，DFS 按行放置。colSet、dia1(row+col)、dia2(row-col) 记录占用；每行枚举列，合法则递归下一行并累加方案数。
 *
 * 时间复杂度：O(n!)
 * 空间复杂度：O(n)
 *
 * 示例：n=4 → 2
 */
public class LC52totalNQueens {

    private Set<Integer> colSet = new HashSet<>();
    private Set<Integer> dia1 = new HashSet<>();
    private Set<Integer> dia2 = new HashSet<>();

    /**
     * 返回 N 皇后解的个数
     */
    public int totalNQueens(int n) {
        return dfs(n, 0);
    }

    private int dfs(int n, int row) {
        if (row == n) return 1;

        int count = 0;
        for (int col = 0; col < n; col++) {
            if (colSet.contains(col) || dia1.contains(row + col) || dia2.contains(row - col)) {
                continue;
            }
            colSet.add(col);
            dia1.add(row + col);
            dia2.add(row - col);
            count += dfs(n, row + 1);
            colSet.remove(col);
            dia1.remove(row + col);
            dia2.remove(row - col);
        }
        return count;
    }
}
