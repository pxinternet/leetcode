package leetCode;

public class LC55canJump {

    public boolean canJump(int[] nums) {
        //感觉要用辅助数组
        int n = nums.length;
        int startIndex = n - 1;

        for (int i = startIndex; i >= 0; i--) {
            if (nums[i] >= startIndex - i) {
                startIndex = i;
            }
        }
        if (startIndex == 0 ) return true;
        return false;
    }

    public static void main(String[] args) {
        LC55canJump lc55canJump = new LC55canJump();
        int[] nums = new int[]{3,2,1,0,4};

        boolean res = lc55canJump.canJump(nums);

        System.out.println(res);
    }


    public boolean canJumpGreedy(int[] nums) {
        int n = nums.length;
        int rightMost = 0;

        for (int i = 0; i < rightMost; i++) {
            rightMost = Math.max(rightMost, i +nums[i]);
            if (rightMost >= n - 1) {
                return true;
            }
        }
        return false;
    }
}
