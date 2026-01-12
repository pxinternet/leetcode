package ratelimiter;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 真正的滑动窗口限流器（使用分桶实现）
 * 
 * 算法原理：
 * - 将时间窗口分成多个子窗口（buckets）
 * - 每个子窗口存储该时间段内的请求计数
 * - 滑动窗口通过移动子窗口位置实现
 * - 当前窗口计数 = 所有子窗口计数之和
 * 
 * 优点：
 * - 内存占用固定（只存储子窗口数量）
 * - 更新操作O(1)
 * - 适合高并发场景
 * 
 * 缺点：
 * - 精度取决于子窗口数量
 * - 子窗口边界可能有误差
 * 
 * 适用场景：高并发场景，需要固定内存占用
 */
public class SlidingWindowBucketRateLimiter implements RateLimiter {
    private final long windowSizeInMillis;      // 窗口大小（毫秒）
    private final int bucketCount;                // 子窗口数量
    private final long bucketSizeInMillis;       // 每个子窗口大小（毫秒）
    private final long maxRequests;              // 窗口内允许的最大请求数
    private final AtomicLongArray buckets;       // 子窗口计数数组
    private volatile int currentBucketIndex;     // 当前子窗口索引
    private volatile long lastUpdateTime;        // 上次更新时间

    /**
     * 构造函数
     * 
     * @param maxRequests 窗口内允许的最大请求数
     * @param windowSizeInSeconds 窗口大小（秒）
     * @param bucketCount 子窗口数量（越多精度越高，但内存占用也越大）
     */
    public SlidingWindowBucketRateLimiter(long maxRequests, long windowSizeInSeconds, int bucketCount) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("最大请求数必须大于0");
        }
        if (windowSizeInSeconds <= 0) {
            throw new IllegalArgumentException("窗口大小必须大于0");
        }
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("子窗口数量必须大于0");
        }
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInSeconds * 1000;
        this.bucketCount = bucketCount;
        this.bucketSizeInMillis = windowSizeInMillis / bucketCount;
        this.buckets = new AtomicLongArray(bucketCount);
        this.currentBucketIndex = 0;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * 默认构造函数（使用10个子窗口）
     */
    public SlidingWindowBucketRateLimiter(long maxRequests, long windowSizeInSeconds) {
        this(maxRequests, windowSizeInSeconds, 10);
    }

    @Override
    public synchronized boolean tryAcquire() {
        updateBuckets();

        // 计算当前窗口内的总请求数
        long currentCount = getCurrentWindowCount();

        // 检查是否超限
        if (currentCount >= maxRequests) {
            return false;
        }

        // 增加当前子窗口的计数
        buckets.incrementAndGet(currentBucketIndex);
        return true;
    }

    /**
     * 更新子窗口（滑动窗口）
     */
    private void updateBuckets() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastUpdateTime;

        if (elapsed <= 0) {
            return;
        }

        // 计算需要移动的子窗口数量
        int bucketsToMove = (int) (elapsed / bucketSizeInMillis);

        if (bucketsToMove > 0) {
            // 移动子窗口：清空过期的子窗口，移动当前索引
            if (bucketsToMove >= bucketCount) {
                // 如果移动距离超过窗口大小，清空所有子窗口
                for (int i = 0; i < bucketCount; i++) {
                    buckets.set(i, 0);
                }
                currentBucketIndex = 0;
            } else {
                // 清空过期的子窗口
                for (int i = 0; i < bucketsToMove; i++) {
                    int indexToClear = (currentBucketIndex + 1 + i) % bucketCount;
                    buckets.set(indexToClear, 0);
                }
                // 更新当前子窗口索引
                currentBucketIndex = (currentBucketIndex + bucketsToMove) % bucketCount;
            }
            lastUpdateTime = now;
        }
    }

    /**
     * 获取当前窗口内的总请求数
     */
    private long getCurrentWindowCount() {
        long count = 0;
        for (int i = 0; i < bucketCount; i++) {
            count += buckets.get(i);
        }
        return count;
    }

    @Override
    public long getAvailablePermits() {
        updateBuckets();
        long currentCount = getCurrentWindowCount();
        return Math.max(0, maxRequests - currentCount);
    }

    @Override
    public synchronized void reset() {
        for (int i = 0; i < bucketCount; i++) {
            buckets.set(i, 0);
        }
        currentBucketIndex = 0;
        lastUpdateTime = System.currentTimeMillis();
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    public long getMaxRequests() {
        return maxRequests;
    }

    public int getBucketCount() {
        return bucketCount;
    }
}
