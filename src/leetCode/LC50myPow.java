package leetCode;

public class LC50myPow {

    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 /x;
            N = -N;
        }

        double ans = 1;
        double currentProduct = x;
        for (long i = N; i > 0; i /= 2) {
            if ((i % 2) == 1) {
                ans = ans * currentProduct;
            }
            currentProduct = currentProduct * currentProduct;
            System.out.println("i = " + i + " currentProduct = " + currentProduct + " ans = " + ans);

        }
        return ans;
    }

    public static void main(String[] args) {
        LC50myPow myPow = new LC50myPow();
        myPow.myPow(7, 9);


    }
}
