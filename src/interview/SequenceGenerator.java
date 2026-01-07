package interview;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class SequenceGenerator {

    public static int findNth(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        Set<Integer> seen = new HashSet<>();

        minHeap.add(1);
        seen.add(1);

        int current = 1;

        for (int i = 1; i <= n; i++) {
            current = minHeap.poll();

            int next1 = current * 2 + 1;
            int next2 = current * 3 + 1;

            if (seen.add(next1)) {
                minHeap.add(next1);
            }
            if (seen.add(next2)) {
                minHeap.add(next2);
            }
        }
        return current;
    }

    public static void main(String[] args) {
        System.out.println(findNth(10));
    }

}
