package leetCode;

public class LC392isSubsequence {

    public boolean isSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();

        //t短
        if (m < n) return false;

        int i = 0;
        int j = 0;

        while ( i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
            System.out.println("i = " + i +" j = " +  j);

        }
        System.out.println("i = " + i +" j = " +  j);
        //如果i遍历完了，那么存在
        return i == n;
    }

    public static void main(String[] args) {
        LC392isSubsequence lc392isSubsequence = new LC392isSubsequence();
        String s = "abc";
        String t = "ahbgdc";
        boolean result = lc392isSubsequence.isSubsequence(s, t);
        System.out.println(result);

    }


}
