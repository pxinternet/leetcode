package leetCode;

public class LC19removeNthFromEnd {

    public ListNode removeNthFromEnd(ListNode head, int n) {

        int right = 0;

        ListNode node = head;

        ListNode dummy = new ListNode(0);

        ListNode pre = dummy;
        pre.next = head;

        while (node != null && right < n) {
            right++;
            node = node.next;
        }

        //接下来分两种情况 node 为null 和 node 不为null

        while(node != null) {
            node = node.next;
            pre = pre.next;
        }

        pre.next = pre.next.next;


        return dummy.next;
    }


    public ListNode removeNthFromEndRound2(ListNode head, int n) {
        int right = 0;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        ListNode node = head;
        while (node != null && right < n) {
            node = node.next;
            right++;
        }

        while(node != null) {
            node = node.next;
            pre = pre.next;
        }
        pre.next = pre.next.next;
        return dummy.next;
    }


}
