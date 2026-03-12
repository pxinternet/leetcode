package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC138 - 复制带随机指针的链表
 *
 * 题目（概要）：给定带有 random 指针的链表，深拷贝整个链表。random 可能指向任意节点或 null。
 *
 * 解法说明：
 * - 解法一（原地复制）：在每个原节点后插入拷贝节点，则 copy.random = orig.random.next；最后拆分为两链表。
 * - 解法二（HashMap）：建立原节点到拷贝节点的映射，两遍遍历设置 next 和 random。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：解法一 O(1)（不含返回值），解法二 O(n)
 *
 * 边界与注意事项：
 * - 空链表返回 null
 * - random 可能指向 null
 *
 * 示例：1->2->3，random: 1->3, 2->1, 3->2 → 拷贝新链表
 */
public class LC138copyRandomList {

    /**
     * 原地复制：在每节点后插入拷贝，利用 next 关系定位 random 的拷贝
     *
     * 关键点：orig.next.random = orig.random.next，因 random 的拷贝紧跟 random 其后
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;

        // 第一步：在每个原节点后插入拷贝节点，形成 原1->拷1->原2->拷2->...
        Node node = head;
        while (node != null) {
            Node newNode = new Node(node.val);
            newNode.next = node.next;
            node.next = newNode;
            node = newNode.next;
        }

        // 第二步：复制 random 指针。拷贝的 random = 原节点 random 的 next（即其拷贝）
        node = head;
        while (node != null) {
            if (node.random != null) {
                node.next.random = node.random.next;
            }
            node = node.next.next;
        }

        // 第三步：拆分为原链表与拷贝链表
        Node dummy = new Node(0);
        Node copyIter = dummy;
        node = head;
        while (node != null) {
            Node copy = node.next;
            node.next = copy.next;
            copyIter.next = copy;
            copyIter = copy;
            node = node.next;
        }
        return dummy.next;
    }

    /**
     * HashMap 法：建立原节点到拷贝节点的映射，两遍设置 next 和 random
     *
     * @param head 原链表头
     * @return 拷贝链表头
     */
    public Node copyRandomListBetter(Node head) {
        if (head == null) return null;

        Map<Node, Node> nodeMap = new HashMap<>();

        Node node = head;
        while (node != null) {
            nodeMap.put(node, new Node(node.val));
            node = node.next;
        }

        for (Map.Entry<Node, Node> entry : nodeMap.entrySet()) {
            entry.getValue().next = nodeMap.get(entry.getKey().next);
            entry.getValue().random = nodeMap.get(entry.getKey().random);
        }

        return nodeMap.get(head);
    }

    public Node copyRandomListRound2(Node head) {
        if (head == null) return head;

        Map<Node, Node> nodeMap = new HashMap<>();

        Node node = head;
        while(node != null) {
            nodeMap.put(node, new Node(node.val));
            node = node.next;
        }

        for (Map.Entry<Node, Node> entry : nodeMap.entrySet()) {
            entry.getValue().next = nodeMap.get(entry.getKey().next);
            entry.getValue().random = nodeMap.get(entry.getKey().random);
        }
        return nodeMap.get(head);
    }
}
