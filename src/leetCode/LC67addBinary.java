package leetCode;

/**
 * LC67 - 二进制求和
 *
 * 题目（概要）：给定两个非空二进制字符串 a 和 b，返回它们的和（二进制表示）。
 * 字符串仅包含 '0' 和 '1'，且除 "0" 外无前导零。
 *
 * 解法说明：
 * - 核心思想：从低位向高位逐位相加，模拟手算竖式，与十进制竖式加法一致
 * - 从低位处理的原因：进位从低位产生并向高位传播，按此顺序可自然处理进位
 * - res.append 时先写低位，最终结果需高位在前，故最后 reverse
 *
 * 时间复杂度：O(max(len(a), len(b)))
 * 空间复杂度：O(max(len(a), len(b)))
 *
 * 边界与注意事项：
 * - 两串长度可能不同，需分别处理较长串的剩余位
 * - 最后若 carry>0，需补一位（如 "1"+"1" → "10"）
 *
 * 示例：a="11", b="1" → "100"
 */
public class LC67addBinary {

    /**
     * 二进制字符串相加，模拟竖式从低位到高位
     *
     * 关键点：用 len1、len2 作为指针从尾向前扫描；ans%2 为当前位，ans/2 为进位
     *
     * @param a 二进制字符串
     * @param b 二进制字符串
     * @return 和的二进制字符串
     */
    public String addBinary(String a, String b) {
        int len1 = a.length();
        int len2 = b.length();
        int carry = 0;
        StringBuilder res = new StringBuilder();

        // 两串都有位时：逐位相加，当前位 = sum%2，进位 = sum/2
        while (len1 > 0 && len2 > 0) {
            int ans1 = (a.charAt(len1 - 1) - '0') + (b.charAt(len2 - 1) - '0') + carry;
            res.append(ans1 % 2);   // 当前位：两数之和模 2
            carry = ans1 / 2;       // 进位：整除 2，因二进制逢二进一

            len1--;
            len2--;
        }

        // a 或 b 有剩余位时，与进位相加
        while (len1 > 0) {
            int ans1 = (a.charAt(len1 - 1) - '0') + carry;
            res.append(ans1 % 2);
            carry = ans1 / 2;
            len1--;
        }
        while (len2 > 0) {
            int ans = (b.charAt(len2 - 1) - '0') + carry;
            res.append(ans % 2);
            carry = ans / 2;
            len2--;
        }
        // 最高位可能产生新进位，如 "1"+"1" 需要补 1
        if (carry > 0) res.append(carry);

        return res.reverse().toString();  // append 时先写低位，需反转得高位在前
    }

    public static void main(String[] args) {
        LC67addBinary lc67addBinary = new LC67addBinary();
        String a = "11";
        String b = "1";

        String res = lc67addBinary.addBinary(a, b);
        System.out.println(res);
    }
}
