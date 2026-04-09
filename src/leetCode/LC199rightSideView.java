package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LC199 - 二叉树的右视图
 *
 * 题目（概要）：给定二叉树，返回从右侧能看到的节点值（每层最右节点）。
 *
 * 解法说明：
 * - DFS：先右后左，depth==result.size() 时当前节点为该层首次访问即最右。
 * - BFS：层序遍历，每层最后一个节点即为最右。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h) / O(n)
 *
 * 示例：root=[1,2,3,null,5,null,4] → [1,3,4]
 */
public class LC199rightSideView {

    /**
     * DFS 先右后左，每层第一个访问的即为最右
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result, 0);
        return result;
    }

    /**
     * 先右后左 DFS，depth==result.size() 表示该层首次访问，即最右节点
     */
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
