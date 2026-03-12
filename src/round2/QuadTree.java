package round2;

import java.util.HashSet;
import java.util.Set;

/**
 * QuadTree - 四叉树构造（LeetCode 427）
 *
 * 题目（概要）：给定 n×n 的二值网格 grid（0 或 1），构造对应的四叉树。每个节点为叶子（全 0 或全 1）
 * 或非叶子（四个子节点对应左上、右上、左下、右下四块）。
 *
 * 算法原理：
 * - 递归分治：若当前块内值全同则为叶子；否则四分递归构造四个子节点。
 * - 叶子判定：遍历块内所有格子，任一不同则非叶子。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：递归入口 construct(grid, row, col, size)。
 * - 步骤 2：若 isLeaf(grid, row, col, size) 为真，返回 new Node(val, true)。
 * - 步骤 3：否则 halfSize=size/2，递归构造四块 (row,col)、(row,col+halfSize)、(row+halfSize,col)、(row+halfSize,col+halfSize)。
 * - 步骤 4：返回 new Node(false, false, topLeft, topRight, bottomLeft, bottomRight)。
 *
 * 关键洞察：叶子块的值取 grid[row][col]，因块内全同；四分子块的划分与坐标计算需与 halfSize 一致。
 *
 * 时间复杂度：O(n² log n) 或 O(n²)（取决于块均匀程度）
 * 空间复杂度：O(log n) 递归栈
 *
 * 示例：2×2 全 1 网格 → 单个叶子节点 val=true, isLeaf=true
 */
public class QuadTree {

    class Node {
        public boolean val;
        public boolean isLeaf;

        public Node topLeft;
        public Node topRight;

        public Node bottomLeft;
        public Node bottomRight;

        Set<Integer> testSet = new HashSet<>();

        public Node() {

        }

        public Node(boolean _val, boolean _isLeaf) {
            this.val = _val;
            this.isLeaf = _isLeaf;
        }

        public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
            this.val = _val;
            this.isLeaf = _isLeaf;
            this.topLeft = _topLeft;
            this.topRight = _topRight;
            this.bottomLeft = _bottomLeft;
            this.bottomRight = _bottomRight;
        }
    }

    public Node construct(int[][] grid) {
        return construct(grid, 0, 0, grid.length);
    }

    public Node construct(int[][] grid, int row, int col, int size) {
        if (isLeaf(grid, row, col, size)) {
            return new Node(grid[row][col] == 1, true);
        }
        int halfSize = size / 2;
        Node topLeft = construct(grid, row, col, halfSize);
        Node topRight = construct(grid, row, col + halfSize, halfSize);
        Node bottomLeft = construct(grid, row + halfSize, col, halfSize);
        Node bottomRight = construct(grid, row + halfSize, col + halfSize, halfSize);

        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);

    }

    private boolean isLeaf(int[][] grid, int row, int col, int size) {
        int val = grid[row][col];
        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (grid[i][j] != val) {
                    return false;
                }
            }
        }
        return true;
    }

}
