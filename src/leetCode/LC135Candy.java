package leetCode;

/**
 * LC135 - 分发糖果
 *
 * 题目（概要）：n 个孩子排成一排，ratings[i] 为第 i 个孩子的评分。每个孩子至少 1 颗糖，评分更高的孩子比相邻低分孩子糖果更多。求最少糖果总数。
 *
 * 解法说明：
 * - 核心思想：左右两次遍历。left[i] 为仅考虑左邻时的最少糖果数；right[i] 为仅考虑右邻时。
 * - 每人取 max(left[i], right[i])，既满足左规则也满足右规则。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - left[0]=1；right 从右往左时可取 right[i]=max(right[i+1]+1, 1)，但本题 right[i] 与 left 规则类似，最终取 max
 *
 * 示例：ratings=[1,0,2] → 5（2+1+2）
 */
public class LC135Candy {

    /**
     * 左右两次遍历，取 max(left[i], right[i])
     *
     * @param ratings 评分数组
     * @return 最少糖果总数
     */
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];

        left[0] = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int[] right = new int[n];
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.max(left[i], right[i]);
        }
        return sum;
    }

    public static void main(String[] args) {
        LC135Candy solver = new LC135Candy();
        int[] ratings = new int[]{1, 2, 87, 87, 87, 2, 1};
        System.out.println(solver.candy(ratings));
    }
}
