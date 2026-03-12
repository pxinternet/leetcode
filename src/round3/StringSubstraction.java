package round3;

/**
 * StringSubstraction - 大数减法
 *
 * 题目（概要）：两个非负整数字符串 num1、num2，求 num1 - num2。结果为字符串，可为负。
 *
 * 算法原理：
 * - 从低位到高位逐位相减，借位传递；若 num1<num2 则交换并标记负号。
 * - isGreaterOrEqual：先比长度，再逐位比较。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：num1==num2 返回 "0"；若 num1<num2 则交换并 negative=true。
 * - 步骤 2：i、j 从末位开始，borrow 初为 0；sub=digit1-digit2-borrow，负则 +10、borrow=1。
 * - 步骤 3：结果逆向 append，去掉尾部前导零，negative 则追加 '-'，reverse 输出。
 *
 * 关键洞察：与加法类似，借位从高位影响低位；结果去尾零（从低位看是前导零）。
 *
 * 时间复杂度：O(max(len1, len2))
 * 空间复杂度：O(max(len1, len2))
 *
 * 示例："123" - "45" → "78"
 */
public class StringSubstraction {

    public static boolean isGreaterOrEqual(String num1, String num2) {

        if (num1.length() > num2.length()) {
            return true;
        } else if (num1.length() < num2.length()) {
            return false;
        }

        for (int i = 0; i < num1.length(); i++) {
            if (num1.charAt(i) > num2.charAt(i)) {
                return true;
            } else if (num1.charAt(i) < num2.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static String subtract(String num1, String num2) {
        if (num1.equals(num2))
            return "0";

        boolean negative = false;

        if (!isGreaterOrEqual(num1, num2)) {
            negative = true;
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }

        int i = num1.length() - 1;
        int j = num2.length() - 1;

        int borrow = 0;

        StringBuilder res = new StringBuilder();

        while (i >= 0 || j >= 0) {
            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;

            int sub = digit1 - digit2 - borrow;
            if (sub < 0) {
                sub += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            res.append(sub);
            i--;
            j--;
        }

        while (res.length() > 1 && res.charAt(res.length() - 1) == '0') {
            res.deleteCharAt(res.length() - 1);
        }
        if (negative) {
            res.append('-');
        }

        return res.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(subtract("121", "121"));
    }

}
