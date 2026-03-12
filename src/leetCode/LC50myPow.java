package leetCode;

/**
 * LC50 - Pow(x, n)
 *
 * 题目概要：计算 x 的 n 次方。n 可为负。
 *
 * 解法说明：快速幂（二进制分解）。将 n 视为二进制，每次 i/=2 相当于右移；
 * 若当前位为 1 则 ans *= currentProduct。currentProduct 每次自乘实现 x^1, x^2, x^4...
 * 用 long N 避免 n=Integer.MIN_VALUE 取负溢出。
 *
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 */
public class LC50myPow {

    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        double ans = 1;
        double currentProduct = x;
        for (long i = N; i > 0; i /= 2) {
            if ((i % 2) == 1) ans *= currentProduct;
            currentProduct *= currentProduct;
        }
        return ans;
    }

    public static void main(String[] args) {
        LC50myPow myPow = new LC50myPow();
        myPow.myPow(7, 9);


    }
}
