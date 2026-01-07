package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC105 从前序和中序遍历构建二叉树
 *
 * 思路：前序的第一个元素为根，通过在中序中找到根的位置可确定左子树和右子树的长度，递归构建。
 */
public class LC105buildTree {

    private Map<Integer, Integer> inorderIndexMap;
    private  int preorderIndex;
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
