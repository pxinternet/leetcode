package round3;

import java.util.HashMap;

/**
 * LRUWithTTL - 带 TTL 的 LRU 缓存
 *
 * 题目（概要）：capacity 容量的缓存，put(key,val,ttlInSeconds) 写入并设置过期时间；get 时若过期返回 -1 并移除，否则移到头部并返回值。
 *
 * 算法原理：
 * - 双链表 + 哈希：哈希 O(1) 查找；双链表维护访问顺序，头为最近使用，尾为最久未使用。
 * - TTL：每个节点存 expireTime；get 时若 currentTime>=expireTime 则认为过期，移除并返回 -1。
 *
 * 核心逻辑（分步）：
 * - put：已存在则更新 value、expireTime 并 moveToHead；否则若满则删 tail.prev，插入新节点到头。
 * - get：不存在返回 -1；过期（expireTime<=now）则 remove 并返回 -1；否则 moveToHead 并返回 value。
 *
 * 关键洞察：过期节点需从链表和 map 中移除；moveToHead 实现 LRU 更新。
 *
 * 时间复杂度：get/put 均 O(1)
 * 空间复杂度：O(capacity)
 */
public class LRUWithTTL {

    class Node {
        int key;
        int value;

        long expireTime;

        Node prev;
        Node next;

        public Node(int key, int value, long expireTime) {
            this.key = key;
            this.value = value;
            this.expireTime = expireTime;
        }
    }

    private HashMap<Integer, Node> cache;

    private int capacity;

    private Node head;
    private Node tail;

    public LRUWithTTL(int capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;

        head = new Node(0, 0, 0);
        tail = new Node(0, 0, 0);

        head.next = tail;
        tail.prev = head;
    }


    public void put(int key, int val, int ttlInSeconds) {

        if (cache.containsKey(key)) {

            Node node = cache.get(key);
            node.value = val;
            node.expireTime = System.currentTimeMillis() + ttlInSeconds * 1000;
            moveToHead(node);

        } else {
            if (cache.size() >= capacity) {
                Node old = tail.prev;
                remove(old);
                cache.remove(old);
            }

            Node newNode = new Node(key, val, System.currentTimeMillis() + ttlInSeconds * 1000);
            cache.put(key, newNode);
            insert(newNode);
        }

    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            if (node.expireTime <= System.currentTimeMillis()) {
                cache.remove(key);
                remove(node);
                return -1;
            } else {
                moveToHead(node);
                return node.value;
            }

        } else {
            return -1;
        }
    }


    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insert(Node node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;

    }

    private void moveToHead(Node node) {
        remove(node);
        insert(node);
    }
}
