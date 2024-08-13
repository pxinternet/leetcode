package dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class LC216combinationSum3 {

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();

        dfs(k, n, 1, new ArrayDeque<>(), res);

        return res;
    }

    private void dfs(int k, int n, int depth, Deque<Integer> path, List<List<Integer>> res) {
        if (n == 0 && k == path.size()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = depth; i < 10; i++) {
            path.addLast(i);
            dfs(k, n - i, i + 1, path, res);
            path.removeLast();
        }
    }

}
