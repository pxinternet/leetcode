package leetCode;

public class LC473pathSum {

    public int pathSum(TreeNode root, int targetSum) {
       if (root == null) return 0;

       return pathSumFrom(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
    }

    private int pathSumFrom(TreeNode node, int targetSum) {
        if (node == null) return 0;
        return (node.val == targetSum ? 1 : 0) + pathSumFrom(node.left, targetSum - node.val) + pathSumFrom(node.right, targetSum - node.val);
    }
}
