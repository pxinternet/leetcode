package ratelimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流器演示类
 * 展示各种限流算法的使用和特点
 */
public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== 限流器算法演示 ==========\n");

        // 演示1：固定窗口限流
        System.out.println("【演示1】固定窗口限流（Fixed Window）");
        demonstrateFixedWindow();

        // 演示2：滑动窗口限流
        System.out.println("\n【演示2】滑动窗口限流（Sliding Window）");
        demonstrateSlidingWindow();

        // 演示3：令牌桶限流
        System.out.println("\n【演示3】令牌桶限流（Token Bucket）");
        demonstrateTokenBucket();

        // 演示4：漏桶限流
        System.out.println("\n【演示4】漏桶限流（Leaky Bucket）");
        demonstrateLeakyBucket();

        // 演示5：滑动日志限流
        System.out.println("\n【演示5】滑动日志限流（Sliding Log）");
        demonstrateSlidingLog();

        // 演示6：并发测试
        System.out.println("\n【演示6】并发场景测试");
        demonstrateConcurrency();
    }

    /**
     * 演示固定窗口限流
     */
    private static void demonstrateFixedWindow() {
        // 每秒允许5个请求
        RateLimiter limiter = new FixedWindowRateLimiter(5, 1);

        System.out.println("限制：每秒5个请求");
        System.out.println("测试：连续发送10个请求");
        
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.printf("请求 %d: %s (可用许可: %d)\n", 
                i, allowed ? "✓ 允许" : "✗ 拒绝", limiter.getAvailablePermits());
            
            if (i == 5) {
                System.out.println("--- 等待1秒后继续 ---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 演示滑动窗口限流
     */
    private static void demonstrateSlidingWindow() {
        // 每秒允许5个请求
        RateLimiter limiter = new SlidingWindowRateLimiter(5, 1);

        System.out.println("限制：每秒5个请求（滑动窗口）");
        System.out.println("测试：连续发送10个请求");
        
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.printf("请求 %d: %s (可用许可: %d)\n", 
                i, allowed ? "✓ 允许" : "✗ 拒绝", limiter.getAvailablePermits());
            
            if (i == 5) {
                System.out.println("--- 等待0.5秒后继续 ---");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 演示令牌桶限流（展示突发流量处理）
     */
    private static void demonstrateTokenBucket() {
        // 桶容量10，每秒补充5个令牌
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(10, 5);

        System.out.println("限制：桶容量10，每秒补充5个令牌");
        System.out.println("初始状态：桶是满的（10个令牌）");
        System.out.println("测试：连续发送15个请求");
        
        for (int i = 1; i <= 15; i++) {
            boolean allowed = limiter.tryAcquire();
            long available = limiter.getAvailablePermits();
            System.out.printf("请求 %d: %s (剩余令牌: %d)\n", 
                i, allowed ? "✓ 允许" : "✗ 拒绝", available);
            
            if (i == 10) {
                System.out.println("--- 等待1秒让令牌补充 ---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 演示漏桶限流（展示平滑输出）
     */
    private static void demonstrateLeakyBucket() {
        // 桶容量10，每秒流出5个请求
        LeakyBucketRateLimiter limiter = new LeakyBucketRateLimiter(10, 5);

        System.out.println("限制：桶容量10，每秒流出5个请求");
        System.out.println("测试：快速发送10个请求，然后观察流出");
        
        // 快速发送10个请求
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.printf("发送请求 %d: %s (桶中水位: %d)\n", 
                i, allowed ? "✓ 接受" : "✗ 拒绝", 
                10 - limiter.getAvailablePermits());
        }
        
        System.out.println("\n观察流出过程（每秒检查一次）：");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long available = limiter.getAvailablePermits();
            System.out.printf("第%d秒后: 可用许可 %d (桶中水位: %d)\n", 
                i + 1, available, 10 - available);
        }
    }

    /**
     * 演示滑动日志限流
     */
    private static void demonstrateSlidingLog() {
        // 每秒允许5个请求
        RateLimiter limiter = new SlidingLogRateLimiter(5, 1);

        System.out.println("限制：每秒5个请求（滑动日志）");
        System.out.println("测试：连续发送10个请求");
        
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.printf("请求 %d: %s (可用许可: %d)\n", 
                i, allowed ? "✓ 允许" : "✗ 拒绝", limiter.getAvailablePermits());
            
            if (i == 5) {
                System.out.println("--- 等待0.6秒后继续 ---");
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 演示并发场景
     */
    private static void demonstrateConcurrency() throws InterruptedException {
        RateLimiter limiter = new TokenBucketRateLimiter(20, 10);
        int threadCount = 5;
        int requestsPerThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        System.out.println("并发测试：5个线程，每个线程发送10个请求");
        System.out.println("限流器：令牌桶（容量20，每秒补充10个）");

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < requestsPerThread; j++) {
                        if (limiter.tryAcquire()) {
                            successCount.incrementAndGet();
                        } else {
                            failCount.incrementAndGet();
                        }
                        Thread.sleep(50); // 模拟请求间隔
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        System.out.printf("\n结果统计：\n");
        System.out.printf("  成功: %d 个请求\n", successCount.get());
        System.out.printf("  失败: %d 个请求\n", failCount.get());
        System.out.printf("  总计: %d 个请求\n", successCount.get() + failCount.get());
    }
}
