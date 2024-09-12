package round2;

public class QuadTree {

    class Node {
        public boolean val;
        public boolean isLeaf;

        public Node topLeft;
        public Node topRight;

        public Node bottomLeft;
        public Node bottomRight;

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
