package leetCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LC2096getDirections {

    TreeNode startNode;
    TreeNode destNode;

    TreeNode rootNode;
    Map<TreeNode, TreeNode> parent = new HashMap<>();

    int start = 0;
    int dest = 0;

    public String getDirections(TreeNode root, int startValue, int destValue) {
        start = startValue;
        dest = destValue;
        rootNode = root;
        dfs(root);
        return "";
    }

    private void dfs(TreeNode node) {
        if (node == null) return;

        if (node.val == start) {
            startNode = node;
        }
        if (node.val == dest) {
            destNode = node;
        }
        if (node.left != null) {
            parent.put(node.left, node);
            dfs(node.left);
        }
        if (node.right != null) {
            parent.put(node.right, node);
            dfs(node.right);
        }
    }

    private List<Character> path(TreeNode node) {

        List<Character> res = new LinkedList<>();

        while (node != rootNode) {
            TreeNode pa = parent.get(node);
            if (node == pa.left) {
                res.add('L');
            } else {
                res.add('R');
            }
            node = pa;
        }
        return res;

    }
}
