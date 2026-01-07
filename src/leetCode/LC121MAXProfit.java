package leetCode;

public class LC121MAXProfit {
    // LC121 - Best Time to Buy and Sell Stock I
    // 思路：维护一个最小买入价格下标 leftIndex，遍历每一天计算当前卖出能获得的收益并维护最大值
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
