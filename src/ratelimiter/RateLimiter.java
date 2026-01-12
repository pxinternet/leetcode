package ratelimiter;

/**
 * 限流器统一接口
 * 
 * 所有限流算法都应该实现此接口
 */
public interface RateLimiter {
    
    /**
     * 尝试获取许可（非阻塞）
     * 
     * @return true 如果允许请求，false 如果请求被限流
     */
    boolean tryAcquire();
    
    /**
     * 尝试获取指定数量的许可（非阻塞）
     * 
     * @param permits 请求的许可数量
     * @return true 如果允许请求，false 如果请求被限流
     */
    default boolean tryAcquire(int permits) {
        if (permits <= 0) {
            throw new IllegalArgumentException("许可数量必须大于0");
        }
        for (int i = 0; i < permits; i++) {
            if (!tryAcquire()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 获取当前可用的许可数量（如果支持）
     * 
     * @return 可用许可数量，如果不支持则返回-1
     */
    default long getAvailablePermits() {
        return -1; // 默认不支持
    }
    
    /**
     * 重置限流器状态（如果支持）
     */
    default void reset() {
        throw new UnsupportedOperationException("该限流器不支持重置操作");
    }
}
