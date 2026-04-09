package leetCode;

/**
 * LC201 - 数字范围按位与
 *
 * 题目（概要）：求 [left, right] 内所有整数按位与的结果。
 *
 * 解法说明：公共前缀。left、right 不断右移直到相等，记录 shift，返回 left<<shift。或利用 right & (right-1) 消最低 1。
 *
 * 时间复杂度：O(1)（最多 31 位）
 * 空间复杂度：O(1)
 *
 * 示例：left=5, right=7 → 4
 */
public class LC201rangeBitwiseAnd {

    /** 返回 [left, right] 的按位与 */
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
