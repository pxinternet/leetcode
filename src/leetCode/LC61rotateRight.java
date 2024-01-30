package leetCode;

public class LC61rotateRight {

    public ListNode rotateRight(ListNode head, int k) {
        //找到倒数第k个，然后断开连接

        if (head == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;

        int n;

        for (n = 0; fast.next != null; n++) {
            fast = fast.next;
        }

        for (int j = n - k % n; j > 0; j--) {
            slow = slow.next;
        }

        fast.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;

        return dummy.next;
    }
}
