package leetCode;

import java.util.*;

/**
 * LC40 - 组合总和 II
 *
 * 题目概要：从含重复的 candidates 中选数使和为 target，每个数最多用一次，返回所有不重复组合。
 *
 * 解法说明：排序 + 回溯。去重：同一层中若 candidates[i]==candidates[i-1] 且 i>begin 则跳过，
 * 原因与 LC47 相同：避免产生重复组合。
 *
 * 时间复杂度：O(2^n)
 * 空间复杂度：O(n)
 */
public class LC40 {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);

        int length = candidates.length;

        List<List<Integer>> res = new ArrayList<>();

        dfs(candidates, target, 0, length, new ArrayDeque<>(), res);

        //在这里面去个重
        return res;



    }

    private void dfs(int[] candidates, int target, int begin, int length, Deque<Integer> path, List<List<Integer>> res) {

        //结束条件
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i < length; i++) {
            //退出条件
            if (target - candidates[i] < 0) {
                break;
            }

            if (i > begin && candidates[i] == candidates[i - 1]) continue;

            path.addLast(candidates[i]);

            dfs(candidates, target - candidates[i], i + 1, length, path, res);

            //todo 如何去重？

            path.removeLast();
        }

    }

    public static void main(String[] args) {
        System.err.println("111");
    }
}
