package leetCode;

import java.util.HashMap;
import java.util.Map;

public class LC560sum {

    public int subarraySum(int[] nums, int k) {
        int count = 0, pre = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (map.containsKey(pre - k)) {
                count += map.get(pre - k);
            }
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return count;

    }

    public static void main(String[] args) {
        LC560sum lC560sum = new LC560sum();
        int[] nums = new int[]{1, -1, 0};
        int res = lC560sum.subarraySum(nums, 0);
        System.out.println(res);

    }
}
