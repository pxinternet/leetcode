package leetCode;

public class LC114Flatten {

    private TreeNode prev = null;


    //本质是一个后序遍历
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

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
               while (p.right != null) {
                   p = p.right;
               }
               p.right = cur.right;
               cur.right = cur.left;
               cur.left = null;
           }
           cur = cur.right;
       }
    }
}
