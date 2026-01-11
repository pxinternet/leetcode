package round6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * smallestRange: 寻找覆盖 k 个列表中每个列表至少一个元素的最小闭区间 [a,b]
 *
 * 说明（中文注释）：
 * - 算法思路：把所有列表元素平坦化为 (value, listIndex) 对并排序，然后在排序后的数组上
 *   使用滑动窗口（双指针）来寻找包含每个列表至少一个元素的最短区间。维护一个 count[]
 *   记录当前窗口内各列表出现的次数，用 covered 表示当前窗口覆盖了多少个不同的列表。
 * - 复杂度：时间 O(N log N)（N 为所有元素总数，来自排序），滑动窗口部分 O(N)。空间 O(N + k)。
 * - 改进与健壮性：添加了参数校验、稳健的最小区间初始化（使用 found 标志），避免魔法常量。
 *
 * 正确性要点：对于排序后的所有元素，以每个右端 end 为界，通过向右扩展然后尽可能收缩左端
 * 可以得到以该 end 为右端覆盖所有列表的最小左端；遍历所有 end 可得到全局最小区间。
 */
public class SmallestRangeQuick {

    public static int[] smallestRange(List<List<Integer>> nums) {
        // 参数校验：如果输入为空或包含空子列表，则无法从每个列表中至少取一个元素，返回空数组表示无解
        if (nums == null || nums.isEmpty()) {
            return new int[0];
        }
        for (List<Integer> list : nums) {
            if (list == null || list.isEmpty()) {
                return new int[0];
            }
        }

        // 将所有元素平坦化为 (value, rowIndex)
        List<int[]> allElements = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            for (int val : nums.get(i)) {
                allElements.add(new int[] { val, i });
            }
        }

        // 如果没有元素，直接返回无解
        if (allElements.isEmpty()) {
            return new int[0];
        }

        // 按值排序，便于用连续区间表示数值区间
        allElements.sort(Comparator.comparingInt(a -> a[0]));

        // count[row] 表示当前窗口中来自第 row 个列表的元素数量
        int k = nums.size();
        int[] count = new int[k];
        int start = 0, end = 0, covered = 0;

        // 使用稳健的初始化：用 found 标志判断是否已有初次合法区间
        int minStart = 0, minEnd = Integer.MAX_VALUE;
        boolean found = false;

        // 滑动窗口遍历：end 向右扩展，直到窗口包含所有 k 个列表，然后收缩 start
        while (end < allElements.size()) {
            int[] current = allElements.get(end); // 当前被加入窗口的元素
            int rowIndex = current[1];
            count[rowIndex]++;
            if (count[rowIndex] == 1) {
                // 当某个列表第一次出现在窗口中时，covered 增加
                covered++;
            }

            // 当窗口已覆盖所有列表（每个列表至少有一个元素）时，尝试收缩左端使窗口尽可能小
            while (covered == k) {
                int startValue = allElements.get(start)[0];
                int endValue = allElements.get(end)[0];

                // 更新最小区间：按长度优先，长度相同时优先左端更小的区间（与原实现保持一致）
                if (!found || endValue - startValue < minEnd - minStart ||
                        (endValue - startValue == minEnd - minStart && startValue < minStart)) {
                    minStart = startValue;
                    minEnd = endValue;
                    found = true;
                }

                // 尝试把左端元素移出窗口
                int startRow = allElements.get(start)[1];
                count[startRow]--;
                if (count[startRow] == 0) {
                    // 若某个列表的计数变为 0，窗口不再覆盖全部列表
                    covered--;
                }
                start++;
            }
            end++;
        }

        // 如果未找到任何合法区间，返回空数组表示无解
        if (!found) {
            return new int[0];
        }

        return new int[] { minStart, minEnd };
    }

    // 简单的 main 演示，使用 LeetCode 示例并打印结果
    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        nums.add(Arrays.asList(4, 10, 15, 24, 26));
        nums.add(Arrays.asList(0, 9, 12, 20));
        nums.add(Arrays.asList(5, 18, 22, 30));

        int[] range = smallestRange(nums);
        if (range.length == 0) {
            System.out.println("No valid range");
        } else {
            System.out.println("smallest range: [" + range[0] + ", " + range[1] + "]");
        }

        // 另外一个示例，边界情况
        List<List<Integer>> nums2 = new ArrayList<>();
        nums2.add(Arrays.asList(1));
        nums2.add(Arrays.asList(2));
        int[] r2 = smallestRange(nums2);
        System.out.println("example2: " + (r2.length == 0 ? "no" : Arrays.toString(r2)));
    }

}
