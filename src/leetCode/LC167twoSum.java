package leetCode;

/**
 * LC167 - 两数之和 II（有序数组）
 *
 * 题目（概要）：给定非递减数组 numbers 与整数 target，求两个不同下标（1-indexed）
 * 使其对应元素之和等于 target。题目保证有且仅有一组解。
 *
 * 解法说明：
 * - 核心思想：双指针。left=0、right=n-1，若 sum>target 则 right--，否则 left++。
 * - 单调性：数组有序，sum 过大只能通过 right-- 减小，过小只能通过 left++ 增大。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 下标从 1 开始，返回时需 +1
 * - 题目保证有解，不必处理无解情况
 *
 * 示例：numbers=[2,7,11,15], target=9 → [1,2]
 */
public class LC167twoSum {

    /**
     * 双指针在有序数组中找两数之和等于 target
     *
     * @param numbers 非递减数组
     * @param target  目标和
     * @return 两下标（1-indexed），若不存在返回空数组
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum > target) right--;      // 过大，右指针左移减小
            else if (sum < target) left++;   // 过小，左指针右移增大
            else return new int[]{left + 1, right + 1};
        }
        return new int[0];
    }
}
