package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC149 - 直线上最多的点数
 *
 * 题目概要：给定平面上若干点，求同一条直线上点的最大数量。
 *
 * 解法说明：枚举基准点 i，对每个 i 统计与 i 共线的其他点数量。用斜率（化简后）作为 key：
 * - 用 (x,y) 差值的 GCD 化简，避免浮点误差，且 (1,1) 与 (2,2) 归一为同一方向
 * - 重叠点（与 i 相同）单独计数 overlap
 * - 结果 = 同斜率最大数量 + overlap + 1（基准点自身）
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 */
public class LC149maxPoints {

    public int maxPoints(int[][] points) {
        if (points.length < 3) return points.length;

        int max = 0;
        for (int i = 0; i < points.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            int overlap = 0;
            int lineMax = 0;

            for (int j = i + 1; j < points.length; j++) {
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];

                if (x == 0 && y == 0) {
                    overlap++;
                    continue;
                }

                int gcd = generateGCD(x, y);
                if (gcd != 0) {
                    x /= gcd;
                    y /= gcd;
                }

                String key = x + "@" + y;
                int count = map.getOrDefault(key, 0) + 1;
                map.put(key, count);
                lineMax = Math.max(lineMax, count);
            }
            max = Math.max(max, lineMax + overlap + 1);
        }
        return max;
    }

    private int generateGCD(int a, int b) {
        if (b == 0) return a;
        return generateGCD(b, a % b);
    }
}
