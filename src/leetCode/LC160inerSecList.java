package leetCode;

public class LC160inerSecList {

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
