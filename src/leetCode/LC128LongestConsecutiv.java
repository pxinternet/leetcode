package leetCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.security.spec.MGF1ParameterSpec;

import java.lang.reflect.Array;

import java.lang.reflect.Array;

import java.util.concurrent.CountDownLatch;

import java.nio.MappedByteBuffer;

import java.security.InvalidAlgorithmParameterException;

public class LC128LongestConsecutiv {

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (int num : nums) {
            if (map.getOrDefault(num, 0) != 0) {
                continue;
            }

            int left = map.getOrDefault(num- 1, 0);
            int right = map.getOrDefault(num + 1, 0);

            int tmp = left + right + 1;
            if (map.getOrDefault(num - left,   0) != 0) {
                map.put(num - left, tmp);
            }
            if (map.getOrDefault(num + right, 0) != 0) {
                map.put(num + right, tmp);
            }

            map.put(num, tmp);
            max = Math.max(max, tmp);
        }
        return max;
    }

    public void moveZeros(int[] nums) {
        int left = 0, right = 0;

        while (right < nums.length) {
            if (nums[right] != 0) {
                int tmp  = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;

                left++;
            }
            right++;
        }
    }

    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int maxArea = 0;

        while(left < right) {

            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * right - left);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }

        }

        return maxArea;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < n -2; i++) {
            if (nums[i] > 0) break;

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int s = nums[i] + nums[j] + nums[k];

                if (s > 0) k--;
                else if (s < 0) j++;

                else {
                    List<Integer> ans = new ArrayList<>();
                    ans.add(nums[i]);
                    ans.add(nums[j]);
                    ans.add(nums[k]);
                    res.add(ans);
                    j++;
                    while(j < k && nums[j] == nums[j - 1]) j++;
                    k--;
                    while(j < k && nums[k] == nums[k + 1]) k--;
                }
            }
        }

        return res;

    }

    public int trap(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int leftMax = 0;
        int rightMax = 0;

        int ans = 0;

        while (left <= right) {
            rightMax = Math.max(rightMax, height[right]);
            leftMax = Math.max(leftMax, height[left]);

            if (leftMax > rightMax) {

                ans += rightMax - height[right];
                right--;
            } else {
                ans+= leftMax - height[left];
                left++;
            }


        }

        return ans;
    }


    public int lengthOfLongestSubString(String s) {
        Set<Character> visited = new HashSet<>();
        int end = 0;
        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                visited.remove(s.charAt(i - 1));
            }

            while (end < s.length() && !visited.contains(s.charAt(end))) {
                visited.add(s.charAt(end));
                end++;
            }

            maxLength = Math.max(maxLength, end - i);
        }

        return maxLength;
    }

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

    public int subarraySun(int[] nums, int k) {
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
    }

    public boolean isPalindrome(int x) {

        if (x == 0) return true;

        if (x < 0 || x % 10 == 0) return false;

        int reverseNum = 0;
        while (x > reverseNum) {
            reverseNum = reverseNum * 10 + x % 10;
            if (x == reverseNum) return true;

            x /= 10;

        }

        if (x == reverseNum) return true;
        return false;
    }


    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;

        for (int num : nums) {
            if (map.getOrDefault(num, 0) !=0 ) {
                continue;
            }

            int left = map.getOrDefault(num - 1, 0);
            int right = map.getOrDefault(num + 1, 0);

            int tmp = left + 1 + right;

            if (map.getOrDefault(num - left, 0) != 0) {
                map.put(num - left, tmp);
            }
            if (map.getOrDefault(num + right, 0) != 0) {
                map.put(num + right, tmp);
            }
            map.put(num, tmp);
            max = Math.max(max, tmp);

        }
        return max;
    }


    public int trap(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int leftMax = 0;
        int rightMax = 0;

        int ans = 0;

        while (left <= right) {
            rightMax = Math.max(rightMax, height[right]);
            leftMax = Math.max(leftMax, height[left]);

            if (leftMax > rightMax) {
                ans += rightMax - height[right];
                right--;
            } else {
                ans += leftMax- height[left];
                left++;
            }

        }

        return ans;
    }

    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int maxArea = 0;

        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    public int maxSubArray(int[] nums) {
        int pre = nums[0];

        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            pre = pre < 0 ?  nums[i] : pre + nums[i];

            maxSum = Math.max(pre, maxSum);
        }
        return maxSum;
    }

    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }

        for (int i = 0; i < n; i++) {

            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(num - 1);
            }
        }

        for (int i = 0; i < n; i++) {

            if (nums[i] > 0 ) {
                return i + 1;
            }

        }

        return n + 1;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);

        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = pre;

        while (head != null) {
            end = pre;
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }

            if (end == null) return dummy.next;

            ListNode start = pre.next;
            head = end.next;

            end.next = null;
            ListNode curr = pre.next, tmp;

            ListNode prev = null;

            while (curr != null) {
                tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            pre.next = prev;
            start.next = head;
            pre = start;
        }

        return dummy.next;

    }


    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = pre;

        while (head != null) {
            end = pre;

            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }

            if (end == null) return dummy.next;

            ListNode start = pre.next;
            head = end.next;
            end.next = null;

            ListNode curr = pre.next, tmp;
            ListNode prev = null;

            while (curr != null) {
                tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            pre.next = prev;
            start.next = head;
            pre = start;
        }

        return dummy.next;
    }


    public int[][] merge(int[][] intervals) {
        List<int[]> resList = new ArrayList<>();

        Arrays.sort(intervals, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) return 1;
                if (o1[0] < o2[0]) return -1;
                return 0;
            }


        });

        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int[] interval : intervals) {
            if (end < interval[0]) {
                resList.add(new int[]{start, end});
                start = interval[0];
                end = interval[1];

            } else {
                end = Math.max(end, interval[1]);
            }
        }

        resList.add(new int[]{start, end});

        int[][] ans = new int[resList.size()][];

        for (int i = 0; i< resList.size(); i++) {
            ans[i] = resList.get(i);
        }
        return ans;
    }


    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        int[] ans = new int[n];

        ans[0]= 1;

        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            ans[i] = right * ans[i];
            right = right * nums[i];
        }
        return ans;

    }

}
