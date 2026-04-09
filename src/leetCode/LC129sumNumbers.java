package leetCode;

/**
 * LC129sumNumbers - 求根节点到叶节点数字之和
 *
 * 题目（概要）：每条从根到叶的路径表示一个数字（父节点值×10+子节点值依次拼接），求所有路径数字之和。
 *
 * 解法说明：递归维护当前路径数值 sum=sum*10+node.val，到叶节点时返回 sum，否则递归左右子并相加。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 示例：root=[1,2,3]（1 左 2 右 3）→ 12+13=25
 */
public class LC129sumNumbers {

    public int sumNumbers(TreeNode root) {
        return sumSubTree(root, 0);
    }

    /**
     * 递归：node 为当前节点，sum 为从根到 node 父节点的累积数值
     *
     * @param node 当前节点
     * @param sum  路径上到父节点为止构成的数字
     * @return 以 node 为根的子树中，所有根到叶路径数字之和
     */
    public int sumSubTree(TreeNode node, int sum) {
        if (node == null) {
            return 0;
        }

        sum = sum * 10 + node.val;

        if (node.left == null && node.right == null) {
            return sum;
        } else {
            return sumSubTree(node.left, sum) + sumSubTree(node.right, sum);
        }
    }
}
