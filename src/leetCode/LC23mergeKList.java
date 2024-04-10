package leetCode;

import java.util.PriorityQueue;

public class LC23mergeKList {

    public ListNode mergeKLists(ListNode[] lists) {


        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        PriorityQueue<ListNode> nodeQueue = new PriorityQueue<>(
                (a, b) -> a.val - b.val
        );

        for (ListNode node : lists) {
            if (node != null) {
                nodeQueue.offer(node);
            }
        }

        while (!nodeQueue.isEmpty()) {
            ListNode tmp = nodeQueue.poll();
            tail.next = tmp;
            tail = tail.next;
            if (tmp.next != null) {
                nodeQueue.offer(tmp.next);
            }
        }

        return dummy.next;

    }

}
