package leetCode;

public class LC92ReverseBetween {

    public ListNode reverseBetween(ListNode head, int left, int right) {

        if (left == right) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode start = dummy;
        ListNode end = dummy;

        while (left > 1) {
            left--;
            right--;
            pre = pre.next;
            end = end.next;
        }

        start = pre.next;
//        System.out.println("start = " + start.val);

        //end要多走2步
        while (right >= 0) {
            right--;
            end = end.next;
        }


//        System.out.println("end = " + end.val);

        ListNode reverse = reverse(start, end);
//        System.out.println("reverse =====================");

        ListNode.printList(reverse);

//        System.out.println("reverse =====================");


        pre.next = reverse;


        return dummy.next;
    }

    private ListNode reverse(ListNode start, ListNode end) {

        ListNode pre = end;
        ListNode curr = start;

        while (curr != end) {

            curr = start.next;
            start.next = pre;
            pre = start;
            start = curr;
        }
        return pre;
    }

    public static void main(String[] args) {
        LC92ReverseBetween lc92ReverseBetween = new LC92ReverseBetween();

        int[] arr = new int[]{1, 2, 3, 4, 5};

        ListNode head = ListNode.createFromArray(arr);

        ListNode res = lc92ReverseBetween.reverseBetween(head, 2, 4);

        ListNode.printList(res);

    }

    public ListNode reverseBetweenRound2(ListNode head, int left, int right) {
        if (left == right) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode start = dummy;
        ListNode end = dummy;
        ListNode pre = dummy;

        while (left > 1) {
            pre = pre.next;
            end = end.next;
            left--;
            right--;
        }

        start = pre.next;

        while (right >= 0) {
            end = end.next;
            right--;
        }

        ListNode curr = start;
        ListNode prev = end;
        while (curr != end) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        pre.next = prev;
        return dummy.next;
    }
}
