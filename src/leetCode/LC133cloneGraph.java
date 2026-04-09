package leetCode;

import java.util.*;

/**
 * LC133 - 克隆图
 *
 * 题目概要：给定无向连通图的一个节点，深拷贝整张图（包括所有节点和边）。
 *
 * 解法说明：BFS + HashMap。用 copyMap 记录原节点到新节点的映射，遍历时对未访问邻居
 * 创建新节点并入队，同时对当前节点的新拷贝添加对应邻居引用。
 *
 * 时间复杂度：O(V + E)
 * 空间复杂度：O(V)
 */
public class LC133cloneGraph {

    /**
     * BFS 克隆图
     *
     * @param node 源图任意节点
     * @return 克隆图的对应节点；node 为 null 时返回 null
     */
    public Node cloneGraph(Node node) {
        if (node == null) return node;
        Map<Node, Node> copyMap = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        copyMap.put(node, new Node(node.val, new ArrayList<>()));

        while (!queue.isEmpty()) {
            Node n = queue.poll();
            for (Node neighbor : n.neighbors) {
                if (!copyMap.containsKey(neighbor)) {
                    copyMap.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    queue.add(neighbor);
                }
                copyMap.get(n).neighbors.add(copyMap.get(neighbor));
            }
        }
        return copyMap.get(node);
    }
}
