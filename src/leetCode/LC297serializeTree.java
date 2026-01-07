package leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LC297 二叉树的序列化与反序列化（这里使用层序序列化）
 */
public class LC297serializeTree {

    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) return ""; // 使用空字符串表示空树
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                res.append("null,");
            } else {
                res.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        // 去掉末尾逗号
        if (res.length() > 0) res.setLength(res.length() - 1);
        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] nodes = data.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < nodes.length) {
            TreeNode parent = queue.poll();

            // 左子
            if (i < nodes.length && !nodes[i].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(nodes[i]));
                parent.left = left;
                queue.add(left);
            }
            i++;

            // 右子
            if (i < nodes.length && !nodes[i].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(nodes[i]));
                parent.right = right;
                queue.add(right);
            }
            i++;
        }
        return root;
    }

    public static void main(String[] args) {
        LC297serializeTree codec = new LC297serializeTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{1,2,3,null,null,4,5});
        String s = codec.serialize(root);
        System.out.println("serialize: " + s);
        TreeNode r2 = codec.deserialize(s);
        System.out.println("deserialize: " + r2);
        System.out.println("期望: 序列化再反序列化后结构相等（人工观察输出）");
    }
}
