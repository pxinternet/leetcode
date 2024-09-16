package round3;

import java.util.Arrays;
import java.util.Comparator;

public class TaskScheduler {

    public static int minCompletionTime(int[] tasks, int k) {

        if (tasks == null || tasks.length == 0)
            return 0;
        if (k <= 0)
            return -1;

        int left = Arrays.stream(tasks).max().getAsInt();
        int right = Arrays.stream(tasks).sum();

        // Arrays.sort(tasks, Comparator.comparingInt(i -> -i));

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canFinished(tasks, k, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }

        }
        return left;
    }

    private static boolean canFinished(int[] tasks, int k, int limit) {
        int requireProcess = 1;
        int currentLoad = 0;

        for (int task : tasks) {
            if (task > limit)
                return false;

            if (currentLoad + task <= limit) {
                currentLoad += task;
            } else {
                requireProcess++;
                currentLoad = task;
                if (requireProcess > k)
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] array = { "apple", "orange", "banana", "pear" };
        int[] tasks = { 1, 4, 5, 6, 2, 34, 2, 3, 1 };

        Integer[] newArr = Arrays.stream(tasks).boxed().toArray(Integer[]::new);
        // 自定义排序，按字母顺序逆向排序
        Arrays.sort(newArr, Comparator.reverseOrder());

        System.out.println(Arrays.toString(newArr));
    }
}
