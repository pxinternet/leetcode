package leetCode;

public class Node {

    int val;
    Node next;
    Node random;

    public Node left;
    public Node right;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

}
