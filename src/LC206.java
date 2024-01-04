public class LC206 {
    public ListNode reverseList(ListNode head) {
        if (head == null) return head;

        ListNode pre = null;
        ListNode curr = head;

        while(curr !=null) {
            curr = head.next;
            head.next = pre;
            pre = head;
            head = curr;
        }

        return pre;

    }
}
