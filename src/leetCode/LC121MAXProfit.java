package leetCode;

public class LC121MAXProfit {
    public int maxProfit(int[] prices) {

        int res = 0;

        int leftIndex = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[leftIndex])
                leftIndex = i;

            res = Math.max(res, prices[i] - prices[leftIndex]);

        }

        return res;
    }

    public static void main(String[] args) {
        LC121MAXProfit lc121MAXProfit = new LC121MAXProfit();
//        int[] arr = new int[] {7, 1, 5, 3, 6, 4};
        int[] arr = new int[] {7,6,4,3,1};
        int res = lc121MAXProfit.maxProfit(arr);
        System.out.println(res);
    }
}
