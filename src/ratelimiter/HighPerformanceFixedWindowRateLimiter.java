package ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 高性能固定窗口限流器（High Performance Fixed Window Rate Limiter）
 * 
 * 针对高QPS场景的优化：
 * 1. 时间戳缓存：减少 System.currentTimeMillis() 调用
 * 2. 快速路径：窗口未切换时快速返回
 * 3. 减少CAS重试：优化重试逻辑
 * 4. 预计算常量：避免重复计算
 */
public class HighPerformanceFixedWindowRateLimiter implements RateLimiter {
    private final long windowSizeInMillis;  // 窗口大小（毫秒）
    private final long maxRequests;         // 每个窗口允许的最大请求数
    private final AtomicLong windowStart;    // 当前窗口开始时间
    private final AtomicLong requestCount;  // 当前窗口请求计数
    
    // 优化：时间戳缓存（每10ms更新一次）
    private static final long CACHE_TIME_MILLIS = 10L;
    private volatile long cachedTime = System.currentTimeMillis();
    private volatile long cacheExpiry = cachedTime + CACHE_TIME_MILLIS;

    /**
     * 构造函数
     * 
     * @param maxRequests 每个窗口允许的最大请求数
     * @param windowSizeInSeconds 窗口大小（秒）
     */
    public HighPerformanceFixedWindowRateLimiter(long maxRequests, long windowSizeInSeconds) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("最大请求数必须大于0");
        }
        if (windowSizeInSeconds <= 0) {
            throw new IllegalArgumentException("窗口大小必须大于0");
        }
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInSeconds * 1000;
        long now = System.currentTimeMillis();
        this.windowStart = new AtomicLong(now);
        this.requestCount = new AtomicLong(0);
        this.cachedTime = now;
        this.cacheExpiry = now + CACHE_TIME_MILLIS;
    }

    @Override
    public boolean tryAcquire() {
        long now = getCachedTime();
        long currentWindowStart = windowStart.get();
        
        // 优化：快速路径 - 如果窗口未切换，直接尝试增加计数
        if (now - currentWindowStart < windowSizeInMillis) {
            // 窗口未切换，快速路径
            long currentCount = requestCount.get();
            if (currentCount >= maxRequests) {
                return false;
            }
            // 直接尝试CAS
            if (requestCount.compareAndSet(currentCount, currentCount + 1)) {
                return true;
            }
            // CAS失败，继续正常流程
        }
        
        // 正常路径：需要检查或重置窗口
        return tryAcquireNormal(now);
    }

    /**
     * 正常路径：检查窗口并尝试获取
     */
    private boolean tryAcquireNormal(long now) {
        long currentWindowStart = windowStart.get();
        
        // 检查是否需要重置窗口
        if (now - currentWindowStart >= windowSizeInMillis) {
            // 尝试重置窗口（CAS操作，保证线程安全）
            if (windowStart.compareAndSet(currentWindowStart, now)) {
                requestCount.set(0);
            }
            // 如果CAS失败，说明其他线程已经重置了，重新获取当前窗口
            currentWindowStart = windowStart.get();
        }
        
        // 检查当前窗口是否已满
        long currentCount = requestCount.get();
        if (currentCount >= maxRequests) {
            return false;
        }
        
        // 尝试增加计数
        int retries = 0;
        while (retries < 10) {
            currentCount = requestCount.get();
            if (currentCount >= maxRequests) {
                return false;
            }
            if (requestCount.compareAndSet(currentCount, currentCount + 1)) {
                return true;
            }
            retries++;
        }
        
        return false;
    }

    /**
     * 获取缓存的时间戳（每10ms更新一次）
     * 优化：使用volatile + 无锁更新，减少同步开销
     */
    private long getCachedTime() {
        long now = cachedTime;
        long expiry = cacheExpiry;
        long currentTime = System.currentTimeMillis();
        
        // 如果缓存未过期，直接返回
        if (currentTime < expiry) {
            return now;
        }
        
        // 缓存过期，尝试更新（无锁）
        // 使用volatile保证可见性，允许轻微的竞态条件（不影响正确性）
        cachedTime = currentTime;
        cacheExpiry = currentTime + CACHE_TIME_MILLIS;
        return cachedTime;
    }

    @Override
    public long getAvailablePermits() {
        long now = getCachedTime();
        long currentWindowStart = windowStart.get();
        
        // 检查是否需要重置窗口
        if (now - currentWindowStart >= windowSizeInMillis) {
            if (windowStart.compareAndSet(currentWindowStart, now)) {
                requestCount.set(0);
            }
        }
        
        long currentCount = requestCount.get();
        return Math.max(0, maxRequests - currentCount);
    }

    @Override
    public void reset() {
        long now = System.currentTimeMillis();
        windowStart.set(now);
        requestCount.set(0);
        cachedTime = now;
        cacheExpiry = now + CACHE_TIME_MILLIS;
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    public long getMaxRequests() {
        return maxRequests;
    }
}
