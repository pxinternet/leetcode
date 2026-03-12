package leetCode;

/**
 * LC376 - 摆动序列
 *
 * 题目（概要）：求数组中最长摆动子序列的长度。摆动序列定义：相邻元素差正负交替（或含等号）。
 *
 * 解法说明：
 * - 核心思想（DP）：up[i] 表示以 i 结尾、最后一段为上升的最长长度；down[i] 为下降。
 * - 若 nums[i]>nums[i-1]：up[i]=max(up[i-1], down[i-1]+1)，down[i]=down[i-1]
 * - 若 nums[i]<nums[i-1]：down[i]=max(down[i-1], up[i-1]+1)，up[i]=up[i-1]
 * - 核心思想（贪心）：统计「峰」「谷」数量，prediff 与 curdiff 符号交替则长度+1
 *
 * 时间复杂度：O(n)
 * 空间复杂度：DP O(n)，贪心 O(1)
 *
 * 边界与注意事项：
 * - n<2 直接返回 n
 *
 * 示例：nums=[1,7,4,9,2,5] → 6；nums=[1,2,1,3,3,3,1] → 4
 */
public class LC376wiggleMaxLength {

    /**
     * DP：up[i]/down[i] 分别表示以 i 结尾、最后趋势为升/降的最长摆动长度
     *
     * @param nums 整数数组
     * @return 最长摆动子序列长度
     */
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n < 2) return n;

        int[] up = new int[n];
        int[] down = new int[n];
        up[0] = down[0] = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = Math.max(up[i - 1], down[i - 1] + 1);
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = Math.max(down[i - 1], up[i - 1] + 1);
                up[i] = up[i - 1];
            } else {
                up[i] = up[i - 1];
                down[i] = down[i - 1];
            }
        }
        return Math.max(up[n - 1], down[n - 1]);
    }

    /**
     * 贪心：统计峰谷交替次数，prediff 与 curdiff 符号不同时 ret++
     *
     * 关键点：prediff==0 时只算 1 个有效点；遇到符号交替则 prediff=curdiff
     *
     * @param nums 整数数组
     * @return 最长摆动子序列长度
     */
    public int wiggleMaxLengthGreedy(int[] nums) {
        int n = nums.length;
        if (n < 2) return n;

        int prediff = nums[1] - nums[0];
        int ret = prediff == 0 ? 1 : 2;

        for (int i = 2; i < n; i++) {
            int curdiff = nums[i] - nums[i - 1];
            if ((prediff <= 0 && curdiff > 0) || (prediff >= 0 && curdiff < 0)) {
                ret++;
                prediff = curdiff;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        LC376wiggleMaxLength solver = new LC376wiggleMaxLength();
        int[] nums = {1, 2, 1, 3, 3, 3, 1};
        System.out.println(solver.wiggleMaxLengthGreedy(nums));
    }
}
