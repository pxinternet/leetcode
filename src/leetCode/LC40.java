package leetCode;

import java.util.*;

public class LC40 {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        //数组上来先排序
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

            if (i > begin && candidates[i] == candidates[i-1]) {
                //如果这两个元素一样，那么说明，这个组合已经在里面了，不需要再遍历了
                //如果没想明白这部分，那么就说明没想明白什么叫深度优先遍历******
                continue;
            }

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
