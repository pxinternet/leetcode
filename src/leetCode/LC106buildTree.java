package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC106 从中序和后序遍历构建二叉树
 *
 * 注意：后序的最后一个元素为根，构建时先构建右子树再构建左子树（因为我们从后往前处理）。
 */
public class LC106buildTree {

    int postIdx;
    int[] postOrder;
    int[] inOrder;
    Map<Integer, Integer> idxMap = new HashMap<>();
    public TreeNode buildTree(int[] inOrder, int[] postOrder) {
        this.postOrder = postOrder;
        this.inOrder = inOrder;

        postIdx = postOrder.length - 1;

        int idx = 0;
        for (Integer val : inOrder)
            idxMap.put(val, idx++);

        return helper(0, inOrder.length - 1);

    }

    private TreeNode helper(int inLeft, int inRight) {
        if (inLeft > inRight) {
            return null;
        }

        int rootVal = postOrder[postIdx];
        TreeNode root = new TreeNode(rootVal);

        int index = idxMap.get(rootVal);

        postIdx--;

        root.right = helper(index + 1, inRight);
        root.left = helper(inLeft, index - 1);
        return root;
    }


    int postIndex;
    Map<Integer, Integer> inorderIndexMap;
    public TreeNode buildTree1(int[] inorder, int[] postorder) {

        postIndex = postorder.length-1;

        inorderIndexMap = new HashMap<>();

        for (int i =0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return buildTree(postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTree(int[] postorder, int left, int right) {
        if (left > right) {
            return null;
        }
        int rootVal = postorder[postIndex--];
        TreeNode rootNode = new TreeNode(rootVal);

        //这种倒着排的先构建右子树，再构建左子树，要看看postIndex--里面是啥

        rootNode.right = buildTree(postorder, inorderIndexMap.get(rootVal) + 1, right);
        rootNode.left = buildTree(postorder, left, inorderIndexMap.get(rootVal) - 1);

        return rootNode;
    }

    public static void main(String[] args) {
        LC106buildTree solver = new LC106buildTree();
        int[] inorder = new int[]{9,3,15,20,7};
        int[] postorder = new int[]{9,15,7,20,3};
        TreeNode root = solver.buildTree(inorder, postorder);
        System.out.println(root);
        System.out.println("期望: [3,9,20,null,null,15,7]");
    }
}
