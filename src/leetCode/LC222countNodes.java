package leetCode;

/**
 * LC222 - 完全二叉树的节点个数
 *
 * 题目概要：求完全二叉树的节点总数。
 *
 * 解法一（本实现）：普通 DFS 计数。也可利用完全二叉树性质：若左子树高度=右子树高度，
 * 则左子树为满二叉树，节点数 2^h-1 + 1（根）+ 右子树递归。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 */
public class LC222countNodes {

    private int count = 0;

    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        count++;
        countNodes(root.left);
        countNodes(root.right);
        return count;
    }
}
