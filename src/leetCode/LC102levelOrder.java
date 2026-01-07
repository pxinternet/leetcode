package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LC102 二叉树的层序遍历
 *
 * 思路：BFS 使用队列按层遍历，记录每层节点。
 */
public class LC102levelOrder {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()) {

            List<Integer> line = new ArrayList<>();
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
