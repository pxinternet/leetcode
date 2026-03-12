package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * TopKFrequentElements - 前 K 个高频元素（LeetCode 347）
 *
 * 题目（概要）：给定整数数组，返回出现频率最高的 k 个元素，顺序任意。
 *
 * 算法原理：
 * - 堆：维护大小为 k 的最小堆，按频率比较；堆顶为第 k 大频率，超过 k 则弹出堆顶。
 * - 桶排序：buckets[freq]=元素列表，从高 freq 往低取满 k 个。
 *
 * 核心逻辑（分步）：
 * - topKFrequentHeap：统计词频；遍历 entry，入堆，size>k 则 poll；最后堆中 k 个即为答案。
 * - topKFrequentBuck：buckets[freq] 收集；从 buckets.length-1 递减，依次取元素直到满 k。
 *
 * 关键洞察：最小堆堆顶为当前第 k 大，新元素大于堆顶则替换；桶按频次分桶。
 *
 * 时间复杂度：堆 O(n log k)；桶 O(n)
 * 空间复杂度：O(n)
 *
 * 示例：nums=[1,1,1,2,2,3], k=2 → [1,2]
 */
public class TopKFrequentElements {

    public static List<Integer> topKFrequentHeap(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            frequencyMap.put(nums[i], frequencyMap.getOrDefault(nums[i], 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        List<Integer> topK = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            topK.add(minHeap.poll().getKey());

        }

        return topK;
    }

    public static List<Integer> topKFrequetBuck(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        List<Integer>[] buckets = new List[nums.length + 1];

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int frequency = entry.getValue();
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(entry.getKey());
        }

        List<Integer> topK = new ArrayList<>();

        for (int i = buckets.length - 1; i >= 0 && topK.size() < k; i--) {
            if (buckets[i] != null) {
                topK.addAll(buckets[i]);
            }
        }
        return topK.subList(0, k);

    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];

        swap(nums, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        swap(nums, storeIndex, right);
        return storeIndex;

    }

    private static int quickSelect(int[] nums, int left, int right, int k) {

        if (left == right) {
            return left;
        }

        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);

        pivotIndex = partition(nums, left, right, pivotIndex);

        if (pivotIndex == k) {
            return nums[pivotIndex];
        } else if (pivotIndex < k) {
            return quickSelect(nums, pivotIndex + 1, right, k);
        } else {
            return quickSelect(nums, left, pivotIndex - 1, k);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 2, 4, 5, 9, 3, 1, 7, 6, 8, 0 };

        int result = quickSelect(nums, 0, nums.length - 1, 9);
        System.out.println("The 4th smallest element is: " + result);

        System.out.println("Array after quickSelect: " + Arrays.toString(nums));

    }
}
