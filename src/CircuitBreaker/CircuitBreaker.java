package CircuitBreaker;

import java.lang.foreign.SymbolLookup;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import javax.sound.midi.SysexMessage;

public class CircuitBreaker {

    private volatile CircuitBreakerState state = CircuitBreakerState.COLSED;

    private final int failureThreshold;

    private final int successThreshold;

    private final long timeOut;

    private final ExecutorService executorService;

    private final int requestVolumThreshold;

    private final AtomicInteger failureCount = new AtomicInteger(0);

    private final AtomicInteger successCount = new AtomicInteger(0);

    private final AtomicInteger requestCount = new AtomicInteger(0);


    private final AtomicLong lastFailureTime = new AtomicLong(0);

    private final Object lock = new Object();

    public CircuitBreaker(ExecutorService executorService, int failureThreshold, int requestVolumThreshold, int successThreshold, long timeOut) {
        this.executorService = executorService;
        this.failureThreshold = failureThreshold;
        this.requestVolumThreshold = requestVolumThreshold;
        this.successThreshold = successThreshold;
        this.timeOut = timeOut;
    }




    private void onSuccess() {
        if (state == CircuitBreakerState.HALF_OPEN) {
            int currentSucess = successCount.incrementAndGet();
            if (currentSucess >= successThreshold) {
                synchronized (lock) {
                    if (state == CircuitBreakerState.HALF_OPEN) {
                        state = CircuitBreakerState.COLSED;
                        resetCounts();
                    }
                }
            }
        } else if (state == CircuitBreakerState.COLSED) {
            requestCount.incrementAndGet();
        }
    }

    private void onFailure() {
        if (state == CircuitBreakerState.HALF_OPEN) {
            synchronized (lock) {
                if (state == CircuitBreakerState.HALF_OPEN) {
                    state = CircuitBreakerState.OPEN;
                    lastFailureTime.set(System.currentTimeMillis());
                    resetCounts();
                }
            }
        } else if (state == CircuitBreakerState.COLSED) {
            int currentFailure = failureCount.incrementAndGet();
            int currentRequest = requestCount.incrementAndGet();

            if (currentRequest >= requestVolumThreshold) {

                int failureRate = (currentFailure * 100) / currentRequest;

                if (failureRate >= failureThreshold) {
                    synchronized (lock) {
                        if (state == CircuitBreakerState.COLSED) {
                            state = CircuitBreakerState.OPEN;
                            lastFailureTime.set(System.currentTimeMillis());
                            resetCounts();
                        }
                    }
                }
            }

        }
    }


    public <T> T call(Callable<T> callable, Supplier<T> fallback) throws Exception {

        if (state == CircuitBreakerState.OPEN) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastFailureTime.get() > timeOut) {

                synchronized (lock) {

                    if (state == CircuitBreakerState.OPEN && currentTime - lastFailureTime.get() > timeOut) {
                        state = CircuitBreakerState.HALF_OPEN;
                        resetCounts();
                    }
                }

            } else {
                return fallback.get();
            }
        }

        if (state == CircuitBreakerState.HALF_OPEN || state == CircuitBreakerState.COLSED) {

            Future<T> future = executorService.submit(callable);

            try {

                T result = future.get(timeOut, TimeUnit.MILLISECONDS);
                onSuccess();
                return result;

            } catch (Exception e) {
                onFailure();
                return fallback.get();
            }
        } else {
            return fallback.get();
        }

    }

    private void resetCounts() {
        failureCount.set(0);
        successCount.set(0);
        requestCount.set(0);
    }


}
