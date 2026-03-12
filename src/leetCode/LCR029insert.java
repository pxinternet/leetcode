package leetCode;

/**
 * LCR029 - 排序的循环链表插入
 *
 * 题目（概要）：循环有序链表，插入 insertVal 并保持有序，返回头节点。
 *
 * 解法说明：找到插入位置：在 pre.val<=insertVal<=sub.val 的区间，或 pre>sub 的拐点（最大到最小处）。遍历一圈未插入则插在 head 前。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LCR029insert {

    /** 在循环有序链表中插入 insertVal，返回头节点 */
    public Node insert(Node head, int insertVal) {

        Node insertNode = new Node(insertVal);

        if (head == null) {
            insertNode.next = insertNode;
           return insertNode;
        }

        if (head.next == head) {
            head.next = insertNode;
            insertNode.next = head;
            return head;
        }

        Node pre = head;
        Node sub = head.next;
        boolean inserted = false;

        while (true) {
            if ((insertVal >= pre.val && insertVal <= sub.val)
                ||((pre.val > sub.val) && (insertVal >= pre.val || insertVal <= sub.val))) {
                pre.next = insertNode;
                insertNode.next = sub;
                inserted = true;
                break;
            }
            pre = sub;
            sub = sub.next;
            if (pre == head) break;
        }

        if (!inserted) {
            pre.next = insertNode;
            insertNode.next = sub;
        }
        return head;
    }
}
