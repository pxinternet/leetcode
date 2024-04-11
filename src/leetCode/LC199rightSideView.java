package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LC199rightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result, 0);
        return result;
    }

    private void dfs(TreeNode node, List<Integer> result, int depth) {
        if (node == null) {
            return;
        }

        if (depth == result.size()) {
            result.add(node.val);
        }
        dfs(node.right, result, depth + 1);
        dfs(node.left, result, depth + 1);
    }


    public List<Integer> rightSideViewBFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode node = queue.peek();
        while(!queue.isEmpty()) {
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(node.val);
        }
        return res;
    }
}
