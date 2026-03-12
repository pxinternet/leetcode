package leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LC117 - 填充每个节点的下一个右侧节点指针 II
 *
 * 题目概要：给定普通二叉树，将同一层的节点用 next 指针串成链表。
 *
 * 解法说明：层序遍历（BFS），每层遍历时让当前节点 next 指向同层下一个节点；
 * 每层最后一个节点的 next 置为 null。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，队列最多存一层节点
 */
public class LC117Connect {

    /**
     * 使用层序遍历连接同一层节点的 next 指针
     *
     * @param root 树根
     * @return 原树根（已修改 next）
     */
    public Node connect(Node root) {
        if (root == null) return null;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            Node node = queue.peek();

            for (int i = 0; i < currentSize; i++) {
                node = queue.poll();
                node.next = queue.peek();  // 指向同层下一个；本层最后一个时 peek 为 null，符合题意

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            node.next = null;  // 显式将每层最后一个节点的 next 置 null
        }
        return root;
    }
}
