package leetCode;

import java.sql.Time;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import java.util.Timer;


public class EnhancedSlidingWindowCircuitBreaker {

    private enum BreakState {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    private BreakState state = BreakState.CLOSED;

    private final int failureThreshold;
    private final int successThreshold;

    private long openTimeout;
    private long windowSize;

    private final Queue<Long> failureTimestamps;
    private final Timer timer;

    private final ReentrantLock lock = new ReentrantLock();
    private Consumer<State> onStateChange;
    private int consecutiveSucessCount;

    public EnhancedSlidingWindowCircuitBreaker(int failureThreshold, int successThreshold, long openTimeout, long windowSize) {
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
        this.openTimeout = openTimeout;
        this.windowSize = windowSize;
        this.failureTimestamps = new LinkedList<>();
        this.timer = new Time(true);
    }

    public boolean allowRequest() {
        lock.lock();
        try {

            if (state == BreakState.OPEN) {
                return false;
            }
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            lock.unlock();
        }
    }


    public void onSuccess() {
        lock.lock();

        try {
            if (state == BreakState.HALF_OPEN) {
                consecutiveSucessCount++;
                if (consecutiveSucessCount >= successThreshold) {
                    //要不要清0看情况
                    changeState(BreakState.CLOSED);
                }
            }

        } finally {
            lock.unlock();
        }
    }

    public void onFailure() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            failureTimestamps.add(now);
            cleanUpOldFailures(now);

            if (state == BreakState.HALF_OPEN || failureTimestamps.size() >= failureThreshold) {
                openCircuit();
            }
        } finally {
            lock.unlock();
        }
    }

    private void cleanUpOldFailures(long now) {
        while (!failureTimestamps.isEmpty() && now - failureTimestamps.peek() > closeTimeOut) {
            failureTimestamps.poll();

        }
    }

    private void openCircuit() {
        changeState(BreakState.OPEN);
        consecutiveSucessCount = 0;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                attemptReset();
            }

        }, openTimeout);


        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                attemptReset();
            }

        }, openTimeout);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                attemptReset();
            }

        }, openTimeout);

    }

    private void attemptReset() {

        lock.lock();
        try {

            if (state == BreakState.OPEN) {
                changeState(BreakState.HALF_OPEN);
            }

        } finally {
            lock.unlock();
        }

    }

    private void changeState(BreakState newState) {
        if (this.state != newState) {
            this.state = newState;
            this.consecutiveSucessCount = 0;
        }
    }


}
