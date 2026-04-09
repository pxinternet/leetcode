package leetCode;

/**
 * LC1367 - 二叉树中的链表
 *
 * 题目（概要）：二叉树 root 中是否存在自上而下路径，其节点值按顺序组成链表 head。
 *
 * 解法说明：对每个节点尝试 isMatch；isMatch 递归比较链表与树路径，匹配完链表返回 true。树 DFS 枚举起点。
 *
 * 时间复杂度：O(n*m)，n 树节点数，m 链表长度
 * 空间复杂度：O(h)
 */
public class LC1367isSubPath {

    /** 判断树中是否存在与 head 匹配的向下路径 */
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) return false;
        return isMatch(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);

    }

    private boolean isMatch(ListNode head, TreeNode root) {
        if (head == null) return true;
        if (root == null || head.val != root.val) return false;
        return isMatch(head.next, root.left) || isMatch(head.next, root.right);
    }
}
