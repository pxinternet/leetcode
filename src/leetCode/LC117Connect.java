package leetCode;

import java.util.LinkedList;
import java.util.Queue;

public class LC117Connect {

    //层序遍历
    public Node connect(Node root) {
        if (root == null) return null;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()) {
            int currentSize = queue.size();

            Node node = queue.peek();
            for (int i = 0; i < currentSize; i++) {
                node = queue.poll();
                node.next = queue.peek();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            node.next = null;
        }

        return root;
    }
}
