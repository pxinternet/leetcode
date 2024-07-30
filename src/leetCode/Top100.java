package leetCode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import leetCode.ListNode;

import leetCode.ListNode;

import java.util.ArrayList;
import java.util.Arrays;

public class Top100 {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;

        int[] result = new int[n - k + 1];

        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offer(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peek()];
            }
        }

        return result;
    }

    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();

        if (m < n) return "";


        int minLength = m + 1;
        int minIndex = 0;

        Map<Character, Integer> map = new HashMap<>();

        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;

        int counter = map.size();

        while (right < m) {

            char c = s.charAt(right);

            if (map.containsKey(c)) {

                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) counter--;

            }
            right++;

            while (counter == 0) {
                char tmp = s.charAt(left);

                if (map.containsKey(tmp)) {
                    map.put(tmp, map.get(tmp) + 1);
                    if (map.get(tmp) > 0) counter++;
                }

                if (right - left < minLength) {
                    minIndex = left;
                    minLength = right - left;
                }
                left++;
            }

        }
        return s.length() < minLength ? "" : s.substring(minIndex, minIndex + minLength);

    }


    public int longestValidParentheses(String s) {
        int left = 0, right = 0, maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                maxLength = Math.max(maxLength, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }

        left = right = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ')') {
                right++;
            } else {
                left++;
            }
            if  (left == right) {
                maxLength = Math.max(maxLength, 2 * left) {}
            } else if (left > right) {
                left = right = 0;
            }
        }
        return maxLength;
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;

        return (getKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + getKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;

    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);

        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode node = head;

        while (node != null && node.next != null) {
            ListNode temp = node.next;

            pre.next = node.next;
            node.next = node.next.next;
            temp.next = node;
            pre = node;
            node = node.next;
        }

        return dummy.next;
    }

    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();

        int freshCOunt = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] {i, j});
                } else if (grid[i][j] == 1) {
                    freshCOunt++;
                }
            }
        }

        int[][] directories = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        int time = 0;

        while (!queue.isEmpty() && freshCOunt > 0) {
            time++;
            int size = queue.size();

            for(int i = 0; i < size; i++) {
                int[] point = queue.poll();

                for (int[] dir : directories) {
                    int x = point[0] + dir[0];
                    int y = point[1] + dir[1];

                    if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
                        continue;
                    }

                    if (grid[x][y] == 1) {
                        grid[x][y] = 2;
                        freshCOunt--;
                        queue.offer(new int[] {x, y});
                    }

                        }
            }
        }
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(nums[0], 0);

        for (int i = 1; i < nums.length; i++) {
            int k = target - nums[i];
            int v = map.getOrDefault(k, -1);
            if (v != -1) {
                int[] res = new int[2];
                res[0] = v;
                res[1] = i;
                return res;
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();

        Map<String, List<String>> groupMap = new HashMap<>();

        for  (String str : strs) {
            String key = sortStrForKey(str);

            List<String> line = groupMap.get(key);
            if (line != null) {
                line.add(str);
            } else {
                line = new ArrayList<>();
                line.add(str);
                groupMap.put(key, line);
            }
        }

        for (List<String> value : groupMap.values()) {
            res.add(value);
        }
        return res;
    }

    String sortStrForKey(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }


}


