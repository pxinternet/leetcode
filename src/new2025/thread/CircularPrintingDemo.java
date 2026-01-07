package new2025.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularPrintingDemo {

    static class ConditionCircularPrinter {
        private final Lock lock = new ReentrantLock();
        private final Condition[] conditions;
        private final int threadCount;
        private volatile int currentThread = 0;

        public ConditionCircularPrinter(int threadCount) {
            this.threadCount = threadCount;
            this.conditions = new Condition[threadCount];
            for (int i = 0; i < threadCount; i++) {
                conditions[i] = lock.newCondition();
            }
        }

        private void print (int threadId, String content, int rounds) {
            for (int round = 0; round < rounds; round++) {
                lock.lock();
                try {
                    while (currentThread != threadId) {
                        conditions[threadId].await();
                    }
                    System.out.println(content);


                    currentThread = (currentThread + 1) % threadCount;
                    conditions[currentThread].signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                finally {
                    lock.unlock();
                }
            }
        }

        public static void demo() throws  InterruptedException {
            ConditionCircularPrinter printer = new ConditionCircularPrinter(3);
            String[] letters = {"A", "B", "C"};

            Thread[] threads = new Thread[3];

            for(int i = 0; i < 3; i++) {
                final int id = i;
                threads[i] = new Thread(() -> {
                    printer.print(id, letters[id], 10);
                }, "THREAD-" + i);
                threads[i].start();
            }

            for (Thread t : threads) {
                t.join();
            }
            System.out.println("\n");
        }
    }
}
