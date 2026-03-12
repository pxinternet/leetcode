package leetCode;

/**
 * LC124 - 二叉树中的最大路径和
 *
 * 题目概要：在二叉树中找一条路径，使路径上节点值之和最大。路径至少包含一个节点。
 *
 * 解法说明：后序遍历，对每个节点计算 maxGain（向父节点方向能贡献的最大路径和）。
 * 以该节点为「拐点」的路径和 = node.val + leftGain + rightGain；用 Math.max(子路径, 0) 表示不选负收益子路径。
 * 原因：路径只能「向上」贡献一条分支，故向上返回值只能是 node.val + max(left, right)；拐点路径在递归中更新全局 max。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 */
public class LC124maxPathSum {

    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    /**
     * @return 以 node 为根、向父节点方向可贡献的最大路径和
     */
    private int maxGain(TreeNode node) {
        if (node == null) return 0;

        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);

        int priceNewPath = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, priceNewPath);

        return node.val + Math.max(leftGain, rightGain);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        LC124maxPathSum lc124maxPathSum = new LC124maxPathSum();

        int res = lc124maxPathSum.maxPathSum(root);

        System.out.println(res);

    }

}
