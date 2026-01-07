package leetCode;



public class LC124maxPathSum {

    // 题目：二叉树中的最大路径和（LC124）
    // 思路：对每个节点，计算其向父节点贡献的最大路径和（maxGain），以及以该节点为根的最大路径（leftGain + node.val + rightGain），更新全局最大值。

    // 全局变量保存遍历过程中遇到的最大路径和
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        // 启动递归计算并返回全局最大
        maxGain(root);
        return maxSum;
    }

    // 返回值含义：以 node 为根，向上（父节点方向）能够贡献的最大路径和（即 node + max(leftGain, rightGain, 0)）
    private int maxGain(TreeNode node) {
        if (node == null) return 0;

        // 如果左/右子路径为负数，则以 0 代替（不选取该子路径）
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);

        // 以当前节点为根节点的最大路径和（可能穿过节点并连接左右子树）
        int priceNewPath = node.val + leftGain + rightGain;

        // 更新全局最大
        maxSum = Math.max(maxSum, priceNewPath);

        // 返回当前节点向上能贡献的最大和（用于其父节点的计算）
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
