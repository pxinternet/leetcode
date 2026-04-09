package round3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SimpleTaskScheduler - 简单任务调度器
 *
 * 题目（概要）：基于 ScheduledExecutorService，支持延迟执行与周期执行；scheduledTask 返回 taskId，cancelTask 可取消。
 *
 * 算法原理：
 * - schedule：单次任务用 schedule(task, delay, SECONDS)；周期任务用 scheduleAtFixedRate(task, delay, period, SECONDS)。
 * - 任务管理：taskId 自增生成，ScheduledFuture 存入 map，cancel 时调用 future.cancel(false)。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：scheduledTask：taskId++，根据 isRepeating 选择 schedule 或 scheduleAtFixedRate，存入 map。
 * - 步骤 2：cancelTask：从 map 取 ScheduledFuture，未取消则 cancel(false)。
 *
 * 关键洞察：ScheduledFuture 控制任务生命周期；cancel(false) 允许执行中的任务完成。
 *
 * 时间复杂度：scheduledTask O(1)；cancelTask O(1)
 * 空间复杂度：O(任务数)
 */
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
