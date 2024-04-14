package leetCode;

public class LC331isValidSerialization {

    /*
    要验证二叉树的前序序列化是否有效，我们可以使用一个计数器 degree 来跟踪节点的度数。对于二叉树中的任何节点，如果添加一个新的节点，那么度数就会增加1，因为新的节点提供了一个新的出度；但是如果新的节点是一个非空节点，那么度数就会增加2，因为非空节点提供了两个新的入度。如果序列化是有效的，那么在遍历完整个序列化字符串后，所有的度数应该都能够抵消。
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.isEmpty()) return false;

        String[] nodes = preorder.split(",");
        int degree = -1;

        for (String node  : nodes) {
            degree++;
            if (degree > 0)
                return false;

            if (!node.equals("#")) {
                degree -=2;
            }
        }
        return degree == 0;



    }
}
