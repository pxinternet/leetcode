package leetCode;

/**
 * LeetCode 8: 字符串转换整数 (atoi)
 * 
 * 实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
 * 
 * 算法需要遵循以下步骤：
 * 1. 读入字符串并丢弃无用的前导空格
 * 2. 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）
 * 3. 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略
 * 4. 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）
 * 5. 如果整数数超过 32 位有符号整数范围 [−2^31, 2^31 − 1]，需要截断这个整数，使其保持在这个范围内
 */
public class LC8myAtoi {

    /**
     * 将字符串转换为整数
     * 
     * 算法思路：
     * 1. 跳过前导空格
     * 2. 读取符号（+ 或 -）
     * 3. 读取数字字符，直到遇到非数字字符
     * 4. 在读取过程中检查溢出
     * 
     * 溢出检查：
     * - 在 res * 10 + digit 之前检查
     * - 如果 res > Integer.MAX_VALUE / 10，则 res * 10 会溢出
     * - 如果 res == Integer.MAX_VALUE / 10 且 digit > 7，则 res * 10 + digit 会溢出
     * - 对于负数，如果 res < Integer.MIN_VALUE / 10，则 res * 10 会溢出
     * - 如果 res == Integer.MIN_VALUE / 10 且 digit > 8，则 res * 10 - digit 会溢出
     * 
     * @param s 输入的字符串
     * @return 转换后的整数，如果溢出则返回边界值
     */
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;
        int len = s.length();

        // 步骤 1: 跳过前导空格
        while (index < len && s.charAt(index) == ' ') {
            index++;
        }

        // 如果字符串全是空格，返回 0
        if (index == len) {
            return 0;
        }

        // 步骤 2: 读取符号
        int sign = 1; // 默认为正数
        if (s.charAt(index) == '+') {
            sign = 1;
            index++;
        } else if (s.charAt(index) == '-') {
            sign = -1;
            index++;
        }

        // 步骤 3 和 4: 读取数字并转换
        int result = 0;
        int maxDiv10 = Integer.MAX_VALUE / 10; // 214748364
        int maxMod10 = Integer.MAX_VALUE % 10; // 7
        int minDiv10 = Integer.MIN_VALUE / 10; // -214748364
        int minMod10 = -(Integer.MIN_VALUE % 10); // 8 (注意：MIN_VALUE % 10 是负数，需要取反)

        while (index < len) {
            char c = s.charAt(index);

            // 如果遇到非数字字符，停止读取
            if (c < '0' || c > '9') {
                break;
            }

            int digit = c - '0'; // 将字符转换为数字

            // 溢出检查：在 result * 10 + digit 之前检查
            if (sign == 1) {
                // 正数溢出检查
                // 如果 result > maxDiv10，那么 result * 10 会溢出
                // 如果 result == maxDiv10 且 digit > maxMod10 (7)，那么 result * 10 + digit 会溢出
                if (result > maxDiv10 || (result == maxDiv10 && digit > maxMod10)) {
                    return Integer.MAX_VALUE;
                }
            } else {
                // 负数溢出检查
                // 对于负数，我们实际上是在做 result * 10 - digit
                // 如果 result < minDiv10，那么 result * 10 会溢出
                // 如果 result == minDiv10 且 digit > minMod10 (8)，那么 result * 10 - digit 会溢出
                if (result < minDiv10 || (result == minDiv10 && digit > minMod10)) {
                    return Integer.MIN_VALUE;
                }
            }

            // 更新结果：result = result * 10 + sign * digit
            // 对于正数：result = result * 10 + digit
            // 对于负数：result = result * 10 - digit (通过 sign = -1 实现)
            result = result * 10 + sign * digit;
            index++;
        }

        return result;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        LC8myAtoi solution = new LC8myAtoi();

        // 测试用例
        System.out.println("Test 1: \"42\" -> " + solution.myAtoi("42")); // 期望: 42
        System.out.println("Test 2: \"   -42\" -> " + solution.myAtoi("   -42")); // 期望: -42
        System.out.println("Test 3: \"4193 with words\" -> " + solution.myAtoi("4193 with words")); // 期望: 4193
        System.out.println("Test 4: \"words and 987\" -> " + solution.myAtoi("words and 987")); // 期望: 0
        System.out.println("Test 5: \"-91283472332\" -> " + solution.myAtoi("-91283472332")); // 期望: -2147483648 (溢出)
        System.out.println("Test 6: \"91283472332\" -> " + solution.myAtoi("91283472332")); // 期望: 2147483647 (溢出)
        System.out.println("Test 7: \"   +0 123\" -> " + solution.myAtoi("   +0 123")); // 期望: 0
        System.out.println("Test 8: \"-2147483648\" -> " + solution.myAtoi("-2147483648")); // 期望: -2147483648
        System.out.println("Test 9: \"2147483647\" -> " + solution.myAtoi("2147483647")); // 期望: 2147483647
    }
}
