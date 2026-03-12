package leetCode;

/**
 * LC206 - 反转链表
 *
 * 题目（概要）：给定单链表头节点 head，反转链表并返回新的头节点。
 *
 * 解法说明：
 * - 核心思想：迭代，使用 prev、curr、next 三个指针逐个反转指向
 * - prev 初始为 null，curr 从 head 开始，每次将 curr.next 指向 prev，然后 prev=curr、curr=next
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 空链表或单节点链表直接返回原 head
 *
 * 示例：1->2->3->4->null → 4->3->2->1->null
 */
public class LC206 {

    /**
     * 迭代反转链表
     *
     * 关键点：每次将 curr.next 改为指向 prev，再更新 prev、curr
     *
     * @param head 原链表头节点
     * @return 反转后的链表头节点
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;  // 先保存后继，否则反转后丢失
            curr.next = prev;           // 反转指针
            prev = curr;                // prev 前移
            curr = next;                // curr 前进到原后继
        }

        return prev;
    }

    public static void main(String[] args) {
        LC206 solver = new LC206();
        ListNode head = ListNode.createFromArray(new int[]{1,2,3,4});
        System.out.println("before: " + head);
        ListNode rev = solver.reverseList(head);
        System.out.println("after: " + rev);
        System.out.println("期望: 4->3->2->1");
    }
}
