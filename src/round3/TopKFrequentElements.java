package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class  TopKFrequentElements {

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

        for (int i = buckets.length - 1; i >= 0 && topK.size() < k; i++) {
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
