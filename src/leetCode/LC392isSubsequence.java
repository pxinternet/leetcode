package leetCode;

/**
 * LC392isSubsequence - 判断子序列
 *
 * 题目（概要）：判断 s 是否为 t 的子序列（子序列不改变相对顺序，可删除若干字符）。
 *
 * 解法说明：双指针，i 指 s，j 指 t；若 s[i]==t[j] 则 i++；j 始终前进。最后 i==n 则存在。
 *
 * 时间复杂度：O(m+n)
 * 空间复杂度：O(1)
 *
 * 示例：s="abc", t="ahbgdc" → true
 */
public class LC392isSubsequence {

    /**
     * 双指针判断 s 是否为 t 的子序列
     */
    public boolean isSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();
        if (m < n) return false;

        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
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
