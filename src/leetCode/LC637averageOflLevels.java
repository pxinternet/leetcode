package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LC637 - 二叉树的层平均值
 *
 * 题目（概要）：给定二叉树，返回每层节点值的平均值。
 *
 * 解法说明：
 * - 核心思想：BFS 层序遍历，每层求和与计数，求平均。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：root=[3,9,20,null,null,15,7] → [3.0, 14.5, 11.0]
 */
public class LC637averageOflLevels {

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            long sum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add((double) sum / size);
        }
        return res;
    }
}
