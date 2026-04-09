package leetCode;

/**
 * LC670 - 最大交换
 *
 * 题目（概要）：给定非负整数，最多交换其中两位数字一次，使结果最大。
 *
 * 解法说明：记录每个数字 0-9 最后出现位置；从左往右找第一个可换大的位置（右侧有更大数字且位置更靠右），交换一次即返回。
 *
 * 时间复杂度：O(digits)
 * 空间复杂度：O(1)
 *
 * 示例：2736 → 7236
 */
public class LC670MaximumSwap {

    /** 最多交换两位，返回最大可能值 */
    public int maximumSwap(int num) {
        if (num < 10) return num;
        //暴力算法

        //前面的数字交换权重会比较到
        //感觉从高位往低位走，找到第一个就是；
        //怎么最左侧数字替换右侧最大值；
        char[] digits = Integer.toString(num).toCharArray();
        int[] last = new int[10];

        for(int i = 0; i < digits.length; i++) {
            last[digits[i] - '0'] = i;
        }

        for (int i = 0; i < digits.length; i++) {
            for (int d = 9; d > digits[i] - '0'; d--) {
                if (last[d] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[last[d]];
                    digits[last[d]] = tmp;
                    return Integer.parseInt(new String(digits));
                }
            }


        }
        return num;
    }
}
