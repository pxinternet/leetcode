package dfs;

import java.util.Arrays;
import java.util.Deque;

public class LC47permuteUnique {

    public List<List<Integer>> permuteUnique(int[] nums) {
        int len = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        if (len == 0) {
            return res;
        }

        Arrays.sort(nums);

        Deque<Integer> path = new LinkedList<>();

        boolean[] used = new boolean[len];

        dfs(nums, 0, len, used, path, res);

        return res;
    }

    private void dfs(int[] nums, int depth, int len, boolean[] used, Deque<Integer> path, List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (used[i]) continue;
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            path.addLast(nums[i]);
            used[i] = true;
            dfs(nums, depth + 1, len, used, path, res);
            used[i] = false;
            path.removeLast();
        }
    }

}
