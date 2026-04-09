package leetCode;

/**
 * LC79exist - 单词搜索
 *
 * 题目（概要）：给定二维字符网格 board 和字符串 word，判断 word 是否存在于网格中。单词必须按相邻单元格（上下左右）连接形成。
 *
 * 解法说明：
 * - 核心思想：回溯 + DFS，从每个格子出发尝试匹配 word，用 visited 避免重复使用
 *
 * 时间复杂度：O(m*n*4^len)
 * 空间复杂度：O(m*n)，visited 与递归栈
 *
 * 示例：board=[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word="ABCCED" → true
 */
public class LC79exist {

    /**
     * 判断 word 是否存在于 board 中
     *
     * @param board 二维字符网格
     * @param word  待查找单词
     * @return 存在返回 true
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 从 (i,j) 开始 DFS 匹配 word[index..]
     */
    private boolean dfs(char[][] board, String word, int i, int j, int index, boolean[][] visited) {
        if (index == word.length()) {
            return true;
        }

        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] || board[i][j] != word.charAt(index) ) {
            return false;
        }

        visited[i][j] = true;

        boolean found = dfs(board, word, i + 1, j, index + 1, visited)
                        || dfs(board, word,  i - 1, j, index + 1, visited)
                        || dfs(board, word, i, j - 1, index + 1, visited)
                        || dfs(board, word, i, j + 1, index + 1, visited);

        visited[i][j] = false;
        return found;
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;

        return (getKth(nums1, 0, m - 1, nums2, 0, n -1, left) + getKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;
    }

    private int getKth(int[] nums1, int start1, int end1,  int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);

        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k/2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }

    }



}
