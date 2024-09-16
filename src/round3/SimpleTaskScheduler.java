package round3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleTaskScheduler {


    private final ScheduledExecutorService scheduler;

    private final AtomicInteger taskIdGenerator = new AtomicInteger(0);

    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public SimpleTaskScheduler(int threadPoolSize) {

        this.scheduler = Executors.newScheduledThreadPool(threadPoolSize);

    }

    public int scheduledTask(Runnable task, long delayInSeconds, long periodInSeconds, boolean isRepeating) {
        int taskId = taskIdGenerator.incrementAndGet();

        ScheduledFuture<?> scheduledFuture;

        if (isRepeating) {
            scheduledFuture = scheduler.scheduleAtFixedRate(task, delayInSeconds, periodInSeconds, TimeUnit.SECONDS);
        } else {
            scheduledFuture = scheduler.schedule(task, delayInSeconds, TimeUnit.SECONDS);
        }

        scheduledTasks.put(taskId, scheduledFuture);

        return taskId;
    }

    public void cancelTask(int taskId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(taskId);
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
    }




}
