package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC60getPermutation {

    public String getPermutation(int n, int k) {



        //感觉这个应有公式
        List<String> res = new ArrayList<>();
        boolean[] used = new boolean[n + 1];
        StringBuilder path = new StringBuilder();
        dfs(0, n, k, path, res, used);

//        Tools.printList(res);
        return res.get(res.size() - 1);

    }

    private void dfs(int start, int n, int k, StringBuilder path, List<String> res, boolean[] used) {
        if (start == n) {
            res.add(path.toString());
            return;
        }

        int length = path.length();

        for (int i = 1; i <= n; i++) {
            if (used[i]) {
                continue;
            }
            path.append(i);
            used[i] = true;
            System.out.println("before: " + path.toString());
            dfs(start + 1, n, k, path, res, used);
            if (res.size() == k) {
                return;
            }
            used[i] = false;
            path.setLength(length);
            System.out.println("after: " + path.toString());

        }

    }


    public static void main(String[] args) {
        LC60getPermutation lc60getPermutation = new LC60getPermutation();
        lc60getPermutation.getPermutation(3, 3);
    }
}
