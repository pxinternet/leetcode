package thread;

import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.atomic.AtomicBoolean;

import java.util.concurrent.atomic.AtomicInteger;

public class LC1114 {
    // Add your code here
    private AtomicInteger firstJobDone = new AtomicInteger(0);
    private AtomicInteger secondJobDone = new AtomicInteger(0);


    public LC1114() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // Add your code here
        printFirst.run();

        firstJobDone.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // Add your code here
        while (firstJobDone.get() != 1) {
            continue;
        }
        printSecond.run();
        secondJobDone.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while(secondJobDone.get()!= 1) {
            continue;
        }
        printThird.run();
    }
}
