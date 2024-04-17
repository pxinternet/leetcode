package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
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


    public String getPermutationMath(int n, int k) {
        int[] factorial = new int[n];
        factorial[0] = 1;
        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        --k;

        StringBuilder ans = new StringBuilder();
        int[] valid = new int[n + 1];
        Arrays.fill(valid, 1);
        for (int i = 1; i <= n; i++) {
            int order = k / factorial[n - i] + 1;
            for (int j = 1; j <= n; j++) {
                order -= valid[j];
                if (order == 0) {
                    ans.append(j);
                    valid[j] = 0;
                    break;
                }
            }
            k %= factorial[n-1];
        }
        return ans.toString();



    }


    public static void main(String[] args) {
        LC60getPermutation lc60getPermutation = new LC60getPermutation();
        lc60getPermutation.getPermutation(3, 3);
    }
}
