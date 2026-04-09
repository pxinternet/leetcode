package leetCode;

/**
 * LC104maxDepth - 二叉树的最大深度
 *
 * 题目（概要）：给定二叉树根节点 root，求从根到最远叶子节点的最长路径上的节点数（即最大深度）。
 *
 * 解法说明：
 * - 核心思想：递归，当前深度 = 1 + max(左子树深度, 右子树深度)
 * - 空树深度为 0
 *
 * 时间复杂度：O(n)，n 为节点数
 * 空间复杂度：O(h)，h 为树高（递归栈）
 *
 * 边界与注意事项：
 * - 空树返回 0
 *
 * 示例：[3,9,20,null,null,15,7] → 3
 */
public class LC104maxDepth {

    /**
     * 递归计算二叉树最大深度
     *
     * 关键点逐行说明：
     * - 空树深度为 0
     * - 非空树深度 = 1（当前节点）+ max(左子树深度, 右子树深度)
     * - 例如：根 3，左深 1，右深 2，则总深度 = 1 + 2 = 3
     *
     * @param root 二叉树根节点
     * @return 最大深度，空树返回 0
     */
    public int maxDepth(TreeNode root) {
        // 边界条件：空树深度为 0
        if (root == null) return 0;
        // 当前层贡献 1，加上左右子树中较大的深度
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static void main(String[] args) {
        LC104maxDepth solver = new LC104maxDepth();
        TreeNode root = TreeNode.fromArray(new Integer[]{3,9,20,null,null,15,7});
        System.out.println("maxDepth: " + solver.maxDepth(root));
        System.out.println("期望: 3");
    }
}
