package debouncer;

public class Throttler {

    private long lastExecutionTime = 0;
    private final long interval;
    private final Runnable task;

    public Throttler(long interval, Runnable task) {
        this.interval = interval;
        this.task = task;
    }

    public synchronized void call() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastExecutionTime >= interval) {
            task.run();
            lastExecutionTime = System.currentTimeMillis();
        }
    }

}
