package leetCode;

public class LC66plusOne {

    public int[] plusOne(int[] digits) {
        int n = digits.length;

        int res = digits[n - 1] + 1;
        int curry = res /10;
        digits[n - 1] = res % 10;


        for (int i = n - 2; i >= 0; i--) {
            res = digits[i]  + curry;
            curry = res /10;
            digits[i] = res % 10;
        }

        if (digits[0] == 0) {
            digits = new int[n + 1];
            digits[0] = 1;
        }
        return digits;
    }
}
