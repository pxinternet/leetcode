package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LC102levelOrder - 二叉树的层序遍历
 *
 * 题目（概要）：给定二叉树根节点，返回其层序遍历结果（即逐层从左到右访问节点）。
 *
 * 解法说明：
 * - 核心思想：BFS 用队列，按层遍历。每层开始时记录当前队列大小，弹出该数量的节点即为本层
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，队列最大宽度
 *
 * 边界与注意事项：
 * - 空树返回空列表
 *
 * 示例：[3,9,20,null,null,15,7] → [[3],[9,20],[15,7]]
 */
public class LC102levelOrder {

    /**
     * 层序遍历二叉树，每层作为一个子列表
     *
     * 关键点：每层开始时记录 queue.size()，只弹出该数量的节点，保证同层节点在一轮内处理完
     *
     * @param root 二叉树根节点
     * @return 按层遍历的节点值列表
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            List<Integer> line = new ArrayList<>();
            // 重要：先记录当前层节点数，避免子节点入队后影响循环次数
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();
                line.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(line);
        }
        return res;
    }

    public static void main(String[] args) {
        LC102levelOrder solver = new LC102levelOrder();
        TreeNode root = TreeNode.fromArray(new Integer[]{3,9,20,null,null,15,7});
        System.out.println(solver.levelOrder(root));
        System.out.println("期望: [[3],[9,20],[15,7]]");
    }
}
