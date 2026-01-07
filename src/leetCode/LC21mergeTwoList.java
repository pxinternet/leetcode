package leetCode;

/**
 * LC21 合并两个有序链表
 *
 * 思路：使用哨兵节点(dummy)进行尾插合并，比较两个链表的当前节点值，时间复杂度 O(m+n)，空间 O(1)。
 */
public class LC21mergeTwoList {

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
