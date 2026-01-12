package ratelimiter;

import java.util.ArrayList;
import java.util.List;

/**
 * 限流算法对比分析
 * 展示不同算法的特点和适用场景
 */
public class RateLimiterComparison {

    public static void main(String[] args) {
        System.out.println("========== 限流算法对比分析 ==========\n");

        // 创建相同配置的不同限流器
        List<RateLimiterInfo> limiters = new ArrayList<>();
        limiters.add(new RateLimiterInfo("固定窗口", new FixedWindowRateLimiter(10, 1)));
        limiters.add(new RateLimiterInfo("滑动窗口", new SlidingWindowRateLimiter(10, 1)));
        limiters.add(new RateLimiterInfo("令牌桶", new TokenBucketRateLimiter(10, 10)));
        limiters.add(new RateLimiterInfo("漏桶", new LeakyBucketRateLimiter(10, 10)));
        limiters.add(new RateLimiterInfo("滑动日志", new SlidingLogRateLimiter(10, 1)));

        // 测试场景1：突发流量
        System.out.println("【场景1】突发流量测试（连续发送20个请求）");
        testBurstTraffic(limiters);

        // 测试场景2：平滑流量
        System.out.println("\n【场景2】平滑流量测试（每秒2个请求，持续5秒）");
        testSmoothTraffic(limiters);

        // 打印算法特点对比
        printComparison();
    }

    /**
     * 测试突发流量
     */
    private static void testBurstTraffic(List<RateLimiterInfo> limiters) {
        System.out.println("配置：每秒允许10个请求");
        System.out.println("操作：连续快速发送20个请求\n");

        for (RateLimiterInfo info : limiters) {
            info.limiter.reset();
            int success = 0;
            int fail = 0;

            for (int i = 0; i < 20; i++) {
                if (info.limiter.tryAcquire()) {
                    success++;
                } else {
                    fail++;
                }
            }

            System.out.printf("%-10s: 成功=%2d, 失败=%2d, 成功率=%.1f%%\n",
                info.name, success, fail, (success * 100.0 / 20));
        }
    }

    /**
     * 测试平滑流量
     */
    private static void testSmoothTraffic(List<RateLimiterInfo> limiters) {
        System.out.println("配置：每秒允许10个请求");
        System.out.println("操作：每秒发送2个请求，持续5秒（共10个请求）\n");

        for (RateLimiterInfo info : limiters) {
            info.limiter.reset();
            int success = 0;
            int fail = 0;

            for (int second = 0; second < 5; second++) {
                for (int req = 0; req < 2; req++) {
                    if (info.limiter.tryAcquire()) {
                        success++;
                    } else {
                        fail++;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.printf("%-10s: 成功=%2d, 失败=%2d, 成功率=%.1f%%\n",
                info.name, success, fail, (success * 100.0 / 10));
        }
    }

    /**
     * 打印算法特点对比表
     */
    private static void printComparison() {
        System.out.println("\n========== 算法特点对比 ==========\n");
        System.out.println("算法名称     | 内存占用 | 时间复杂度 | 突发流量 | 流量平滑度 | 适用场景");
        System.out.println("------------|---------|-----------|---------|-----------|------------------");
        System.out.println("固定窗口     | 低      | O(1)      | 允许    | 低        | 简单限流场景");
        System.out.println("滑动窗口     | 中      | O(n)      | 部分允许| 中        | 需要平滑限流");
        System.out.println("令牌桶       | 低      | O(1)      | 允许    | 高        | API限流（推荐）");
        System.out.println("漏桶         | 低      | O(1)      | 不允许  | 最高      | 严格控制输出速率");
        System.out.println("滑动日志     | 高      | O(n)      | 部分允许| 高        | 精确限流");
        
        System.out.println("\n========== 选择建议 ==========\n");
        System.out.println("1. 固定窗口：适合对精度要求不高的简单场景");
        System.out.println("2. 滑动窗口：适合需要平滑限流但不需要突发的场景");
        System.out.println("3. 令牌桶：  适合API限流，允许突发流量（最常用）");
        System.out.println("4. 漏桶：    适合需要严格控制输出速率的场景");
        System.out.println("5. 滑动日志：适合需要精确限流的场景（内存充足时）");
    }

    static class RateLimiterInfo {
        String name;
        RateLimiter limiter;

        RateLimiterInfo(String name, RateLimiter limiter) {
            this.name = name;
            this.limiter = limiter;
        }
    }
}
