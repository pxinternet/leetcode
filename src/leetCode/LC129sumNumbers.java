package leetCode;

public class LC129sumNumbers {

    public int sumNumbers(TreeNode root) {

        return sumSubTree(root, 0);

    }

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
