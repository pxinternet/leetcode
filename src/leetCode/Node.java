package leetCode;

import java.util.ArrayList;
import java.util.List;

public class Node {

    int val;
    Node next;
    Node random;

    public List<Node> neighbors;


    public Node left;
    public Node right;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
        neighbors = new ArrayList<Node>();

    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }

}
