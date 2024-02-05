package leetCode;

public class LC236 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        //先处理pq有父子关系
        if (root.val == p.val) return p;
        if (root.val == q.val) return q;

        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        if(leftRes != null && rightRes != null) return root;

        if (leftRes != null) return leftRes;

        return rightRes;
    }
}


