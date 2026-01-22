package leetCode;

/**
 * LC05LongPalindrome - 最长回文子串
 *
 * 题目（概要）：给定一个字符串 s，找到 s 中最长的回文子串。如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 *
 * 解法说明：
 * 本类提供了三种解法：
 * 1. longestPalindrome：动态规划方法
 * - 使用 dp[i][j] 表示子串 s[i..j] 是否为回文串
 * - 状态转移：dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]
 * - 按子串长度从小到大填充 dp 表
 *
 * 2. longestPalindromeExpandAroundCenter：中心扩展方法（版本1）
 * - 以每个字符和每对相邻字符为中心，向两边扩展
 * - 分别处理奇数长度和偶数长度的回文串
 *
 * 3. longestPalindrome2：中心扩展方法（版本2）
 * - 与版本1思路相同，但实现略有不同
 *
 * 时间复杂度：
 * - 动态规划：O(n²)，需要填充 n² 的 dp 表
 * - 中心扩展：O(n²)，每个位置最多扩展 n 次
 *
 * 空间复杂度：
 * - 动态规划：O(n²)，dp 表的大小
 * - 中心扩展：O(1)，只使用常数额外空间
 *
 * 边界与注意事项：
 * - 空字符串或长度为 1 的字符串，本身就是回文串
 * - 所有单个字符都是回文串（长度为 1）
 * - 需要同时考虑奇数长度和偶数长度的回文串
 * - 动态规划方法需要按长度从小到大填充，确保子问题已解决
 *
 * 示例：
 * - s = "babad" → 最长回文子串为 "bab" 或 "aba"
 * - s = "cbbd" → 最长回文子串为 "bb"
 * - s = "a" → 最长回文子串为 "a"
 */
public class LC05LongPalindrome {

    /**
     * 方法一：动态规划求解最长回文子串
     *
     * 核心思想：
     * - 使用 dp[i][j] 表示子串 s[i..j] 是否为回文串
     * - 状态转移方程：
     * - 基础情况：dp[i][i] = true（单个字符是回文串）
     * - 两个字符：dp[i][i+1] = (s[i] == s[i+1])
     * - 多个字符：dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]
     * - 按子串长度从小到大填充 dp 表，确保子问题已解决
     *
     * 关键点逐行说明：
     * - 初始化 dp 表，所有单个字符都是回文串
     * - 按长度 L 从 2 到 n 遍历，确保计算 dp[i][j] 时 dp[i+1][j-1] 已计算
     * - 对于每个起始位置 i，计算结束位置 j = L + i - 1
     * - 如果两端字符不相等，肯定不是回文串
     * - 如果两端字符相等，且长度 <= 3，肯定是回文串（如 "aa", "aba"）
     * - 如果两端字符相等且长度 > 3，取决于内部子串是否为回文串
     * - 记录最长回文子串的起始位置和长度
     *
     * @param s 输入字符串
     * @return 最长回文子串
     */
    public String longestPalindrome(String s) {
        int n = s.length();
        // dp[i][j] 表示子串 s[i..j] 是否为回文串
        boolean[][] dp = new boolean[n][n];

        // 基础情况：所有单个字符都是回文串
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // 记录最长回文子串的起始位置和长度
        int maxLength = 1; // 初始长度为 1（单个字符）
        int startIndex = 0; // 初始起始位置为 0

        // 按子串长度 L 从小到大遍历（从 2 开始，因为长度为 1 的情况已处理）
        // 这样确保计算 dp[i][j] 时，dp[i+1][j-1] 已经计算完成
        for (int L = 2; L <= n; L++) {
            // 对于每个可能的起始位置 i
            for (int i = 0; i < n; i++) {
                // 计算结束位置 j，满足 j - i + 1 = L
                int j = L + i - 1;
                // 如果 j 超出字符串范围，跳出内层循环
                if (j >= n) {
                    break;
                }

                // 如果两端字符不相等，肯定不是回文串
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    // 两端字符相等，需要判断内部子串
                    if (j - i < 3) {
                        // 长度 <= 3 的情况：
                        // - 长度为 2：如 "aa"，肯定是回文串
                        // - 长度为 3：如 "aba"，两端相等中间一个字符，肯定是回文串
                        dp[i][j] = true;
                    } else {
                        // 长度 > 3：取决于内部子串 s[i+1..j-1] 是否为回文串
                        dp[i][j] = dp[i + 1][j - 1];
                    }

                    // 如果当前子串是回文串，且长度大于已知最大长度，更新记录
                    if (dp[i][j] && j - i + 1 > maxLength) {
                        startIndex = i;
                        maxLength = j - i + 1;
                    }
                }
            }
        }

        // 返回最长回文子串
        return s.substring(startIndex, startIndex + maxLength);
    }

    /**
     * 方法二：中心扩展法求解最长回文子串（版本1）
     *
     * 核心思想：
     * - 以每个字符和每对相邻字符为中心，向两边扩展
     * - 分别处理奇数长度和偶数长度的回文串：
     * - 奇数长度：以单个字符为中心，如 "aba" 以 'b' 为中心
     * - 偶数长度：以两个相同字符为中心，如 "abba" 以两个 'b' 为中心
     *
     * 关键点逐行说明：
     * - 遍历每个可能的中点位置 i
     * - len1：以 i 为中心（奇数长度）的回文串长度
     * - len2：以 i 和 i+1 为中心（偶数长度）的回文串长度
     * - 取两者中的较大值
     * - 根据回文串长度计算起始和结束位置
     * - 更新最长回文子串的记录
     *
     * @param s 输入字符串
     * @return 最长回文子串
     */
    public String longestPalindromeExpandAroundCenter(String s) {
        // 记录最长回文子串的起始和结束位置
        int start = 0;
        int end = 0;

        // 遍历每个可能的中点位置
        for (int i = 0; i < s.length(); i++) {
            // len1：以 i 为中心的回文串长度（奇数长度，如 "aba"）
            int len1 = expandAroundCenter(s, i, i);
            // len2：以 i 和 i+1 为中心的回文串长度（偶数长度，如 "abba"）
            int len2 = expandAroundCenter(s, i, i + 1);
            // 取两者中的较大值
            int len = Math.max(len1, len2);

            // 如果当前找到的回文串更长，更新记录
            if (len > end - start) {
                // 计算回文串的起始位置
                // 对于奇数长度：start = i - (len-1)/2
                // 对于偶数长度：start = i - (len-1)/2（同样适用）
                start = i - (len - 1) / 2;
                // 计算回文串的结束位置
                // 对于奇数长度：end = i + len/2
                // 对于偶数长度：end = i + len/2（同样适用）
                end = i + (len) / 2;
            }
        }
        // 返回最长回文子串（注意 substring 的结束位置是 exclusive 的，所以需要 +1）
        return s.substring(start, end + 1);
    }

    /**
     * 从指定中心向两边扩展，计算回文串的长度
     *
     * 关键点说明：
     * - left 和 right 表示当前扩展的左右边界
     * - 如果 left == right，表示奇数长度的回文串（以单个字符为中心）
     * - 如果 left + 1 == right，表示偶数长度的回文串（以两个字符为中心）
     * - 向两边扩展直到字符不匹配或越界
     * - 返回的回文串长度 = right - left - 1
     * 因为循环结束时，left 和 right 已经指向不匹配的位置
     *
     * @param s     输入字符串
     * @param left  左边界（包含）
     * @param right 右边界（包含）
     * @return 回文串的长度
     */
    private int expandAroundCenter(String s, int left, int right) {
        // 向两边扩展，直到字符不匹配或越界
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--; // 左边界向左移动
            right++; // 右边界向右移动
        }
        // 返回回文串的长度
        // 注意：循环结束时，left 和 right 已经指向不匹配的位置
        // 所以实际回文串长度 = (right - 1) - (left + 1) + 1 = right - left - 1
        return right - left - 1;
    }

    public String longestPalindrome2(String s) {

        int n = s.length();

        int start = 0;
        int end = 0;

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            int len1 = expandFromCenter(s, i, i);
            int len2 = expandFromCenter(s, i, i + 1);

            int len = Math.max(len1, len2);
            System.out.println("i = " + i + " len1 = " + len1 + " len2 = " + len2 + " len = " + len);

            if (maxLength < len) {
                maxLength = len;
                start = i - (len - 1) / 2;
                end = i + (len) / 2;
            }

        }
        return s.substring(start, end + 1);

    }

    private int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
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
