package leetCode;

import java.util.Arrays;

public class LC135Candy {

    public int candy(int[] ratings) {
        //找一下左右两边的最值
        int n = ratings.length;

        int[] left = new int[n];

        left[0] = 1;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i-1] + 1;
            } else {
                left[i] = 1;
            }
        }
        System.out.println("rating = " + Arrays.toString(ratings));
        System.out.println("left  = " + Arrays.toString(left));


        int[] right = new int[n];
        right[n -1] = left[n -1];

        for (int i = n -2; i >= 0; i--) {
            if(ratings[i] > ratings[i+1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }

        System.out.println("right  = " + Arrays.toString(right));

        int[] candy = new int[n];
        for (int i = 0; i < n; i++) {
            candy[i] = Math.max(left[i], right[i]);
        }


        System.out.println("rating = " + Arrays.toString(ratings));
        System.out.println("candy  = " + Arrays.toString(candy));



        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += Math.max(left[i], right[i]);
        }

        return sum;
    }

    public static void main(String[] args) {
        LC135Candy lc135Candy = new LC135Candy();
        int[] ratings = new int[]{1,2,87,87,87,2,1};

        int res = lc135Candy.candy(ratings);

        System.out.println(res);
    }
}
