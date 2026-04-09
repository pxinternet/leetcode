package leetCode;

/**
 * LCR078 - 合并 K 个升序链表
 *
 * 题目（概要）：k 个升序链表，合并为一个升序链表。
 *
 * 解法说明：分治。两两合并，递归 merge(lists, start, mid) 与 merge(lists, mid+1, end)，再 mergeTwo。
 *
 * 时间复杂度：O(N*log k)，N 总节点数
 * 空间复杂度：O(log k) 递归栈
 */
public class LCR078 {

    /** 分治合并 K 个链表 */
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
