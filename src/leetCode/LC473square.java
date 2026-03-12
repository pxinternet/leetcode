package leetCode;

import java.util.Arrays;

/**
 * LC473 - 火柴拼正方形
 *
 * 题目（概要）：用火柴数组拼成正方形，每根用一次，能否拼成。
 *
 * 解法说明：总和须被 4 整除，边长 target=sum/4。回溯：四边 sums[4]，按长度降序尝试放入每条边，剪枝 sums[i]+stick>target。
 *
 * 时间复杂度：O(4^n)
 * 空间复杂度：O(n)
 */
public class LC473square {

    /** 判断能否拼成正方形 */
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks.length < 4) return false;
        int sum = 0;
        for (int matchstick : matchsticks) {
            sum += matchstick;
        }
        if (sum % 4 != 0) return false;

        Arrays.sort(matchsticks);
        reverse(matchsticks);

        return dfs(matchsticks, new int[4], 0, sum / 4);
    }

    private boolean dfs(int[] matchsticks, int[] sums, int index, int target) {
        if (index == matchsticks.length) {
            if (sums[0] == target && sums[1] == target && sums[2] == target) {
                return true;
            }
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (sums[i] + matchsticks[index] > target) {
                continue;
            }
            sums[i] += matchsticks[index];
            if (dfs(matchsticks, sums, index + 1, target)) {
                return true;
            }
            sums[i] -= matchsticks[index];
        }
        return false;
    }


    private void reverse(int[] nums) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        LC473square lC473square = new LC473square();
        int[] matchsticks = new int[]{1, 1, 2, 2, 2};
        boolean res = lC473square.makesquare(matchsticks);
        System.out.println(res);
    }

}
