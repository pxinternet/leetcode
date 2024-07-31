package queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

public class MessageQueue {

    private BlockingQueue<Message> queue;
    private PriorityBlockingQueue<Message> ttlQueue;

    public MessageQueue() {
        queue = new LinkedBlockingDeque<>();
        ttlQueue = new PriorityBlockingQueue<>(
            (m1, m2) -> Long.compare(m1.getCreateTime() + m1.getTTL(), m2.getCreateTime() + m2.getTTL());
        );
    }

    public void addMessage(Message message) {
        queue.put(message);
        ttlQueue.put(message);
    }

    public Message getMessage() {
        while (true) {
            Message message = queue.take();
            if (!message.isExpired()) {
                return message;
            } else {
                ttlQueue.remove(message);
            }
        }
    }

    public void removeExpiredMessage() {
        while (!ttlQueue.isEmpty() && ttlQueue.peek().isExpired()) {
            Message exMessage = ttlQueue.poll();
            queue.remove(exMessage);
        }
    }

}
