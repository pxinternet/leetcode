package leetCode;

import java.util.*;

/**
 * LC39 - 组合总和
 *
 * 题目（概要）：给定无重复元素的数组 candidates 和目标数 target，找出 candidates 中所有和为目标数的组合。
 * candidates 中的数字可无限制重复选用。
 *
 * 解法说明：
 * - 核心思想：回溯 + 剪枝。先排序，按顺序选数，target 减到 0 时记录路径
 * - 可重复选取：下一层递归仍从 i 开始；剪枝：若 target - candidates[i] < 0 则 break
 *
 * 时间复杂度：与解的数量相关，最坏指数级
 * 空间复杂度：O(target/min(candidates))，递归栈深度
 *
 * 边界与注意事项：
 * - candidates 无重复
 *
 * 示例：candidates = [2,3,6,7], target = 7 → [[2,2,3],[7]]
 */
public class LC39 {

    /**
     * 求所有和为 target 的组合，每个数可重复使用
     *
     * @param candidates 候选数数组，无重复
     * @param target     目标和
     * @return 所有满足条件的组合
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int len = candidates.length;
        Arrays.sort(candidates);
        dfs(candidates, target, 0, len, new ArrayDeque<>(), res);
        return res;
    }

    /**
     * 回溯：从 begin 开始选数，凑 target
     *
     * @param path 当前路径
     */
    private void dfs(int[] candidates, int target, int begin, int length, Deque<Integer> path, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i < length; i++) {
            if (target - candidates[i] < 0) break;
            path.addLast(candidates[i]);
            dfs(candidates, target - candidates[i], i, length, path, res);
            path.removeLast();
        }
    }
}
