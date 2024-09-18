package round3;

import java.util.LinkedList;
import java.util.Queue;

public class Codec {

    class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();

        if (root == null)
            return res.toString();

        queue.add(root);

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();

                if (node != null) {
                    res.append(node).append(",");
                    queue.add(node.left);
                    queue.add(node.right);
                } else {
                    res.append("null,");
                }
            }
        }
        res.setLength(res.length() - 1);
        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty())
            return null;

        String[] nodes = data.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        for (int i = 1; i < nodes.length;) {
            TreeNode parent = queue.poll();

            if (!nodes[i].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(nodes[i]));
                parent.left = left;
                queue.add(left);
            }
            i++;

            if (i < nodes.length && !nodes[i].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(nodes[i]));
                parent.right = right;
                queue.add(right);
            }
            i++;
        }
        return root;
    }

}
