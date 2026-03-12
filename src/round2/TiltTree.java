package round2;

import leetCode.TreeNode;

/**
 * TiltTree - 二叉树的坡度（LeetCode 563）
 *
 * 题目（概要）：定义节点坡度为 |左子树和 - 右子树和|，求二叉树所有节点坡度之和。
 *
 * 算法原理：
 * - 后序遍历：先求得左右子树和，再计算当前坡度并累加，返回以当前节点为根的子树和。
 * - 递归：sum(node) = node.val + sum(left) + sum(right)；tilt(node) = |sum(left) - sum(right)|。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：postOrderSum(node)：null 返回 0。
 * - 步骤 2：leftSum=postOrderSum(left)，rightSum=postOrderSum(right)。
 * - 步骤 3：totalTilt += |leftSum - rightSum|。
 * - 步骤 4：return node.val + leftSum + rightSum。
 *
 * 关键洞察：坡度为当前节点的贡献，与子树和无关；后序保证在计算 tilt 时左右和已就绪。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h 为树高
 *
 * 示例：[1,2,3] 根坡度 |2-3|=1，子节点坡度 0，总坡度 1
 */
public class TiltTree {

    private int totalTilt = 0;

    public int findTilt(TreeNode root) {
        postOrderSum(root);
        return totalTilt;
    }

    private int postOrderSum(TreeNode node) {

        if (node == null)
            return 0;

        int leftSum = postOrderSum(node.left);
        int rightSum = postOrderSum(node.right);

        int nodeTilt = Math.abs(leftSum - rightSum);

        totalTilt += nodeTilt;

        return node.val + leftSum + rightSum;

    }

}
