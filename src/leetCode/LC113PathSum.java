package leetCode;

import java.util.ArrayList;
import java.util.List;

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
