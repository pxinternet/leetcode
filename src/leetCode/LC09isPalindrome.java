package leetCode;

/**
 * LC9 - 回文数
 *
 * 题目概要：判断整数 x 是否为回文数（从左到右与从右到左读相同）。
 *
 * 解法说明：反转后半部分数字与前半比较。负数或尾数为 0 的非零数直接 false。
 * 循环中每次 x/=10、reverseNum = reverseNum*10 + x%10，当 reverseNum >= x 时停下，
 * 比较 x==reverseNum（偶数位）或 x==reverseNum/10（奇数位，中间位在 reverseNum 中）。
 *
 * 时间复杂度：O(log x)
 * 空间复杂度：O(1)
 */
public class LC09isPalindrome {

    public boolean isPalindrome(int x) {
        if (x == 0) return true;
        if (x < 0 || x % 10 == 0) return false;

        int reverseNum = 0;
        while (x > reverseNum) {
            reverseNum = reverseNum * 10 + x % 10;
            if (x == reverseNum) return true;
            x /= 10;
        }
        return x == reverseNum;
    }


    public static void main(String... args) {
        System.out.println(1);
    }
}
