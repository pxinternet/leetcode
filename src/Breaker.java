public class Breaker {

    private enum State {
        CLOSED, OPEN, HALF_OPEN
    }

    private State state;
    private AtomicInteger failureCount;
    private AtomicInteger successCount;

    private final int failureThreshold;
    private final long timeout;

    private 

}
