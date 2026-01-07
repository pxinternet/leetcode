package thread;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicTest {

    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    private CyclicBarrier cb = new CyclicBarrier(10, () -> {
        int result = 0;
        Set<String> set = map.keySet();
        for (String s : set) {
            //
        }
        // dosth;
    });

    public void count() {
        for (int i = 0; i < 5; i++) {
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        cb.await();
                    } catch (Exception e) {
                    }
                }

            });
    }

}