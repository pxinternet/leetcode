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

            //在这段代码中，order变量用于确定当前位置的数字。order的初始值是k / factorial[n - i] + 1，表示在剩余的数字中，当前位置的数字应该是第order大的。  然后，代码遍历从1到n的所有数字，对于每个数字j，如果它还没有被使用（即valid[j]为1），就将order减1。当order减到0时，说明当前位置的数字就是j，然后将j添加到结果中，并将valid[j]设置为0，表示数字j已经被使用。  因此，if (order == 0)的意思是，如果order减到0，说明已经找到了当前位置的数字，就可以停止遍历。
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
