package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC54 - 螺旋矩阵
 *
 * 题目概要：按顺时针螺旋顺序返回矩阵所有元素。
 *
 * 解法说明：用 top/bottom/left/right 四边界模拟一圈圈收缩。每圈按 上→右→下→左 依次遍历，
 * 每遍历一条边后收缩对应边界并检查是否结束。
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(1)（不含输出）
 */
public class LC54spiralOrder {

    public List<Integer> spiralOrder(int[][] matrix) {
        int top = 0, left = 0;
        int right = matrix[0].length - 1;
        int bottom = matrix.length - 1;
        List<Integer> res = new ArrayList<>();

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) res.add(matrix[top][i]);
            top++;
            if (top > bottom) break;

            for (int i = top; i <= bottom; i++) res.add(matrix[i][right]);
            right--;
            if (left > right) break;

            for (int i = right; i >= left; i--) res.add(matrix[bottom][i]);
            bottom--;
            if (top > bottom) break;

            for (int i = bottom; i >= top; i--) res.add(matrix[i][left]);
            left++;
        }
        return res;
    }

    public static void main(String[] args) {
        LC54spiralOrder lc54spiralOrder = new LC54spiralOrder();

        int[][] matrix = new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12}};

        List<Integer> res = lc54spiralOrder.spiralOrder(matrix);

        System.out.println(res);
    }
}
