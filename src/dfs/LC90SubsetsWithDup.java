package dfs;

import java.util.Arrays;

public class LC90SubsetsWithDup {

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();

        backtrack(0, nums, res, new LinkedList<Integer>());

        return res;
    }

    private void backtrack(int i, int[] nums, List<List<Integer>> res, LinkedList<Intger> tmp) {
        res.add(new ArrayList<>(tmp));

        for (int j = i; j < nums.length; j++) {
            if (j > i && nums[j] == nums[j - 1]) {
                continue;
            }
        }
        tmp.add(nums[j]);
        backtrack(j + 1, nums, res, tmp);
        tmp.removeLast();
    }

}
