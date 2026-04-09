package leetCode;

/**
 * LC226invertTree - 翻转二叉树
 *
 * 题目（概要）：给定二叉树根节点，翻转每个节点的左右子节点，返回新的根节点。
 *
 * 解法说明：
 * - 核心思想：递归，交换左右子树并递归处理
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h 为树高
 *
 * 边界与注意事项：
 * - 空树返回 null
 *
 * 示例：[4,2,7,1,3,6,9] → [4,7,2,9,6,3,1]
 */
public class LC226invertTree {

    /**
     * 递归翻转二叉树的左右子树
     *
     * 关键点：先保存左子引用 temp，再递归翻转右子赋给 left，递归翻转原左子赋给 right
     *
     * @param root 二叉树根节点
     * @return 翻转后的根节点
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode temp = root.left;                       // 保存左子，因 root.left 马上被覆盖
        root.left = invertTree(root.right);               // 左指针指向翻转后的右子树
        root.right = invertTree(temp);                    // 右指针指向翻转后的原左子树
        return root;
    }
}
