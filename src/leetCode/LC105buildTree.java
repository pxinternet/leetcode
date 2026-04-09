package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC105buildTree - 从前序与中序遍历序列构造二叉树
 *
 * 题目（概要）：给定前序和中序遍历序列，构造并返回二叉树。假设无重复元素。
 *
 * 解法说明：
 * - 前序第一个元素为根，在中序中找到根的位置可划分左、右子树
 * - 递归：左子树区间 [left, rootIdx-1]，右子树 [rootIdx+1, right]
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，HashMap + 递归栈
 *
 * 示例：preorder=[3,9,20,15,7], inorder=[9,3,15,20,7] → 对应二叉树
 */
public class LC105buildTree {

    private Map<Integer, Integer> inorderIndexMap;
    private int preorderIndex;

    /**
     * 根据前序和中序构造二叉树
     *
     * @param preorder 前序遍历序列
     * @param inorder  中序遍历序列
     * @return 二叉树根节点
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        inorderIndexMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return buildTree(preorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        int rootVale = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootVale);

        root.left = buildTree(preorder, left, inorderIndexMap.get(rootVale) - 1);
        root.right = buildTree(preorder, inorderIndexMap.get(rootVale) +1, right);

        return root;

    }

    public static void main(String[] args) {
        LC105buildTree solver = new LC105buildTree();
        int[] preorder = new int[]{3,9,20,15,7};
        int[] inorder = new int[]{9,3,15,20,7};
        TreeNode root = solver.buildTree(preorder, inorder);
        System.out.println(root);
        System.out.println("期望: [3,9,20,null,null,15,7] 或等价结构");
    }
}
