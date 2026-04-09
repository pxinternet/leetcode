package leetCode;

/**
 * LC66plusOne - 加一
 *
 * 题目（概要）：给定表示非负整数的数组 digits（高位在前），返回加一后的结果数组。
 *
 * 解法说明：
 * - 从末位加 1，维护进位 carry，从右向左逐位处理
 * - 若最高位产生进位（digits[0]==0 表示全 9 加一后溢出），则新数组长度为 n+1，首位为 1
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1) 或 O(n)（最高位进位时）
 *
 * 边界与注意事项：
 * - 最高位进位时需新建长度为 n+1 的数组，首位为 1，其余为 0
 *
 * 示例：digits=[1,2,3] → [1,2,4]；[9,9] → [1,0,0]
 */
public class LC66plusOne {

    /**
     * 数组表示的数加一（从末位向高位逐位处理）
     *
     * 关键点：digits[0]==0 表示发生最高位进位（原数为 99...9），需扩展数组
     *
     * @param digits digits[i] 为 0-9，无前导零
     * @return 加一后的数组
     */
    public int[] plusOne(int[] digits) {
        int n = digits.length;

        // 末位加 1
        int res = digits[n - 1] + 1;
        int carry = res / 10;
        digits[n - 1] = res % 10;

        for (int i = n - 2; i >= 0; i--) {
            res = digits[i] + carry;
            carry = res / 10;
            digits[i] = res % 10;
        }

        // 最高位进位：如 999+1=1000，此时 digits[0]=0 且 carry=1
        if (carry > 0) {
            digits = new int[n + 1];
            digits[0] = 1;
        }
        return digits;
    }
}
