package queue;

public class ProducerService {

    private MessageQueue messageQueue;

    public ProducerService(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void produce(String content, long ttl) {
        Message message = new Message(content, ttl);
        messageQueue.addMessage(message);
    }
}
