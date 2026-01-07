package leetCode;

/**
 * LC206 反转链表（迭代）
 *
 * 思路：使用三个指针 prev, curr, next 逐步反转指向，时间 O(n)，空间 O(1)。
 */
public class LC206 {
    public ListNode reverseList(ListNode head) {
        // 边界：空链表或单节点链表直接返回
        if (head == null || head.next == null) return head;

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
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
