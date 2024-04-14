package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC257binaryTreePath {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> resArray = new ArrayList<>();
        if (root == null) return resArray;

        StringBuilder path = new StringBuilder();
        binaryTreePath(root, path, resArray);

        return resArray;
    }

    public void binaryTreePath(TreeNode node, StringBuilder path, List<String> res) {
        if (node == null) return;
        int length = path.length();
        path.append(node.val);
        if (node.left == null && node.right == null) {
            res.add(path.toString());
        } else {
            path.append("->");
            binaryTreePath(node.left, path, res);
            binaryTreePath(node.right, path, res);
        }
        path.setLength(length);

    }


}
