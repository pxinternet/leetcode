package leetCode;

/**
 * LC200numIslands - 岛屿数量
 *
 * 题目（概要）：给定二维网格 grid，'1' 陆地 '0' 水，岛屿由上下左右相邻的陆地连接形成。求岛屿数量。
 *
 * 算法原理：
 * - 连通块等价性：DFS 与 BFS 均可一次遍历标记整块连通陆地，结果等价。DFS 用递归栈，BFS 用队列。
 * - 沉岛策略：访问过的陆地置为 '0'，避免重复计数。每次主循环遇到 '1' 说明是新岛屿，计数后 DFS 将其整块沉没。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：遍历每个格子，若 grid[i][j]=='1'，ans++，调用 dfs(i,j)。
 * - 步骤 2：dfs 中若越界或为水则 return；否则置 grid[i][j]='0'，向上下左右递归。
 *
 * 关键洞察：
 * - 沉岛即隐式 visited，无需额外 visit 数组，节省空间。
 * - 与 LC695 最大岛屿面积类似，都是连通块 DFS/BFS。
 *
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n) 递归栈
 *
 * 示例：grid = [["1","1","0"],["1","1","0"],["0","0","1"]] → 2
 */
public class LC200numIslands {

    /**
     * 计算岛屿数量
     *
     * @param grid 二维网格，'1' 陆地 '0' 水
     * @return 岛屿数量
     */
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    /**
     * DFS 将 (i,j) 所在的整块陆地沉为 '0'，避免重复计数
     *
     * 关键点：先判越界与水，再标记为 '0'，然后向四方向递归
     *
     * @param grid 网格
     * @param i    行索引
     * @param j    列索引
     */
    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';   // 沉岛，避免重复访问
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }


}
