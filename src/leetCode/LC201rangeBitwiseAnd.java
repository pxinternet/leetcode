package leetCode;

public class LC201rangeBitwiseAnd {

    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;

        while (left < right) {
            left >>= 1;
            right >>= 1;
            ++shift;
        }
        return left << shift;
    }

    public int rangeBitwiseAndBk(int left, int right) {
        while (left < right) {
            right = right & (right - 1);
        }
        return right;
    }
}
