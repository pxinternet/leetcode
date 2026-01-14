package queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadSafeBlockingQueueDemo - 演示线程安全阻塞队列的使用
 *
 * 本演示类展示了多线程环境下使用 ThreadSafeBlockingQueue 的场景：
 * - 多个生产者线程并发地向队列中添加元素
 * - 多个消费者线程并发地从队列中取出元素
 * - 验证线程安全性和阻塞行为
 */
public class ThreadSafeBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个容量为 5 的阻塞队列
        ThreadSafeBlockingQueue<Integer> queue = new ThreadSafeBlockingQueue<>(5);

        // 使用线程池管理生产者和消费者线程
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 用于同步的计数器
        CountDownLatch latch = new CountDownLatch(2);

        // 创建 3 个生产者线程，每个生产 10 个元素
        for (int i = 0; i < 3; i++) {
            final int producerId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        int value = producerId * 10 + j;
                        queue.put(value);
                        System.out.println("生产者 " + producerId + " 生产: " + value + 
                                         " (队列大小: " + queue.size() + ")");
                        Thread.sleep(100); // 模拟生产耗时
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 创建 2 个消费者线程，每个消费 15 个元素
        for (int i = 0; i < 2; i++) {
            final int consumerId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 15; j++) {
                        Integer value = queue.take();
                        System.out.println("消费者 " + consumerId + " 消费: " + value + 
                                         " (队列大小: " + queue.size() + ")");
                        Thread.sleep(150); // 模拟消费耗时
                    }
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 等待所有消费者完成
        latch.await(10, TimeUnit.SECONDS);

        // 演示非阻塞方法
        System.out.println("\n=== 演示非阻塞方法 ===");
        System.out.println("队列是否为空: " + queue.isEmpty());
        System.out.println("队列是否已满: " + queue.isFull());
        System.out.println("队列当前大小: " + queue.size());
        System.out.println("队列容量: " + queue.capacity());

        // 尝试非阻塞添加（如果队列满会返回 false）
        for (int i = 0; i < 10; i++) {
            boolean success = queue.offer(i);
            System.out.println("offer(" + i + ") = " + success + 
                             " (队列大小: " + queue.size() + ")");
        }

        // 尝试非阻塞取出（如果队列空会返回 null）
        for (int i = 0; i < 10; i++) {
            Integer value = queue.poll();
            System.out.println("poll() = " + value + 
                             " (队列大小: " + queue.size() + ")");
        }

        // 关闭线程池
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("\n演示完成！");
    }
}
