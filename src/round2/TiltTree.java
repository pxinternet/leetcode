package round2;

import leetCode.TreeNode;

public class TiltTree {

    private int totalTilt = 0;

    public int findTilt(TreeNode root) {
        postOrderSum(root);
        return totalTilt;
    }

    private int postOrderSum(TreeNode node) {

        if (node == null)
            return 0;

        int leftSum = postOrderSum(node.left);
        int rightSum = postOrderSum(node.right);

        int nodeTitle = Math.abs(leftSum - rightSum);

        totalTilt += nodeTitle;

        return node.val + leftSum + rightSum;

    }

}
