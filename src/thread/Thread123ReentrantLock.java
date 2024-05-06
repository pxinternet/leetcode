package thread;

import java.util.concurrent.locks.ReentrantLock;

public class Thread123ReentrantLock {

    private volatile static int loopNum = 5000;

    private volatile static int currentValue = 1;

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    static class ReentrantLockTask implements Runnable {

        @Override
        public void run() {
            while(true) {
                reentrantLock.lock();
                try {

                    if (currentValue > 3) {
                        return;
                    }
                    System.out.println(currentValue);
                    currentValue++;
//                    if (currentValue == 3) {
//                        currentValue = 1;
//                    } else {
//                        currentValue++;
//                    }
                    loopNum--;

                } finally {
                    reentrantLock.unlock();
                }
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new ReentrantLockTask()).start();
        new Thread(new ReentrantLockTask()).start();
        new Thread(new ReentrantLockTask()).start();
    }
}
