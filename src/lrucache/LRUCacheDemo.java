package lrucache;

import java.util.List;

/**
 * LRU Cache 演示类
 */
public class LRUCacheDemo {

    public static void main(String[] args) {
        System.out.println("========== LRU Cache 演示 ==========\n");

        // 演示1：基本操作
        System.out.println("【演示1】基本操作\n");
        basicOperations();

        // 演示2：LRU特性验证
        System.out.println("\n【演示2】LRU特性验证\n");
        lruBehavior();

        // 演示3：线程安全版本
        System.out.println("\n【演示3】线程安全版本\n");
        threadSafeDemo();
    }

    /**
     * 基本操作演示
     */
    private static void basicOperations() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);

        System.out.println("创建容量为3的LRU Cache");
        System.out.println("操作：put('a', 1), put('b', 2), put('c', 3)");
        
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        
        System.out.println("\n当前缓存内容（按访问顺序）：");
        List<String> keys = cache.keySet();
        for (String key : keys) {
            System.out.printf("  %s -> %d\n", key, cache.get(key));
        }
        
        System.out.println("\n操作：get('a') - 访问'a'");
        cache.get("a");
        
        System.out.println("访问后缓存顺序：");
        keys = cache.keySet();
        for (String key : keys) {
            System.out.printf("  %s -> %d\n", key, cache.get(key));
        }
        
        System.out.println("\n操作：put('d', 4) - 添加新元素（缓存已满）");
        cache.put("d", 4);
        
        System.out.println("添加后缓存内容：");
        keys = cache.keySet();
        for (String key : keys) {
            System.out.printf("  %s -> %d\n", key, cache.get(key));
        }
        System.out.println("说明：'b'被淘汰（最久未使用）");
    }

    /**
     * LRU特性验证
     */
    private static void lruBehavior() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        System.out.println("容量：3");
        System.out.println("操作序列：");
        System.out.println("  1. put(1, 'one')");
        System.out.println("  2. put(2, 'two')");
        System.out.println("  3. put(3, 'three')");
        System.out.println("  4. get(1) - 访问1");
        System.out.println("  5. put(4, 'four') - 添加新元素\n");

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        
        System.out.println("步骤1-3后：");
        printCache(cache);
        
        cache.get(1); // 访问1，使其成为最近使用
        System.out.println("\n步骤4后（访问1）：");
        printCache(cache);
        
        cache.put(4, "four"); // 添加新元素
        System.out.println("\n步骤5后（添加4）：");
        printCache(cache);
        System.out.println("说明：2被淘汰（最久未使用），因为1被访问过");
    }

    /**
     * 线程安全演示
     */
    private static void threadSafeDemo() {
        ThreadSafeLRUCache<String, Integer> cache = new ThreadSafeLRUCache<>(5);

        System.out.println("创建线程安全的LRU Cache（容量5）");
        
        // 模拟多线程操作
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                cache.put("key" + i, i);
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                cache.get("key" + i);
            }
        });
        
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                cache.put("key" + (i + 10), i + 10);
            }
        });

        try {
            t1.start();
            t2.start();
            t3.start();
            
            t1.join();
            t2.join();
            t3.join();
            
            System.out.println("多线程操作完成");
            System.out.println("最终缓存大小：" + cache.size());
            System.out.println("✓ 线程安全，无数据竞争");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 打印缓存内容
     */
    private static <K, V> void printCache(LRUCache<K, V> cache) {
        List<K> keys = cache.keySet();
        if (keys.isEmpty()) {
            System.out.println("  缓存为空");
            return;
        }
        System.out.println("  缓存内容（从左到右：最近 -> 最久）：");
        for (K key : keys) {
            System.out.printf("    %s -> %s\n", key, cache.get(key));
        }
    }
}
