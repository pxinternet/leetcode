package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC98 - 验证二叉搜索树
 *
 * 题目概要：判断二叉树是否为有效 BST（左 < 根 < 右，子树递归）。
 *
 * 解法一：递归传递 [minVal, maxVal] 区间，节点值必须在区间内。左子树区间 (minVal, node.val)，右子树 (node.val, maxVal)。
 *
 * 解法二：中序遍历，BST 的中序序列严格递增。迭代中序逐节点比较前驱即可。
 *
 * 时间复杂度：O(n)；空间：O(h) 递归 / O(h) 栈
 */
public class LC98isValidBST {

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode node, long minVal, long maxVal) {
        if (node == null) return true;
        if (node.val <= minVal || node.val >= maxVal) return false;
        return isValidBST(node.left, minVal, node.val)
                && isValidBST(node.right, node.val, maxVal);
    }

    public boolean isValidBSTInOrder(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();

        int inOrder = Integer.MIN_VALUE;
        if (root == null) return true;


        while (!stack.isEmpty() || root !=null) {
            while(root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.val <= inOrder) {
                return false;
            }
            inOrder = root.val;
            root = root.right;
        }
        return true;
    }

    private Long inOrderG = Long.MIN_VALUE;

    public boolean isValidBSTInOrder2(TreeNode root) {
        if (root == null) return true;
        boolean res1 = isValidBST(root.left);
        if (root.val <= inOrderG) {
            return false;
        }
        inOrderG = (long) root.val;
        boolean res2 = isValidBST(root.right);

        return res1 && res2;
    }

}
