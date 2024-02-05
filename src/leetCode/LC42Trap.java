package leetCode;

public class LC42Trap {

    public int trap(int[] height) {

        int n = height.length;
        int left = 0;
        int right = n - 1;

        int rightMax = 0;
        int leftMax = 0;
        int ans = 0;

        while(left <= right) {
            rightMax = Math.max(height[right], rightMax);
            leftMax = Math.max(height[left], leftMax);

            if (leftMax < rightMax) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }

        return ans;
    }

    public int trapDp(int[] height) {
        int n = height.length;

        int[] dp = new int[n];
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        maxLeft[0] = height[0];
        maxRight[n - 1] = height[n -1];

        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i-1],  height[i]);
        }

        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }
        return ans;
    }

}
