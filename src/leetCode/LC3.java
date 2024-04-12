package leetCode;

import java.util.*;


public class LC3 {
    public int lengthOfLongestSubstring(String s) {
        //用动态规划解决
//        int[] dp = new int[s.length() +1];
//        int[] last = new int[128];
//
//        Arrays.fill(last, -1);
//
//        int res = 0;
//        for (int i = 1; i <= s.length(); i++) {
//            int j = last[s.charAt(i-1)];
//            last[s.charAt(i-1)] = i;
//
//            if (i - j > dp[i-1]) {
//                dp[i] = dp[i-1] +1;
//            } else {
//                dp[i] = i - j;
//            }
//            res = Math.max(res, dp[i]);
//
//        }
//        return res;

        Set<Character> occ = new HashSet<>();

        int n = s.length();
        int rk = -1, ans = 0;

        for (int i =0; i<n; ++i) {
            if (i != 0) {
                occ.remove(s.charAt(i-1));
            }
            while (rk + 1 < n &&!occ.contains(s.charAt(rk + 1))) {
                occ.add(s.charAt(rk +1));
                ++rk;
            }

            ans = Math.max(ans, rk -i +1);
        }
        return ans;
    }


    public static void main(String... arg) {
        LC3 lc3 = new LC3();
        String str = "bbbbb";

        int res = lc3.lengthOfLongestSubstring(str);
        System.out.println(res);

    }

}
