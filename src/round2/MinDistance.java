package round2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 题目：在一个二维网格上若 grid[i][j]==1 表示有人，需要找到一个聚会点使得所有人的总行走距离（曼哈顿距离）最小。
 *
 * 证明/直觉（写在代码中以便查阅）：
 *
 * 一、曼哈顿距离的可分解性
 *   对任何两点 (x1,y1) 与 (x,y)，曼哈顿距离为 |x1-x| + |y1-y|。
 *   因此 n 个人到同一点 (x,y) 的总距离为
 *     F(x,y) = Sum_i (|x_i - x| + |y_i - y|) = Sum_i |x_i - x| + Sum_i |y_i - y|.
 *   可以看到 F(x,y) 是 x 和 y 的可分离函数：选择最优的 x 和 y 可以分别独立地优化两部分。
 *
 * 二、1D 问题：为什么中位数最小化 Sum_i |a_i - z|
 *   方法 A（计数论证，离散）：
 *     令 f(z) = Sum_i |a_i - z|。当 z 向右移动一步（z -> z+1）时，f 的变化量 = (#points 左于 z) * (+1) + (#points 在 z 位置或右于 z) * (-1) —— 更直观地可写为
 *       f(z+1) - f(z) = count(a_i <= z) - count(a_i > z) = (#<=z) - (#>z).
 *     因此，f 在 z 右移时的单步增量由左侧点与右侧点的数量差决定。当左侧点数小于右侧点数时，f 会减少，说明最优点需使左右两侧点数尽量平衡。
 *     中位数的定义恰是使得左右两侧点数不超过一半，从而达到或进入局部最小点。由离散单调性可得中位数处即为全局最小。
 *
 *   方法 B（凸性/微分观）：
 *     f(z) 是关于 z 的凸函数（绝对值和的凸性）。任何凸函数在全局最小点处的一阶导（或次梯度）包含 0。对绝对值和，次梯度为
 *       g(z) = Sum_i sign(z - a_i)（注意 sign(0) 的取值范围使中位数区间成为次梯度包含 0 的解），
 *     因此中位数（或任一中位数区间）是使次梯度包含 0 的点，从而为最小点。
 *
 *   结论：在一维上，任何中位数（对于偶数个点，中位数可以是区间内任一点）都使 Sum |a_i - z| 达到最小值。
 *
 * 三、结合两点：二维问题的最优解为 (median_x, median_y)
 *   由于 F(x,y) = Fx(x) + Fy(y)，Fx 与 Fy 可独立最小化，因此 x 取所有 x_i 的中位数，y 取所有 y_i 的中位数。
 *
 * 实现细节与复杂度
 * - 我们收集所有为 1 的单元格的行索引列表 rows 和列索引列表 cols（k 为 1 的个数）。
 * - 分别对 rows 与 cols 取中位数并求绝对差和：排序方式为 O(k log k)，整体复杂度 O(k log k)。
 * - 备注：rows 在按行扫描时自然是有序的，但为代码鲁棒性我们仍对其排序；cols 通常无序。
 */
public class MinDistance {

    public int minTotalDistance(int[][] grid) {
        // 输入校验：处理 null 或空网格的情况
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();

        // 收集所有人的行坐标（按行扫描）
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    rows.add(i);
                }
            }
        }

        // 收集所有人的列坐标
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    cols.add(j);
                }
            }
        }

        // 若没有人，则总距离为 0
        if (rows.isEmpty()) {
            return 0;
        }

        // 找到行与列的中位数
        int medianRow = findMedian(rows);
        int medianCol = findMedian(cols);

        // 计算曼哈顿总距离（行距离 + 列距离）
        return calculateDistance(cols, medianCol) + calculateDistance(rows, medianRow);
    }

    // 找到列表的中位数（若元素个数为偶数，本实现取高位中位数 list.get(size/2)，此处选择高位中位数是合理的——
    // 对于偶数个点，任意处于两个中位数之间的点都是最优，故选其中任意一个都可得到最优值）
    private int findMedian(List<Integer> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("list must not be empty");
        }
        Collections.sort(list);
        int mid = list.size() / 2; // 若 size 为偶数，则取上中位数
        return list.get(mid);
    }

    // 计算到 median 的绝对距离和（1D）
    private int calculateDistance(List<Integer> points, int median) {
        int distance = 0;
        for (int point : points) {
            distance += Math.abs(point - median);
        }
        return distance;
    }

}
