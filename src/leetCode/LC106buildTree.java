package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC106 - 从中序与后序遍历序列构造二叉树
 *
 * 题目（概要）：给定中序和后序遍历序列（无重复值），构造二叉树。
 *
 * 解法说明：
 * - 核心思想：后序最后一个为根；在中序中找到根的位置，划分左右子树；递归构建。
 * - 先构建右子树再左子树：因后序从后往前，postIdx 递减时先遇到右子树的根。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：inorder=[9,3,15,20,7], postorder=[9,15,7,20,3] → [3,9,20,null,null,15,7]
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
