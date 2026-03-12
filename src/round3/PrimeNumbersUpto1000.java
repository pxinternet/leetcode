package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PrimeNumbersUpto1000 - 埃氏筛求 n 以内质数
 *
 * 题目（概要）：给定 n，返回 [2, n] 范围内所有质数的列表。
 *
 * 算法原理：
 * - 埃拉托斯特尼筛法：从 2 开始，将每个质数的倍数标记为非质数；未被标记的即为质数。
 * - 只需枚举到 sqrt(n)：大于 sqrt(n) 的合数必有因子 <= sqrt(n)，已被筛掉。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：isPrime 数组初为 true；0、1 置 false。
 * - 步骤 2：p 从 2 到 sqrt(n)，若 isPrime[p] 则从 p*p 起，步长 p，全部置 false。
 * - 步骤 3：遍历 2..n，isPrime[p] 为 true 的加入结果。
 *
 * 关键洞察：从 p*p 开始筛，因 2p、3p..(p-1)p 已被更小质数筛过。
 *
 * 时间复杂度：O(n log log n)
 * 空间复杂度：O(n)
 *
 * 示例：n=10 → [2,3,5,7]
 */
public class PrimeNumbersUpto1000 {

    public static List<Integer> primeNumbers(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);

        isPrime[0] = false;
        isPrime[1] = false;

        List<Integer> res = new ArrayList<>();

        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }

        for (int p = 2; p <= n; p++) {
            if (isPrime[p]) {
                res.add(p);
            }
        }

        return res;
    }

}
