package leetCode;

import java.util.Map;
import java.util.TreeMap;

/**
 * LC82DeleteDuplicate - 删除排序链表中的重复元素 II
 *
 * 题目（概要）：给定升序链表，删除所有重复的节点，只保留出现一次的数字。
 *
 * 解法一（deleteDuplicates）：用 TreeMap 统计每个值出现次数，只保留次数为 1 的，按升序重建链表。
 *
 * 解法二（deleteDuplicatesLocal）：原地一遍扫描，遇到连续相同值则跳过整段，若该段长度为 1 则保留。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n) 或 O(1)
 *
 * 示例：head=[1,2,3,3,4,4,5] → [1,2,5]
 */
public class LC82DeleteDuplicate {

    /**
     * TreeMap 统计后重建
     */
    public ListNode deleteDuplicates(ListNode head) {
        Map<Integer, Integer> nodeCountsMap = new TreeMap<>();

        while (head != null) {
            nodeCountsMap.put(head.val, nodeCountsMap.getOrDefault(head.val, 0) + 1);
            head = head.next;
        }

        ListNode copy = new ListNode(0);
        ListNode pre = copy;

        for (Map.Entry<Integer, Integer> counts : nodeCountsMap.entrySet()) {
            if (counts.getValue() == 1) {
                ListNode node = new ListNode(counts.getKey());
                pre.next = node;
                pre = node;
            }
        }

        return copy.next;
    }

    /**
     * 原地删除：连续相同值整段跳过，只保留出现一次的
     */
    public ListNode deleteDuplicatesLocal(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode current = head;

        while (current != null) {
            while (current.next != null && current.val == current.next.val) {
                current = current.next;
            }
            if (pre.next == current) {
                pre = pre.next;
            } else {
                pre.next = current.next;
            }

            current = current.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        LC82DeleteDuplicate lc82DeleteDuplicate = new LC82DeleteDuplicate();

        int[] arr = new int[]{1, 2, 3, 3, 4, 4, 5};

        ListNode head = ListNode.createFromArray(arr);

        ListNode res = lc82DeleteDuplicate.deleteDuplicates(head);

        ListNode.printList(res);
    }
}
