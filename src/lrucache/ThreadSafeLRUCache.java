package lrucache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的 LRU Cache
 * 
 * 实现原理：
 * - 基于 LRUCache，使用 ReentrantReadWriteLock 保证线程安全
 * - 读操作使用读锁（可并发）
 * - 写操作使用写锁（互斥）
 * 
 * 性能特点：
 * - 读操作可以并发执行
 * - 写操作互斥，保证数据一致性
 */
public class ThreadSafeLRUCache<K, V> {
    /**
     * 双向链表节点
     */
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node() {
            this(null, null);
        }

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * 构造函数
     * 
     * @param capacity 缓存容量
     */
    public ThreadSafeLRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("容量必须大于0");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        this.head = new Node<>();
        this.tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 获取值（读锁）
     */
    public V get(K key) {
        readLock.lock();
        try {
            Node<K, V> node = cache.get(key);
            if (node == null) {
                return null;
            }
            
            // 需要移动到头部，需要升级为写锁
            readLock.unlock();
            writeLock.lock();
            try {
                // 双重检查（因为可能被其他线程修改）
                node = cache.get(key);
                if (node == null) {
                    return null;
                }
                moveToHead(node);
                return node.value;
            } finally {
                readLock.lock(); // 重新获取读锁
                writeLock.unlock();
            }
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 放入键值对（写锁）
     */
    public void put(K key, V value) {
        writeLock.lock();
        try {
            Node<K, V> node = cache.get(key);
            
            if (node != null) {
                // 键已存在，更新值并移动到头部
                node.value = value;
                moveToHead(node);
            } else {
                // 键不存在，需要添加新节点
                if (cache.size() >= capacity) {
                    // 缓存已满，删除尾部节点
                    Node<K, V> tailNode = removeTail();
                    cache.remove(tailNode.key);
                }
                
                // 创建新节点并添加到头部
                Node<K, V> newNode = new Node<>(key, value);
                cache.put(key, newNode);
                addToHead(newNode);
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 删除键值对（写锁）
     */
    public V remove(K key) {
        writeLock.lock();
        try {
            Node<K, V> node = cache.get(key);
            if (node == null) {
                return null;
            }
            
            removeNode(node);
            cache.remove(key);
            return node.value;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 清空缓存（写锁）
     */
    public void clear() {
        writeLock.lock();
        try {
            cache.clear();
            head.next = tail;
            tail.prev = head;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取缓存大小（读锁）
     */
    public int size() {
        readLock.lock();
        try {
            return cache.size();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 检查是否包含键（读锁）
     */
    public boolean containsKey(K key) {
        readLock.lock();
        try {
            return cache.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 检查缓存是否为空（读锁）
     */
    public boolean isEmpty() {
        readLock.lock();
        try {
            return cache.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 将节点移动到头部
     */
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 将节点添加到头部
     */
    private void addToHead(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 删除节点
     */
    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * 删除尾部节点
     */
    private Node<K, V> removeTail() {
        Node<K, V> lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode;
    }
}
