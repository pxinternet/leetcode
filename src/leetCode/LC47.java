package leetCode;

import java.util.*;

public class LC47 {
    public List<List<Integer>> permuteUnique(int[] nums) {

        int len = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        if (len == 0) {
            return res;
        }

        Arrays.sort(nums);

        System.out.println(Arrays.toString(nums));

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
            if(i > 0 && nums[i] == nums[i-1] && !used[i-1]) {
                //这个条件检查当前元素 nums[i] 是否与前一个元素 nums[i - 1] 相同，并且前一个元素没有被使用过。如果满足这些条件，我们就使用 continue 语句跳过这个元素，因为它会生成与前一个元素相同的组合。
                continue;
            }
            System.out.println(nums[i]);

                path.addLast(nums[i]);
                used[i] = true;
                dfs(nums, depth +1, len, used, path, res);
                used[i] = false;
                path.removeLast();

        }
    }

    public static  void  main(String... arg) {
        LC47 lc47 = new LC47();
        int[] nums = new int[]{3, 3, 0,3};

        List<List<Integer>> res = lc47.permuteUnique(nums);

        System.out.println(res);

    }


}
