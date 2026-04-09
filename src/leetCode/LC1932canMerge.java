package leetCode;

import java.util.List;

/**
 * LC1932 - 合并多棵二叉搜索树
 *
 * 题目（概要）：给定若干棵 BST，每棵树根值唯一。若某棵树的叶节点值等于另一棵树的根值，可合并。
 * 判断能否合并成一棵 BST，若能返回合并后根节点。
 *
 * 解法说明：
 * - 典型解法：找到根值只出现一次的根作为最终根，用 Map 记录根值->树；DFS 递归合并，校验中序是否严格递增。
 * - 本实现为占位，返回 null。
 *
 * 示例：trees=[2,1,3],[5],[4] → 可合并为单棵 BST
 */
public class LC1932canMerge {

    public TreeNode canMerge(List<TreeNode> trees) {
        return null;
    }
}
