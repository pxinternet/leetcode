package dfs;

import java.util.Deque;

public class LC46Permute {

    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();

        if (len == 0) return res;

        Deque<Integer> path = new LinkedList<>();

        boolean[] used = new boolean[len];

        dfs(nums, len, 0, path, used, res);
        return res;
    }

    private void dfs(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;
                dfs(nums, len, depth + 1, path, used, res);
                used[i] = false;
                path.removeLast();
            }
        }
    }
}
