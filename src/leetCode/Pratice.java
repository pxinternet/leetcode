package

import java.util.Collections;

import javax.print.attribute.standard.QueuedJobCount;

import java.awt.image.IndexColorModel;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import java.util.Arrays;
import java.util.Deque;

import org.w3c.dom.NamedNodeMap;

import java.util.spi.CurrencyNameProvider;

import java.time.temporal.IsoFields;

import java.util.PrimitiveIterator;

leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import leetCode.ListNode;

import leetCode.ListNode;

import java.util.function.DoubleUnaryOperator;

import javax.sound.midi.Track;

import java.util.Set;
import java.util.TreeMap;
import java.io.InputStream;

import java.util.Arrays;

import java.security.DrbgParameters;

public class Pratice {

    public double findMedianSortedArray(int[] nums1, int[] nums2) {

        int m = nums1.length;
        int n = nums2.length;

        int left = (m + n + 1) / 2;
        int right = (m + n + 2) /2;

        return (getKth(nums1, 0, m -1, nums2, 0, n - 1, left) + getKth(nums1, 0, m -1, nums2, 0, n - 1, right)) / 2.0;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);

        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k /2) + 1;
        int j = start2 + Math.min(len2, k / 2) + 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 2; j <= n; j+= 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j-2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j-1) == '*') {
                    dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.'));
                } else {
                    dp[i][j] = dp[i -1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                }
            }
        }

        return dp[m][n];
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        PriorityQueue<ListNode> queue = new PriorityQueue<> (
            (a, b) -> a.val - b.val;
        );

        for (ListNode node : lists) {
            if (node != null) {
                queue.offer(node);
            }
        }

        while(!queue.isEmpty()) {
            ListNode temp = queue.poll();
            tail.next = temp;
            tail = tail.next;

            if (temp.next != null) {
                queue.offer(temp.next);
            }
        }


        return dummy.next;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);

        dummy.next = head;

        ListNode pre = dummy;
        ListNdoe end = pre;

        while (head != null) {
            end = pre;
            for (int i = 0; i < k && end != null; i++) end = end.next;

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

    public List<Integer> findSubString(String s, String[] words) {
        List<Integer> res = new ArrayList<>();


        Map<String, Integer> wordsCounts = new HashMap<>();

        int wordLength = words[0].length();

        int n = s.length();

        int sum = 0;

        for (String word: words) {
            sum += word.length();
            wordsCounts.put(word, wordsCounts.getOrDefault(word, 0) + 1);
        }

        if (sum > s.length()) return res;

        Map<String, Integer> wordsMap = new HashMap<>();

        String[] subStrings = new String[n - sum + 1];

        for (int i = 0; i < n - sum + 1; i++) {
            subStrings[i] = s.substring(i, i + sum);
        }

        for (int i = 0; i < subStrings.length; i++) {
            wordsMap.clear();
            int j = 0;
            while (j < words.length) {
                String word = subStrings[i].substring(j * wordLength, (j + 1) * wordLength);
                if (wordsCounts.containsKey(word)) {
                    int counts = wordsCounts.getOrDefault(word, 0);
                    int currentCounts = wordsMap.getOrDefault(word, 0);

                    if (counts > 0 && counts > currentCounts) {
                        wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                j++;
                if (j == words.length) {
                    res.add(i);
                }
            }
            return res;
        }




    }


    public int removeDuplicates(int[] nums) {
        int n = nums.length;

        if (n <= 2) {
            return n;
        }

        int slow = 2, fast = 2;

        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;

    }


    //dp[i][j] 为以 i,j为右下角的边长
    public int maxmalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        int maxLength = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j -1], dp[i][j -1])) + 1;
                    }
                }
                maxLength = Math.max(maxLength, dp[i][j]);
        }
        return maxLength * maxLength;
    }


    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m+1][n+1];

        dp[0][0] = 0;

        for (int i = 1; i < m + 1; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i < n + 1; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[m][n];

    }

    public int maxProfit(int[] prices) {

        int n = prices.length;

        int  buy1 = -prices[0];
        int sell1 = 0;
        int buy2 = -prices[0];
        int sell2 = 0;

        for (int i = 1; i < n; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }

        return sell2;
    }


    public int maxProfit(int k, int[] prices) {

        if (prices.length == 0) {
            return 0;
        }

        if (k >= prices.length / 2) {
            int maxProfit = 0;

            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        int[][] dp = new int[k + 1][prices.length];

        for (int i = 1; i <= k; i++) {

            int buyMax = dp[i - 1][0] - prices[0];

            for (int j = 1; j < prices.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + buyMax);
                buyMax = Math.max(buyMax, dp[i - 1][j] - prices[j]);
            }
        }

        return dp[k][prices.length - 1];
    }

    public String longestPalindrome(String s) {
        int n = s.length();

        int start = 0;
        int end = 0;

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            int len1 = expandfromCenter(s, i, i);
            int len2 = expandfromCenter(s, i, i + 1);

            int len = Math.max(len1, len2);

            if (maxLength < len) {
                maxLength = len;
                start = i - (len - 1)/2;
                end = i + (len) / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandfromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return right - left + 1;

    }


    public int minimumTotal(List<List<Integer>> triangle) {

        int minRes = Integer.MAX_VALUE;
        int n = triangle.size();

        int[] dp = new int[n];

        dp[0] = triangle.get(0).get(0);

        for (int i = 0; i < n; i++) {
            dp[i] = dp[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + triangle.get(i).get(j);
            }
            dp[0] = dp[0] + triangle.get(i).get(0);

        }



        return minRes;

    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] dp = new int[n][n];

        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
            for (int j = 1; j <i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(0);
            }
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }

        int minRes = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[n - 1][i]);
        }
        return minRes;
    }


    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];

    }


    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m ; i++) {
            if (obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] == 1) {
                break;
            }
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];


    }

    public boolean isInterLeave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();

        int s = s3.length();

        if (m + n != s) return false;

        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && (s1.charAt(i - 1) == s3.charAt(i - 1));
        }

        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] && (s2.charAt(j - 1) == s3.charAt(j - 1));
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dp[i][j] =
                    (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                    ||
                    (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public int climbStairs(int n) {
        if (n < 3) return n;
        int[] f = new int[n + 1];
        f[1] = 1;
        f[2] = 1;
        for (int i = 3; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f[n];
    }



    //感觉动态规划还是没有消化的太好
    public int rob(int[] nums) {
        int n  = nums.length;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[1];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i -1]);
        }
        return dp[n];
    }

    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;

        int max = amount + 1;
        int n = coins.length;

        int[] dp = new int[n + 1];

        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int coin : coins) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }

        return dp[amount] == max ? -1 : dp[amount];
    }


    public int lengthOfLIS(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];

        Arrays.fill(dp, 1);

        int max = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;

        int[] raising = new int[nums.length];

        int len = 1;
        raising[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int low = 0, high = len;
            while (low < high) {
                int mid = (low + high) / 2;
                if (raising[mid] >= nums[i]) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            raising[low] = nums[i];
            if (low == len) {
                len++;
            }
        }

        return len;

    }

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);

        int n = s.length();

        boolean[] dp = new dp[n + 1];
        dp[0] = true;

        for (int i = 1; i <=n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];

    }

    private String[] letterMap = {
        "",
        "",
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

    private ArrayList<String> res;
    private StringBuilder s;

    public List<String> letterCombinations(String digits) {
        res = new ArrayList<>();
        s = new StringBuilder();

        if (digits == null || digits.isEmpty()) {
            return res;
        }

        findCombinations(digits, 0);

        return res;
    }

    private void findCombinations(String digits, int index) {
        if (index == digits.length()) {
            res.add(s.toString());
            return;
        }

        char c = digits.charAt(index);
        String letter = letterMap[c = '0'];

        for (int i = 0; i < letter.length(); i++) {
            s.append(letter.charAt(i));
            findCombinations(digits, index + 1);
            s.deleteCharAt(s.length() - 1);

        }
    }


    public lc5(String s) {
        int n = s.length();
        int start = 0;
        int end = 0;

        int maxLength = 0;

        for (int i =0; i < n; i++) {
            int len1 = expandFromCenter(s, i, i);
            int len2 = expandFromCenter(s, i, i + 1);

            int len = Math.max(len1, len2);

            if (maxLength < len) {
                maxLength = len;
                start = i - (len - 1) /2;
                end = i + (len) /2;
            }



        }

        return s.substring(start, end + 1);
    }


    private int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left + 1;
    }


    public int findLength(int[] A, int[] B) {
        int lenA = A.length;
        int lenB = B.length;

        int max = 0;

        int[][] dp = new int[lenA + 1][lenB + 1];

        for (int i = 1; i <= lenA; i++) {
            for (int j = 1; j <= lenB; j++) {
                if (A[i - 1] == B[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }

        return max;
    }

    public int longestPalindromeSubSeq(String s) {
        if (s == null) return 0;

        int n = s.length();

        if (n < 2) return n;

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int i = n -1; i >=0; i--) {
            for (int j = i + 1; j <n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1];

    }

    public int removeElement(int[] nums, int val) {
        int n = nums.length;

        int left = 0;
        for (int right = 0; right < n; right++) {
            if (nums[right] != val) {
                nums[left] = nums[right];
                left++;
            }
        }
        return left;
    }

    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n < 2) return n;

        int fast = 1, slow = 1;

        while (fast < n) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    public int removeDuplicates2(int[] nums) {
        int n = nums.length;
        if (n <= 2) return n;

        int fast = 2, slow = 2;

        while (fast < n) {
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;

    }

    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    public void rotate(int[] nums, int k) {
        int n = nums.length;

        k = n - k % n;

        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
        reverse(nums, 0, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        int n = nums.length;
        if (start < 0 || end >= n) {
            return;
        }

        while (end > start) {
            int tmp = nums[end];
            nums[end] = nums[start];
            nums[start] = tmp;
            end--;
            start++;
        }
    }

    public int maxProfit(int[] prices) {
        int res = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (min > prices[i]) {
                min = prices[i];
            }

            if (prices[i] > min) {
                res = Math.max(res, prices[i] - min);
            }
        }
        return res;
    }


    public int maxProfit2(int[] prices) {
        int res = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }

        return res;
    }


    public boolean canJump(int[] nums) {
        int max = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            }
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }

    public int jump(int[] nums) {
        int n = nums.length;
        int end = 0;
        int step = 0;
        int maxPosition = 0;


        for (int i = 0; i < nums.length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);

            if (i == end) {
                end = maxPosition;
                step++;
                if (end >= n - 1) break;
            }
        }
        return step;
    }


    public int largestSumAfterKNegations(int[] nums, int k) {
        nums = InputStream.of(nums).boxed().sorted(
            (o1, o2) -> Math.abs(o2) - Math.abs(o1)
        ).mapToInt(Integer::intValue)
        .toArray();

        int len = nums.length;

        for (int i = 0; i < len; i++) {
            if (nums[i] < 0 && k > 0) {
                nums[i] = -nums[i];
                k--;
            }
        }

        if (k % 2 == 1) nums[len - 1] = -nums[len - 1];
        return Arrays.stream(nums).sum();
    }


    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        int[] ans = new int[n];

        ans[0] = 1;

        for (int i = 1; i< n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        int right = 1;
        for (int i = n - 1; i>= 0; i--) {
            ans[i] = right * ans[i];
            right = right * nums[i];
        }
        return ans;
    }



    public int  trap(int[] height) {
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
                ans += leftMax - height[left];
                left++;
            }
        }

        return ans;
    }

    public int trapdp(int[] height) {
        int n = height.length;

        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        maxLeft[0] = height[0];
        maxRight[n - 1] = height[n - 1];

        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
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


    public int lengthOfLongestSubString(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        int[] last = new int[128];

        dp[0] = 0;

        Arrays.fill(last, -1);

        for (int i = 1; i <= n; i++) {
            int j = last[s.charAt(i - 1)];
            last[s.charAt(i - 1)] = j;

            if (i -j > dp[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = i - j;
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    public int lols(String s) {
        Set<Character> visisted = new HashSet<>();

        int end = 0;
        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                visisted.remove(s.charAt(i - 1));
            }

            while (end < s.length() && !visisted.contains(s.charAt(end))) {
                visisted.add(s.charAt(end));
                end++;
            }
            maxLength = Math.max(maxLength, end - i);
        }

        return maxLength;

    }

    public ListNode partition(ListNode head, int x) {
        if (head == null) return null;

        ListNode smallHead = new ListNode(0);
        ListNode largeHead = new ListNode(0);

        ListNode small = smallHead;
        ListNode large = largeHead;


        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = head;
            } else {
                large.next = head;
                large = head;
            }
            head = head.next;
        }

        small.next = largeHead.next;
        large.next = null;
        return smallHead.next;
    }

    public int longestPalindromSs(String s) {
        if (s == null) return 0;

        int n = s.length();

        if (n < 2) return n;

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int i = n - 1; i>= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][ j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
    }


    public int maxProfit(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n][3];

        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        dp[0][2] = 0;

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
            dp[i][1] = dp[i - 1][0] + prices[i];

            dp[i][2] = Math.max(dp[i - 1][1], dp[i - 1][2];)
        }
        return Math.max(dp[n - 1][1], dp[n - 1][2]);


        public int lengthOfLIS(int[] nums) {
            if (nums.lenght == 0) return 0;

            int[] raising = new int[nums.length];
            int len = 1;

            raising[0] = nums[0];

            for (int i = 1; i < nums; i++) {
                int low = 0, high = len;
                while(low < high) {
                    int mid = (low + high) /2;
                    if (raising[mid] >= nums[i]) {
                        high = mid;
                    } else {
                        low = mid + 1;
                    }
                }

                raising[low] = nums[i];
                if (low == len) {
                    len++;
                }
            }
        }
        return len;

    }

    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int buy = -prices[0];
        int sell = 0;

        for (int i = 1; i < n; i++) {
            buy = Math.max(buy, sell - prices[i]);
            sell = Math.max(sell, buy + prices[i] - fee);
        }
        return sell;
    }

    public int maxProfitK(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        if (k >= prices.length / 2) {
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        int[][] dp = new int[k + 1][prices.length];

        for (int i = 1; i <= k; i++) {
            int localMax = dp[i - 1][0] - prices[0];
            for (inti j = 1; j < prices.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + buyMax);
                localMax = Math.max(buyMax, dp[i - 1][j] - prices[j]);
            }
        }
    }

    public int longestValidParent(String s) {
        int left = 0, right = 0, maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                maxLength = Math.max(maxLength, 2 * left);
            } else if (right > left) {
                left = right = 0;
            }
        }

        left = right = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                maxLength = Math.max(maxLength, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
         }
         return maxLength;
    }


    public ListNode detectCycle(ListNode head) {

        if (head == null) return null;

        ListNode slow = head, fast = head;

        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }

            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;

        ListNode slow = head, fast = head;

        while (fast != null) {
            slow = slow.next;

            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }

            if (slow == fast) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;

    }


    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int startIndex = 0;
        int totalGas = 0;
        int currentGas = 0;

        for (int i = 0; i < n; i++) {
            currentGas += gas[i] - cost[i];
            totalGas += gas[i] - cost[i];

            if (currentGas < 0) {
                currentGas = 0;
                startIndex = i + 1;
            }
        }

        return totalGas >= 0 ? startIndex : -1;

    }

    public int candy(int[] ratings) {
        int n = ratings.length;

        int[] left  = new int[n];
        left[0] = 1;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int[] right = new int[n];
        right[n - 1] = left[n - 1];

        for (int i = n -2; i >= 0; i++) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.max(left[i], right[i]);
        }
        return sum;
    }


    Map<Character, Integer> symValues = new HashMap<Character, Integer>() { {
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }

    };
//XXVII 可视作 X+X+V+I+I=10+10+5+1+1=27。
//XIV 可视作 X−I+V=10−1+5=14。
    public int romanToInt(String s) {
        int ans = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int value = symValues.get(s.charAt(i));

            if (i < n - 1 && value < symValues.get(s.charAt(i + 1))) {
                ans -= value;
            } else {
                ans += value;
            }
        }
        return ans;
    }

    public String intToRoman(int num) {
        String[] romans = new String[] {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        int[] values = new int[] {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < values.length && num > 0; i++) {
            while (num >= values[i]) {
                num -= values[i];
                res.append(romans[i]);
            }
        }
        return res.toString();
    }

    // 还可以用列表法，这个不展开了

    public int lengthOfLastWord(String s) {
        int index = s.length() - 1;

        while (s.charAt(index) == ' ') {
            index--;
        }

        int length = 0;

        while (index >= 0 && s.charAt(index) != ' ') {
            length++;
            index--;
        }

        return length;
    }

    public String longestCp(Strting[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        int minLength = Integer.MAX_VALUE;

        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }

        int low = 0, high = minLength;

        while (low < high) {
            int mid = (high - low + 1) / 2 + low;

            if (isCommonPrefix(strs, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return strs[0].substring(0, low);

    }


    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return  "";
        }

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            prefix = getPrefix(prefix, strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }

    public String getPrefix(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());

        int index = 0;
        while (index < length && s1.charAt(index) == s2.charAt(index)) {
            index++;
        }
        return s1.substring(0, index);
    }

    public String reversWords(String s) {
        int n = s.length();
        int i = 0;
        int j = 0;

        char[] chars = s.toCharArray();

        while (j < n) {
            while (j < n && s.charAt(j) == ' ') j++;
            while (j < n && s.charAt(j) != ' ') chars[i++] = chars[j++];
            while (j < n && s.charAt(j) == ' ') j++;
            if (j < n) chars[i++] = ' ';
        }

        reverse(chars, 0, i - 1);

        int start = 0, end = 0;

        while (end < i) {
            while (end < i && chars[end] != ' ') end++;
            reverse(chars, start, end - 1);
            start = end + 1;
            end++;
        }

        return new String(chars, 0, i);
    }

    public void reverse(char[] chars, int start, int end) {
        if (start < 0 || end > chars.length) {
            return;
        }

        while  (start < end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;

            start++;
            end--;
        }
    }

    public String convert(String s, int numRows) {
        if (numRows == 1) return s;

        List<StringBuilder> rowList = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            rowList.add(new StringBuilder())；
        }

        int mod = 2 * numRows - 2;

        for (int i = 0; i < s.length(); i++) {
            int index = i % mod;
            if (index >= numRows) index = mod - index;

            rowList.get(index).append(s.charAt(i));
        }
        StringBuilder res = StringBuilder();

        for (StringBuilder sb : rowList) {
            res.append(sb);
        }

        return res.toString();


    }


    public boolean isValidSudoku(int[][] board) {

        boolean[][] row = new boolean[9][9];

        boolean[][] col = new boolean[9][9];

        boolean[][] box = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int num = board[i][j] - '1';
                int boxIndex = (i / 3) * 3 + (j / 3);

                if (row[i][num] || col[j][num] || box[boxIndex][num]) {
                    return false;
                } else {
                    row[i][num] = true;
                    col[j][num] = true;
                    box[boxIndex][num] = true;
                }
            }
        }
        return true;
    }


    public void solveSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];

        boolean[][] cols = new boolean[9][9];

        boolean[][] box = new boolean[9][9];

        for (int i = 0; i < 9;i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    rows[i][num] = true;
                    cols[j][num] = true;
                    int boxIndex = (i / 3) * 3 + (j / 3);
                    box[boxIndex][num] = true;
                }
            }
        }

        backtrackSk(board, 0, 0, rows, cols, box);
    }

    private boolean backtrackSk(char[][] board, int i, int j, boolean[][] rows, boolean[][] cols, boolean[][] box) {
        if(j == 9) {
            return backtrackSk(board, i + 1, 0, rows, cols, box);
        }
        if (i == 9) {
            return true;
        }

        if (board[i][j] != '.') {
            return backtrackSk(board, i,  j + 1, rows, cols, box);
        }

        for (char ch = '1'; ch <= '9'; ch++) {
            int num = ch - '1';
            int boxIndex = (i / 3) * 3 + (j / 3);

            if (rows[i][num] || cols[j][num] || box[boxIndex][num]) {
                continue;
            }

            board[i][j] = ch;
            rows[i][num] = true;
            cols[j][num] = true;
            box[boxIndex][num] = true;

            if (backtrackSk(board, i, j + 1, rows, cols, box)) {
                return true;
            }

            board[i][j] = '.';
            rows[i][num] = false;
            cols[j][num] = false;
            box[boxIndex][num] = false;

        }
        return false;


    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int top = 0;
        int left = 0;
        int bottom = matrix.length - 1;
        int right = matrix[0].length - 1;

        List<Integer> res = new ArrayList<>();

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;
            if (top > bottom) break;

            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;
            if (left > right) break;

            for (int i = right; i>= left; i--) {
                res.add(matrix[bottom][i]);
            }
            bottom--;
            if (top > bottom) beak;

            for (int i = bottom ; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            left++;

        }

        return res;

    }


    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {

            int pivot = partition(arr, left, right);

            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }

    public static int partition(int[] arr, int left, int right) {
        int pivotValue = arr[left];

        while (left < right) {
            while(left < right && arr[right] >= pivotValue) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && arr[left] <= pivotValue) {
                left++;
            }
            arr[right] = arr[left];

        }

        arr[left] = pivotValue;
        return left;
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        for (int i = 0; i < n; i++) {
            int start = 0, end = n - 1;
            while (start < end) {
                int tmp = matrix[i][start];
                matrix[i][start] = matrix[i][end];
                matrix[i][end] = tmp;
                start++;
                end--;
            }
        }
    }


    public void setZeroes(int[][] matrix) {

        boolean isFirstRowZero = false;
        boolean isFirstColZero = false;


        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                isFirstColZero = true;
            }
        }

        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                isFirstRowZero = true;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }


        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0 ) {
                for (int i = 1; i < matrix.length; i++) {
                    matrix[i][j] = 0;
                }
            }
         }

         if (isFirstColZero) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
          }

          if (isFirstRowZero) {
            Arrays.fill(matrix[0], 0);
          }



    }


    int[][] direcitons = new int[][] {
        {-1, -1},
        {-1, 0},
        {-1, 1},
        {0, -1},
        {0, 1},
        {1, -1},
        {1, 0},
        {1, 1}
    };

    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] aliveMatrix = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                aliveMatrix[i][j] = isAlive(board, i, j);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (aliveMatrix[i][j]) {
                    board[i][j] = 1;
                } else {
                    board[i][j] = 0;
                }
            }
        }

    }


    private boolean isAlive(int[][] board, int i, int j) {
        int m = board.length;
        int n = board[0].length;

        int aliveCount = 0;

        for (int[] dir : direcitons) {
            int r = i + dir[0];
            int c = j + dir[1];
            if ((r >= 0 && r < m) && (c >= 0 && c < n) && board[r][c] == 1) {
                aliveCount++;
            }
        }

        if (aliveCount == 3) {
            return true;
        }

        if (aliveCount == 2 && board[i][j] == 1) {
            return true;
        }
        return false;
    }


    public boolean canJump(int[] nums) {
        int max = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            }

            max = Math.max(max, i + nums[i]);
        }
        return true;
    }

    public int jump(int[] nums) {
        int n = nums.length;
        int step = 0;
        int maxPosition = 0;
        int end = 0;

        for (int i = 0; i < n - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                step++;
                end = maxPosition;
                if (end >= n-1) break;
            }
        }
        return step;

    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow ) return true;
        }
        return false;
    }


    public ListNode reverseBetween(ListNode head, int left, int right) {

        if (left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode start = head;
        ListNode end =head;
        ListNode pre = head;

        while (left > 1) {
            pre = pre.next;
            end = end.next;
            left--;
            right--;
        }

        start = pre.next;

        while (right >= 0) {
            end = end.next;
            right--;
        }

        ListNode curr = start;
        ListNode prev = end;
        while (curr != end) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        pre.next = prev;




        return dummy.next;





    }





    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;

        ListNode current = head;

        while (current != null) {

            while (current.next != null && current.val == current.next.val) current = current.next;

            if (pre.next == current) {
                pre = pre.next;
            } else {
                pre.next = current.next;
            }

            current = current.next;
        }


        return dummy.next;

    }


    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        while (head != null && head.next != null) {

            if (head.val == head.next.val) head.next = head.next.next;
            else head = head.next;
        }

        return dummy.next;
    }


    public Node copyRandomList(Node head) {
       if (head == null) return head;

       Map<Node, Node> nodeMap = new HashMap<>();

       Node node = head;

       while (node != null) {
         nodeMap.put(node, new Node(node.val));
         node = node.next;
       }

       for (Map.Entry<Node, Node> entry : nodeMap.entrySet()) {
        entry.getValue().next = nodeMap.get(entry.getKey().next);
        entry.getValue().random = nodeMap.get(entry.getKey().random);
       }
       return nodeMap.get(head);

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


    public ListNode removeNthFromEnd(ListNode head, int n) {
        int right = 0;

        ListNode dummy = new ListNode(0);

        dummy.next = head;
        ListNode pre = dummy;

        ListNode node = head;

        while (node != null && right < n) {
            node = node.next;
            right++;
        }

        while (node !=null) {
            node = node.next;
            pre = pre.next;
        }

        pre.next =  pre.next.next;
        return dummy.next;
    }

    public int maxArea(int[] height){
        int n = height.length;

        int left =0;
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


    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        int h = 0;
        int i = n -1;

        while (i >= 0 && citations[i] > h) {
            h++;
            i--;
        }
        return h;
    }

    public String simplifyPath(String path) {
        Deque<String> deque = new ArrayDeque<>();

        String[] nams = path.split("/");

        StringBuilder res = new StringBuilder();

        for (String name  : names) {
            if (name.isEmpty() || name.equals(".")) {
                continue;
            } else if (name.equals("..")) {
                if (!deque.isEmpty()) {
                    deque.pollLast();
                }
            } else {
                deque.offerLast(name);
            }
        }

        if (deque.isEmpty()) {
            res.append("/");
        } else {
            while (!deque.isEmpty()) {
                res.append("/").append(deque.pollFirst());
            }
        }
        return res.toString();
    }


    public Node connect(Node root) {
        if (root == null) return null;

        Queue<Node> queue = new LinkedList<>();

        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.peek();

            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                node = queue.poll();
                node.next = queue.peek();
                if (node.left!=null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            node.next = null;
        }
        return root;

    }

    TreeNode prev = null;

    public void flattern(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode p = curr.left;
                while (p.right != null) {
                    p = p.right;
                }
                p.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    private Map<Integer, Integer> inorderIndexMap;
    private int preorderIndex;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        inorderIndexMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        return buildTree(preorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        int rootVal = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(preorder, left, inorderIndexMap.get(rootVal) - 1);
        root.right = buildTree(preorder, inorderIndexMap.get(rootVal) + 1, right);

        return root;
    }

    int postIndex;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;

        inorderIndexMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return buildTree(postorder, 0, postorder.length - 1);
    }

    privaate TreeNode buildTree(int[] postorder, int left, int right) {
        if (left < right) return null;
        int rootVal = postorder[postIndex--];
        TreeNode rootNode = new TreeNode(rootVal);

        rootNode.right = buildTree(postorder, inorderIndexMap.get(rootVal) + 1, right);
        rootNode.left = buildTree(postorder, left, inorderIndexMap.get(rootVal) - 1);

        return rootNode;

    }


    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        if (root.left == null && root.right == null && targetSum - root.val == 0) return true;

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right,  targetSum - root.val);
    }


    public int sumNumbers(TreeNode root) {
        return sumSubTree(root, 0);
    }

    private int sumSubTree(TreeNode node, int sum) {
        if (node == null) return 0;

        sum = sum * 10 + node.val;

        if (node.left == null && node.right == null) return sum;

        return sumSubTree(node.left, sum) + sumSubTree(node.right, sum);

    }

    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode node) {
        maxGain(root);
        return maxSum;
    }

    public int maxGain(TreeNode root) {
        if (root == null) return 0;

        int leftGain = Math.max(maxGain(root.left), 0);
        int rightGain = Math.max(maxGain(root.right), 0);

        int pathSum = leftGain + rightGain + root.val;

        maxSum = Math.max(maxSum, pathSum);

        return rootVal + Math.max(leftGain, rightGain);
    }


    int count = 0;

    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        count++;
        countNodes(root.left);
        countNodes(root.right);

        return count;
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root.val == p.val) return p;
        if (root.val == q.val) return q;

        TreeNode leftRes = lowestCommonAncestor(root.left, p , q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        if (leftRes != null && rightRes != null) return root;

        if (leftRes != null) return leftRes;

        return rightRes;
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        dfs(root, res, 0);
        return res;
    }

    private void dfs(TreeNode node, List<Integer> res, int depth) {
        if (node == null) return;
        if (depth == res.size()) {
            res.add(node.val);
        }
        dfs(node.right, res, depth + 1);
        dfs(node.left, res, depth + 1);
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            LinkList<Integer> line = new LinkedList<>();

            while (size-- > 0) {
                TreeNode curr = queue.poll();
                if (level % 2 == 0) {
                    line.addLast(curr.val);
                } else {
                    line.addFirst(curr.val);
                }


                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
            }
            res.add(line);
            level++;
        }
        return res;
    }


    public List<List<Integer>> getSkyLine(int[][] buildings) {

        List<List<Integer>> res = new ArrayList<>();


        List<Integer> hights = new ArrayList<>();

        for (int[] building : buildings) {
            hights.add(new int[]{building[0], -building[2]});
            hights.add(new int[]{building[1], building[1]});
        }

        Collections.sort(heights, (h1, h2) -> {
            if (h1[0] == h2[0]) return h1[1] - h2[1];
            return h1[0] - h2[0];
        });

        TreeMap<Integer, Integer> map = new TreeMap<>();

        map.put(0, 1);

        int prev = 0;

        for (int[] h: heights) {
            if (h[1] < 0) {
                map.put(-h[1], map.getOrDefault(-h[1], 0) + 1);
            } else {
                if (map.get(h[1]) > 1) {
                    map.put(h[1], map.get(h[1]) - 1);
                } else {
                    map.remove(h[1]);
                }
            }

            int currentMax = map.lastKey();
            if (currentMax != prev) {
                prev = currentMax;
                res.add(Arrays.asList(h[0], currentMax));
            }
        }
        return res;


    }


    public double findMedianSortedArray(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;

        return (getKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + getKth(nums1, 0, m - 1, 0, n - 1, right)) / 2.0;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);

        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j])  {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2,  - (i - start1 + 1));
        }
    }


    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[] dp = new int[n];

        dp[0] = triangel.get(0).get(0);

        for (int i = 1; i < n; i++) {
            d[i] = dp[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j], d[j - 1] + triangle.get(i).get(j));
            }
            dp[0] = dp[0] + triangle.get(i).get(0);
        }

        int minRes = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[i]);
        }
        return minRes;
    }


    while (k > 0) {
        int parent = (k - 1) >>> 1;
        Object e = array[parent];
        if (key.comparte((K) e, (K) key) <= 0) {
            break;
        }
    }
    array[k] = e;
    k = oarnent



    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int startIndex = 0;
        int totalGas = 0;
        int currentGas = 0;

        for (int i = 0; i < n; i++) {
            currentGas += gas[i] - cost[i];
            totalGas += gas[i] - cost[j];

            if (currentGas < 0) {
                currentGas = 0;
                startIndex = i + 1;
            }
        }

        return totalGas >= 0 ? startIndex : -1;
    }


    public int candy(int[] ratings) {
        int n = ratings.length;

        int[] left = new int[n];

        left[0] = 1;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }


        int[] right = new int[n];
        right[n - 1] = left[n - 1];

        for (int i = n -2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }

        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += Math.max(left[i], right[i]);
        }

        return sum;
    }

    public int trap(int[] height) {
        int n = height.length;

        int left = 0;
        int right = n -1;

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
                ans += leftMax - height[left];
                left++;
            }
        }
        return ans;

    }


    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSort(nums, 0, n - 1, n - k);
    }

    private int quickSort(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];

        int pivot = nums[left];
        int i = left - 1;
        int j = right + 1;

        while(i < j) {
            do i++; while(nums[i] < pivot);
            do j--; while(nums[j] > pivot);

            if (i < j) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        if (k <= j) return quickSort(nums, left, j, k);
        else return quickSort(nums, j + 1, right, k);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        int row = 0;
        int col = n - 1;

        while (row < m && col >= 0) {
            int element = matrix[row][col];
            if (element == target) {
                return ture;
            } else if (element < target) {
                row++;
            } else {
                col--;
            }


        }
        return false;
    }


    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }




    public int singleNumber(int[] nums) {
        int[] bits = new int[32];

        int ans = 0;

        for (int i = 0; i < 32; i++) {
            for (int num : nums) {
                bits[i] += (num >> i) & 1;
            }
        ans |= (bits[i] % 3) << i;
        }

        return ans;

    }


    public int maxSubArray(int[] nums) {
        int pre = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.lenght; i++) {
            pre = pre < 0? nums[i] : nums[i] + pre;
            maxSum = Math.max(maxSum, pre);
        }
        return maxSum;
    }



    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1, -1};
        int[] res = {-1, -1};

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) right = mid;
            else left = mid + 1;
        }

        if (nums[left] != target) return res;

        res[0] = left;
        left = 0;
        right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2 + 1;
            if (nums[mid] <= target) left = mid;
            else right = mid - 1;
        }
        res[1] = right;
        return res;
    }


    public int uniquePathsWithObstacles( int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }

        for (int i = 0; i < n; i++) {
            if (obstacleGrid[0][i] == 1) {
                break;
            }
            dp[i][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public String longestPalindrome(String s) {

        int n = s.length();

        int start = 0;
        int end = 0;

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            int len1 = expendFromCenter(s, i, i);
            int len2 = expandFromCenter(s, i, i + 1);

            int len = Math.max(len1, len2);

            if (maxLength < len) {
                maxLength = len;
                start = i - (len - 1) / 2;
                end = i + len /2;
            }
        }

        return s.substring(start, end + 1);

    }

    private int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        dp[0][0] = 0;

        for (int i = 1; i < m + 1; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i < n + 1; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
        return dp[m][n];
    }

    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        int maxSquare = Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 && j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    }
                }

                maxSquare = Math.max(maxSquare, dp[i][j]);
            }
        }

        return maxSquare * maxSquare;
    }

    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left ,right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }

    public int partition(int[] arr, int left, int right) {
        int pivotValue = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivotValue) {
                right--;
            }
            arr[left] = arr[right];

            while (left < right && arr[left] <= pivotValue) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = pivotValue;
        return left;
    }



    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode node, long minVal, long maxVal) {
        if (node == null) return true;
        if (node.val <= minVal || node.val >= maxVal) return false;

        return isValidBST(node.left, minVal, node.val) && isValidBST(node.right, node.val, maxVal);

    }


    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root){
        maxGain(root);
        return maxSum;
    }

    private int maxGain(TreeNode root) {
        if (root == null) return 0;

        int leftGain = Math.max(maxGain(root.left), 0);
        int rightGain = Math.max(maxGain(root.right), 0);

        int pathSum = root.val + leftGain + rightGain;

        maxSum = Math.max(pathSum, maxSum);

        return root.val + Math.max(leftGain, rightGain);
    }
}



