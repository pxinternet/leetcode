package leetCode;

import java.util.HashMap;
import java.util.Map;

public class LC106buildTree {

    int postIdx;
    int[] postOrder;
    int[] inOrder;
    Map<Integer, Integer> idxMap = new HashMap<>();
    public TreeNode buildTree(int[] inOrder, int[] postOrder) {
        this.postOrder = postOrder;
        this.inOrder = inOrder;

        postIdx = postOrder.length - 1;

        int idx = 0;
        for (Integer val : inOrder)
            idxMap.put(val, idx++);

        return helper(0, inOrder.length - 1);

    }

    private TreeNode helper(int inLeft, int inRight) {
        if (inLeft > inRight) {
            return null;
        }

        int rootVal = postOrder[postIdx];
        TreeNode root = new TreeNode(rootVal);

        int index = idxMap.get(rootVal);

        postIdx--;

        root.right = helper(index + 1, inRight);
        root.left = helper(inLeft, index - 1);
        return root;
    }
}
