package leetCode;

/**
 * LC337rob - 打家劫舍 III（树形）
 *
 * 题目（概要）：房屋组织成二叉树，相邻（父子）不能同时偷，求最大金额。
 *
 * 解法说明：树形 DP，dp(node) 返回 [不偷该节点的最大收益, 偷该节点的最大收益]。偷则子节点都不能偷，不偷则子节点取 max(偷,不偷)。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 示例：root=[3,2,3,null,3,null,1] → 7（偷 3+3+1）
 */
public class LC337rob {

    /**
     * 树形 DP，返回 [不偷, 偷] 的最大收益
     */
    public int rob(TreeNode root) {
        int[] res = dp(root);
        return Math.max(res[0], res[1]);
    }

    public int[] dp(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dp(root.left);
        int[] right = dp(root.right);

        int rob = root.val + left[0] + right[0];
        int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{notRob, rob};
    }
}
