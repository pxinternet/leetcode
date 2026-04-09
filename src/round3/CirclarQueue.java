package round3;

/**
 * CirclarQueue - 循环队列（支持扩容）
 *
 * 题目（概要）：固定容量循环队列，enQueue/deQueue/front/rare；满时可 resize 扩容至多 MAX_SIZE。
 *
 * 算法原理：
 * - 环形缓冲区：head、tail 模 capacity 移动；牺牲一格区分空/满（(tail+1)%capacity==head 为满）。
 * - 扩容：capacity 翻倍，将 [head..tail) 拷贝到新数组 0..size-1，head=0、tail=size。
 *
 * 核心逻辑（分步）：
 * - isEmpty：head==tail；isFull：(tail+1)%capacity==head。
 * - enQueue：满则 resize，再满返回 false；否则 queue[tail]=val，tail=(tail+1)%capacity。
 * - deQueue：空返回 -1；否则取 queue[head]，head=(head+1)%capacity。
 *
 * 关键洞察：capacity 存为 capacity+1，实际可用 capacity 格；牺牲一格判满。
 *
 * 时间复杂度：enQueue 均摊 O(1)
 * 空间复杂度：O(capacity)
 */
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
        int newCapacity = (capacity - 1) * 2 + 1;

        newCapacity = Math.min(newCapacity, MAX_SIZE);

        if (newCapacity <= currentSize + 1) {
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
