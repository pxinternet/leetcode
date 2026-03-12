package leetCode;

/**
 * LC331 - 验证二叉树的前序序列化
 *
 * 题目（概要）：前序序列化用逗号分隔，空节点为 "#"。判断给定字符串是否为有效二叉树的合法序列化。
 *
 * 解法说明：
 * - 核心思想：槽位计数。根提供 1 个槽；每个节点消耗 1 槽；非 "#" 节点提供 2 槽。遍历时槽位不可为负，结束时槽位应为 0。
 * - 本实现用 degree：初始 -1 等价于 1 槽；每节点 degree++（消耗 1 槽），degree>0 即槽不足；非 "#" 时 degree-=2（提供 2 槽）。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n) split
 *
 * 示例：preorder="9,3,4,#,#,1,#,#,2,#,6,#,#" → true
 */
public class LC331isValidSerialization {

    /**
     * 槽位/度数校验：根 1 槽，每节点耗 1 槽，非空节点补 2 槽
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.isEmpty()) return false;

        String[] nodes = preorder.split(",");
        int degree = -1;

        for (String node : nodes) {
            degree++;
            if (degree > 0) return false;
            if (!node.equals("#")) degree -= 2;
        }
        return degree == 0;



    }
}
