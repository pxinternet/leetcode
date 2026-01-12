package ratelimiter;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 滑动日志限流器（Sliding Log Rate Limiter）
 * 
 * 算法原理：
 * - 记录每个请求的精确时间戳
 * - 每次请求时，统计时间窗口内的请求数
 * - 如果请求数未超限，允许请求并记录时间戳
 * 
 * 优点：
 * - 精确控制，不会出现窗口边界问题
 * - 可以精确统计任意时间窗口内的请求数
 * 
 * 缺点：
 * - 需要存储所有时间戳，内存占用最大
 * - 性能相对较低（需要遍历和清理）
 * 
 * 适用场景：需要精确限流的场景，对内存占用不敏感的场景
 */
public class SlidingLogRateLimiter implements RateLimiter {
    private final long windowSizeInMillis;  // 窗口大小（毫秒）
    private final long maxRequests;         // 窗口内允许的最大请求数
    private final ConcurrentLinkedQueue<Long> requestLogs; // 请求时间戳队列

    /**
     * 构造函数
     * 
     * @param maxRequests 窗口内允许的最大请求数
     * @param windowSizeInSeconds 窗口大小（秒）
     */
    public SlidingLogRateLimiter(long maxRequests, long windowSizeInSeconds) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("最大请求数必须大于0");
        }
        if (windowSizeInSeconds <= 0) {
            throw new IllegalArgumentException("窗口大小必须大于0");
        }
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInSeconds * 1000;
        this.requestLogs = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        long windowStart = now - windowSizeInMillis;
        
        // 清理窗口外的旧日志
        while (!requestLogs.isEmpty() && requestLogs.peek() < windowStart) {
            requestLogs.poll();
        }
        
        // 检查窗口内请求数是否超限
        if (requestLogs.size() >= maxRequests) {
            return false;
        }
        
        // 记录当前请求时间戳
        requestLogs.offer(now);
        return true;
    }

    @Override
    public long getAvailablePermits() {
        long now = System.currentTimeMillis();
        long windowStart = now - windowSizeInMillis;
        
        // 清理窗口外的旧日志
        while (!requestLogs.isEmpty() && requestLogs.peek() < windowStart) {
            requestLogs.poll();
        }
        
        return Math.max(0, maxRequests - requestLogs.size());
    }

    @Override
    public synchronized void reset() {
        requestLogs.clear();
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    public long getMaxRequests() {
        return maxRequests;
    }
}
