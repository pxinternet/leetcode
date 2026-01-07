package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread123Condition {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static final Condition condition3 = lock.newCondition();
    private static int number = 1; // 用于控制打印的数字

    public static void main(String[] args) {
        new Thread(new PrintTask(1, condition1, condition2)).start();
        new Thread(new PrintTask(2, condition2, condition3)).start();
        new Thread(new PrintTask(3, condition3, condition1)).start();

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        try {
            // 唤醒 condition1，开始打印
            condition1.signal();
        } finally {
            lock.unlock();
        }
    }

    static class PrintTask implements Runnable {
        private int threadNo; // 当前线程编号
        private Condition currentCondition; // 当前线程的条件变量
        private Condition nextCondition; // 下一个线程的条件变量

        public PrintTask(int threadNo, Condition currentCondition, Condition nextCondition) {
            this.threadNo = threadNo;
            this.currentCondition = currentCondition;
            this.nextCondition = nextCondition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                while (true) {
                    // 当 number 不等于当前线程编号时，等待
                    // while (number % 3 != threadNo % 3) {
                        currentCondition.await();
                    // }
                    // 打印当前线程编号
                    System.out.print(threadNo);
                    // 修改 number 的值
                    number++;
                    // 唤醒下一个线程
                    nextCondition.signal();
                    // 防止当前线程过多占用资源，主动让出锁
                    // currentCondition.await();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}