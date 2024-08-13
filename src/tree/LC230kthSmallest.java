package tree;

import leetCode.TreeNode;

public class LC230kthSmallest {

    int count = 0;
    int result = 0;

    public int kthSmallest(TreeNode root, int k) {
        inOrder(root, k);
        return result;
    }

    public void inOrder(TreeNode root, int k) {
        if (root == null) return;

        inOrder(root.left, k);
        count++;
        if (count == k) {
            result = root.val;
            return;
        }
        inOrder(root.right, k);
    }

}
