package interview;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TTLLRU {
//
//    //我们通过双向链表的方式来实现一个LRUcache
//
//    class Node {
//        String key;
//        String value;
//
//        long expireTime;
//
//        Node pre;
//        Node next;
//
//        public Node(String key, String value, long expiryTime) {
//            this.key = key;
//            this.value = value;
//            this.expireTime = expiryTime;
//        }
//    }
//
//    class DoublyLinkedList {
//        private Node head;
//        private Node tail;
//
//        public DoublyLinkedList() {
//            head = new Node("", "", 0);
//            tail = new Node("", "", 0);
//            head.next = tail;
//            tail.pre = head;
//        }
//
//        void addHead(Node node) {
//            node.next = head.next;
//            node.pre = head;
//            head.next.pre = node;
//            head.next = node;
//        }
//
//        void remove(Node node) {
//            node.pre.next = node.next;
//            node.next.pre = node.pre;
//        }
//
//        void moveToHead(Node node) {
//            remove(node);
//            addHead(node);
//        }
//
//        Node removeTail() {
//            if (tail.pre == head) {
//                return null;
//            }
//            Node node = tail.pre;
//            remove(node);
//            return node;
//        }
//
//
//    }
//
//    private final Map<String, Node> cache;
//    private final DoublyLinkedList list;
//    private final int capacity;
//
//    private final ScheduledExecutorService executor;
//
//    //还可以进一步优化，用优先级队列；按照时间排序，这样定时任务在遍历的时候，可以提前结束
//
//
//
//    public TTLLRU(int capacity) {
//        this.capacity = capacity;
//        this.cache = new HashMap<>();
//        this.list = new DoublyLinkedList();
//        this.executor = Executor.newScheduledThreadPool(1);
//        startExpirationTask();
//    }
//
//    private void startExpirationTask() {
//        executor.scheduleAtFixedRate(this::expireEntries, 0, 1, TimeUnit.SECONDS);
//    }
//
//    private synchronized  void expireEntries() {
//        long currentTime = System.currentTimeMillis();
//
//        for(Node node : cache.values()) {
//            if (node.expireTime < currentTime) {
//                cache.remove(node);
//                list.remove(node);
//            }
//        }
//    }
//
//    public String get(String key) {
//        if (cache.containsKey(key)) {
//            Node node = cache.get(key);
//            if (node.expireTime < System.currentTimeMillis()) {
//                cache.remove(key);
//                list.remove(node);
//                return null;
//            }
//            list.moveToHead(node);
//            return node.value;
//        }
//        return null;
//    }
//
//    public void put(String key, String value, long lifeTime) {
//        long expireTime = System.currentTimeMillis() + lifeTime;
//
//        if (cache.containsKey(key)) {
//            Node node = cache.get(key);
//            node.value = value;
//            node.expireTime = expireTime;
//            list.moveToHead(node);
//        } else {
//            if (cache.size() == capacity) {
//                Node tail = list.removeTail();
//                cache.remove(tail.key);
//            }
//
//            Node node = new Node(key, value, expireTime);
//            list.addHead(node);
//            cache.put(node.key, node);
//        }
//    }
//

}
