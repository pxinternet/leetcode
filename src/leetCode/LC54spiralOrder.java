package leetCode;

import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.List;

public class LC54spiralOrder {

    public List<Integer> spiralOrder(int[][] matrix) {
        int top = 0;
        int left = 0;
        int right = matrix[0].length - 1;
        int bottom = matrix.length - 1;

        List<Integer> res = new ArrayList<>();
        while(top <= bottom && left <= right) {

            System.out.println("==========================================================================");
            System.out.println("Top: " + top + ", Bottom: " + bottom + ", Left: " + left + ", Right: " + right);
            System.out.println(res);


            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;
            System.out.println("Top: " + top + ", Bottom: " + bottom + ", Left: " + left + ", Right: " + right);
            System.out.println(res);



            if(top > bottom) break;
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;

            System.out.println("Top: " + top + ", Bottom: " + bottom + ", Left: " + left + ", Right: " + right);
            System.out.println(res);

            if (left > right) break;

            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
            }
            bottom--;
            System.out.println("Top: " + top + ", Bottom: " + bottom + ", Left: " + left + ", Right: " + right);
            System.out.println(res);


            if (top > bottom) break;

            for(int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            left++;
            System.out.println("Top: " + top + ", Bottom: " + bottom + ", Left: " + left + ", Right: " + right);
            System.out.println(res);

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
