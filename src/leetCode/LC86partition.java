package leetCode;

import java.util.List;

public class LC86partition {

    public ListNode partition(ListNode head, int x) {
        ListNode smallHead = new ListNode(0), small = smallHead;
        ListNode largeHead = new ListNode(0), large = largeHead;

        while(head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }

        small.next = largeHead.next;
        large.next = null;

        return smallHead.next;

    }
}
