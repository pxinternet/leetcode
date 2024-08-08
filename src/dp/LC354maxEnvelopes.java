package dp;

import java.util.Arrays;

import java.util.Arrays;

import java.util.Arrays;

public class LC354maxEnvelopes {

    public int maxEnvelopes(int[][] envelopes) {

        if (envelopes == null || envelopes.length == 0 || envelopes[0].length != 2) {
            return 0;
        }

        //step 1 Sort the envelops

        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            } else {
                return a[0] - b[0];
            }
        });

        //Step2 Find the longest increasing subsequence based on heights
        int[] dp = new int[envelopes.length];
        int length = 0;

        for (int[] envelope : envelopes) {
            int height = envelope[1];
            int index = Arrays.binarySearch(dp, 0, length, height);
            if (index < 0) {
                index = -(index + 1);
            }
            dp[index] = height;
            if (index == length) {
                length++;
            }
        }
        return length;

    }

    public int maxEnvelopes2(int[][] envelops) {
        if (envelops.length == 0) {
            return 0;
        }

        int n = envelops.length;

        Arrays.sort(envelops, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            } else {
                return a[0] - b[0];
            }
        });

        int[] dp = new int[n];

        Arrays.fill(dp, 1);

        int ans  = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (envelops[j][1] < envelops[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }


}
