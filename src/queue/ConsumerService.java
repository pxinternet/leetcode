package queue;

public class ConsumerService {

    private MessageQueue messageQueue;

    public ConsumerService(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void consume() {
        while (true) {
            Message message = messageQueue.getMessage();
            Thread.sleep(2000);
        }
    }

}
