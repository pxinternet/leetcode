package leetCode;

public class LC670MaximumSwap {

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
