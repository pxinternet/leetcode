package leetCode;

import java.util.HashSet;
import java.util.Set;

public class LC52totalNQueens {

    Set<Integer>  colSet = new HashSet<>();
    Set<Integer> dia1 = new HashSet<>();
    Set<Integer> dia2 = new HashSet<>();

    public int totalNQueens(int n) {

        return dfs(n, 0);

    }

    private int dfs(int n, int row) {
        if (row == n) {
            return 1;
        }
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
