package ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 固定窗口限流器（Fixed Window Rate Limiter）
 * 
 * 算法原理：
 * - 将时间划分为固定大小的窗口（如1秒、1分钟等）
 * - 每个窗口内允许固定数量的请求
 * - 窗口重置时，计数器清零
 * 
 * 优点：
 * - 实现简单，内存占用小
 * - 性能好，O(1)时间复杂度
 * 
 * 缺点：
 * - 窗口边界可能出现流量突增（如窗口切换瞬间）
 * - 无法平滑限流
 * 
 * 适用场景：对限流精度要求不高的场景
 */
public class FixedWindowRateLimiter implements RateLimiter {
    private final long windowSizeInMillis;  // 窗口大小（毫秒）
    private final long maxRequests;         // 每个窗口允许的最大请求数
    private final AtomicLong windowStart;   // 当前窗口开始时间
    private final AtomicLong requestCount;  // 当前窗口请求计数

    /**
     * 构造函数
     * 
     * @param maxRequests 每个窗口允许的最大请求数
     * @param windowSizeInSeconds 窗口大小（秒）
     */
    public FixedWindowRateLimiter(long maxRequests, long windowSizeInSeconds) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("最大请求数必须大于0");
        }
        if (windowSizeInSeconds <= 0) {
            throw new IllegalArgumentException("窗口大小必须大于0");
        }
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInSeconds * 1000;
        this.windowStart = new AtomicLong(System.currentTimeMillis());
        this.requestCount = new AtomicLong(0);
    }

    @Override
    public boolean tryAcquire() {
        long now = System.currentTimeMillis();
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
        return requestCount.compareAndSet(currentCount, currentCount + 1);
    }

    @Override
    public long getAvailablePermits() {
        long now = System.currentTimeMillis();
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
        windowStart.set(System.currentTimeMillis());
        requestCount.set(0);
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    public long getMaxRequests() {
        return maxRequests;
    }
}
