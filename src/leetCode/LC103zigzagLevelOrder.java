package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LC103 - 二叉树的锯齿形层序遍历
 *
 * 题目（概要）：层序遍历二叉树，奇数层（0-indexed）从右到左，偶数层从左到右。
 *
 * 解法说明：
 * - 核心思想：BFS 层序，偶数层 addLast，奇数层 addFirst（或用 flag 控制）。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：[3,9,20,null,null,15,7] → [[3],[20,9],[15,7]]
 */
public class LC103zigzagLevelOrder {

    /**
     * 锯齿形层序：偶数层正序，奇数层逆序
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {

        List<List<Integer>> res = new ArrayList<>();

        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> line = new LinkedList<>();
            while (size-- > 0) {
                TreeNode curr = queue.poll();
                if (level % 2 == 0) {
                    line.addLast(curr.val);
                } else {
                    line.addFirst(curr.val);

                }
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }

            }
            level++;
            res.add(line);
        }



        return res;

    }

    public static void main(String[] args) {
        LC103zigzagLevelOrder solver = new LC103zigzagLevelOrder();
        TreeNode root = TreeNode.fromArray(new Integer[]{3,9,20,null,null,15,7});
        System.out.println(solver.zigzagLevelOrder(root));
        System.out.println("期望: [[3],[20,9],[15,7]]");
    }
}
