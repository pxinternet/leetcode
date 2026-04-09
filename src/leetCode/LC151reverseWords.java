package leetCode;

/**
 * LC151 - 反转字符串中的单词
 *
 * 题目概要：反转字符串中单词的顺序，单词间保留一个空格，首尾无多余空格。
 *
 * 解法说明：三步法（O(1) 额外空间，原地操作）：
 * 1. 原地压缩空格：双指针 i 写、j 读，跳过连续空格，单词间只留一个空格
 * 2. 整体反转 [0, i-1]
 * 3. 逐词反转每个单词
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，char[] 为题目输入副本
 */
public class LC151reverseWords {

    public String reverseWords(String s) {
        int n = s.length();
        int i = 0, j = 0;
        char[] chars = s.toCharArray();

        // 原地压缩：去除首尾及中间多余空格，单词间留一个空格
        while (j < n) {
            while (j < n && chars[j] == ' ') j++;
            while (j < n && chars[j] != ' ') chars[i++] = chars[j++];
            while (j < n && chars[j] == ' ') j++;
            if (j < n) chars[i++] = ' ';
        }

        reverse(chars, 0, i - 1);

        // 逐词反转：start 指向单词首，end 指向单词尾后的空格或结尾
        int start = 0, end = 0;
        while (start < i) {
            while (end < i && chars[end] != ' ') end++;
            reverse(chars, start, end - 1);
            start = end + 1;
            end++;
        }
        return new String(chars, 0, i);
    }

    private void reverse(char[] chars, int start, int end) {
        if (start < 0 || end >= chars.length) return;
        while (start < end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            start++;
            end--;
        }
    }
}
