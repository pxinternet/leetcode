package leetCode;

public class LC222countNodes {

    int count = 0;
    public int countNodes(TreeNode root) {
        if(root == null) return 0;
        count++;
        countNodes(root.left);
        countNodes(root.right);

        return count;
    }
}
