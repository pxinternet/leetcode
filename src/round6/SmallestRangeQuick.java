package round6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmallestRangeQuick {

    public static int[] smallestRange(List<List<Integer>> nums) {

        List<int[]> allElements = new ArrayList<>();

        for (int i = 0; i < nums.size(); i++) {
            for (int val : nums.get(i)) {
                allElements.add(new int[] { val, i });
            }
        }

        allElements.sort(Comparator.comparingInt(a -> a[0]));

        int[] count = new int[nums.size()];
        int start = 0, end = 0, covered = 0;

        int minStart = -100000, minEnd = 100000;

        while (end < allElements.size()) {
            int[] current = allElements.get(end);
            int rowIndex = current[1];
            count[rowIndex]++;
            if (count[rowIndex] == 1) {
                covered++;
            }

            while (covered == nums.size()) {
                int startValue = allElements.get(start)[0];
                int endValue = allElements.get(end)[0];

                if (endValue - startValue < minEnd - minStart ||
                        (endValue - startValue == minEnd - minStart && startValue < minStart)) {
                    minStart = startValue;
                    minEnd = endValue;
                }

                int startRow = allElements.get(start)[1];
                count[startRow]--;
                if (count[startRow] == 0) {
                    covered--;
                }
                start++;
            }
            end++;
        }

        return new int[] { minStart, minEnd};

    }

}
