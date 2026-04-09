package leetCode;

import java.util.*;

/**
 * LC47 - 全排列 II（含重复元素）
 *
 * 题目概要：给定可包含重复数字的序列 nums，返回所有不重复的全排列。
 *
 * 解法说明：
 * - 回溯 + 去重。先排序使相同元素相邻，在 DFS 中通过「相同元素只用前一个未用时才选」来剪枝，避免重复排列。
 * - 原因：若 nums[i]==nums[i-1] 且 !used[i-1]，说明在同一层中，nums[i-1] 已被回溯放弃，此时若选 nums[i]，
 *   则会产生与选 nums[i-1] 相同的分支（因后续可选集合相同），故跳过。
 *
 * 时间复杂度：O(n! * n)，最坏全排列数 * 拷贝 path
 * 空间复杂度：O(n)，递归栈 + path + used
 *
 * 边界：空数组返回空列表；单元素返回单排列。
 */
public class LC47 {
    /**
     * 求所有不重复全排列
     *
     * @param nums 可含重复的整数数组
     * @return 所有不重复排列的列表
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) return res;

        // 排序是去重的前提：相同元素相邻，才能用 nums[i]==nums[i-1] 判断
        Arrays.sort(nums);

        Deque<Integer> path = new LinkedList<>();
        boolean[] used = new boolean[len];
        dfs(nums, 0, len, used, path, res);
        return res;
    }

    /**
     * 回溯生成排列
     *
     * @param nums  原数组
     * @param depth 当前已选个数
     * @param len   数组长度
     * @param used  标记已选下标
     * @param path  当前路径
     * @param res   结果集
     */
    private void dfs(int[] nums, int depth, int len, boolean[] used, Deque<Integer> path, List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (used[i]) continue;

            // 去重：同一层中，若 nums[i]==nums[i-1] 且 nums[i-1] 未使用，则跳过 nums[i]
            // 原因：nums[i-1] 未使用说明在本次回溯中已被「放弃」，此时选 nums[i] 会和选 nums[i-1] 的后续分支完全重复
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            path.addLast(nums[i]);
            used[i] = true;
            dfs(nums, depth + 1, len, used, path, res);
            used[i] = false;
            path.removeLast();
        }
    }

    public static  void  main(String... arg) {
        LC47 lc47 = new LC47();
        int[] nums = new int[]{3, 3, 0,3};

        List<List<Integer>> res = lc47.permuteUnique(nums);

        System.out.println(res);

    }


}
