package leetCode;

/**
 * LC160inerSecList - 相交链表
 *
 * 题目（概要）：给定两个单链表的头节点 headA 和 headB，找出两链表相交的起始节点。若不相交返回 null。
 *
 * 解法说明：
 * - 核心思想：双指针，inter1 走 A 再走 B，inter2 走 B 再走 A；若相交则必在交点相遇，若不相交则同时到 null
 *
 * 时间复杂度：O(m + n)
 * 空间复杂度：O(1)
 *
 * 示例：A=[4,1,8,4,5], B=[5,6,1,8,4,5]，相交于 8 → 返回指向 8 的节点
 */
public class LC160inerSecList {

    /**
     * 双指针法求相交节点
     *
     * @param headA 链表 A 头节点
     * @param headB 链表 B 头节点
     * @return 相交节点，不相交返回 null
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode inter1 = headA;
        ListNode inter2 = headB;

        while (inter1 != inter2) {
            if (inter1 == null) {
                inter1 = headB;
            } else {
                inter1 = inter1.next;
            }
            if (inter2 == null) {
                inter2 = headA;
            } else {
                inter2 = inter2.next;
            }
        }
        return inter1;
    }
}
