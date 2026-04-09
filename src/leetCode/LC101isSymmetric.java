package leetCode;

/**
 * LC101 - 对称二叉树
 *
 * 题目（概要）：给定二叉树根节点 root，判断是否轴对称（镜像对称）。
 * 轴对称：左子树与右子树互为镜像。
 *
 * 解法说明：
 * - 核心思想：递归比较左右子树是否互为镜像。t1 与 t2 镜像等价于：值相等，
 *   且 t1 的左与 t2 的右镜像、t1 的右与 t2 的左镜像。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 边界与注意事项：
 * - 空树视为对称
 *
 * 示例：root=[1,2,2,3,4,4,3] → true；root=[1,2,2,null,3,null,3] → false
 */
public class LC101isSymmetric {

    /**
     * 判断二叉树是否轴对称
     *
     * @param root 根节点
     * @return 是否对称
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    /**
     * 判断两棵树是否互为镜像（值相等且结构镜像）
     *
     * 关键点：t1 的左与 t2 的右比，t1 的右与 t2 的左比
     *
     * @param t1 左子树根
     * @param t2 右子树根
     * @return 是否镜像
     */
    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;

        return (t1.val == t2.val)
                && isMirror(t1.right, t2.left)   // t1 右 对 t2 左
                && isMirror(t1.left, t2.right);  // t1 左 对 t2 右
    }

    public static void main(String[] args) {
        LC101isSymmetric solver = new LC101isSymmetric();
        TreeNode t1 = TreeNode.fromArray(new Integer[]{1,2,2,3,4,4,3});
        TreeNode t2 = TreeNode.fromArray(new Integer[]{1,2,2,null,3,null,3});
        System.out.println("t1 symmetric: " + solver.isSymmetric(t1) + " (期望: true)");
        System.out.println("t2 symmetric: " + solver.isSymmetric(t2) + " (期望: false)");
    }
}
