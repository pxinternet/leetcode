package tree;

import java.util.LinkedList;
import java.util.Queue;

import javax.print.attribute.standard.QueuedJobCount;

import javax.print.attribute.standard.QueuedJobCount;

public class FullBinaryTree {

    public static TreeNode buildFullBinaryTree(int height) {
        if (height <= 0) {
            return null;
        }
        return buildTree(1, height);
    }

    private static TreeNode buildTree(int level, int height) {
        if (level > height) {
            return null;
        }
        TreeNode root = new TreeNode(level);
        root.left = buildTree(2 * level, height);
        root.right = buildTree(2 * level + 1, height);
        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode buildFullBinTree(int height) {
        if (height <= 0)
            return null;

        TreeNode root = new TreeNode(1);
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);
        int level = 1;
        while (level < height) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                node.left = new TreeNode(2 * node.val);
                node.right = new TreeNode(2 * node.val + 1);
                queue.offer(node.left);
                queue.offer(node.right);
            }
            level++;
        }
        return root;


    }

}
