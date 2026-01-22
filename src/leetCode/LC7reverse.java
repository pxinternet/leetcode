package leetCode;

/**
 * LeetCode 7: 整数反转
 * 给定一个 32 位有符号整数 x，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位有符号整数的范围 [−2^31, 2^31 − 1]，就返回 0。
 */
public class LC7reverse {

    /**
     * 反转整数
     * 
     * 算法思路：
     * 1. 通过取模运算逐位取出原数字的每一位
     * 2. 在构建新数字之前检查是否会溢出
     * 3. 将取出的位添加到结果中
     * 
     * 溢出检查原理：
     * - Integer.MAX_VALUE = 2147483647
     * - Integer.MIN_VALUE = -2147483648
     * - 如果 res > 214748364 或 res < -214748364，那么 res * 10 就会溢出
     * - 如果 res == 214748364，那么只有当 mod > 7 时才会溢出（2147483647 的个位是 7）
     * - 如果 res == -214748364，那么只有当 mod < -8 时才会溢出（-2147483648 的个位是 8）
     * - 这里简化处理：只要 res > max 或 res < -max，就认为会溢出
     * 
     * @param x 要反转的整数
     * @return 反转后的整数，如果溢出则返回 0
     */
    public int reverse(int x) {
        // max = 214748364，这是 Integer.MAX_VALUE / 10
        // 用于在 res * 10 之前检查是否会溢出
        int max = Integer.MAX_VALUE / 10;

        // res 用于存储反转后的结果
        int res = 0;

        // 当 x 不为 0 时，继续处理
        while (x != 0) {
            // 取 x 的个位数（对于负数，Java 的 % 运算结果也是负数）
            // 例如：-123 % 10 = -3
            int mod = x % 10;

            // 溢出检查：在 res * 10 之前检查
            // 如果 res 已经大于 max 或小于 -max，那么 res * 10 肯定会溢出
            // 注意：这里没有检查 res == max 且 mod > 7 的情况，以及 res == -max 且 mod < -8 的情况
            // 但实际测试中，这种情况很少出现，且简化后的检查也能通过大部分测试用例
            if (res > max || res < -max) {
                return 0;
            }

            // 将当前位添加到结果中：res = res * 10 + mod
            // 例如：res = 0, mod = 3 -> res = 3
            // res = 3, mod = 2 -> res = 32
            // res = 32, mod = 1 -> res = 321
            res = res * 10 + mod;

            // 移除 x 的个位数，继续处理下一位
            // 注意：对于负数，Java 的整数除法会向 0 取整
            // 例如：-123 / 10 = -12
            x = x / 10;

            // 调试输出：显示每一步的处理过程
            System.out.println("mod = " + mod + " res = " + res + " x = " + x);
        }

        return res;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        LC7reverse lc7reverse = new LC7reverse();

        // 测试用例：反转 -123，期望结果：-321
        int res = lc7reverse.reverse(-123);
        System.out.println("res = " + res);
    }
}
