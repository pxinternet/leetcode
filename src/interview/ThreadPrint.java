package interview;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadPrint {

    private static ReentrantLock lock = new ReentrantLock();

    private static int count = 0;

    class RunnableTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (count >= 1000000) {
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + " " + count++);

                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new Thread(new ThreadPrint().new RunnableTask()).start();
        }
        
    }


}
