package leetCode;

/**
 * LC1109 - 航班预订统计
 *
 * 题目（概要）：n 个航班 1..n。bookings[i]=[first, last, seats] 表示 first 到 last 航班各增加 seats 个座位。求每班最终座位数。
 *
 * 解法说明：
 * - 解法一：暴力，对每个 booking 的 [first,last] 区间加 seats。O(bookings * 区间长度)。
 * - 解法二：差分。res[first-1]+=seats，res[last]-=seats（last<n 时）；再求前缀和。
 *
 * 时间复杂度：差分 O(n + bookings)
 * 空间复杂度：O(n)
 *
 * 示例：bookings=[[1,2,10],[2,3,20],[2,5,25]], n=5 → [10,55,45,25,25]
 */
public class LC1109corpFlightBooking {

    /** 暴力：对每个区间逐位加 */
    public int[] corpFlightBooking(int[][] bookings, int n) {
        int[] res = new int[n];
        for (int[] b : bookings) {
            int first = b[0], last = b[1], seats = b[2];
            for (int i = first; i <= last; i++) {
                res[i - 1] += seats;
            }
        }
        return res;
    }

    /**
     * 差分：区间 [first,last] 加 k，只需 res[first-1]+=k，res[last]-=k（last<n），再前缀和
     *
     * 关键点：差分数组 [first-1]+=k、[last]-=k 后，前缀和自然在 [first,last] 区间多 k
     */
    public int[] corpFlightBookingBetter(int[][] bookings, int n) {
        int[] res = new int[n];

        for (int[] b : bookings) {
            res[b[0] - 1] += b[2];
            if (b[1] < n) res[b[1]] -= b[2];
        }

        for (int i = 1; i < n; i++) {
            res[i] += res[i - 1];
        }
        return res;
    }
}
