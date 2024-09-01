package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.font.NumericShaper;

import leetCode.ListNode;

import java.util.Map;

public class Top150 {

    int[][] directions = new int[][] {
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

        for (int[] direction : directions) {
            int r = i + direction[0];
            int c = j + direction[1];

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

        if (start < 0 || end >= 0) {
            return;
        }

        while (end > start) {

            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;

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

    public int hIndex(int[] candidates) {

        Arrays.sort(candidates);

        int n = candidates.length;
        int h = 0;

        int i = n - 1;

        while (i >= 0 && candidates[i] > h) {
            h++;
            i--;
        }
        return h;
    }

    public int jump(int[] nums) {
        int n = nums.length;
        int step = 0;
        int maxPosition = 0;
        int end = 0;

        for (int i = 0; i < n - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                step++;
                if (end >= n - 1) break;
            }
        }

        return step;
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

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        ans[0] = 1;

        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        int right = 1;

        for (int i = n - 1; i >= 0; i--) {
            ans[i] = right * ans[i];
            right = right * nums[i];
        }

        return  ans;
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

        for (int i = n - 2; i >= 0; i--) {
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

    public String reverseWords(String s) {
        int n = s.length();
        int i = 0;
        int j = 0;

        char[] chars = s.toCharArray();

        while (j < n) {
            while(j < n && s.charAt(j) == ' ') j++;
            while( j < n && s.charAt(j) != ' ') chars[i++] = chars[j++];
            while(j < n && s.charAt(j) == ' ') j++;
            if (j < n) chars[i++] = ' ';
        }

        revserse(chars, 0, i - 1);

        int start = 0, end = 0;

        while (end < i) {
            while(end < i && chars[end] != ' ') end++;
            reverse(chars, start, end-1);
            start = end + 1;
            end++;
        }

        return new String(chars, 0, i);
    }

    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> rowList = new ArrayList<>(numRows);

        for (int i = 0; i < numRows; i++) {
            rowList.add(new StringBuilder());
        }

        int mod = 2 * numRows - 2;

        for (int i = 0; i < s.length(); i++) {
            int index = i % mod;
            if (index >= numRows) index = mod - index;

            rowList.get(index).append(s.charAt(i));
        }

        StringBuilder res = new StringBuilder();

        for (StringBuilder sb : rowList) {
            res.append(sb);
        }
        return res.toString();

    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();

        int n = words.length;

        int i = 0;

        while (i < n) {
            int j = i, len = 0;

            while (j < n && len + words[j].length() + j - i <= maxWidth) {
                len += words[j++].length();
            }

            StringBuilder line = new StringBuilder(words[i]);
            boolean isLast = (j == n);

            if (j - i == 1 || isLast) {
                for (int k = i + 1; k < j; k++) {
                    line.append(' ').append(words[k]);
                }
                while (line.length() < maxWidth) {
                    line.append(' ');
                }
            } else {
                int spaces = (maxWidth - len) / (j - i - 1);
                int extraSpaces = (maxWidth - len) % (j - i - 1);

                for (int k = i + 1; k < j; k++) {
                    for (int s = spaces; s > 0; s--) {
                        line.append(' ');
                    }
                    if (extraSpaces-- > 0) {
                        line.append(' ');
                    }
                    line.append(words[k]);
                }
            }
            res.add(line.toString());
            i = j;
        }
        return res;
    }

    public boolean isSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();

        if (m < n) return false;
        int i =0, j = 0;


        while(i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == n;
    }


    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;

        int left = 0;
        int right = n - 1;

        int[] ans = new int[2];

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                ans[0] = left + 1;
                ans[1] = right + 1;
                return ans;
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

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < n - 2; i++) {
            if (nums[i] > 0) break;

            if (i > 0 && nums[i] == nums[i -1]) {
                continue;
            }

            int j = i + 1;
            int k = n - 1;

            while(j < k) {
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
                    while (j < k && nums[j] == nums[j - 1]) j++;
                    k--;
                    while (j < k && nums[k] == nums[k + 1]) k--;
                }
            }
        }
        return res;
    }

    public int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        int right = 0;

        int n = nums.length;

        int ans = n + 1;

        int tmp = 0;
        tmp += nums[left];

        while (right < n) {
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



    public String minWindows(String s, String t) {
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

            while(counter == 0) {
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


    public List<Integer> findSubString(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        Map<String, Intger> wordsCounts = new HashMap<>();

        int wordLength = words[0].length();
        int n = s.length();

        int sum = 0;
        for (String word : words) {
            sum += word.length();
            wordsCounts.put(word, wordsCounts.getOrDefault(word, 0) + 1);
        }

        if (sum > s.length()) return res;

        Map<String, Integer> wordsMap = new HashMap<>();

        String[] subStringS = new String[n - sum + 1];

        for (int i = 0; i < n - sum + 1; i++) {
            subStringS[i] = s.substring(i, i + sum);
        }

        for (int i = 0; i < subStringS.length; i++) {
            wordsMap.clear();

            int j = 0;

            while(j < words.length) {

                String word = subStringS[i].substring(j * wordLength, (j + 1) * wordLength);

                if (wordsCounts.containsKey(word)) {
                    int counts = wordsCounts.getOrDefault(word, 0);
                    int currentCounts = wordsMap.getOrDefault(word, 0);

                    if (counts > 0 && counts > currentCounts) {
                        wordsMap.put(word, currentCounts + 1);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                j++;
            }
            if ( j == words.length) {
                res.add(i);
            }
        }
        return res;
    }

    public boolean isValidSudoku(char[][] board) {

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
        boolean[][] boxes = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    rows[i][num] = true;
                    cols[j][num] = true;
                    int boxIndex = (i / 3) * 3 + (j / 3);
                    boxes[boxIndex][num] = true;
                }
            }
        }

        backtrack(board, 0, 0, rows, cols, boxes);
    }

    private boolean backtrack(char[][] board, int i, int j, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        if (j == 9) {
            return backtrack(board, i + 1, j, rows, cols, boxes);
        }
        if (i == 9) {
            return true;
        }

        if (board[i][j] != '.') {
            return backtrack(board, i, j + 1, rows, cols, boxes);
        }

        for (char c = '1'; c <= '9'; c++) {
            int num = c - '1';
            int boxIndex = (i / 3) * 3 + (j / 3);

            if (rows[i][num] || cols[j][num] || boxes[boxIndex][num]) {
                continue;
            }

            board[i][j] = c;
            rows[i][num] = true;
            cols[j][num] = true;
            boxes[boxIndex][num] = true;

            if (backtrack(board, i, j + 1, rows, cols, boxes)) {
                return true;
            }

            board[i][j] = '.';
            rows[i][num] = false;
            cols[j][num] = false;
            boxes[boxIndex][num] = false;

        }

        return false;


    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);

        ListNode curr = dummy;

        int carry = 0;

        while (l1 != null || l2 != null) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;

            carry = sum / 10;
            int val = sum % 10;

            curr.next = new ListNode(val);

            curr = curr.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummy.next;

    }


    public Node copyRandomList(Node head) {

        if (head == null) return head;


        Map<Node, Node> map = new HashMap<>();

        Node node = head;

        while (node != null) {
            map.put(node, new Node(node.val));
            node = node.next;
        }

        for (Map.Entry<Node, Node> entry : map.entrySet()) {
            entry.getValue().next = map.get(entry.getKey().next);
            entry.getValue().random = map.get(entry.getKey().random);
        }

        return map.get(head);


    }

}
