package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC83 - 删除排序链表中的重复元素
 *
 * 题目（概要）：给定升序链表，删除重复节点，使每个值只出现一次。
 *
 * 解法说明：
 * - 核心思想：遍历，若 head.val==head.next.val 则跳过 next；否则 head 前进。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：1->1->2->3->3 → 1->2->3
 */
public class LC83DeleteDuplicates {

    /** 方案一：队列去重（可简化） */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;

        Deque<ListNode> queue = new LinkedList<>();
        queue.offerLast(head);


        while (head.next != null) {
            head = head.next;
            ListNode top = queue.peekLast();
            if (top.val == head.val) continue;
            queue.offerLast(head);
        }

        ListNode dummy = new ListNode();
        ListNode it = queue.removeFirst();
        dummy.next = it;

        while(!queue.isEmpty()) {
            it.next = queue.removeFirst();
            it = it.next;
        }
        it.next = null;
        return dummy.next;
    }

    /** 推荐：原地删除，O(1) 空间 */
    public ListNode deleteDuplicatesList(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        while (head != null && head.next != null) {
            if (head.val == head.next.val) head.next = head.next.next;
            else head = head.next;
        }


        return dummy.next;
    }

    public static void main(String[] args) {
        LC83DeleteDuplicates lc83DeleteDuplicates = new LC83DeleteDuplicates();
        int[] arr = new int[]{1,1,2,3,3};

        ListNode head = ListNode.createFromArray(arr);

        ListNode res = lc83DeleteDuplicates.deleteDuplicatesList(head);
        System.out.println("res");
        ListNode.printList(res);

    }

}
