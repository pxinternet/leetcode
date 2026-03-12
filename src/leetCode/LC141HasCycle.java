package leetCode;

/**
 * LC141HasCycle - 环形链表
 *
 * 题目（概要）：给定链表头节点 head，判断链表中是否存在环。
 *
 * 解法说明：
 * - 核心思想：快慢指针，slow 每次一步，fast 每次两步；若存在环则两者终会相遇
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 空链表或单节点无环返回 false
 *
 * 示例：head = [3,2,0,-4], pos=1 → true（尾节点指向索引 1）
 */
public class LC141HasCycle {

    /**
     * 快慢指针判断是否存在环
     *
     * @param head 链表头节点
     * @return 存在环返回 true
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) return true;
        }
        return false;

    }

}
