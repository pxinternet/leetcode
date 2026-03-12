package leetCode;

/**
 * LC112hasPathSum - 路径总和
 *
 * 题目（概要）：判断是否存在从根到叶的路径，路径上节点值之和等于 targetSum。
 * 路径必须从根开始、到叶节点结束。
 *
 * 解法说明：
 * - 核心思想：递归，每步将 targetSum 减去当前节点值，传递给孩子
 * - 若 root 为叶节点且 targetSum - root.val == 0，说明找到满足条件的路径
 * - 否则递归左右子树，任一满足即可
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h 为树高
 *
 * 边界与注意事项：
 * - 空树返回 false
 * - 叶节点定义：left 和 right 均为 null
 *
 * 示例：root=[5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum=22 → true（路径 5→4→11→2）
 */
public class LC112hasPathSum {

    /**
     * 递归判断是否存在从根到叶、路径和为 targetSum 的路径
     *
     * 关键点逐行说明：
     * - 空节点：无路径，返回 false
     * - 叶节点且 targetSum==root.val：当前节点值恰好等于剩余目标，找到路径
     * - 非叶节点：递归左右子，targetSum 减去 root.val 后传递
     *
     * @param root      二叉树根节点
     * @param targetSum 路径目标和
     * @return 存在满足条件的路径返回 true
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        // 边界条件：空树没有路径，返回 false
        if (root == null) return false;

        // 叶节点：left 和 right 均为 null
        // 若 targetSum - root.val == 0，说明从根到当前叶的路径和恰为 targetSum
        // 例如：路径 5→4→11→2，到叶 2 时 targetSum=2，root.val=2，满足
        if (root.left == null && root.right == null && targetSum - root.val == 0) return true;

        // 非叶节点：递归左右子树，targetSum 减去当前节点值后传递
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }
}
