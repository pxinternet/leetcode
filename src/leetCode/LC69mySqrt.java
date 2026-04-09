package leetCode;

/**
 * LC69 - x 的平方根
 *
 * 题目（概要）：计算非负整数 x 的平方根，只保留整数部分（向下取整）。
 *
 * 解法说明：
 * - 核心思想：二分查找。在 [0, x] 中找最大的 m 使得 m*m <= x。
 * - 单调性：m 越大 m*m 越大，满足二分的单调性。
 * - 用 (long)mid*mid 避免 mid*mid 溢出（x 最大 2^31-1）。
 *
 * 时间复杂度：O(log x)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - x=0 直接返回 0
 * - 循环结束时 left 为第一个使 mid*mid > x 的位置，故答案为 left-1
 *
 * 示例：x=4 → 2；x=8 → 2（2.828 向下取整）
 */
public class LC69mySqrt {

    /**
     * 二分查找最大的整数 m 使得 m*m <= x
     *
     * 关键点：左闭右闭区间，mid*mid<=x 时 left=mid+1，最终 left-1 即为答案
     *
     * @param x 非负整数
     * @return 平方根的整数部分
     */
    public int mySqrt(int x) {
        if (x == 0) return 0;
        int left = 0, right = x;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid <= x) {
                left = mid + 1;   // mid 可行，尝试更大
            } else {
                right = mid - 1;  // mid 过大，缩小右边界
            }
        }
        return left - 1;           // left 为最小使 mid*mid>x 的位置，故 left-1 为最大满足的
    }

    public static void main(String[] args) {
        LC69mySqrt mySqrt = new LC69mySqrt();

        int res = mySqrt.mySqrt(1);

        System.out.println(res);

    }
}
