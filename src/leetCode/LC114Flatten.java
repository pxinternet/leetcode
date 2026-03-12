package leetCode;

/**
 * LC114 - 二叉树展开为链表
 *
 * 题目概要：将二叉树按前序遍历顺序原地展开为「右子树」单链表。
 *
 * 解法一（flatten）：后序遍历。prev 记录「已展开链」的当前头，递归时先右后左，
 * 回溯时把 root.right 接到 prev、root.left 置 null。原因：后序保证了我们先处理子树，
 * 回溯时 prev 恰好是 root 右子树展开后的头。
 *
 * 解法二（flattenO1）：Morris 前序。对每个有左子的节点，把左子最右节点指向 cur.right，
 * 再把 cur 右指针改为左子。原因：左子最右节点是 cur 前序后继的前驱，接上后可不建栈完成前序。
 *
 * 时间复杂度：O(n)；空间：O(h) 递归 / O(1) Morris
 */
public class LC114Flatten {

    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    public void flattenO1(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode p = cur.left;
                while (p.right != null) p = p.right;
                p.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }
}
