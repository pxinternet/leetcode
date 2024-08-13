package tree;

import leetCode.TreeNode;

import javax.swing.RootPaneContainer;

public class LC437pathSum {

    public int pathSum(TreeNode root, long targetSum) {
        if (root == null) return 0;

        return pathSumFrom(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
    }

    private int pathSumFrom(TreeNode node, long targetSum) {

        if (node == null) return 0;
        return (node.val == targetSum ? 1 : 0) + pathSumFrom(node.left, targetSum - node.val) + pathSumFrom(node, targetSum - node.val);

    }

}
