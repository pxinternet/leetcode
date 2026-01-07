package leetCode;

/**
 * LC104 二叉树的最大深度（递归）
 */
public class LC104maxDepth {

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));

    }

    public static void main(String[] args) {
        LC104maxDepth solver = new LC104maxDepth();
        TreeNode root = TreeNode.fromArray(new Integer[]{3,9,20,null,null,15,7});
        System.out.println("maxDepth: " + solver.maxDepth(root));
        System.out.println("期望: 3");
    }
}
