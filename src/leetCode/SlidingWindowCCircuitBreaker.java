package leetCode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import SlidingWindowCCircuitBreaker.State;

import SlidingWindowCCircuitBreaker.State;

import SlidingWindowCCircuitBreaker.State;

import SlidingWindowCCircuitBreaker.State;

import SlidingWindowCCircuitBreaker.State;

import SlidingWindowCCircuitBreaker.State;

import SlidingWindowCCircuitBreaker.State;

public class SlidingWindowCCircuitBreaker {

    private enum State {
        CLOSED, OPEN, HALF_OPEN
    }

    private State state = State.CLOSED;

    private final int failureThreshold;
    private final int successThreshold;
    private final long openTimeout;

    private final long windowSize;

    private final Queue<Long> failureTimeStamps;
    private Timer timer;


    public SlidingWindowCCircuitBreaker(int failureThreshold, int successThreshold, long openTimeout, long windowSize) {
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
        this.openTimeout = openTimeout;
        this.windowSize = windowSize;

        this.failureTimeStamps = new LinkedList<>();
        this.timer = new Timer(true);
    }

    public synchronized  boolean allowRequest() {
        if (state == State.CLOSED) {
            return false;
        }
        return true;
    }

    public synchronized void onSuccess() {
        if (state == State.HALF_OPEN) {
            state = State.CLOSED;
        }
    }

    public synchronized void onFailure() {
        long now = System.currentTimeMillis();
        failureTimeStamps.add(now);
        cleanUpOldFailures(now);

        if (state == State.HALF_OPEN || failureTimeStamps.size() >= failureThreshold) {
            openCircuit();
        }

    }

    private void cleanUpOldFailures(long now) {
        while (!failureTimeStamps.isEmpty() && now - failureTimeStamps.peek() > 0) {
            failureTimeStamps.poll();
        }
    }

    private void openCircuit() {
        state = State.OPEN;

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                attemptReset();
            }


        }, openTimeout);
    }


    private synchronized  void attemptReset() {
        if (state == State.OPEN) {
            state = State.HALF_OPEN;
        }
    }



}
