package leetCode;

import java.util.LinkedList;
import java.util.Queue;

public class LC297serializeTree {

    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) return res.toString();
        queue.add(root);
        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();

                if (node == null) {
                    res.append("null");
                } else {
                    res.append(node).append(",");
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }

        }

        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] nodes = data.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        for (int i = 1; i < nodes.length; i++) {
            TreeNode parent = queue.poll();

            if (!nodes[i].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(nodes[i]));
                parent.left = left;
                queue.add(left);
            }

            if (i + 1 < nodes.length && !nodes[i+1].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(nodes[++i]));
                parent.right = right;
                queue.add(right);
            }
        }
        return root;
    }
}

