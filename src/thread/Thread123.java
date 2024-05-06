package interview;

public class Thread123 {

    private volatile static int loopNum = 500;

    private volatile static int currentValue = 1;

    private static void cyclePrintUserJoin() {
        Thread preThread = null;
        for (int i =0; i < loopNum; i++) {
            preThread = new Thread(new JoinTask(preThread));
            preThread.start();
        }
    }

    static class JoinTask implements Runnable {
        private final Thread preThread;;

        public JoinTask(Thread preThread) {
            this.preThread = preThread;
        }

        @Override
        public void run() {
            if (preThread != null) {
                try {
                    preThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " " + currentValue);
            if (currentValue == 3) {
                currentValue = 1;
            } else {
                currentValue++;
            }

        }
    }

    public static void main(String[] args) {
        cyclePrintUserJoin();
    }
}
