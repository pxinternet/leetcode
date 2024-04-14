package leetCode;

public class LC331isValidSerialization {

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
