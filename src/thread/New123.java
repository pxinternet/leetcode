package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class New123 {

    private static final int LOOP_NUM = 10;

    private static int currentValue = 1;

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static final Condition condition3 = lock.newCondition();

    static class ReentrantLockTask implements Runnable {

        private int threadId;
        private Condition currentCondition;
        private Condition nextCondition;

        public ReentrantLockTask(int threadId, Condition currentCondition, Condition nextCondition) {
            this.threadId = threadId;
            this.currentCondition = currentCondition;
            this.nextCondition = nextCondition;
        }

        @Override
        public void run() {
            for (int i = 0; i < LOOP_NUM; i++) {
                lock.lock();
                try {

                    while (currentValue != threadId) {
                        currentCondition.await();
                    }
                    //do sth;
                    currentValue = currentValue % 3 + 1;
                    nextCondition.signal();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new ReentrantLockTask(1, condition1, condition2));
        Thread t2 = new Thread(new ReentrantLockTask(2, condition2, condition3));
        Thread t3 = new Thread(new ReentrantLockTask(3, condition3, condition1));

        t1.start();
        t2.start();
        t3.start();
    }


    public static void cdlTest() {
        CountDownLatch cdl = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(
                    () -> {
                        //do sth
                        cdl.countDown();
                    }).start();
        }

        try {
            cdl.await();
        } catch (Exception e) {
        }

    }
}
