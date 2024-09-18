package round3;

public class CirclarQueue {

    private int[] queue;

    private int head;

    private int tail;

    private int capacity;

    private static final int MAX_SIZE = 200000;

    public CirclarQueue(int capacity) {
        this.capacity = capacity + 1;
        queue = new int[this.capacity];
        head = 0;
        tail = 0;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return head == (tail + 1) % capacity;
    }

    public int size() {
        return (tail - head + capacity) % capacity;
    }

    public void resize() {
        int currentSize = size();
        // 先把size拿出来
        int newCapacity = (size() - 1) * 2 + 1;

        newCapacity = Math.min(newCapacity, MAX_SIZE);

        if (newCapacity <= size() + 1) {
            return;
        }

        int[] newQueue = new int[newCapacity];

        for (int i = 0; i < size(); i++) {
            newQueue[i] = queue[(head + i) % capacity];
        }

        tail = size();
        head = 0;
        queue = newQueue;
        capacity = newCapacity;
    }

    public int deQueue() {
        if (isEmpty())
            return -1;
        int val = queue[head];
        head = (head + 1) % capacity;
        return val;
    }

    public boolean enQueue(int val) {
        if (isFull()) {
            resize();
        }

        if (isFull()) {
            return false;
        }

        queue[tail] = val;
        tail = (tail + 1) % capacity;
        return true;
    }

    public int front() {
        return isEmpty() ? -1 : queue[head];
    }

    public int rare() {
        return isEmpty() ? -1 : queue[(tail - 1 + capacity) % capacity];
    }

}
