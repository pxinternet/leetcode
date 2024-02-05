package leetCode;

import java.util.Map;
import java.util.TreeMap;

public class LC82DeleteDuplicate {

    public ListNode deleteDuplicates(ListNode head) {

        Map<Integer, Integer> nodeCountsMap = new TreeMap<>();

        while (head != null) {
            nodeCountsMap.put(head.val,nodeCountsMap.getOrDefault(head.val, 0) + 1);
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

    public  ListNode deleteDuplicatesLocal(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode current = head;

        while (current != null) {
            while(current.next != null && current.val == current.next.val) {
                current = current.next;
            }
            if (pre.next == current)  {
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

        int[] arr = new int[]{1,2,3,3,4,4,5};

        ListNode head = ListNode.createFromArray(arr);

        ListNode res = lc82DeleteDuplicate.deleteDuplicates(head);

        ListNode.printList(res);
    }
}
