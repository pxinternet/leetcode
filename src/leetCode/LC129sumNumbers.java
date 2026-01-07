package leetCode;

/*
 LC129 - Sum Root to Leaf Numbers
 题目：给定一个二叉树，每条从根到叶的路径表示一个数字（按节点值拼接），计算所有根到叶路径代表的数字之和。
 思路：在递归过程中维护当前路径对应的数值 sum，遇到叶子节点时把该 sum 加入结果。
 时间复杂度 O(n)，空间复杂度 O(h)（递归栈）。
*/
public class LC129sumNumbers {

    public int sumNumbers(TreeNode root) {

        return sumSubTree(root, 0);

    }

    // 递归辅助：node 为当前节点，sum 为从根到 parent 的累积数值
    public int sumSubTree(TreeNode node, int sum) {
        if (node == null) {
            return 0;
        }

        sum = sum * 10 + node.val; // 把当前节点值拼接到末尾

        // 叶子节点：返回当前构造的完整数字
        if (node.left == null && node.right == null) {
            return sum;
        } else {
            // 否则继续递归左右子树并累加它们的结果
            return sumSubTree(node.left, sum) + sumSubTree(node.right, sum);
        }

    }
}
