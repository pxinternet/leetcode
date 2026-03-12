package leetCode;

/**
 * LC86 - 分隔链表
 *
 * 题目概要：按值 x 将链表分为两段，小于 x 的在前，大于等于 x 的在后，保持相对顺序。
 *
 * 解法说明：双哑元。遍历时按 val 分别接入 small 和 large 两条链，最后 small 尾部接 large 头部。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC86partition {

    public ListNode partition(ListNode head, int x) {
        ListNode smallHead = new ListNode(0), small = smallHead;
        ListNode largeHead = new ListNode(0), large = largeHead;

        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        small.next = largeHead.next;
        large.next = null;
        return smallHead.next;
    }
}
