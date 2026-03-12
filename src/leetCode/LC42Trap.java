package leetCode;

/**
 * LC42Trap - 接雨水
 *
 * 题目（概要）：给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * 解法一（双指针 - trap）：
 * - 核心思想：从左右两端向中间逼近，每次用「较低一侧」的当前最高值作为水槽高度
 * - leftMax：从左到当前位置见过的最高柱子；rightMax：从右到当前位置见过的最高柱子
 * - 若 leftMax < rightMax，说明左侧是短板，当前位置能接的水量由 leftMax 决定；反之由 rightMax 决定
 * - 每次移动 left 或 right 中对应值较小的那一侧，保证不漏算
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 解法二（动态规划 - trapDp）：
 * - 核心思想：每个位置能接的水量 = min(左侧最高, 右侧最高) - 当前高度
 * - maxLeft[i]：位置 i 左侧（含 i）的最高柱子高度
 * - maxRight[i]：位置 i 右侧（含 i）的最高柱子高度
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - 空数组或 n < 3 时，无法形成凹槽，返回 0
 *
 * 示例：height = [0,1,0,2,1,0,1,3,2,1,2,1] → 6
 */
public class LC42Trap {

    /**
     * 双指针解法：从两端向中间扫描
     *
     * @param height 每根柱子的高度数组
     * @return 能接住的雨水量
     */
    public int trap(int[] height) {
        int n = height.length;
        if (n < 3) return 0;

        int left = 0;
        int right = n - 1;
        int leftMax = 0;
        int rightMax = 0;
        int ans = 0;

        while (left <= right) {
            // 更新两侧见过的最高高度（包含当前指针位置）
            rightMax = Math.max(height[right], rightMax);
            leftMax = Math.max(height[left], leftMax);

            // 短板在左侧：当前位置能接的水由 leftMax 决定；反之由 rightMax 决定
            // 原因：接水量 = min(左最高, 右最高) - 当前高；移动较小侧可保证不漏算
            if (leftMax < rightMax) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }

        return ans;
    }

    /**
     * 动态规划解法：预处理每个位置左右的最高柱子
     *
     * 关键点：maxLeft[i] 不含 i 时，需单独处理。本实现 maxLeft[i] 含 i，则当前位置水量为
     * min(maxLeft[i], maxRight[i]) - height[i]，其中 maxLeft 从左到右、maxRight 从右到左递推。
     *
     * @param height 每根柱子的高度数组
     * @return 能接住的雨水量
     */
    public int trapDp(int[] height) {
        int n = height.length;
        if (n < 3) return 0;

        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        maxLeft[0] = height[0];
        maxRight[n - 1] = height[n - 1];

        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }

        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }
        return ans;
    }

}
