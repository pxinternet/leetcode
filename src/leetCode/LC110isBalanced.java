package leetCode;

/**
 * LC110 - 平衡二叉树
 *
 * 题目概要：判断二叉树是否平衡（每个节点左右子树高度差不超过 1）。
 *
 * 解法说明：递归检查。对每个节点计算左右高度，若差>1 则 false；同时递归检查左右子树。
 * 可优化为一次遍历：用 height 返回 -1 表示不平衡，否则返回高度。
 *
 * 时间复杂度：O(n^2) 最坏（每层都求高度）；优化后 O(n)
 * 空间复杂度：O(h)
 */
public class LC110isBalanced {

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }
}
