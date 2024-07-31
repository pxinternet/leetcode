package queue;

public class TimerManager {

    private MessageQueue messageQueue;

    public TimeManager(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public  void start() {
        new Thread(
            () -> {
                try {
                    while (true) {
                        messageQueue.removeExpiredMessage();
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }

        ).start();
    }

}
