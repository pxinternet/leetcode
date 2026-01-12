package ratelimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 滑动窗口限流器（Sliding Window Rate Limiter）
 * 
 * 算法原理：
 * - 维护一个时间窗口内的请求时间戳队列
 * - 每次请求时，清理窗口外的旧请求
 * - 如果窗口内请求数未超限，允许请求并记录时间戳
 * 
 * 优点：
 * - 相比固定窗口，流量更平滑
 * - 避免了窗口边界突增问题
 * 
 * 缺点：
 * - 需要存储时间戳，内存占用较大
 * - 清理操作可能影响性能
 * 
 * 适用场景：需要平滑限流的场景
 */
public class SlidingWindowRateLimiter implements RateLimiter {
    private final long windowSizeInMillis;  // 窗口大小（毫秒）
    private final long maxRequests;         // 窗口内允许的最大请求数
    private final ConcurrentLinkedQueue<Long> requestTimestamps; // 请求时间戳队列

    /**
     * 构造函数
     * 
     * @param maxRequests 窗口内允许的最大请求数
     * @param windowSizeInSeconds 窗口大小（秒）
     */
    public SlidingWindowRateLimiter(long maxRequests, long windowSizeInSeconds) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("最大请求数必须大于0");
        }
        if (windowSizeInSeconds <= 0) {
            throw new IllegalArgumentException("窗口大小必须大于0");
        }
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInSeconds * 1000;
        this.requestTimestamps = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        long windowStart = now - windowSizeInMillis;
        
        // 清理窗口外的旧请求
        while (!requestTimestamps.isEmpty() && requestTimestamps.peek() < windowStart) {
            requestTimestamps.poll();
        }
        
        // 检查窗口内请求数是否超限
        if (requestTimestamps.size() >= maxRequests) {
            return false;
        }
        
        // 记录当前请求时间戳
        requestTimestamps.offer(now);
        return true;
    }

    @Override
    public long getAvailablePermits() {
        long now = System.currentTimeMillis();
        long windowStart = now - windowSizeInMillis;
        
        // 清理窗口外的旧请求
        while (!requestTimestamps.isEmpty() && requestTimestamps.peek() < windowStart) {
            requestTimestamps.poll();
        }
        
        return Math.max(0, maxRequests - requestTimestamps.size());
    }

    @Override
    public synchronized void reset() {
        requestTimestamps.clear();
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    public long getMaxRequests() {
        return maxRequests;
    }
}
