package round2;

public class ShortestPalindrome {

    public String shortesPalindrome(String s) {

        int n = s.length();
        int j = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
        }

        if (j == n) {
            return s;
        }

        String suffix = s.substring(j);
        StringBuilder sb = new StringBuilder(suffix).reverse();

        return sb.toString() + shortesPalindrome(s.substring(0, j)) + suffix;

    }

    public String shortestPalindrome(String s) {

        String reversedS = new StringBuilder(s).reverse().toString();

        String newStr = s + "#" + reversedS;

        int[] prefix = computePrefix(newStr);

        int matched = prefix[newStr.length() - 1];

        String toAdd = reversedS.substring(0, reversedS.length() - matched);

        return toAdd + s;
    }

    private int[] computePrefix(String s) {
        int n = s.length();
        int[] prefix = new int[n];

        int j = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = prefix[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }

}
