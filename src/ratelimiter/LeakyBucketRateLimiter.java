package ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 漏桶限流器（Leaky Bucket Rate Limiter）
 * 
 * 算法原理：
 * - 请求进入漏桶，以固定速率流出
 * - 如果桶满了，新请求被拒绝
 * - 流出速率是恒定的，无法应对突发流量
 * 
 * 优点：
 * - 输出速率恒定，流量非常平滑
 * - 适合需要严格控制输出速率的场景
 * 
 * 缺点：
 * - 无法应对突发流量（与令牌桶的区别）
 * - 桶满时请求会被直接拒绝
 * 
 * 适用场景：需要严格控制输出速率，不允许突发的场景
 */
public class LeakyBucketRateLimiter implements RateLimiter {
    private final long capacity;              // 桶容量
    private final long leakRate;              // 每秒流出的请求数
    private final AtomicLong waterLevel;      // 当前水位（桶中的请求数）
    private final AtomicLong lastLeakTime;    // 上次漏水的时间（纳秒）

    /**
     * 构造函数
     * 
     * @param capacity 桶容量（最大请求数）
     * @param leakRatePerSecond 每秒流出的请求数
     */
    public LeakyBucketRateLimiter(long capacity, long leakRatePerSecond) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("桶容量必须大于0");
        }
        if (leakRatePerSecond <= 0) {
            throw new IllegalArgumentException("流出速率必须大于0");
        }
        this.capacity = capacity;
        this.leakRate = leakRatePerSecond;
        this.waterLevel = new AtomicLong(0);
        this.lastLeakTime = new AtomicLong(System.nanoTime());
    }

    @Override
    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    @Override
    public boolean tryAcquire(int permits) {
        if (permits <= 0) {
            throw new IllegalArgumentException("许可数量必须大于0");
        }
        if (permits > capacity) {
            return false; // 请求的许可数超过桶容量，直接拒绝
        }

        leakWater();

        // 尝试添加请求到桶中（CAS操作）
        while (true) {
            long currentLevel = waterLevel.get();
            long newLevel = currentLevel + permits;
            
            if (newLevel > capacity) {
                return false; // 桶已满
            }
            
            if (waterLevel.compareAndSet(currentLevel, newLevel)) {
                return true; // 成功添加
            }
            // CAS失败，重试
        }
    }

    /**
     * 漏水操作（延迟计算，按需漏水）
     */
    private void leakWater() {
        long now = System.nanoTime();
        long lastLeak = lastLeakTime.get();
        long elapsedNanos = now - lastLeak;

        if (elapsedNanos <= 0) {
            return;
        }

        // 计算应该流出的请求数
        long requestsToLeak = (elapsedNanos * leakRate) / 1_000_000_000L;

        if (requestsToLeak > 0) {
            // 尝试更新时间戳（CAS操作）
            if (lastLeakTime.compareAndSet(lastLeak, now)) {
                // 减少水位，但不能小于0
                waterLevel.updateAndGet(currentLevel -> {
                    long newLevel = currentLevel - requestsToLeak;
                    return Math.max(0, newLevel);
                });
            }
        }
    }

    @Override
    public long getAvailablePermits() {
        leakWater();
        return Math.max(0, capacity - waterLevel.get());
    }

    @Override
    public void reset() {
        waterLevel.set(0);
        lastLeakTime.set(System.nanoTime());
    }

    public long getCapacity() {
        return capacity;
    }

    public long getLeakRate() {
        return leakRate;
    }
}
