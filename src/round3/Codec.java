package round3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Codec - 二叉树的序列化与反序列化（LeetCode 297）
 *
 * 题目（概要）：将二叉树序列化为字符串（如 "1,2,3,null,null,4,5"），并能反序列化还原。
 *
 * 算法原理：
 * - 层序遍历：序列化时 BFS，每节点输出 val 或 "null"，逗号分隔。
 * - 反序列化：按顺序解析，队列维护待挂子节点的父节点，依次挂左右子。
 *
 * 核心逻辑（分步）：
 * - serialize：BFS，非 null 输出 val+"," 并入队左右（含 null）；null 输出 "null,"。
 * - deserialize：split 后，nodes[0] 为根；队列按层，每次取 parent，为 next 两个值建左右子并入队。
 *
 * 关键洞察：null 也入队以保证输出与树结构一一对应；反序列化时顺序与序列化一致。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：root=[1,2,3,null,null,4,5] → "1,2,3,null,null,4,5"
 */
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
