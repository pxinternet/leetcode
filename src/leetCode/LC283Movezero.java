package leetCode;

/**
 * LC283Movezero - 移动零
 *
 * 题目（概要）：给定数组 nums，将所有 0 移动到数组末尾，同时保持非零元素的相对顺序。必须在原地完成，不能复制数组。
 *
 * 解法说明：
 * - 核心思想：双指针，left 指向「下一个非零元素应放的位置」，right 遍历数组
 * - 当 nums[right] != 0 时，与 nums[left] 交换，并 left++；right 始终前进
 * - 这样保证 [0, left) 区间内全是非零元素，且相对顺序不变
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 无零时，left 与 right 同步前进，交换等同于自身交换，无影响
 *
 * 示例：nums = [0,1,0,3,12] → [1,3,12,0,0]
 */
public class LC283Movezero {

    /**
     * 原地将所有零移到末尾，保持非零元素相对顺序
     *
     * 关键点：left 为「写入位置」，right 为「扫描指针」；遇到非零则交换并写入 left，再左移 left
     *
     * @param nums 待处理数组，会被原地修改
     */
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) return;

        // left：下一个非零元素应写入的位置；right：扫描指针
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            if (nums[right] != 0) {
                // 将非零元素交换到 left 位置，保证 [0, left) 全为非零
                // 例如：nums=[0,1,0,3]，right=1 时交换后 [1,0,0,3]，left=1
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
                left++;
            }
            right++;
        }
    }

}
