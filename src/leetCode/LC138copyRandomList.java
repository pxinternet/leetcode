package leetCode;

import com.sun.xml.internal.bind.marshaller.NoEscapeHandler;

import java.util.HashMap;
import java.util.Map;

public class LC138copyRandomList {

    public Node copyRandomList(Node head) {

        if(head == null) return null;


        //第一步进行列表的复制
        Node node = head;
        while(node != null) {
            Node newNode = new Node(node.val);
            newNode.next = node.next;
            node.next = newNode;
            node = newNode.next;
        }

        //复制random
        node = head;

        while (node != null) {
            if (node.random != null) {
                node.next.random = node.random.next;
            }
            node = node.next.next;
        }

        //拆列表;

        Node dummy = new Node(0);
        Node copy, copyIter = dummy;
        node = head;

        while(node != null) {
            copy = node.next;
            node.next = copy.next;
            copyIter.next = copy;
            copyIter = copy;
            node = node.next;


        }

        return dummy.next;

    }


    public Node copyRandomListBetter(Node head) {
        if (head == null) return null;

        Map<Node, Node> nodeMap = new HashMap<>();

        Node node = head;
        while(node != null) {
            nodeMap.put(node, new Node(node.val));
            node = node.next;
        }

        for (Map.Entry<Node, Node> entry : nodeMap.entrySet()) {
            entry.getValue().next = nodeMap.get(entry.getKey().next);
            entry.getValue().random = nodeMap.get(entry.getKey().random);
        }

        return nodeMap.get(head);

    }



    public Node copyRandomListRound2(Node head) {
        if (head == null) return head;

        Map<Node, Node> nodeMap = new HashMap<>();

        Node node = head;
        while(node != null) {
            nodeMap.put(node, new Node(node.val));
            node = node.next;
        }

        for (Map.Entry<Node, Node> entry : nodeMap.entrySet()) {
            entry.getValue().next = nodeMap.get(entry.getKey().next);
            entry.getValue().random = nodeMap.get(entry.getKey().random);
        }
        return nodeMap.get(head);
    }
}
