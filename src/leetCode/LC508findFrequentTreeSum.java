package leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC508findFrequentTreeSum {

    Map<Integer, Integer> sumToCount;
    int maxCount;
    public int[] findFrequentTreeSum(TreeNode root) {
        maxCount = 0;
        sumToCount = new HashMap<>();

        dfs(root);

        List<Integer> res = new ArrayList<>();
        for (int k : sumToCount.keySet()) {
            if (sumToCount.get(k) == maxCount) {
                res.add(k);
            }
        }

        return res.stream().mapToInt(i -> i).toArray();
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int sum = dfs(node.left) + dfs(node.right) + node.val;
        int count = sumToCount.getOrDefault(sum, 0) + 1;

        sumToCount.put(sum, count);

        maxCount = Math.max(maxCount, count);

        return sum;
    }
}
