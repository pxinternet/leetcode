package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC113 - 路径总和 II
 *
 * 题目概要：找出根到叶子路径上节点值之和等于 targetSum 的所有路径。
 *
 * 解法说明：递归。到叶子且 targetSum==root.val 则记录路径；否则对左右子树递归 targetSum-root.val。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 */
public class LC113PathSum {

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        if (root.left == null && root.right == null && targetSum == root.val) {
            List<Integer> path = new ArrayList<>();
            path.add(root.val);
            res.add(path);
            return res;
        }

        List<List<Integer>> leftPaths = pathSum(root.left, targetSum - root.val);
        for (List<Integer> path : leftPaths) {
            path.add(0, root.val);
            res.add(path);
        }

        List<List<Integer>> rightPaths = pathSum(root.right, targetSum - root.val);
        for (List<Integer> path :  rightPaths) {
            path.add(0, root.val);
            res.add(path);
        }

        return res;
    }
}
