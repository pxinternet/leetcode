package leetCode;

public class LC05LongPalindrome {


    public String longestPalindrome(String s) {
        //这是典型的动态规划dp[i][j]
        //dp[i][i] == true;
        //dp[i][i + 1] = true; if s[i] == s[i+1]
        //dp[i][j] = dp[i +1][ j - 1] && (s[i] == s[j])

        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        int maxLength = 1;
        int startIndex = 0;

        //L是每次遍历的字符的长度
        for (int L = 2; L <=n; L++) {
            //从两边往中间找
            for(int i = 0; i < n; i++) {
                //j - i + 1 = L
                int j = L + i - 1;
                if (j >= n) {
                    break;
                }


                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if(j - i < 3)
                        dp[i][j] = true;
                    else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }

                    if (dp[i][j] && j - i + 1 > maxLength) {
                        startIndex = i;
                        maxLength = j - i + 1;
                    }
                }
            }
        }


        return s.substring(startIndex, startIndex + maxLength);
    }

    public String longestPalindromeExpandAroundCenter(String s) {
        int start = 0;
        int end = 0;

        for(int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i+1);
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + (len) / 2;
            }
        }
        System.out.println("start = " + start + " end = " + end );
        return s.substring(start, end + 1);

    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public String longestPalindrome2(String s) {

        int n = s.length();

        int start = 0;
        int end = 0;

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            int len1 = expandFromCenter(s, i, i);
            int len2 = expandFromCenter(s, i, i+1);

            int len = Math.max(len1, len2);
            System.out.println("i = " + i + " len1 = " + len1 + " len2 = " + len2  + " len = " + len);


            if (maxLength < len) {
                maxLength = len;
                start = i - (len - 1) /2;
                end = i + (len) / 2;
            }

        }
        return s.substring(start,maxLength);


    }

    private int expandFromCenter(String s, int left, int right) {
        if (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        System.out.println("left = " + left + " right = " + right);
        return right - left - 1;
    }





    public static void main(String[] args) {
        LC05LongPalindrome lc05LongPalindrome = new LC05LongPalindrome();
        String s = "babad";

        String res = lc05LongPalindrome.longestPalindrome2(s);
        System.out.println(res);
    }

}
