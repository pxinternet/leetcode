package round6;

import java.awt.List;
import java.util.PriorityQueue;

public class SmallestRange1 {

    public static int[] smallestRange(List<List<Integer>> nums) {

        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int max = Integer.MIN_VALUE;

        int start = -1000, end = 1000;

        for (int i = 0; i < nums.size(); i++) {
            int value = nums.get(i).get(0);
            minHeap.offer(new int[] { value, i, 0 });
            max = Math.max(max, value);
        }

        while (minHeap.size() == nums.size()) {
            int[] current = minHeap.poll();
            int minValue = current[0];
            int rowIndex = current[1];
            int colIndex = current[2];

            if (max - minValue < end - start || (max - minValue == end - start && minValue < start)) {
                start = minValue;
                end = max;
            }

            if (colIndex + 1 < nums.get(rowIndex).size()) {
                int nextValue = nums.get(rowIndex).get(colIndex + 1);
                minHeap.offer(new int[] { nextValue, rowIndex, colIndex + 1 });
                max = Math.max(max, nextValue);
            }
        }

        return new int[]  {start, end };
    }

}
