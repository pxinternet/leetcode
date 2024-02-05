package leetCode;

import java.util.List;

public class LCR078 {

    public ListNode mergeKLists(ListNode[] lists) {
        //两种方法一个是分治法
        if (lists.length == 0) return null;

        return merge(lists, 0, lists.length-1);

    }

    private ListNode merge(ListNode[] list, int start, int end) {
        if (start == end) return list[start];
        if (start > end) return null;

        int mid = (start +end)/2;

        return mergeTwo(merge(list, start, mid), merge(list, mid+1, end));

    }

    private ListNode mergeTwo(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head = new ListNode(0);
        ListNode cur = head;

        while(l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1 == null ? l2 : l1;
        return head.next;


    }
}
