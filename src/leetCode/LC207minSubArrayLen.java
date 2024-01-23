package leetCode;

public class LC207minSubArrayLen {

    public int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        int right = 0;

        int n = nums.length;

        int ans = n + 1;
        int tmp =0;
        tmp += nums[left];

        while(right < n) {
            System.out.println("tmp = " + tmp + " left = " + left + " right = " + right + " ans = " + ans);
            if (tmp >= target) {
                ans = Math.min(ans, right - left + 1);
                if (ans == 1) return 1;
                tmp -= nums[left];
                left++;
            } else {
                right++;
                if (right < n) {
                    tmp += nums[right];
                }
            }
        }

        return ans > n ? 0 : ans;
    }

    public static void main(String[] args) {
        LC207minSubArrayLen lc207minSubArrayLen = new LC207minSubArrayLen();

        int[] nums = new int[]{2,3,1,2,4,3};
        int target = 7;

        int res = lc207minSubArrayLen.minSubArrayLen(7, nums);

        System.out.println(res);
    }

}
