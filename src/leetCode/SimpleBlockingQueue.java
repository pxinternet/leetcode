package leetCode;

import java.util.LinkedList;
import java.util.Queue;

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
