package leetCode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * LC503 - 下一个更大元素 II
 *
 * 题目（概要）：循环数组 nums，对每个元素找其右侧（含循环）第一个更大的元素；不存在则为 -1。
 *
 * 算法原理：
 * - 单调栈通用套路：栈中存「尚未找到答案」的下标，保持栈对应值单调递减。当前数 nums[idx] 比栈顶大时，栈顶的「下一个更大」即为 nums[idx]，弹栈赋答案。
 * - 2n 模拟循环：遍历 0..2n-1，idx=i%n。第一遍 n 个元素正常处理；第二遍等价于「从数组头再扫一遍」，可给循环结构中靠右的元素找到左侧更大的数。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：res 初始 -1，栈存下标。
 * - 步骤 2：对 i=0..2n-1，idx=i%n。若 nums[idx] > nums[stack.peek()]，则 res[stack.pop()]=nums[idx]，反复弹直到栈空或栈顶更大。
 * - 步骤 3：将 idx 入栈。每个下标最多入栈出栈各一次，O(n)。
 *
 * 关键洞察：
 * - 非循环时（LC496）只遍历一遍；循环时遍历两遍保证每个元素都能「看到」其右侧（含 wrap）的更大值。
 * - 栈中下标对应的值单调递减，因此栈顶是「最可能需要当前数作为答案」的候选。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：nums=[1,2,1] → [2,-1,2]
 */
public class LC503nextGreaterElements {

    /**
     * 单调栈，遍历 2n 次模拟循环
     *
     * 关键点：i 从 0 到 2n-1，用 i%n 取实际下标
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);

        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < 2 * n; i++) {
            int idx = i % n;  // 模拟循环，第二遍可找到「右侧 wrap 到左侧」的更大值
            while (!stack.isEmpty() && nums[idx] > nums[stack.peek()]) {
                res[stack.pop()] = nums[idx];  // 栈顶找到「下一个更大」
            }
            stack.push(idx);
        }
        return res;
    }
}
