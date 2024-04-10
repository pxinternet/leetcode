package leetCode;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class LC143ReorderList {

     public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        System.out.println("slow : " + slow);
        System.out.println("fast : " + fast);


        ListNode pre = null, curr = slow.next, tmp;
        while (curr != null) {
            tmp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = tmp;
        }
         slow.next = null;
         System.out.println("head : " + head);
         System.out.println("pre  : " + pre);


         ListNode first = head, second = pre;

         //一定是second 短
         while(second != null) {
            tmp = first.next;
            first.next = second;

            first = tmp;
            tmp = second.next;
            second.next = first;
            second = tmp;
         }
     }

    public static void main(String[] args) {
        LC143ReorderList lc143ReorderList = new LC143ReorderList();
        int[] arr = new int[]{1,2,3,4,5};
        ListNode head = ListNode.createFromArray(arr);
        System.out.println(head);

        lc143ReorderList.reorderList(head);
        System.out.println(head);
    }
}
