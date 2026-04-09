package leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * SimpleBlockingQueue - 简单阻塞队列
 *
 * 说明：有界阻塞队列，容量满时 put 阻塞，空时 take 阻塞。使用 synchronized + wait/notifyAll 实现。
 *
 * 方法：
 * - put(element)：队满时等待直到有空间
 * - take()：队空时等待直到有元素可取
 */
public class SimpleBlockingQueue<T> {

    private Queue<T> queue = new LinkedList<>();
    int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized  void put(T element) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(element);
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.remove();
        notifyAll();
        return item;
    }
}
