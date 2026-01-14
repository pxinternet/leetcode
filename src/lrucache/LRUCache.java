package lrucache;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU Cache（Least Recently Used Cache）
 * 
 * 实现原理：
 * - 使用 HashMap + 双向链表实现
 * - HashMap 提供 O(1) 的查找
 * - 双向链表维护访问顺序（最近访问的在头部，最久未访问的在尾部）
 * 
 * 时间复杂度：
 * - get(): O(1)
 * - put(): O(1)
 * 
 * 空间复杂度：O(capacity)
 */
public class LRUCache<K, V> {
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
    private final Node<K, V> head;  // 虚拟头节点
    private final Node<K, V> tail;  // 虚拟尾节点

    /**
     * 构造函数
     * 
     * @param capacity 缓存容量
     */
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("容量必须大于0");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // 初始化双向链表（使用虚拟头尾节点简化操作）
        this.head = new Node<>();
        this.tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 获取值
     * 
     * @param key 键
     * @return 值，如果不存在返回null
     */
    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        
        // 将节点移动到头部（标记为最近使用）
        moveToHead(node);
        return node.value;
    }

    /**
     * 放入键值对
     * 
     * @param key 键
     * @param value 值
     */
    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        
        if (node != null) {
            // 键已存在，更新值并移动到头部
            node.value = value;
            moveToHead(node);
        } else {
            // 键不存在，需要添加新节点
            if (cache.size() >= capacity) {
                // 缓存已满，删除尾部节点（最久未使用）
                Node<K, V> tailNode = removeTail();
                cache.remove(tailNode.key);
            }
            
            // 创建新节点并添加到头部
            Node<K, V> newNode = new Node<>(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
        }
    }

    /**
     * 删除键值对
     * 
     * @param key 键
     * @return 被删除的值，如果不存在返回null
     */
    public V remove(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        
        removeNode(node);
        cache.remove(key);
        return node.value;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.clear();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 获取缓存大小
     */
    public int size() {
        return cache.size();
    }

    /**
     * 检查是否包含键
     */
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    /**
     * 检查缓存是否为空
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    /**
     * 将节点移动到头部（标记为最近使用）
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
     * 删除尾部节点（最久未使用）
     */
    private Node<K, V> removeTail() {
        Node<K, V> lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode;
    }

    /**
     * 获取所有键（按访问顺序，从最近到最久）
     */
    public java.util.List<K> keySet() {
        java.util.List<K> keys = new java.util.ArrayList<>();
        Node<K, V> current = head.next;
        while (current != tail) {
            keys.add(current.key);
            current = current.next;
        }
        return keys;
    }

    /**
     * 获取所有值（按访问顺序，从最近到最久）
     */
    public java.util.List<V> values() {
        java.util.List<V> values = new java.util.ArrayList<>();
        Node<K, V> current = head.next;
        while (current != tail) {
            values.add(current.value);
            current = current.next;
        }
        return values;
    }
}
