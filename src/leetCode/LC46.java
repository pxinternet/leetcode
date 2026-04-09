package leetCode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * LC46 - 全排列
 *
 * 题目（概要）：给定不含重复元素的数组 nums，返回其所有可能的全排列。
 *
 * 解法说明：
 * - 核心思想：回溯，用 used 数组标记已选元素，path 记录当前排列，depth 表示已选个数
 * - 当 depth == length 时得到一个完整排列
 *
 * 时间复杂度：O(n * n!)
 * 空间复杂度：O(n)，递归栈与 used、path
 *
 * 边界与注意事项：
 * - 空数组返回空列表
 *
 * 示例：nums = [1,2,3] → [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 */
public class LC46 {

    /**
     * 返回 nums 的全排列
     *
     * @param nums 不含重复元素的数组
     * @return 所有全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) return res;

        boolean[] used = new boolean[len];
        Deque<Integer> path = new LinkedList<>();
        dfs(nums, len, 0, path, used, res);
        return res;
    }

    /**
     * 回溯生成全排列
     *
     * @param depth 已选元素个数
     * @param used  标记哪些元素已选
     */
    private void dfs(int[] nums, int length, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        if (depth == length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < length; i++) {
            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;
                dfs(nums, depth + 1, length, path, used, res);
                used[i] = false;
                path.removeLast();
            }
        }
    }

}
