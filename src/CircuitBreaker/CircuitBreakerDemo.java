package CircuitBreaker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CircuitBreakerDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

    }

    public static String unreliableService(int i) throws Exception {
        if (i % 2 == 0) {
            Thread.sleep(3000);
            throw new Exception("exception");
        }
        return "success" + i;
    }

    public static String fallbackService(int i) {
        return "fallback " + i;
    }

}
