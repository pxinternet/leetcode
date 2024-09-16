package round3;

import java.lang.foreign.SymbolLookup;
import java.util.concurrent.atomic.AtomicLong;

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
