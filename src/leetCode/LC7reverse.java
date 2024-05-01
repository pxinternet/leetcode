package leetCode;

public class LC7reverse {

    public int reverse(int x) {
        int max = Integer.MAX_VALUE / 10;
        int res = 0;
        while (x != 0) {
            int mod = x % 10;

            if (res > max || res < -max) {
                return 0;
            }
            res = res * 10 + mod;
            x = x/10;
            System.out.println("mod = " +mod + " res = " +res + " x = " +x);
        }
        return res;
    }

    public static void main(String[] args) {

        LC7reverse lc7reverse = new LC7reverse();
        int res = lc7reverse.reverse(-123);
        System.out.println("res = " + res);
    }
}
