package round3;

import java.util.concurrent.atomic.AtomicLong;

/**
 * RateLimiter - 令牌桶限流器
 *
 * 题目（概要）：maxToken 为桶容量，refillRate 为每秒补充令牌数；tryAcquire 消耗 1 个令牌，成功返回 true。
 * 令牌不足时按时间流逝补充，支持并发 CAS 获取。
 *
 * 算法原理：
 * - 令牌桶：桶内令牌数随时间增加，获取时扣减；桶满则不再增加。
 * - refill：根据 (currentTime-lastRefillTime)*refillRate/1e9 计算应补充的令牌，与当前令牌相加后 capped by maxToken。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：tryAcquire 先调用 refill 补充令牌。
 * - 步骤 2：CAS 循环尝试扣减 1 个令牌，失败重试最多 MAX_RETRY 次。
 * - 步骤 3：refill 中 timeElapsed*refillRate/1e9 为纳秒转秒后的补充量；CAS 更新 lastRefillTime 后设置新令牌数。
 *
 * 关键洞察：lastRefillTime 用于计算时间差；纳秒精度；CAS 保证并发安全。
 *
 * 时间复杂度：tryAcquire O(1) 或 O(MAX_RETRY)
 * 空间复杂度：O(1)
 */
public class RateLimiter {

    private final long maxToken;

    private final long refillRate;

    private final AtomicLong avaliableTokens;

    private final AtomicLong lastRefillTimeStamp;

    private static final int MAX_RETRY = 5;

    public RateLimiter(long maxToken, long refillRate) {
        this.maxToken = maxToken;
        this.refillRate = refillRate;
        this.avaliableTokens = new AtomicLong(maxToken);
        this.lastRefillTimeStamp = new AtomicLong(System.nanoTime());
    }

    public boolean tryAcquire() {
        refill();

        int retry = 0;
        while (retry < MAX_RETRY) {
            long currentToken = avaliableTokens.get();
            if (currentToken > 0) {
                if (avaliableTokens.compareAndSet(currentToken, currentToken - 1)) {
                    return true;
                } else {
                    retry++;
                }
            } else {
                retry++;
            }
        }

        return false;
    }

    public void refill() {

        long currentTime = System.nanoTime();

        long lastRefillTime = lastRefillTimeStamp.get();

        long timeElaspsed = currentTime - lastRefillTime;

        if (timeElaspsed > 0) {
            long tokenToAdd = timeElaspsed * refillRate / 1_000_000_000L;

            if (tokenToAdd > 0) {
                long newAvaliableTokens = Math.min(maxToken, tokenToAdd + avaliableTokens.get());
                if (lastRefillTimeStamp.compareAndSet(lastRefillTime, currentTime)) {
                    avaliableTokens.set(newAvaliableTokens);
                }
            }
        }
    }

}
