package leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LC508 - 出现次数最多的子树元素和
 *
 * 题目（概要）：给定二叉树 root，每个节点的子树和 = 该节点 + 左子树和 + 右子树和。
 * 求出现次数最多的子树和，若有多个则全部返回。
 *
 * 解法说明：
 * - 核心思想：DFS 后序遍历，计算每个节点的子树和，用 HashMap 统计各和的出现次数，维护 maxCount。
 * - 递归返回子树和，同时更新 sumToCount 和 maxCount。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - 空树返回空数组
 *
 * 示例：root=[5,2,-3] → [4,-3,2]（和 4 出现 2 次，-3 和 2 各 1 次，题设可能要求返回所有最高频的）
 */
public class LC508findFrequentTreeSum {

    private Map<Integer, Integer> sumToCount;
    private int maxCount;

    /**
     * 找出现次数最多的子树和
     *
     * @param root 二叉树根节点
     * @return 所有出现次数最多的子树和
     */
    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) return new int[0];

        maxCount = 0;
        sumToCount = new HashMap<>();

        dfs(root);

        List<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : sumToCount.entrySet()) {
            if (e.getValue() == maxCount) {
                res.add(e.getKey());
            }
        }

        return res.stream().mapToInt(i -> i).toArray();
    }

    /**
     * DFS 返回子树和，并统计到 sumToCount
     */
    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int sum = dfs(node.left) + dfs(node.right) + node.val;
        int count = sumToCount.getOrDefault(sum, 0) + 1;
        sumToCount.put(sum, count);
        maxCount = Math.max(maxCount, count);

        return sum;
    }
}
