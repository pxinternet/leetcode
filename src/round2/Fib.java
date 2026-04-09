package round2;

/**
 * Fib - 斐波那契数列
 *
 * 题目（概要）：F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2)，求 F(n)。
 *
 * 算法原理：
 * - fib：朴素递归，base case n=0/1；递归 F(n-1)+F(n-2)。存在大量重复计算。
 * - fib2：迭代，用 pre1、pre2 滚动，从 2 到 n 递推，O(n) 时间 O(1) 空间。
 *
 * 核心逻辑（分步）：
 * - fib：if n<=1 return n；return fib(n-1)+fib(n-2)。
 * - fib2：pre2=0, pre1=1；for i=2..n: result=pre1+pre2, pre2=pre1, pre1=result。
 *
 * 关键洞察：递归指数级；迭代可优化为 O(1) 空间，亦可矩阵快速幂至 O(log n)。
 *
 * 时间复杂度：fib O(2^n)；fib2 O(n)
 * 空间复杂度：fib O(n) 栈；fib2 O(1)
 *
 * 示例：n=10 → 55
 */
public class Fib {

    /** 递归实现，存在大量重复子问题 */
    public int fib(int n) {
        if (n == 0 || n == 1)
            return n;
        return fib(n - 1) + fib(n - 2);
    }

    /** 迭代实现，O(1) 空间 */
    public int fib2(int n) {

        if (n == 0 || n == 1) {
            return n;
        }

        int pre1 = 1;
        int pre2 = 0;
        int counter = 2;
        int result = 0;

        while (counter <= n) {
            result = pre1 + pre2;
            pre2 = pre1;
            pre1 = result;
            counter++;
        }

        return result;
    }
