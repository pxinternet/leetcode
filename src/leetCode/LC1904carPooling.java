package leetCode;

/**
 * LC1094 - 拼车（注：类名为 LC1904）
 *
 * 题目概要：trips[i]=[num,from,to]，在 from 上车 num 人、to 下车。判断能否在不超 capacity 下完成。
 *
 * 解法说明：差分数组。diff[from]+=num、diff[to]-=num，再前缀和得每公里人数。
 *
 * 时间复杂度：O(n + max(to))
 * 空间复杂度：O(max(to))
 */
public class LC1904carPooling {

    public boolean carPooling(int[][] trips, int capacity) {
        int n = 1000;
        int[] diff = new int[n + 1];
        for (int[] trip : trips) {
            diff[trip[1]] += trip[0];
            diff[trip[2]] -= trip[0];
        }
        if (diff[0] > capacity) {
            return false;
        }
        for (int i = 1; i<=n; i++) {
            diff[i] += diff[i - 1];
            if (diff[i] > capacity) {
                return false;
            }
        }
        return true;
    }

    //不差分，直接算也行

}
