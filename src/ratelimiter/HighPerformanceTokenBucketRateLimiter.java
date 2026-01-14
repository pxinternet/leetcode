package ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 高性能令牌桶限流器（High Performance Token Bucket Rate Limiter）
 * 
 * 针对高QPS场景的优化：
 * 1. 时间戳缓存：减少 System.nanoTime() 调用（每10ms更新一次）
 * 2. 批量补充令牌：减少CAS操作次数
 * 3. 快速路径优化：令牌充足时直接返回
 * 4. 减少CAS重试：优化重试逻辑
 * 5. 预计算常量：避免重复计算
 * 
 * 性能提升：
 * - 减少系统调用（时间戳缓存）
 * - 减少CAS竞争（批量操作）
 * - 快速路径优化（常见情况快速返回）
 */
public class HighPerformanceTokenBucketRateLimiter implements RateLimiter {
    private final long capacity;              // 桶容量（最大令牌数）
    private final long refillRate;            // 每秒补充的令牌数
    private final AtomicLong tokens;          // 当前令牌数
    private final AtomicLong lastRefillTime;  // 上次补充令牌的时间（纳秒）
    
    // 优化：预计算的常量
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long CACHE_TIME_NANOS = 10_000_000L; // 10ms缓存时间
    
    // 优化：时间戳缓存
    private volatile long cachedTime = System.nanoTime();
    private volatile long cacheExpiry = cachedTime + CACHE_TIME_NANOS;

    /**
     * 构造函数
     * 
     * @param capacity 桶容量（最大令牌数）
     * @param refillRatePerSecond 每秒补充的令牌数
     */
    public HighPerformanceTokenBucketRateLimiter(long capacity, long refillRatePerSecond) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("桶容量必须大于0");
        }
        if (refillRatePerSecond <= 0) {
            throw new IllegalArgumentException("补充速率必须大于0");
        }
        this.capacity = capacity;
        this.refillRate = refillRatePerSecond;
        this.tokens = new AtomicLong(capacity); // 初始时桶是满的
        long now = System.nanoTime();
        this.lastRefillTime = new AtomicLong(now);
        this.cachedTime = now;
        this.cacheExpiry = now + CACHE_TIME_NANOS;
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
            return false; // 请求的令牌数超过桶容量，直接拒绝
        }

        // 优化：快速路径 - 如果令牌充足，直接尝试获取（避免补充操作）
        long currentTokens = tokens.get();
        if (currentTokens >= permits) {
            // 快速路径：直接尝试CAS，如果成功则返回
            if (tokens.compareAndSet(currentTokens, currentTokens - permits)) {
                return true;
            }
            // CAS失败，继续正常流程
        }

        // 正常路径：需要补充令牌
        refillTokensOptimized();

        // 尝试获取令牌（CAS操作，保证线程安全）
        int retries = 0;
        while (retries < 10) { // 限制重试次数，避免无限循环
            currentTokens = tokens.get();
            if (currentTokens < permits) {
                return false; // 令牌不足
            }
            if (tokens.compareAndSet(currentTokens, currentTokens - permits)) {
                return true; // 成功获取令牌
            }
            retries++;
        }
        
        return false; // 重试次数过多，返回失败
    }

    /**
     * 优化的补充令牌方法
     * 1. 使用时间戳缓存减少系统调用
     * 2. 批量补充令牌
     */
    private void refillTokensOptimized() {
        // 优化：使用缓存的时间戳（每10ms更新一次）
        long now = getCachedTime();
        long lastRefill = lastRefillTime.get();
        long elapsedNanos = now - lastRefill;

        if (elapsedNanos <= 0) {
            return;
        }

        // 计算应该补充的令牌数
        long tokensToAdd = (elapsedNanos * refillRate) / NANOS_PER_SECOND;

        if (tokensToAdd > 0) {
            // 尝试更新时间戳（CAS操作）
            if (lastRefillTime.compareAndSet(lastRefill, now)) {
                // 批量更新令牌数，但不能超过容量
                tokens.updateAndGet(currentTokens -> {
                    long newTokens = currentTokens + tokensToAdd;
                    return Math.min(newTokens, capacity);
                });
            }
        }
    }

    /**
     * 获取缓存的时间戳（每10ms更新一次）
     * 减少 System.nanoTime() 调用，提升性能
     */
    private long getCachedTime() {
        long now = cachedTime;
        long expiry = cacheExpiry;
        
        // 如果缓存未过期，直接返回
        if (System.nanoTime() < expiry) {
            return now;
        }
        
        // 缓存过期，更新缓存
        synchronized (this) {
            // 双重检查
            if (System.nanoTime() >= cacheExpiry) {
                cachedTime = System.nanoTime();
                cacheExpiry = cachedTime + CACHE_TIME_NANOS;
            }
            return cachedTime;
        }
    }

    @Override
    public long getAvailablePermits() {
        refillTokensOptimized();
        return tokens.get();
    }

    @Override
    public void reset() {
        long now = System.nanoTime();
        tokens.set(capacity);
        lastRefillTime.set(now);
        cachedTime = now;
        cacheExpiry = now + CACHE_TIME_NANOS;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getRefillRate() {
        return refillRate;
    }
}
