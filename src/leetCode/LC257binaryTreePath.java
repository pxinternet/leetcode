package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC257 - 二叉树的所有路径
 *
 * 题目（概要）：给定二叉树根节点，返回从根到叶子的所有路径的字符串表示，格式 "1->2->5"。
 *
 * 解法说明：
 * - 核心思想：DFS + StringBuilder 回溯。递归时追加当前节点值，到达叶子时 path 即为一条路径；
 *   回溯时 path.setLength(length) 恢复，以便尝试其它分支。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 边界与注意事项：
 * - 空树返回空列表
 * - 叶节点：left 和 right 均为 null
 *
 * 示例：root=[1,2,3,null,5] → ["1->2->5","1->3"]
 */
public class LC257binaryTreePath {

    /**
     * 收集从根到叶的所有路径
     *
     * @param root 根节点
     * @return 路径字符串列表
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> resArray = new ArrayList<>();
        if (root == null) return resArray;
        StringBuilder path = new StringBuilder();
        binaryTreePath(root, path, resArray);
        return resArray;
    }

    /**
     * DFS 遍历，path 记录当前路径，到达叶子时加入结果
     *
     * 关键点：进入时记录 path.length()，退出前 path.setLength(length) 回溯
     *
     * @param node 当前节点
     * @param path 当前路径
     * @param res  结果集
     */
    private void binaryTreePath(TreeNode node, StringBuilder path, List<String> res) {
        if (node == null) return;
        int length = path.length();
        path.append(node.val);
        if (node.left == null && node.right == null) {
            res.add(path.toString());
        } else {
            path.append("->");                    // 非叶子，先加箭头再递归
            binaryTreePath(node.left, path, res);
            binaryTreePath(node.right, path, res);
        }
        path.setLength(length);   // 回溯：恢复到进入前的状态
    }
}
