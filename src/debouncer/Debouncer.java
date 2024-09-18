package debouncer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Debouncer {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> future;

    private final long delay;
    private final Runnable task;

    public Debouncer(long delay, Runnable task) {
        this.delay = delay;
        this.task = task;
    }

    public synchronized void call(boolean immediate) {

        if (future != null && !future.isDone()) {
            future.cancel(false);
        }

        if (immediate) {
            task.run();
        }

        future = scheduler.schedule(task, delay, TimeUnit.MICROSECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

}
