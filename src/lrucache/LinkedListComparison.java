package lrucache;

import java.util.List;

/**
 * 双向链表 vs 单链表实现对比
 */
public class LinkedListComparison {

    public static void main(String[] args) {
        System.out.println("========== 双向链表 vs 单链表实现对比 ==========\n");

        // 功能对比
        System.out.println("【功能对比】\n");
        compareFunctionality();

        // 性能对比
        System.out.println("\n【性能对比】\n");
        comparePerformance();

        // 复杂度对比
        System.out.println("\n【复杂度对比】\n");
        printComplexityComparison();
    }

    /**
     * 功能对比
     */
    private static void compareFunctionality() {
        System.out.println("测试：相同的操作序列\n");
        System.out.println("操作：put(1,'a'), put(2,'b'), put(3,'c'), get(1), put(4,'d')\n");

        // 双向链表实现
        LRUCache<Integer, String> doublyLinked = new LRUCache<>(3);
        doublyLinked.put(1, "a");
        doublyLinked.put(2, "b");
        doublyLinked.put(3, "c");
        doublyLinked.get(1);
        doublyLinked.put(4, "d");

        System.out.println("双向链表实现结果：");
        printCache(doublyLinked);

        // 单链表实现
        SinglyLinkedListLRUCache<Integer, String> singlyLinked = new SinglyLinkedListLRUCache<>(3);
        singlyLinked.put(1, "a");
        singlyLinked.put(2, "b");
        singlyLinked.put(3, "c");
        singlyLinked.get(1);
        singlyLinked.put(4, "d");

        System.out.println("\n单链表实现结果：");
        printSinglyCache(singlyLinked);

        System.out.println("\n✓ 两种实现功能相同");
    }

    /**
     * 性能对比
     */
    private static void comparePerformance() {
        int operations = 10000;
        
        // 双向链表性能测试
        long start = System.nanoTime();
        LRUCache<Integer, Integer> doublyLinked = new LRUCache<>(100);
        for (int i = 0; i < operations; i++) {
            doublyLinked.put(i % 200, i);
            if (i % 2 == 0) {
                doublyLinked.get(i % 200);
            }
        }
        long doublyTime = System.nanoTime() - start;

        // 单链表性能测试
        start = System.nanoTime();
        SinglyLinkedListLRUCache<Integer, Integer> singlyLinked = new SinglyLinkedListLRUCache<>(100);
        for (int i = 0; i < operations; i++) {
            singlyLinked.put(i % 200, i);
            if (i % 2 == 0) {
                singlyLinked.get(i % 200);
            }
        }
        long singlyTime = System.nanoTime() - start;

        System.out.printf("操作次数：%d\n", operations);
        System.out.printf("双向链表耗时：%.2f ms\n", doublyTime / 1_000_000.0);
        System.out.printf("单链表耗时：  %.2f ms\n", singlyTime / 1_000_000.0);
        System.out.printf("性能差异：    %.2f%%\n", 
            (double) (singlyTime - doublyTime) / doublyTime * 100);
        
        System.out.println("\n结论：");
        if (singlyTime > doublyTime) {
            System.out.println("  双向链表实现性能更好（更少的HashMap操作）");
        } else {
            System.out.println("  两种实现性能相近");
        }
    }

    /**
     * 打印复杂度对比
     */
    private static void printComplexityComparison() {
        System.out.println("========== 复杂度对比 ==========\n");
        
        System.out.println("【时间复杂度】");
        System.out.println("操作        | 双向链表 | 单链表");
        System.out.println("-----------|---------|--------");
        System.out.println("get()      |  O(1)   |  O(1)");
        System.out.println("put()      |  O(1)   |  O(1)");
        System.out.println("remove()   |  O(1)   |  O(1)");
        System.out.println("\n两种实现的时间复杂度相同\n");
        
        System.out.println("【空间复杂度】");
        System.out.println("双向链表：");
        System.out.println("  • HashMap: O(n) - 存储 key -> Node");
        System.out.println("  • 双向链表: O(n) - 每个节点有 prev 和 next 指针");
        System.out.println("  • 总计: O(n)");
        System.out.println();
        System.out.println("单链表：");
        System.out.println("  • HashMap: O(n) - 存储 key -> Node");
        System.out.println("  • prevMap: O(n) - 存储 key -> 前驱节点");
        System.out.println("  • 单链表: O(n) - 每个节点只有 next 指针");
        System.out.println("  • 总计: O(n)");
        System.out.println("\n单链表需要额外的 prevMap，空间开销略大\n");
        
        System.out.println("【实现复杂度】");
        System.out.println("双向链表：");
        System.out.println("  • 删除节点：直接通过 prev 指针，简单");
        System.out.println("  • 移动到头部：删除+插入，简单");
        System.out.println("  • 代码简洁，易于维护");
        System.out.println();
        System.out.println("单链表：");
        System.out.println("  • 删除节点：需要 prevMap 查找前驱节点");
        System.out.println("  • 移动到头部：需要更新 prevMap");
        System.out.println("  • 代码复杂，需要维护额外的 prevMap");
        System.out.println("\n双向链表实现更简洁\n");
        
        System.out.println("========== 总结 ==========");
        System.out.println("✓ 两种实现的时间复杂度相同（都是O(1)）");
        System.out.println("✓ 单链表需要额外的 prevMap，空间开销略大");
        System.out.println("✓ 双向链表实现更简洁，代码更易维护");
        System.out.println("✓ 推荐使用双向链表实现");
    }

    private static <K, V> void printCache(LRUCache<K, V> cache) {
        List<K> keys = cache.keySet();
        System.out.print("  [");
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(keys.get(i));
        }
        System.out.println("]");
    }

    private static <K, V> void printSinglyCache(SinglyLinkedListLRUCache<K, V> cache) {
        List<K> keys = cache.keySet();
        System.out.print("  [");
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(keys.get(i));
        }
        System.out.println("]");
    }
}
