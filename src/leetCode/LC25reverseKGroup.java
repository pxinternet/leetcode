package leetCode;

public class LC25reverseKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);

        dummy.next = head;

        ListNode pre = dummy;

        while (head != null) {
            ListNode end = dummy;

            for (int i = 0; i<k && end!=null; i++)  end = end.next;
            if (end == null) return dummy.next;
            end.print();
            ListNode next = end.next;

            ListNode[] reverse = newReverse(head, end);
            head = reverse[0];
            end = reverse[1];

            pre.next = head;
            end.next = next;

            //继续循环
            pre = end;
            head = end.next;
        }

        return dummy.next;
    }

    public ListNode[] newReverse(ListNode head, ListNode end) {
        ListNode prev = end.next;
        ListNode p = head;
        while (prev != end) {
            ListNode next = p.next;
            p.next = prev;
            prev = p;
            p = next;
        }
        return new ListNode[] {end, head};
    }

    public static void main(String[] args) {
        LC25reverseKGroup lc25reverseKGroup = new LC25reverseKGroup();

        int[] arr = new int[] {1,2,3,4,5};

        ListNode head = ListNode.createFromArray(arr);

        head.printBetter("head");

        ListNode res = lc25reverseKGroup.reverseKGroup2(head, 2);

        res.printBetter("res ");

    }

    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = pre;

        while (head !=null) {
            end = pre;
            for (int i = 0; i < k && end != null; i++) end = end.next; //如果k=2，end就是3

            if (end == null) return dummy.next;

            ListNode start = pre.next;
            head = end.next;
            end.next = null;
            ListNode curr = pre.next, tmp;
            ListNode prev = null;

            while(curr != null) {
                tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            prev.printBetter("prev");

            pre.next = prev;
            start.next = head;
            pre = start;

        }

        return dummy.next;
    }
}
