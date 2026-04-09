package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Node - 通用节点类（用于带随机指针链表、图、多路树等）
 *
 * 说明：本类复用于多种 LeetCode 题目：
 * - 带随机指针的链表（LC138）：val, next, random
 * - 无向图的邻接表（LC133）：val, neighbors
 * - 多路树/完美二叉树（LC117 等）：val, left, right, next
 *
 * 字段含义：
 * - val：节点值
 * - next：单链表下一节点（带随机指针链表）
 * - random：随机指针（LC138）
 * - neighbors：邻接表（图）
 * - left, right：二叉树子节点
 */
public class Node {

    int val;
    Node next;
    Node random;

    public List<Node> neighbors;

    public Node left;
    public Node right;

    /** 用于带随机指针链表或图的节点 */
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
