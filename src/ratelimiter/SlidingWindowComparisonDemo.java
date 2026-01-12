package ratelimiter;

/**
 * Sliding Window vs Sliding Log 对比演示
 * 展示两种算法的区别和适用场景
 */
public class SlidingWindowComparisonDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== Sliding Window vs Sliding Log 对比演示 ==========\n");

        // 演示1：内存占用对比
        System.out.println("【演示1】内存占用对比\n");
        demonstrateMemoryUsage();

        // 演示2：精度对比
        System.out.println("\n【演示2】精度对比\n");
        demonstratePrecision();

        // 演示3：性能对比
        System.out.println("\n【演示3】性能对比\n");
        demonstratePerformance();

        // 演示4：实际场景对比
        System.out.println("\n【演示4】实际场景对比\n");
        demonstrateRealWorldScenarios();
    }

    /**
     * 演示内存占用对比
     */
    private static void demonstrateMemoryUsage() {
        System.out.println("配置：限制每秒10个请求，时间窗口10秒");
        System.out.println("Sliding Window：10个子窗口（每个1秒）");
        System.out.println("Sliding Log：存储所有时间戳\n");

        SlidingWindowBucketRateLimiter slidingWindow = 
            new SlidingWindowBucketRateLimiter(10, 10, 10);
        SlidingLogRateLimiter slidingLog = new SlidingLogRateLimiter(10, 10);

        // 发送100个请求
        System.out.println("发送100个请求后：");
        for (int i = 0; i < 100; i++) {
            slidingWindow.tryAcquire();
            slidingLog.tryAcquire();
            try {
                Thread.sleep(10); // 模拟请求间隔
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("  Sliding Window 内存占用：");
        System.out.println("    - 子窗口数组：10个long = 80字节（固定）");
        System.out.println("    - 其他变量：约50字节");
        System.out.println("    - 总计：约130字节（固定，不随请求数增长）\n");

        System.out.println("  Sliding Log 内存占用：");
        System.out.println("    - 时间戳队列：最多100个long = 800字节（随请求数增长）");
        System.out.println("    - 其他变量：约50字节");
        System.out.println("    - 总计：约850字节（随请求数增长）\n");

        System.out.println("结论：");
        System.out.println("  ✓ Sliding Window 内存占用固定，适合长时间运行");
        System.out.println("  ✓ Sliding Log 内存占用随请求数增长，需要定期清理");
    }

    /**
     * 演示精度对比
     */
    private static void demonstratePrecision() {
        System.out.println("配置：限制每秒5个请求");
        System.out.println("操作：在窗口边界附近发送请求\n");

        SlidingWindowBucketRateLimiter slidingWindow = 
            new SlidingWindowBucketRateLimiter(5, 1, 5); // 5个子窗口，每个0.2秒
        SlidingLogRateLimiter slidingLog = new SlidingLogRateLimiter(5, 1);

        System.out.println("Sliding Window（5个子窗口）：");
        System.out.println("  - 精度：0.2秒级别");
        System.out.println("  - 子窗口边界可能有误差");
        System.out.println("  - 示例：请求在0.15秒和0.25秒，可能被分到不同子窗口\n");

        System.out.println("Sliding Log（精确时间戳）：");
        System.out.println("  - 精度：毫秒级别");
        System.out.println("  - 可以精确统计任意时间窗口");
        System.out.println("  - 示例：请求在100ms和200ms，精确记录\n");

        System.out.println("结论：");
        System.out.println("  ✓ Sliding Log 精度更高（精确到毫秒）");
        System.out.println("  ✓ Sliding Window 精度取决于子窗口数量");
    }

    /**
     * 演示性能对比
     */
    private static void demonstratePerformance() {
        System.out.println("配置：限制每秒100个请求，时间窗口10秒");
        System.out.println("操作：发送1000个请求，测量耗时\n");

        SlidingWindowBucketRateLimiter slidingWindow = 
            new SlidingWindowBucketRateLimiter(100, 10, 10);
        SlidingLogRateLimiter slidingLog = new SlidingLogRateLimiter(100, 10);

        // 测试 Sliding Window
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            slidingWindow.tryAcquire();
        }
        long windowTime = System.nanoTime() - start;

        // 测试 Sliding Log
        slidingLog.reset();
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            slidingLog.tryAcquire();
        }
        long logTime = System.nanoTime() - start;

        System.out.println("性能对比：");
        System.out.printf("  Sliding Window: %.2f 微秒/请求\n", windowTime / 1000.0);
        System.out.printf("  Sliding Log:   %.2f 微秒/请求\n", logTime / 1000.0);
        System.out.println("\n结论：");
        System.out.println("  ✓ Sliding Window 性能更好（O(1)操作）");
        System.out.println("  ✓ Sliding Log 需要清理过期时间戳（O(n)操作）");
    }

    /**
     * 演示实际场景对比
     */
    private static void demonstrateRealWorldScenarios() {
        System.out.println("【场景1】高并发API限流");
        System.out.println("需求：每秒限制1000个请求，需要高性能\n");
        System.out.println("推荐：Sliding Window");
        System.out.println("原因：");
        System.out.println("  • 内存占用固定（10个子窗口 = 80字节）");
        System.out.println("  • 更新操作O(1)，性能好");
        System.out.println("  • 适合高并发场景\n");

        System.out.println("【场景2】精确限流（低并发）");
        System.out.println("需求：每秒限制10个请求，需要精确到毫秒\n");
        System.out.println("推荐：Sliding Log");
        System.out.println("原因：");
        System.out.println("  • 精度最高（精确到毫秒）");
        System.out.println("  • 可以精确统计任意时间窗口");
        System.out.println("  • 低并发时内存占用可控\n");

        System.out.println("【场景3】长时间运行服务");
        System.out.println("需求：服务需要24小时运行，内存不能无限增长\n");
        System.out.println("推荐：Sliding Window");
        System.out.println("原因：");
        System.out.println("  • 内存占用固定，不会随请求数增长");
        System.out.println("  • 适合长时间运行的服务\n");

        System.out.println("========== 总结 ==========");
        System.out.println("Sliding Window（分桶）：");
        System.out.println("  ✓ 内存占用固定");
        System.out.println("  ✓ 性能好（O(1)）");
        System.out.println("  ✓ 适合高并发、长时间运行");
        System.out.println("  ✗ 精度取决于子窗口数量\n");

        System.out.println("Sliding Log（时间戳）：");
        System.out.println("  ✓ 精度最高（毫秒级）");
        System.out.println("  ✓ 可以精确统计任意时间窗口");
        System.out.println("  ✗ 内存占用随请求数增长");
        System.out.println("  ✗ 性能相对较低（O(n)清理）");
    }
}
