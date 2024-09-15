package round3;

import java.lang.foreign.SymbolLookup;
import java.util.HashMap;

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
            if (node.expireTime > System.currentTimeMillis()) {
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
