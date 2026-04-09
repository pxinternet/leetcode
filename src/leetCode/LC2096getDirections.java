package leetCode;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * LC2096 - 从二叉树一个节点到另一个节点每一步的方向
 *
 * 题目（概要）：给定二叉树 root 和两节点值 startValue、destValue，求从 start 到 dest 的路径字符串。
 * "L" 表示向左子走，"R" 表示向右子走，"U" 表示向父走。
 *
 * 解法说明：
 * - 核心思想：先 DFS 建立 parent 映射并定位 start、dest 节点；再分别求 start→root、dest→root 的路径；
 * - 找最近公共祖先后，start→LCA 全为 "U"，LCA→dest 为 "L"/"R" 组合。
 * - 本实现中 getDirections 与 path 为框架，完整逻辑需：path(start)、path(dest)，找 LCA，拼接 "U" 与 L/R 路径。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：root=[5,1,2,3,null,6,4], startValue=3, destValue=6 → "UURL"
 */
public class LC2096getDirections {

    private TreeNode startNode;
    private TreeNode destNode;
    private TreeNode rootNode;
    private final Map<TreeNode, TreeNode> parent = new HashMap<>();
    private int start;
    private int dest;

    /**
     * 从 start 到 dest 的路径方向字符串（实现待完善）
     *
     * @param root       二叉树根
     * @param startValue 起点节点值
     * @param destValue  终点节点值
     * @return "L"、"R"、"U" 组成的路径
     */
    public String getDirections(TreeNode root, int startValue, int destValue) {
        start = startValue;
        dest = destValue;
        rootNode = root;
        dfs(root);

        if (startNode == null || destNode == null) return "";

        List<Character> pathStart = path(startNode);
        List<Character> pathDest = path(destNode);
        Collections.reverse(pathStart);
        Collections.reverse(pathDest);

        int i = 0;
        while (i < pathStart.size() && i < pathDest.size() && pathStart.get(i).equals(pathDest.get(i))) {
            i++;
        }
        StringBuilder res = new StringBuilder();
        for (int j = i; j < pathStart.size(); j++) res.append('U');
        for (int j = i; j < pathDest.size(); j++) res.append(pathDest.get(j));
        return res.toString();
    }

    private void dfs(TreeNode node) {
        if (node == null) return;
        if (node.val == start) startNode = node;
        if (node.val == dest) destNode = node;
        if (node.left != null) {
            parent.put(node.left, node);
            dfs(node.left);
        }
        if (node.right != null) {
            parent.put(node.right, node);
            dfs(node.right);
        }
    }

    /** 从 node 到 root 的路径（L/R 序列，从根到 node 的方向） */
    private List<Character> path(TreeNode node) {
        List<Character> res = new LinkedList<>();
        while (node != rootNode) {
            TreeNode pa = parent.get(node);
            if (node == pa.left) res.add('L');
            else res.add('R');
            node = pa;
        }
        return res;
    }
}
