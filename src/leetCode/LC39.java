package leetCode;

import org.omg.IOP.ComponentIdHelper;

import java.util.*;

public class LC39 {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //这种就回溯
        List<List<Integer>> res = new ArrayList<>();


        int len = candidates.length;

        Arrays.sort(candidates);

        dfs(candidates, target, 0, len, new ArrayDeque<>(), res);





        return res;

    }

    public void dfs(int[] candidates, int target, int begin, int length, Deque<Integer> path, List<List<Integer>> res) {
        if ( target == 0) {
            //退出条件
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i < length; i++) {
            if (target - candidates[i] < 0) {
                //另一个退出条件
                break;
            }
            //继续往下迭代
            path.addLast(candidates[i]);
            //可重复，下次还是从i开始
            dfs(candidates, target - candidates[i], i, length, path,  res);
            path.removeLast();
        }
    }
}
