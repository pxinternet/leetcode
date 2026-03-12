package leetCode;

/**
 * LC61 - 旋转链表
 *
 * 题目概要：将链表每个节点向右移动 k 个位置。如 1->2->3->4->5，k=2 → 4->5->1->2->3。
 *
 * 解法说明：先求长度 n，则有效 k = k % n；旋转等价于把倒数 k 个节点整体移到表头。
 * 用 slow 指向新尾（原倒数第 k+1 个），断开 slow.next，将原尾接回 dummy.next。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC61rotateRight {

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;

        int n = 0;
        for (; fast.next != null; n++) fast = fast.next;
        if ((k % n) == 0) return head;

        int steps = n - k % n;
        for (int j = 0; j < steps; j++) slow = slow.next;

        fast.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;

        return dummy.next;
    }
}
