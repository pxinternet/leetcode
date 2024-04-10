package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LC103zigzagLevelOrder {

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
}
