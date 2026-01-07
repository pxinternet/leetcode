package CircuitBreaker;

import java.nio.file.OpenOption;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Practice {

    public <T> T call(Callable<T> task, Supplier<T> fallback) {

        if (state == CircuitBreakerState.OPEN) {

            // todo 可以转化成 half open

            // else
            {
                fallback.get();
            }
        }

        if (state == CircuitBreakerState.HALF_OPEN || state == CircuitBreakerState.COLSED) {
            Future<T> future = ExecutorService.submit(task);

            try {
                T reuslt = future.get();
                onSuccess();
                return result;
            } catch (Exception e) {
                onFailure();
                return fallback;
            }
        } else {
            return fallback.get()
        }
    }

}
