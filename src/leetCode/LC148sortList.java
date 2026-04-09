package leetCode;

import java.util.PriorityQueue;

/**
 * LC148sortList - 排序链表
 *
 * 题目（概要）：给单链表排序，要求 O(n log n) 时间、O(1) 空间（或尽量小）。
 *
 * 解法一：用 PriorityQueue 收集所有节点再依次弹出，时间 O(n log n) 空间 O(n)。
 *
 * 解法二（归并）：找中点拆分，递归排序两半，再合并。满足 O(n log n) 时间和 O(log n) 递归栈。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n) 或 O(log n)
 */
public class LC148sortList {

    /**
     * 堆排序：将所有节点入堆再依次连接
     */
    public ListNode sortList(ListNode head) {

        if(head == null || head.next == null) {
            return head;
        }

        PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
        ListNode node = head;
        while (node != null) {
            queue.offer(node);
            node = node.next;
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!queue.isEmpty()) {
            tail.next = queue.poll();
            tail = tail.next;
        }
        tail.next = null;
        return dummy.next;
    }

    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head, fast = head, prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;

        ListNode l1 = sortList2(head);
        ListNode l2 = sortList2(slow);

        return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        curr.next = (l1 != null) ? l1 : l2;

        return dummy.next;
    }
}
