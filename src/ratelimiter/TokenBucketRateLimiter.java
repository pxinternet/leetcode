package ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 令牌桶限流器（Token Bucket Rate Limiter）
 * 
 * 算法原理：
 * - 维护一个令牌桶，以固定速率向桶中添加令牌
 * - 请求到达时，从桶中取走一个令牌
 * - 如果桶中没有令牌，请求被限流
 * - 桶有最大容量，防止令牌无限累积
 * 
 * 优点：
 * - 允许突发流量（桶中有令牌时）
 * - 流量平滑，适合需要应对突发流量的场景
 * - 实现相对简单
 * 
 * 缺点：
 * - 需要定期补充令牌（可以通过延迟计算优化）
 * 
 * 适用场景：需要允许突发流量的场景，如API限流
 */
public class TokenBucketRateLimiter implements RateLimiter {
    private final long capacity;              // 桶容量（最大令牌数）
    private final long refillRate;            // 每秒补充的令牌数
    private final AtomicLong tokens;          // 当前令牌数
    private final AtomicLong lastRefillTime; // 上次补充令牌的时间（纳秒）

    /**
     * 构造函数
     * 
     * @param capacity 桶容量（最大令牌数）
     * @param refillRatePerSecond 每秒补充的令牌数
     */
    public TokenBucketRateLimiter(long capacity, long refillRatePerSecond) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("桶容量必须大于0");
        }
        if (refillRatePerSecond <= 0) {
            throw new IllegalArgumentException("补充速率必须大于0");
        }
        this.capacity = capacity;
        this.refillRate = refillRatePerSecond;
        this.tokens = new AtomicLong(capacity); // 初始时桶是满的
        this.lastRefillTime = new AtomicLong(System.nanoTime());
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

        refillTokens();

        // 尝试获取令牌（CAS操作，保证线程安全）
        while (true) {
            long currentTokens = tokens.get();
            if (currentTokens < permits) {
                return false; // 令牌不足
            }
            if (tokens.compareAndSet(currentTokens, currentTokens - permits)) {
                return true; // 成功获取令牌
            }
            // CAS失败，重试
        }
    }

    /**
     * 补充令牌（延迟计算，按需补充）
     */
    private void refillTokens() {
        long now = System.nanoTime();
        long lastRefill = lastRefillTime.get();
        long elapsedNanos = now - lastRefill;

        if (elapsedNanos <= 0) {
            return;
        }

        // 计算应该补充的令牌数
        // refillRate 是每秒的速率，需要转换为纳秒速率
        long tokensToAdd = (elapsedNanos * refillRate) / 1_000_000_000L;

        if (tokensToAdd > 0) {
            // 尝试更新时间戳（CAS操作）
            if (lastRefillTime.compareAndSet(lastRefill, now)) {
                // 更新令牌数，但不能超过容量
                tokens.updateAndGet(currentTokens -> {
                    long newTokens = currentTokens + tokensToAdd;
                    return Math.min(newTokens, capacity);
                });
            }
        }
    }

    @Override
    public long getAvailablePermits() {
        refillTokens();
        return tokens.get();
    }

    @Override
    public void reset() {
        tokens.set(capacity);
        lastRefillTime.set(System.nanoTime());
    }

    public long getCapacity() {
        return capacity;
    }

    public long getRefillRate() {
        return refillRate;
    }
}
