package leetCode;

/**
 * LC21mergeTwoList - 合并两个有序链表
 *
 * 题目（概要）：将两个升序链表合并为一个新的升序链表并返回。
 *
 * 解法说明：
 * - 核心思想：使用 dummy 哨兵节点，比较两个链表当前节点，较小者接入结果并前进
 * - 一方遍历完后，将另一方剩余部分直接接上
 *
 * 时间复杂度：O(m + n)
 * 空间复杂度：O(1)（不计结果链表）
 *
 * 边界与注意事项：
 * - 两个链表均可为空
 *
 * 示例：list1=[1,3,5], list2=[2,4,6] → [1,2,3,4,5,6]
 */
public class LC21mergeTwoList {

    /**
     * 合并两个升序链表
     *
     * 关键点：dummy 哨兵简化头节点处理；一方耗尽后直接接上另一方剩余部分
     *
     * @param list1 第一个升序链表头节点
     * @param list2 第二个升序链表头节点
     * @return 合并后的升序链表头节点
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }
        // 一方耗尽，剩余部分直接接上（可能为 null，即都已遍历完）
        curr.next = list1 != null ? list1 : list2;

        return dummy.next;
    }

    public static void main(String[] args) {
        LC21mergeTwoList solver = new LC21mergeTwoList();
        ListNode l1 = ListNode.createFromArray(new int[]{1,3,5});
        ListNode l2 = ListNode.createFromArray(new int[]{2,4,6});
        ListNode res = solver.mergeTwoLists(l1, l2);
        System.out.println("merged: " + res);
        System.out.println("期望: 1->2->3->4->5->6");
    }
}
