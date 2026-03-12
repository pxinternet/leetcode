package leetCode;

/**
 * LC111 - 二叉树的最小深度
 *
 * 题目（概要）：求从根到最近叶子节点的最短路径上的节点数（最小深度）。
 * 叶子节点：left 和 right 均为 null。
 *
 * 解法说明：
 * - 核心思想：递归。空节点返回 0；叶子返回 1；否则取左右子最小深度 +1。
 * - 易错点：若只有一侧子树，另一侧为 null 时 minDepth(null) 返回 0，若直接取 min 会得到 0+1=1 从而错误。
 *   正确做法：只对非空子树递归，若两侧都空已在叶子分支返回。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 边界与注意事项：
 * - 空树返回 0
 *
 * 示例：root=[3,9,20,null,null,15,7] → 2（根到 9）
 */
public class LC111minDepth {

    /**
     * 递归求最小深度
     *
     * 关键点：仅对非空子节点递归，避免单子树时误用 null 的 0
     *
     * @param root 根节点
     * @return 最小深度
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;

        int res = Integer.MAX_VALUE;
        if (root.left != null) res = Math.min(res, minDepth(root.left));
        if (root.right != null) res = Math.min(res, minDepth(root.right));
        return res + 1;
    }
}
