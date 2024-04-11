package leetCode;



public class LC124maxPathSum {

    //这里要明确两个概念，左子树的贡献和右子树的贡献，如果左子树 + 右子树 +当前节点，就是一个路径和。     左子树 + 当前节点，或右子树 + 当前节点，分别是，当前节点，对父节点路径和的贡献

    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

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
