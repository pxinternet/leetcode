package leetCode;

/**
 * LC100 判断两棵二叉树是否相同
 *
 * 思路：递归比较两棵树的结构与节点值，短路条件为任一不匹配或结构不同。
 */
public class LC100isSameTree {

    /**
     * 判断两棵树是否相同（结构和值完全一致）
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        LC100isSameTree solver = new LC100isSameTree();
        TreeNode t1 = TreeNode.fromArray(new Integer[]{1,2,3});
        TreeNode t2 = TreeNode.fromArray(new Integer[]{1,2,3});
        TreeNode t3 = TreeNode.fromArray(new Integer[]{1,3,2});

        System.out.println("t1 vs t2: " + solver.isSameTree(t1, t2) + " (期望: true)");
        System.out.println("t1 vs t3: " + solver.isSameTree(t1, t3) + " (期望: false)");
    }
}
