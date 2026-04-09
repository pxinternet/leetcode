package leetCode;

/**
 * Orchard - 果园摘苹果（面试/练习题）
 *
 * 题目（概要）：数组 apples 表示每棵树苹果数，选两段不重叠区间长度分别为 k 和 l，求最大苹果总数。
 *
 * 解法说明：前缀和 + 预处理 maxKsum、maxLsum，枚举第二段右端点，第一段取左侧已预处理的最大 k/l 段和。
 */
public class Orchard {

    /** 两段不重叠区间（长度 k、l）的最大苹果和，无法满足时返回 -1 */
    public static int maxApples(int[] apples, int k, int l) {
        int n = apples.length;

       if (k + l > n) return -1;

        int[] prefixSums = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefixSums[i + 1] = prefixSums[i] + apples[i];
        }

        int[] maxKsum = new int[n + 1];

        for (int i = k; i <= n; i++) {
            maxKsum[i] = Math.max(maxKsum[i - 1], prefixSums[i] - prefixSums[i - k]);
        }

       int[] maxLsum = new int[n + 1];
       for (int i = l; i <= n; i++) {
         maxLsum[i] = Math.max(maxLsum[i - 1], prefixSums[i] - prefixSums[i - l]);
       }

       int maxApples = 0;

       for (int i = k + l; i <= n; i++) {
        maxApples = Math.max(maxApples, maxKsum[i - l] + (prefixSums[i] - prefixSums[i - l]));
        }

        for (int i = k + l; i <= n; i++) {
            maxApples = Math.max(maxApples, maxLsum[i - k] + (prefixSums[i] - prefixSums[i - k]));
        }
        return maxApples;
    }
}
