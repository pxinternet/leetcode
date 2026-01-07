package leetCode;

/**
 * LC101 对称二叉树
 *
 * 思路：递归比较左子树和右子树是否互为镜像，或使用队列迭代比较对应节点。
 */
public class LC101isSymmetric {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;

        return (t1.val == t2.val) && isMirror(t1.right, t2.left) && isMirror(t1.left, t2.right);
    }

    public static void main(String[] args) {
        LC101isSymmetric solver = new LC101isSymmetric();
        TreeNode t1 = TreeNode.fromArray(new Integer[]{1,2,2,3,4,4,3});
        TreeNode t2 = TreeNode.fromArray(new Integer[]{1,2,2,null,3,null,3});
        System.out.println("t1 symmetric: " + solver.isSymmetric(t1) + " (期望: true)");
        System.out.println("t2 symmetric: " + solver.isSymmetric(t2) + " (期望: false)");
    }
}
