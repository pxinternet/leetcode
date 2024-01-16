package leetCode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class LC46 {

    public List<List<Integer>> permute(int[] nums) {

        //全排列
        int len = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        if(len == 0) {
            return res;
        }

        boolean[] used = new boolean[len];
        Deque<Integer> path = new LinkedList<>();
        dfs(nums, len, 0, path,  used, res);
        int begin = 0;


        return res;
    }

    //递归就是把大问题拆解成小问题
    public void dfs(int[] nums, int depth, int length, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {

        //先找退出条件
        if (depth == length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < length; i++) {

            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;
                dfs(nums,depth+1, length, path, used, res);
                used[i] = false;
                path.removeLast();
            }

        }
    }

}
