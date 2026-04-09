package leetCode;

/**
 * LC92 - 反转链表 II
 *
 * 题目概要：反转链表中从位置 left 到 right 的节点（1-indexed），其余不变。
 *
 * 解法说明：定位 pre（区间前驱）、start（区间头）、end（区间尾），反转 [start,end) 后接回。
 * reverse 中 pre 初始为 end（区间后继），将 [start,end) 节点逐一前插到 pre 前。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC92ReverseBetween {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy, end = dummy;

        for (int i = 1; i < left; i++) {
            pre = pre.next;
            end = end.next;
            right--;
        }
        ListNode start = pre.next;
        for (int i = 0; i <= right; i++) end = end.next;

        ListNode reversed = reverse(start, end);
        pre.next = reversed;
        return dummy.next;
    }

    private ListNode reverse(ListNode start, ListNode end) {
        ListNode pre = end, curr = start;
        while (curr != end) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
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
        ListNode start, end = dummy, pre = dummy;

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
