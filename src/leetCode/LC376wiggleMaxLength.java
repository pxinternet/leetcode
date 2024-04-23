package leetCode;

public class LC376wiggleMaxLength {

    //动态规划算法
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int[] up = new int[n];
        int[] down = new int[n];

        up[0] = down[0] = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i-1]) {
                up[i] = Math.max(up[i-1], down[i-1] + 1);
                down[i] = down[i-1];
            } else if (nums[i] < nums[i - 1]) {
                up[i] = up[i - 1];
                down[i] = Math.max(up[i - 1] + 1, down[i - 1]);
            }
             else {
                up[i] = up[i-1];
                down[i] = down[i-1];
             }
        }
        return Math.max(up[n-1], down[n-1]);
    }

    public int wiggleMaxLengthGreedy(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }

        int prediff = nums[1] - nums[0];
        int ret = prediff == 0 ? 1 : 2;
        for (int i = 2; i < n; i++) {
            int curdiff = nums[i] - nums[i - 1];

            if ( (prediff <= 0 && curdiff > 0) || (curdiff < 0 && prediff >= 0)) {
                ret++;
                //因为是在这里更新，因此没啥问题
                prediff = curdiff;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        LC376wiggleMaxLength lc376wiggleMaxLength = new LC376wiggleMaxLength();
        int[] nums = {1, 2, 1, 3, 3, 3, 1};
        System.out.println(lc376wiggleMaxLength.wiggleMaxLengthGreedy(nums));
    }
}
