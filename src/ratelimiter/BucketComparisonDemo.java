package ratelimiter;

/**
 * 令牌桶 vs 漏桶 对比演示
 * 直观展示两种算法的区别和适用场景
 */
public class BucketComparisonDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== 令牌桶 vs 漏桶 对比演示 ==========\n");

        // 场景1：突发流量处理
        System.out.println("【场景1】突发流量处理对比\n");
        demonstrateBurstTraffic();

        // 场景2：平滑流量处理
        System.out.println("\n【场景2】平滑流量处理对比\n");
        demonstrateSmoothTraffic();

        // 场景3：实际应用场景模拟
        System.out.println("\n【场景3】实际应用场景模拟\n");
        demonstrateRealWorldScenarios();
    }

    /**
     * 演示突发流量处理
     */
    private static void demonstrateBurstTraffic() {
        System.out.println("配置：");
        System.out.println("  令牌桶：容量10，每秒补充5个令牌");
        System.out.println("  漏桶：容量10，每秒流出5个请求");
        System.out.println("\n操作：快速连续发送10个请求\n");

        TokenBucketRateLimiter tokenBucket = new TokenBucketRateLimiter(10, 5);
        LeakyBucketRateLimiter leakyBucket = new LeakyBucketRateLimiter(10, 5);

        System.out.println("令牌桶（允许突发）：");
        for (int i = 1; i <= 10; i++) {
            boolean allowed = tokenBucket.tryAcquire();
            long available = tokenBucket.getAvailablePermits();
            System.out.printf("  请求 %d: %s (剩余令牌: %d)\n", 
                i, allowed ? "✓ 允许" : "✗ 拒绝", available);
        }

        System.out.println("\n漏桶（恒定输出）：");
        for (int i = 1; i <= 10; i++) {
            boolean allowed = leakyBucket.tryAcquire();
            long available = leakyBucket.getAvailablePermits();
            System.out.printf("  请求 %d: %s (可用容量: %d)\n", 
                i, allowed ? "✓ 接受" : "✗ 拒绝", available);
        }

        System.out.println("\n关键区别：");
        System.out.println("  ✓ 令牌桶：初始桶满，可以一次性消耗所有令牌（突发）");
        System.out.println("  ✓ 漏桶：请求进入桶中，以固定速率流出（平滑）");
    }

    /**
     * 演示平滑流量处理
     */
    private static void demonstrateSmoothTraffic() throws InterruptedException {
        System.out.println("配置：");
        System.out.println("  令牌桶：容量10，每秒补充5个令牌");
        System.out.println("  漏桶：容量10，每秒流出5个请求");
        System.out.println("\n操作：每秒发送2个请求，持续5秒\n");

        TokenBucketRateLimiter tokenBucket = new TokenBucketRateLimiter(10, 5);
        LeakyBucketRateLimiter leakyBucket = new LeakyBucketRateLimiter(10, 5);

        System.out.println("令牌桶：");
        for (int second = 1; second <= 5; second++) {
            for (int req = 1; req <= 2; req++) {
                boolean allowed = tokenBucket.tryAcquire();
                System.out.printf("  第%d秒-请求%d: %s (剩余令牌: %d)\n", 
                    second, req, allowed ? "✓" : "✗", tokenBucket.getAvailablePermits());
            }
            Thread.sleep(1000);
        }

        System.out.println("\n漏桶：");
        leakyBucket.reset();
        for (int second = 1; second <= 5; second++) {
            for (int req = 1; req <= 2; req++) {
                boolean allowed = leakyBucket.tryAcquire();
                System.out.printf("  第%d秒-请求%d: %s (可用容量: %d)\n", 
                    second, req, allowed ? "✓" : "✗", leakyBucket.getAvailablePermits());
            }
            Thread.sleep(1000);
        }

        System.out.println("\n关键区别：");
        System.out.println("  ✓ 两种算法在平滑流量下表现相似");
        System.out.println("  ✓ 区别主要体现在突发流量的处理上");
    }

    /**
     * 演示实际应用场景
     */
    private static void demonstrateRealWorldScenarios() throws InterruptedException {
        // 场景1：API限流（令牌桶）
        System.out.println("【应用场景1】API限流 - 使用令牌桶");
        System.out.println("需求：允许用户偶尔突发请求，但长期平均速率受限\n");
        
        TokenBucketRateLimiter apiLimiter = new TokenBucketRateLimiter(100, 50);
        System.out.println("用户平时请求很少，突然需要批量查询：");
        int success = 0;
        for (int i = 0; i < 80; i++) {
            if (apiLimiter.tryAcquire()) {
                success++;
            }
        }
        System.out.printf("  快速发送80个请求：成功 %d 个（利用桶中积累的令牌）\n", success);
        System.out.println("  ✓ 用户体验好，允许突发操作\n");

        // 场景2：消息队列消费者（漏桶）
        System.out.println("【应用场景2】消息队列消费者 - 使用漏桶");
        System.out.println("需求：保护下游处理服务，无论消息多快都以固定速率处理\n");
        
        LeakyBucketRateLimiter consumerLimiter = new LeakyBucketRateLimiter(100, 10);
        System.out.println("消息队列突然涌入大量消息：");
        int accepted = 0;
        for (int i = 0; i < 50; i++) {
            if (consumerLimiter.tryAcquire()) {
                accepted++;
            }
        }
        System.out.printf("  快速接收50个消息：接受 %d 个进入桶中\n", accepted);
        System.out.println("  处理速率：固定为每秒10个（保护下游服务）");
        System.out.println("  ✓ 下游服务不会被压垮\n");

        // 场景3：对比说明
        System.out.println("【总结】");
        System.out.println("令牌桶适合：");
        System.out.println("  • 对外API限流（允许用户突发）");
        System.out.println("  • 用户行为限流（允许偶尔快速操作）");
        System.out.println("  • 网络流量控制（允许突发传输）");
        System.out.println();
        System.out.println("漏桶适合：");
        System.out.println("  • 下游服务保护（恒定输出速率）");
        System.out.println("  • 数据库限流（恒定查询速率）");
        System.out.println("  • 日志写入（恒定写入速率）");
        System.out.println("  • 第三方API调用（严格遵守速率限制）");
    }
}
