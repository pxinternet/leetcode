package leetCode;

/**
 * LC233 - 数字 1 的个数
 *
 * 题目（概要）：给定 n，统计 [1,n] 中所有整数十进制表示里数字 1 出现的总次数。
 *
 * 解法说明：按位（个、十、百…）统计。当前位为 1 的个数 = (n/(base*10))*base + min(max(n%base-base+1,0), base)。
 *
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 *
 * 示例：n=13 → 6（1,10,11,12,13）
 */
public class LC233countDigitOne {

    /** 返回 [1,n] 中数字 1 出现的总次数 */
    public int countDigitOne(int n) {
        long mulk = 1;
        int ans = 0;
        for (int k = 0; n >= mulk; ++k) {
            ans += (int) ((n / (mulk * 10)) * mulk  + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk));
            mulk *= 10;
        }
        return ans;
    }
}
