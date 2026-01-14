package lrucache;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用单链表实现的 LRU Cache
 * 
 * 实现原理：
 * - 使用 HashMap + 单链表实现
 * - HashMap 存储 key -> Node 的映射
 * - HashMap 存储 key -> 前驱节点 的映射（用于快速删除）
 * 
 * 时间复杂度：
 * - get(): O(1) - 需要更新前驱节点映射
 * - put(): O(1) - 需要更新前驱节点映射
 * 
 * 空间复杂度：O(capacity) - 比双向链表多存储前驱节点映射
 * 
 * 注意：单链表实现比双向链表更复杂，且需要额外的HashMap存储前驱节点
 */
public class SinglyLinkedListLRUCache<K, V> {
    /**
     * 单链表节点
     */
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> cache;  // key -> Node
    private final Map<K, Node<K, V>> prevMap; // key -> 前驱节点（用于快速删除）
    private Node<K, V> head;  // 头节点（最近使用的）
    private Node<K, V> tail;  // 尾节点（最久未使用的）

    /**
     * 构造函数
     * 
     * @param capacity 缓存容量
     */
    public SinglyLinkedListLRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("容量必须大于0");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.prevMap = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    /**
     * 获取值
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
                removeTail();
            }
            
            // 创建新节点并添加到头部
            Node<K, V> newNode = new Node<>(key, value);
            addToHead(newNode);
            cache.put(key, newNode);
        }
    }

    /**
     * 删除键值对
     */
    public V remove(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        
        removeNode(node);
        cache.remove(key);
        prevMap.remove(key);
        return node.value;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.clear();
        prevMap.clear();
        head = null;
        tail = null;
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
        // 如果已经是头节点，不需要移动
        if (node == head) {
            return;
        }
        
        // 删除节点
        removeNode(node);
        
        // 添加到头部
        addToHead(node);
    }

    /**
     * 将节点添加到头部
     */
    private void addToHead(Node<K, V> node) {
        if (head == null) {
            // 第一个节点
            head = node;
            tail = node;
        } else {
            // 添加到头部
            node.next = head;
            // 更新前驱节点映射：head的前驱是node
            prevMap.put(head.key, node);
            head = node;
        }
        // 头节点没有前驱
        prevMap.remove(node.key);
    }

    /**
     * 删除节点
     */
    private void removeNode(Node<K, V> node) {
        if (node == head) {
            // 删除头节点
            head = head.next;
            if (head != null) {
                prevMap.remove(head.key);
            } else {
                // 链表为空
                tail = null;
            }
        } else {
            // 删除非头节点：需要找到前驱节点
            Node<K, V> prev = prevMap.get(node.key);
            if (prev != null) {
                prev.next = node.next;
                // 更新前驱节点映射
                if (node.next != null) {
                    prevMap.put(node.next.key, prev);
                } else {
                    // node是尾节点
                    tail = prev;
                }
                prevMap.remove(node.key);
            }
        }
    }

    /**
     * 删除尾部节点（最久未使用）
     */
    private void removeTail() {
        if (tail == null) {
            return;
        }
        
        K tailKey = tail.key;
        removeNode(tail);
        cache.remove(tailKey);
        prevMap.remove(tailKey);
    }

    /**
     * 获取所有键（按访问顺序，从最近到最久）
     */
    public java.util.List<K> keySet() {
        java.util.List<K> keys = new java.util.ArrayList<>();
        Node<K, V> current = head;
        while (current != null) {
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
        Node<K, V> current = head;
        while (current != null) {
            values.add(current.value);
            current = current.next;
        }
        return values;
    }
}
