package queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ThreadSafeBlockingQueue - 线程安全的固定容量阻塞队列
 *
 * 题目（概要）：实现一个线程安全的阻塞队列，具有固定容量。当队列满时，put 操作会阻塞直到有空间；
 * 当队列空时，take 操作会阻塞直到有元素可用。
 *
 * 实现原理说明：
 * - 使用数组作为底层存储结构，实现循环队列（环形缓冲区）以充分利用空间。
 * - 使用 ReentrantLock 保证线程安全，使用两个 Condition（notEmpty 和 notFull）实现阻塞等待。
 * - 核心变量：
 * - items[]: 存储元素的数组
 * - count: 当前队列中的元素数量
 * - putIndex: 下一个 put 操作的位置（队尾）
 * - takeIndex: 下一个 take 操作的位置（队头）
 * - 阻塞机制：
 * - put(): 如果队列满，线程在 notFull 条件上等待，直到有空间可用
 * - take(): 如果队列空，线程在 notEmpty 条件上等待，直到有元素可用
 * - 循环队列实现：
 * - putIndex 和 takeIndex 在到达数组末尾时回绕到 0，实现循环使用
 * - 通过 (index + 1) % capacity 实现索引的循环递增
 *
 * 时间复杂度：
 * - put(): O(1) 平均时间复杂度（不考虑阻塞等待时间）
 * - take(): O(1) 平均时间复杂度（不考虑阻塞等待时间）
 * - size(): O(1)
 *
 * 空间复杂度：O(capacity)
 *
 * 边界与注意事项：
 * - capacity 必须大于 0，否则抛出 IllegalArgumentException
 * - put() 操作在队列满时会阻塞，直到有空间或线程被中断
 * - take() 操作在队列空时会阻塞，直到有元素或线程被中断
 * - 支持中断：当线程在阻塞时被中断，会抛出 InterruptedException
 * - 非阻塞方法 offer() 和 poll() 在队列满/空时立即返回 false/null，不阻塞
 * - 所有操作都是线程安全的，可以在多线程环境下安全使用
 *
 * 示例：
 * - 创建一个容量为 10 的队列：ThreadSafeBlockingQueue<Integer> queue = new
 * ThreadSafeBlockingQueue<>(10);
 * - 生产者线程：queue.put(1); // 如果队列满则阻塞
 * - 消费者线程：Integer item = queue.take(); // 如果队列空则阻塞
 */
public class ThreadSafeBlockingQueue<E> {

    /** 存储队列元素的数组 */
    private final Object[] items;

    /** 队列的容量（固定值） */
    private final int capacity;

    /** 当前队列中的元素数量 */
    private int count;

    /** 下一个 put 操作的位置（队尾索引） */
    private int putIndex;

    /** 下一个 take 操作的位置（队头索引） */
    private int takeIndex;

    /** 用于同步的锁 */
    private final ReentrantLock lock;

    /** 队列非空条件：当队列为空时，take 操作在此条件上等待 */
    private final Condition notEmpty;

    /** 队列未满条件：当队列满时，put 操作在此条件上等待 */
    private final Condition notFull;

    /**
     * 构造一个固定容量的线程安全阻塞队列
     *
     * @param capacity 队列容量，必须大于 0
     * @throws IllegalArgumentException 如果 capacity <= 0
     */
    public ThreadSafeBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.items = new Object[capacity];
        this.count = 0;
        this.putIndex = 0;
        this.takeIndex = 0;
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * 将元素放入队列（阻塞方法）
     *
     * 关键点逐行说明：
     * - 获取锁以保证线程安全
     * - 如果队列满，在 notFull 条件上等待，直到有空间可用或被中断
     * - 将元素放入 putIndex 位置
     * - 更新 putIndex（循环递增）
     * - 增加 count
     * - 唤醒在 notEmpty 上等待的线程（如果有消费者在等待）
     *
     * @param item 要放入队列的元素
     * @throws InterruptedException 如果当前线程在等待时被中断
     */
    public void put(E item) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 如果队列满，等待直到有空间可用
            while (count == capacity) {
                notFull.await();
            }
            // 将元素放入队列
            items[putIndex] = item;
            // 更新 putIndex，实现循环队列（到达末尾时回绕到 0）
            putIndex = (putIndex + 1) % capacity;
            // 增加元素计数
            count++;
            // 唤醒在 notEmpty 上等待的消费者线程
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从队列中取出元素（阻塞方法）
     *
     * 关键点逐行说明：
     * - 获取锁以保证线程安全
     * - 如果队列空，在 notEmpty 条件上等待，直到有元素可用或被中断
     * - 从 takeIndex 位置取出元素
     * - 清空该位置（帮助 GC）
     * - 更新 takeIndex（循环递增）
     * - 减少 count
     * - 唤醒在 notFull 上等待的线程（如果有生产者在等待）
     *
     * @return 队列头部的元素
     * @throws InterruptedException 如果当前线程在等待时被中断
     */
    @SuppressWarnings("unchecked")
    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 如果队列空，等待直到有元素可用
            while (count == 0) {
                notEmpty.await();
            }
            // 从队列头部取出元素
            E item = (E) items[takeIndex];
            // 清空该位置，帮助垃圾回收
            items[takeIndex] = null;
            // 更新 takeIndex，实现循环队列（到达末尾时回绕到 0）
            takeIndex = (takeIndex + 1) % capacity;
            // 减少元素计数
            count--;
            // 唤醒在 notFull 上等待的生产者线程
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试将元素放入队列（非阻塞方法）
     *
     * 如果队列满，立即返回 false，不阻塞
     *
     * @param item 要放入队列的元素
     * @return 如果成功放入返回 true，如果队列满返回 false
     */
    public boolean offer(E item) {
        lock.lock();
        try {
            // 如果队列满，立即返回 false
            if (count == capacity) {
                return false;
            }
            // 将元素放入队列
            items[putIndex] = item;
            putIndex = (putIndex + 1) % capacity;
            count++;
            // 唤醒等待的消费者
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试从队列中取出元素（非阻塞方法）
     *
     * 如果队列空，立即返回 null，不阻塞
     *
     * @return 队列头部的元素，如果队列空返回 null
     */
    @SuppressWarnings("unchecked")
    public E poll() {
        lock.lock();
        try {
            // 如果队列空，立即返回 null
            if (count == 0) {
                return null;
            }
            // 从队列头部取出元素
            E item = (E) items[takeIndex];
            items[takeIndex] = null;
            takeIndex = (takeIndex + 1) % capacity;
            count--;
            // 唤醒等待的生产者
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 返回队列中的元素数量
     *
     * @return 队列中的元素数量
     */
    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检查队列是否为空
     *
     * @return 如果队列为空返回 true，否则返回 false
     */
    public boolean isEmpty() {
        lock.lock();
        try {
            return count == 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检查队列是否已满
     *
     * @return 如果队列已满返回 true，否则返回 false
     */
    public boolean isFull() {
        lock.lock();
        try {
            return count == capacity;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 返回队列的容量
     *
     * @return 队列的容量
     */
    public int capacity() {
        return capacity;
    }

}
