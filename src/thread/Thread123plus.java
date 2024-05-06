package thread;


public class Thread123plus {

    private static int loopNum = 5000;

    private static int currentValue = 1;

    private static final Object lockObj = new Object();

    static class SynchronizedTaskPlus implements Runnable {

        private final int target;

        public SynchronizedTaskPlus(int target) {
            this.target = target;
        }


        @Override
        public void run() {

            while(true) {
                synchronized (lockObj) {
                    if (loopNum < 0) {
                        return;
                    }
                    if (currentValue != target) {
                        try {
                            lockObj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(currentValue);
                    if (currentValue == 3) {
                        currentValue = 1;
                    } else {
                        currentValue++;
                    }
                    loopNum--;
                    lockObj.notifyAll();
                }
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new SynchronizedTaskPlus(1)).start();
        new Thread(new SynchronizedTaskPlus(2)).start();
        new Thread(new SynchronizedTaskPlus(3)).start();
    }
}
