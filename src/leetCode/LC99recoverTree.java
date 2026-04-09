package leetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LC99 - 恢复二叉搜索树
 *
 * 题目概要：BST 中恰好有两个节点被错误交换，恢复之。要求 O(1) 空间（不含递归栈）。
 *
 * 解法说明：中序遍历找逆序对。第一次遇到 pred.val > root.val 时 x=pred；第二次（或仅一次）y=root。交换 x、y。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h) 栈
 */
public class LC99recoverTree {

    public void recoverTree(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode x = null, y = null, pred = null;

        while(!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pred != null && root.val < pred.val) {
                y  = root;
                if (x == null) {
                    x = pred;
                } else {
                    break;
                }
            }
            pred = root;
            root = root.right;
        }
        swap(x, y);
    }

    private void swap(TreeNode x, TreeNode y) {
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }
}
