package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumbersUpto1000 {

    public static List<Integer> primeNumbers(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);

        isPrime[0] = false;
        isPrime[1] = false;

        List<Integer> res = new ArrayList<>();

        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }

        for (int p = 2; p <= n; p++) {
            if (isPrime[p]) {
                res.add(p);
            }
        }

        return res;
    }

}
