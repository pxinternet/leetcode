package leetCode;

/**
 * LC236 - 二叉树的最近公共祖先
 *
 * 题目（概要）：给定二叉树根节点 root 和两个节点 p、q，求 p 和 q 的最近公共祖先。
 * 最近公共祖先：同时为 p、q 祖先的节点中深度最大的（一个节点可以是自己的祖先）。
 *
 * 解法说明：
 * - 核心思想：递归。若 root 为 null 或等于 p 或 q，直接返回 root
 * - 递归求 left=左子树中的 LCA 结果，right=右子树中的 LCA 结果
 * - 若 left、right 均非 null，说明 p、q 分居两侧，root 即为 LCA
 * - 若仅一侧非 null，则 p、q 都在该侧，返回该侧结果
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 示例：root=[3,5,1,6,2,0,8,null,null,7,4], p=5, q=1 → 3
 */
public class LC236 {

    /**
     * 求节点 p 和 q 的最近公共祖先
     *
     * 关键点逐行说明：
     * - root 为 null：无 LCA，返回 null
     * - root 等于 p 或 q：root 本身就是 p 或 q 的祖先，直接返回（也处理了 p、q 为父子关系的情况）
     * - 递归左右子树，若两边都有非 null 结果，则 root 为 LCA
     * - 否则返回非 null 的一侧（p、q 都在该侧子树中）
     *
     * @param root 当前子树根节点
     * @param p    目标节点之一
     * @param q    目标节点之二
     * @return p 和 q 的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        // 若 root 就是 p 或 q，说明 root 是其中一者的祖先，直接返回
        // 例如：p 是 q 的祖先时，递归到 p 就会返回 p
        if (root.val == p.val) return p;
        if (root.val == q.val) return q;

        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        if (leftRes != null && rightRes != null) return root;

        if (leftRes != null) return leftRes;

        return rightRes;
    }
}
