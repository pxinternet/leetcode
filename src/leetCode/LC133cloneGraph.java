package leetCode;

import java.util.*;

public class LC133cloneGraph {

    public Node cloneGraph(Node node) {
        if (node == null) return node;
        Map<Node, Node> copyMap = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        copyMap.put(node, new Node(node.val, new ArrayList<>()));

        while(!queue.isEmpty()) {
            Node n = queue.poll();
            for (Node neighbor : n.neighbors) {
                if (!copyMap.containsKey(neighbor))  {
                    copyMap.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    queue.add(neighbor);
                }
                copyMap.get(n).neighbors.add(copyMap.get(neighbor));
            }
        }


        return copyMap.get(node);
    }

}
