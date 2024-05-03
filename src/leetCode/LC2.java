package leetCode;

public class LC2 {

    //不如ai优雅  --需要再练

      public class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
    //两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode header = null;
        int curry = 0;
        while(l1 !=null && l2!= null) {
            int val1 = l1.val;
            int val2 = l2.val;

            int ans = val1 +val2 + curry;

            curry = ans /10;
            ans = ans%10;

            ListNode ansNode = new ListNode(ans, header);
            header = ansNode;
            l1 = l1.next;
            l2 = l2.next;
        }

        while(l1!= null) {
            int val1 = l1.val;
            int ans = val1 + curry;
            curry = ans /10;
            ans = ans%10;

            ListNode ansNode = new ListNode(ans, header);
            header = ansNode;
            l1 = l1.next;

        }

        while(l2!= null) {
            int val1 = l2.val;
            int ans = val1 + curry;
            curry = ans /10;
            ans = ans%10;

            ListNode ansNode = new ListNode(ans, header);
            header = ansNode;
            l2 = l2.next;
        }

        if (curry != 0) {
            ListNode ansNode = new ListNode(1, header);
            header = ansNode;
        }

        return reverse(header);
    }

    private ListNode reverse(ListNode listNode) {
        ListNode header = listNode;
        ListNode pre = null;
        ListNode end = null;

        while(listNode!=null) {
            header = listNode.next;
            listNode.next = pre;
            pre = listNode;
            listNode = header;

        }
        return pre;

    }



    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
          ListNode dummy = new ListNode(0);
          ListNode curr = dummy;
            int carry = 0;
          while (l1 != null || l2 != null) {
              int x = (l1 != null) ? l1.val : 0;
              int y = (l2 != null) ? l2.val : 0;

              int sum = x + y + carry;

              carry = sum / 10;
              int val = sum % 10;

              curr.next = new ListNode(val);

              curr = curr.next;

              if (l1 != null) l1 = l1.next;
              if (l2 != null) l2 = l2.next;
          }

          if(carry > 0) {
              curr.next = new ListNode(carry);
          }
          return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }

}
