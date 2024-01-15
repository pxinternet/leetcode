import java.util.Deque;
import java.util.LinkedList;

public class LC83DeleteDuplicates {

    public ListNode deleteDuplicates(ListNode head) {

        if (head == null) return head;
        //方案1 dequeue
        Deque<ListNode> queue = new LinkedList<>();
        queue.offerLast(head);


        while(head.next != null) {
            head = head.next;
            System.out.println("head = " + head.val);
            ListNode top = queue.peekLast();
            System.out.println("top = " + top.val);
            if (top.val == head.val) continue;
            queue.offer(head);
        }

        ListNode dummy = new ListNode();
        ListNode it = queue.removeFirst();
        dummy.next = it;

        while(!queue.isEmpty()) {
            it.next = queue.removeFirst();
            it = it.next;
        }
        it.next = null;
        return dummy.next;
    }

    public static void main(String[] args) {
        LC83DeleteDuplicates lc83DeleteDuplicates = new LC83DeleteDuplicates();
        int[] arr = new int[]{1,1,2,3,3};

        ListNode head = ListNode.createFromArray(arr);

        ListNode res = lc83DeleteDuplicates.deleteDuplicates(head);
        System.out.println("res");
        ListNode.printList(res);

    }

}
