package leetCode;

public class LC69mySqrt {

    public int mySqrt(int x) {

        if (x == 0) {
            return 0;
        }

        int left = 0, right = x;
        int ans = -1;

        //要兼容 == 1的情况
        while (left <= right) {
            int mid = left + (right - left) / 2;

            System.out.println("left = " +left +" right = " + right + " mid = " + mid );

            if ( (long)mid * mid <= x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left - 1;

    }

    public static void main(String[] args) {
        LC69mySqrt mySqrt = new LC69mySqrt();

        int res = mySqrt.mySqrt(1);

        System.out.println(res);

    }
}
