package leetCode;

public class LCR029insert {

    public Node insert(Node head, int insertVal) {

        Node insertNode = new Node(insertVal);

        if (head == null) {
            insertNode.next = insertNode;
           return insertNode;
        }

        if (head.next == head) {
            head.next = insertNode;
            insertNode.next = head;
            return head;
        }

        Node pre = head;
        Node sub = head.next;
        boolean inserted = false;

        while (true) {
            if ((insertVal >= pre.val && insertVal <= sub.val)
                ||((pre.val > sub.val) && (insertVal >= pre.val || insertVal <= sub.val))) {
                pre.next = insertNode;
                insertNode.next = sub;
                inserted = true;
                break;
            }
            pre = sub;
            sub = sub.next;
            if (pre == head) break;
        }

        if (!inserted) {
            pre.next = insertNode;
            insertNode.next = sub;
        }
        return head;
    }
}
