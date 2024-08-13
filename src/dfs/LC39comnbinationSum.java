package dfs;

import java.util.Deque;

public class LC39comnbinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();

        int len = candidates.length;

        Arrays.sort(candidates);

        dfs(candidates, target, 0, len, new ArrayDeque<>(), res);

        return res;
    }

    public void dfs(int[] candidates, int target, int begin, int length, Deque<Integer> path, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i < length; i++) {
            if (target - candidates[i] < 0) {
                break;
            }

            path.add(candidates[i]);
            dfs(candidates, target - candidates[i], i, length, path, res);
            path.removeLast();
        }
    }
}
