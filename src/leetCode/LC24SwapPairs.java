package leetCode;

public class LC24SwapPairs {

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode node = head;

        while (node != null && node.next!=null) {
            ListNode temp = node.next;
            pre.next = node.next;
            node.next = node.next.next;
            temp.next = node;
            pre = node;
            node = node.next;
        }

        return dummy.next;
    }
}
