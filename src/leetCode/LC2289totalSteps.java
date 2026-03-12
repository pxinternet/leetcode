package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC2289 - 使数组按非递减顺序排列
 *
 * 题目（概要）：每次可删除满足 nums[i-1] > nums[i] 的 nums[i]，求直到无法删除为止的步数。
 *
 * 算法原理：
 * - 步数传递性：若 A > B，则 B 的删除会「触发」A 在下一步被左侧元素考虑；A 的删除步数 = 1 + max(B 的步数, ...)。
 * - 单调栈作用：栈中按从左到右维护「尚未被右侧元素触发删除」的元素；当前 num 比栈顶大时，栈顶元素会被 num 间接触发，弹栈并传递步数。
 * - 栈存 (值, 步数)：步数表示「该元素被删除所需的轮数」，弹栈时取 max 因为要等所有右侧更小元素都删完才能轮到当前元素。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：从左到右遍历，栈保持单调递减（栈底到栈顶）。
 * - 步骤 2：当 num >= 栈顶：弹栈，steps = max(steps, 弹掉元素的步数)，直到栈空或栈顶 > num。
 * - 步骤 3：入栈 (num, steps+1)（栈非空时当前元素会被栈顶「挡住」，需等栈顶先删，故 +1）；若栈空则 steps=0（当前不会被删）。
 *
 * 关键洞察：
 * - 为何栈中存 (值, 步数)：值用于比较大小，步数用于传递「删除顺序」；同一时刻可能多个元素在同一步被删，取 max 保证步数正确。
 * - 示例：nums=[5,3,4,4,7,3,6,11,8,5,11]，7 右侧的 3,6,11,8,5 形成多轮删除，最大步数为 3。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：nums=[5,3,4,4,7,3,6,11,8,5,11] → 3
 */
public class LC2289totalSteps {

    /**
     * 单调栈求删除过程的总步数
     *
     * 关键点：栈中记录 (值, 被删除所需步数)，当前数触发栈顶弹出时更新最大步数
     *
     * @param nums 整数数组
     * @return 总删除步数
     */
    public int totalSteps(int[] nums) {
        Deque<int[]> stack = new LinkedList<>();
        int maxSteps = 0;

        for (int num : nums) {
            int steps = 0;
            // 当前 num 比栈顶大（或等），栈顶会被 num 右侧的某个更小值间接触发删除，弹栈并传递步数
            while (!stack.isEmpty() && stack.peek()[0] <= num) {
                steps = Math.max(steps, stack.pop()[1]);  // 取 max：要等所有弹掉元素都删完
            }
            steps = stack.isEmpty() ? 0 : steps + 1;  // 栈空则不会被删；否则需等栈顶先删
            maxSteps = Math.max(maxSteps, steps);
            stack.push(new int[]{num, steps});
        }
        return maxSteps;
    }
}
