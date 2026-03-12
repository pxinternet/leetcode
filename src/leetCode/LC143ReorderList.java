package leetCode;

/**
 * LC143 - 重排链表
 *
 * 题目概要：将链表按 L0→Ln→L1→Ln-1→... 重排。
 *
 * 解法说明：找中点、反转后半、交替合并。快慢指针找中点，反转后半段，再从 head 与后半头交替接。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC143ReorderList {

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode pre = null, curr = slow.next, tmp;
        while (curr != null) {
            tmp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = tmp;
        }
        slow.next = null;

        ListNode first = head, second = pre;
        while (second != null) {
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
