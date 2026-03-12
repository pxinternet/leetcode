package leetCode;

/**
 * LC236lowestCommonAncestor - 二叉树的最近公共祖先
 *
 * 题目（概要）：给定二叉树 root 和节点 p、q，求 p 和 q 的最近公共祖先（深度最大的共同祖先）。
 *
 * 算法原理：
 * - 分治：若 p、q 分别在 root 的左右子树，则 root 必为 LCA（因为 root 是连接两子树的唯一点）。
 * - 递归语义：lowestCommonAncestor(root,p,q) 返回「root 子树内 p、q 的 LCA」或「若只有一个在子树内则返回该节点」。
 * - 若 root==p 或 root==q：root 自身可能是 LCA（当另一节点在其子树内），或需向上传递。直接返回 root 可统一处理。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：root 为 null 或 root==p 或 root==q，返回 root。
 * - 步骤 2：left=LCA(root.left)，right=LCA(root.right)。
 * - 步骤 3：若 left、right 均非 null，p、q 分居两侧，root 为 LCA；否则返回非 null 的一侧（或 null）。
 *
 * 关键洞察：
 * - 「两侧都有」则 root 为 LCA 的证明：LCA 必在 root 到 p、q 的路径上，若 p 在左、q 在右，则 root 是唯一交点。
 * - 若只一侧非 null，说明 p、q 同在该侧，LCA 必在该侧子树内，返回该侧递归结果。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 示例：root=[3,5,1,6,2,0,8,null,null,7,4], p=5, q=1 → 3
 */
public class LC236lowestCommonAncestor {

    /**
     * 求节点 p 和 q 的最近公共祖先
     *
     * 关键点：若左右递归均有非 null 结果，则 root 为 LCA；否则返回非 null 的一侧
     *
     * @param root 当前子树根节点
     * @param p    目标节点之一
     * @param q    目标节点之二
     * @return p 和 q 的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;  // p、q 分居两侧，root 为 LCA
        if (left != null) return left;   // p、q 均在左子树
        return right;
    }
}
