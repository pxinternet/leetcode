public class LC91numDecoding {

    /**
     * 这个是个典型的动态规划
     * 设dp[] i 前i个元素的解码数
     * 如果第i个字符在【1-9】之间，那么 dp[i] = dp[i-1];
     * 如果第i-1个和第i个字符组成的字符在 【10-26之间】 dp[i] = dp[i-2];
     * dp[i] = dp[i-1](如果有) + dp[i-2]（如果有）
     * @param s
     * @return
     */
    public int numDecodings(String s) {

        int n = s.length();

        int[] dp = new int[n+1];

        //0个字符有一种编码方式，返回0
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            int a = s.charAt(i-1) - '0';
            if (a >=1 && a <= 9) dp[i+1] += dp[i];


            if (i > 1) {
                int b = (s.charAt(i - 2) - '0') * 10 + a;
                if (b >= 10 && b <= 26) dp[i+1] += dp[i-1];
            }
        }
        return dp[n];
    }


    public int numDecodingsMemoryImprove(String s) {

        int n = s.length();

        //i -2
        int f2 = 0;

        //i -1
        int f1 = 1;

        //final
        int res = 0;

        //0个字符有一种编码方式，返回0

        for (int i = 1; i <= n; i++) {
            //这步不能忘记，res每次进入循环都要初始化一下
            res = 0;
            int a = s.charAt(i-1) - '0';
            if (a >=1 && a <= 9) res += f1;


            if (i > 1) {
                int b = (s.charAt(i - 2) - '0') * 10 + a;
                if (b >= 10 && b <= 26) res += f2;
            }
            f2 = f1;
            f1 = res;
        }
        return res;
    }
}
