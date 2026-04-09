package leetCode;

/**
 * LC55 - 跳跃游戏
 *
 * 题目（概要）：数组 nums[i] 表示从索引 i 最多可跳的步数，判断能否从索引 0 到达最后一个索引。
 *
 * 解法说明：
 * - 解法一（从后往前）：维护 startIndex 为当前需到达的位置，若 nums[i] >= startIndex-i 则可从 i 跳到 startIndex，更新 startIndex=i。
 * - 解法二（贪心）：维护 rightMost 为从 [0,i] 起跳能到达的最远位置；若 i > rightMost 则无法到达 i，返回 false。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 单元素数组直接返回 true
 *
 * 示例：nums=[2,3,1,1,4] → true；[3,2,1,0,4] → false
 */
public class LC55canJump {

    /**
     * 从后往前递推：startIndex 为当前需到达的位置，若能从 i 跳到 startIndex 则更新
     *
     * 关键点：nums[i] >= startIndex - i 表示从 i 至少可跳 (startIndex-i) 步到达 startIndex
     *
     * @param nums 每格最大跳跃步数
     * @return 能否到达最后
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int startIndex = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] >= startIndex - i) {
                startIndex = i;   // 可从 i 到达 startIndex，则只需到达 i
            }
        }
        return startIndex == 0;
    }

    /**
     * 贪心：维护能到达的最远位置 rightMost，若 i 不可达则返回 false
     *
     * @param nums 每格最大跳跃步数
     * @return 能否到达最后
     */
    public boolean canJumpGreedy(int[] nums) {
        int n = nums.length;
        int rightMost = 0;

        for (int i = 0; i < n; i++) {
            if (i > rightMost) return false;   // 无法到达 i
            rightMost = Math.max(rightMost, i + nums[i]);
            if (rightMost >= n - 1) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LC55canJump lc55canJump = new LC55canJump();
        int[] nums = new int[]{3,2,1,0,4};
        boolean res = lc55canJump.canJump(nums);
        System.out.println(res);
    }
}
