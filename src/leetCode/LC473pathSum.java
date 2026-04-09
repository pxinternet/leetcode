package leetCode;

/**
 * LC473pathSum - 路径总和 III
 *
 * 题目（概要）：给定二叉树根节点 root 和整数 targetSum，返回路径和等于 targetSum 的路径数量。
 * 路径不需要从根开始，也不需要到叶结束，但必须向下（从父到子）。
 *
 * 解法说明：
 * - 核心思想：双递归。pathSum(root, targetSum) 统计以 root 为根的树中所有满足条件的路径数，等于：
 *   1) 以 root 为起点的路径数 pathSumFrom(root, targetSum)
 *   2) 左子树中的路径数 pathSum(root.left, targetSum)
 *   3) 右子树中的路径数 pathSum(root.right, targetSum)
 * - pathSumFrom(node, targetSum)：从 node 出发向下，路径和恰为 targetSum 的路径数
 *
 * 时间复杂度：O(n^2)，最坏情况每个节点作为起点都要遍历子树
 * 空间复杂度：O(h)，递归栈深度
 *
 * 边界与注意事项：
 * - 空树返回 0
 * - targetSum 可能为负数，路径和可为 0
 *
 * 示例：root=[10,5,-3,3,2,null,11,3,-2,null,1], targetSum=8 → 3
 * 路径：5->3、5->2->1、-3->11
 */
public class LC473pathSum {

    /**
     * 统计以 root 为根的树中，路径和等于 targetSum 的路径数量
     *
     * 关键点逐行说明：
     * - 空树直接返回 0，无路径
     * - 总路径数 = 以 root 为起点的路径数 + 左子树中的路径数 + 右子树中的路径数
     * - 注意：左、右子树用相同的 targetSum，因为路径可以不从子树的根开始
     *
     * @param root      二叉树根节点
     * @param targetSum 目标和
     * @return 满足条件的路径数量
     */
    public int pathSum(TreeNode root, int targetSum) {
        // 边界条件：空树没有路径
        if (root == null) return 0;

        // 以 root 为起点的路径数 + 左子树中所有路径数 + 右子树中所有路径数
        // 例如：root=10，左子树有路径 5->3 和为 8，右子树有路径 -3->11 和为 8，加上以 10 为起点的路径
        return pathSumFrom(root, targetSum)
                + pathSum(root.left, targetSum)
                + pathSum(root.right, targetSum);
    }

    /**
     * 从 node 出发向下走，路径和恰为 targetSum 的路径数量
     *
     * 关键点逐行说明：
     * - 空节点返回 0
     * - 若 node.val == targetSum，当前节点单独构成一条路径，计 1
     * - 递归左右子，目标变为 targetSum - node.val（因为必须包含 node）
     *
     * 例如：node=5, targetSum=8，则 5 本身不是 8；递归左子 targetSum=3，若左子为 3 则找到一条
     *
     * @param node      当前节点
     * @param targetSum 剩余目标和（即从父到当前节点已累加后还差多少）
     * @return 从 node 出发且和为 targetSum 的路径数
     */
    private int pathSumFrom(TreeNode node, int targetSum) {
        // 边界条件：空节点无法构成路径
        if (node == null) return 0;

        // 当前节点值是否恰好等于剩余目标：相等则 node 单独成路径，计 1；否则计 0
        int count = (node.val == targetSum ? 1 : 0);

        // 继续向下：目标变为 targetSum - node.val，因为路径必须包含当前 node
        count += pathSumFrom(node.left, targetSum - node.val);
        count += pathSumFrom(node.right, targetSum - node.val);

        return count;
    }
}
