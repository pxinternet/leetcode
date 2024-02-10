package leetCode;

import java.util.HashMap;
import java.util.Map;

public class LC149maxPoints {

    public int maxPoints(int[][] points) {
        if (points.length < 3) {
            return points.length;
        }

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
                int count = map.getOrDefault(key, 0);
                count++;
                map.put(key, count);

                lineMax = Math.max(lineMax, count);
            }

            max = Math.max(max, lineMax + overlap + 1);
        }
        return max;
    }

    private int generateGCD(int a, int b) {
        if (b == 0) return a;
        else return generateGCD(b, a % b);
    }
}
