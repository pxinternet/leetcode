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

    //以下是该方法的工作原理：
    //如果当前节点为 null，直接返回，因为空节点不包含任何节点。
    //检查当前节点的深度是否等于结果列表的大小。如果等于，说明这是当前层的第一个节点，将其添加到结果列表中。因为我们先遍历右子树，所以每一层的第一个节点就是最右边的节点。
    //递归地遍历当前节点的右子树和左子树。在递归调用时，需要将深度加 1。
    //这个方法之所以正确，是因为它利用了 DFS 的特性，先遍历右子树，然后遍历左子树。这样，每一层的第一个节点就是最右边的节点。
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
