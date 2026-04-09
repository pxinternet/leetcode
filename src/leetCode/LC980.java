package leetCode;

/**
 * LC980 - 不同路径 III
 *
 * 题目（概要）：网格 0 可走、1 起点、2 终点、-1 障碍，必须经过所有 0 恰好一次，求从起点到终点的路径数。
 *
 * 解法说明：DFS 回溯。统计需经过的空格数 empty，从起点 DFS，经过则标记、empty--，到终点且 empty==0 计数 1，回溯恢复。
 *
 * 时间复杂度：O(4^(m*n))
 * 空间复杂度：O(m*n)
 */
public class LC980 {
    int c;
    int r;

    int empty =1; //包含1,1这个点
    int start_i, start_j, end_i, end_j;

    //方向，右左下上 四个方向
    int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    public int uniquePathsIII(int[][] grid) {

        c = grid.length;
        r = grid[0].length;

        //先找到开始和结束位置，以及要走过多少空格
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                if (grid[i][j] == 1) {
                    start_i = i;
                    start_j = j;
                }
                if (grid[i][j] == 2) {
                    end_i = i;
                    end_j = j;
                } else if (grid[i][j] == 0) {
                    empty++;
                }
            }
        }

        return dfs(grid, start_i, start_j);

    }

    //totalPaths的最大值不一定是4

    private int dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= c || j < 0 || j >= r || grid[i][j]  < 0) {
            return 0;
        }
        if (i == end_i && j == end_j) {
            return empty == 0? 1:0;
        }

        grid[i][j] = -2;
        empty--;

        int totalPaths = 0;
        for (int[] dir : dirs) {
            //totalPath 是因为要往4个方向找，假设4个方向都有，那就返回4;
            totalPaths += dfs(grid, i + dir[0], j + dir[1]);
        }

        grid[i][j] = 0;
        empty++;

        return totalPaths;
    }
}
