package leetCode;

import java.util.PriorityQueue;

/**
 * LC23 合并 k 个有序链表
 *
 * 思路：使用最小堆（PriorityQueue）保持 k 个链表当前头节点的最小者，反复弹出并将该节点的 next 入堆，
 * 复杂度：时间 O(N log k)，其中 N 为所有节点总数，k 为链表个数；空间 O(k)。
 */
public class LC23mergeKList {

    public ListNode mergeKLists(ListNode[] lists) {


        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        PriorityQueue<ListNode> nodeQueue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val)
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

    public static void main(String[] args) {
        LC23mergeKList solver = new LC23mergeKList();
        ListNode[] lists = new ListNode[]{
                ListNode.createFromArray(new int[]{1,4,5}),
                ListNode.createFromArray(new int[]{1,3,4}),
                ListNode.createFromArray(new int[]{2,6})
        };
        ListNode res = solver.mergeKLists(lists);
        System.out.println("merged k lists: " + res);
        System.out.println("期望: 1->1->2->3->4->4->5->6");
    }

}
