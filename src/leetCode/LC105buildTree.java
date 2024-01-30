package leetCode;

import java.util.HashMap;
import java.util.Map;

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
}
