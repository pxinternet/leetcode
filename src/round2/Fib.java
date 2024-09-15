package round2;

public class Fib {

    public int fib(int n) {
        if (n == 0 || n == 1)
            return n;
        return fib(n - 1) + fib(n - 2);
    }

    public int fib2(int n) {

        if (n == 0 || n == 1) {
            return n;
        }

        int pre1 = 1;
        int pre2 = 0;
        int counter = 2;
        int result = 0;

        while (counter <= n) {
            result = pre1 + pre2;
            pre2 = pre1;
            pre1 = result;
            counter++;
        }

        return result;
    }

}
