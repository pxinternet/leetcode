package interview;

public class Thread123Sync {

    private volatile static int loopNum = 5000;

    private volatile static int currentVal = 1;

    private static final Object lock = new Object();

    private static void cyclePrintUseSynchronized() {
        new Thread(new SynchronizedTask()).start();
        new Thread(new SynchronizedTask()).start();
        new Thread(new SynchronizedTask()).start();
    }

    static class SynchronizedTask implements Runnable {


        @Override
        public void run() {
            while (true) {
                synchronized (Thread123Sync.class) {
                    if (loopNum < 0) {
                        return;
                    }
                    System.out.println(currentVal);
                    if (currentVal == 3) {
                        currentVal = 1;
                    } else {
                        currentVal++;
                    }
                    loopNum--;
                }
            }
        }
    }

    public static void main(String[] args) {
        cyclePrintUseSynchronized();
    }


}
