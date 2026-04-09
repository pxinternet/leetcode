package leetCode;

/**
 * LC988 - 从叶结点开始的最小字符串
 *
 * 题目（概要）：二叉树节点值 0-25 表示 'a'-'z'。求从根到叶所有路径中，字典序最小的路径字符串。
 * 路径从叶到根读取（即先 append 叶，再 parent）。
 *
 * 解法说明：
 * - 核心思想：DFS 从根到叶，path 记录当前路径（根→叶顺序）。到叶时 reverse 得到叶→根字符串，与 ans 比较取小。
 * - sb.append((char)('a'+node.val))，回溯时 sb.deleteCharAt(sb.length()-1)。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 边界与注意事项：
 * - ans 初始为 "~"（大于任意小写串）
 *
 * 示例：root=[0,1,2,3,4,3,4] → "dba"
 */
public class LC988smallestFromLeaf {

    private String ans = "~";

    /**
     * 求从叶到根字典序最小的路径字符串
     *
     * 关键点：路径从根到叶构建，到叶时 reverse 比较（因题目要求从叶到根读）
     *
     * @param root 二叉树根（节点值 0-25）
     * @return 最小字典序路径串
     */
    public String smallestFromLeaf(TreeNode root) {
        dfs(root, new StringBuilder());
        return ans;
    }

    private void dfs(TreeNode node, StringBuilder sb) {
        if (node == null) return;

        sb.append((char) ('a' + node.val));

        if (node.left == null && node.right == null) {
            sb.reverse();
            String s = sb.toString();
            sb.reverse();
            if (s.compareTo(ans) < 0) ans = s;
        }

        dfs(node.left, sb);
        dfs(node.right, sb);
        sb.deleteCharAt(sb.length() - 1);
    }
}
