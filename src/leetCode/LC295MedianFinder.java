package leetCode;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * LC295 - 数据流的中位数
 *
 * 题目概要：支持 addNum 和 findMedian，找当前数据流的中位数。
 *
 * 解法说明：双堆。maxHeap 存较小的一半，minHeap 存较大的一半；保持 |maxHeap|-|minHeap|<=1，
 * 中位数即堆顶或两堆顶平均。
 *
 * 时间复杂度：addNum O(log n)，findMedian O(1)
 * 空间复杂度：O(n)
 */
public class LC295MedianFinder {

    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

    public LC295MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();

    }

    public void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }

        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }

}
