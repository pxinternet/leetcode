package leetCode;

/**
 * LC100 - 相同的树
 *
 * 题目（概要）：给定两棵二叉树的根节点 p、q，判断它们在结构上和节点值上是否完全相同。
 *
 * 解法说明：
 * - 核心思想：递归。若两节点都为空则相同；若一个空一个非空则不同；值不同则不同；
 *   否则递归比较左右子树。
 *
 * 时间复杂度：O(n)，n 为节点数
 * 空间复杂度：O(h)，h 为树高
 *
 * 边界与注意事项：
 * - 两空树视为相同
 *
 * 示例：p=[1,2,3], q=[1,2,3] → true；p=[1,2], q=[1,null,2] → false
 */
public class LC100isSameTree {

    /**
     * 递归判断两棵树是否相同（结构+值）
     *
     * 关键点：p==null || q==null 时 return p==q 可同时处理 (null,null) 与 (null,非null)
     *
     * @param p 第一棵树根节点
     * @param q 第二棵树根节点
     * @return 是否相同
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;   // 都空 true，一空一非空 false
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
