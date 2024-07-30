package leetCode;

public class Orchard {

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
