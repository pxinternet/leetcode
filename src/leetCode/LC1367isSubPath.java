package leetCode;

public class LC1367isSubPath {

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
