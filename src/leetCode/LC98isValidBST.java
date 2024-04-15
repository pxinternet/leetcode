package leetCode;

import java.util.Deque;
import java.util.LinkedList;

public class LC98isValidBST {

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);

    }

    private boolean isValidBST(TreeNode node, long minVal, long maxVal) {
        if (node == null) return true;

        if (node.val <= minVal || node.val >= maxVal) return false;

        return isValidBST(node.left, minVal, node.val)
                && isValidBST(node.right, node.val, maxVal);
    }


    //中序遍历可以走一遍

    public boolean isValidBSTInOrder(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();

        int inOrder = Integer.MIN_VALUE;
        if (root == null) return true;


        while (!stack.isEmpty() || root !=null) {
            while(root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.val <= inOrder) {
                return false;
            }
            inOrder = root.val;
            root = root.right;
        }
        return true;
    }


    private Long inOrderG = Long.MIN_VALUE;
    public boolean isValidBSTInOrder2(TreeNode root) {
        if (root == null) return true;
        boolean res1 = isValidBST(root.left);
        if (root.val <= inOrderG) {
            return false;
        }
        inOrderG = (long) root.val;
        boolean res2 = isValidBST(root.right);

        return res1 && res2;
    }

}
