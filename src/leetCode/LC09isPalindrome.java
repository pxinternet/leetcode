package leetCode;

public class LC09isPalindrome {

    public boolean isPalindrome(int x) {
        if (x == 0) return true;

        //负数和0结尾肯定不是回文数
        if (x < 0 || x % 10 == 0) return false;

        int reverseNum = 0;
        while(x > reverseNum) {
            reverseNum = reverseNum * 10 + x % 10;

            if (x == reverseNum) return true;
            x /= 10;
        }

        if (x == reverseNum) return true;
        return false;

    }


    public static void main(String... args) {
        System.out.println(1);
    }
}
