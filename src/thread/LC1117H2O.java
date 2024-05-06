package thread;

import java.util.concurrent.CyclicBarrier;

public class LC1117H2O {
    private CyclicBarrier barrier = new CyclicBarrier(3);
private int hydrogenCount = 0;

    public LC1117H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        try {
            while (hydrogenCount == 2) {
                Thread.yield();
            }
            hydrogenCount++;
            barrier.await();
            releaseHydrogen.run();
        } catch (Exception e) {
        }
    }


    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        releaseOxygen.run();
    }
}
