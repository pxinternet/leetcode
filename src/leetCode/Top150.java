package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.awt.font.NumericShaper;

import leetCode.ListNode;

import java.util.Map;

import java.awt.HeadlessException;

import javax.swing.RootPaneContainer;

import java.awt.font.NumericShaper;

import java.awt.font.NumericShaper;

import javax.imageio.plugins.tiff.ExifGPSTagSet;

import java.util.Queue;

import javax.print.attribute.standard.QueuedJobCount;

import java.util.ArrayDeque;

import java.util.Deque;

import java.util.ArrayDeque;

import java.lang.reflect.Array;

import java.util.Comparator;

import java.util.Arrays;

import leetCode.ListNode;

import leetCode.ListNode;

import java.lang.reflect.Array;

import java.util.ArrayList;

import java.util.List;

public class Top150 {

    int[][] directions = new int[][] {
            { -1, -1 },
            { -1, 0 },
            { -1, 1 },
            { 0, -1 },
            { 0, 1 },
            { 1, -1 },
            { 1, 0 },
            { 1, 1 }
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
                if (end >= n - 1)
                    break;
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

        return ans;
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
            while (j < n && s.charAt(j) == ' ')
                j++;
            while (j < n && s.charAt(j) != ' ')
                chars[i++] = chars[j++];
            while (j < n && s.charAt(j) == ' ')
                j++;
            if (j < n)
                chars[i++] = ' ';
        }

        revserse(chars, 0, i - 1);

        int start = 0, end = 0;

        while (end < i) {
            while (end < i && chars[end] != ' ')
                end++;
            reverse(chars, start, end - 1);
            start = end + 1;
            end++;
        }

        return new String(chars, 0, i);
    }

    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        List<StringBuilder> rowList = new ArrayList<>(numRows);

        for (int i = 0; i < numRows; i++) {
            rowList.add(new StringBuilder());
        }

        int mod = 2 * numRows - 2;

        for (int i = 0; i < s.length(); i++) {
            int index = i % mod;
            if (index >= numRows)
                index = mod - index;

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

        if (m < n)
            return false;
        int i = 0, j = 0;

        while (i < n && j < m) {
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
            if (nums[i] > 0)
                break;

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int s = nums[i] + nums[j] + nums[k];
                if (s > 0)
                    k--;
                else if (s < 0)
                    j++;

                else {
                    List<Integer> ans = new ArrayList<>();
                    ans.add(nums[i]);
                    ans.add(nums[j]);
                    ans.add(nums[k]);
                    res.add(ans);
                    j++;
                    while (j < k && nums[j] == nums[j - 1])
                        j++;
                    k--;
                    while (j < k && nums[k] == nums[k + 1])
                        k--;
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
                if (ans == 1)
                    return 1;
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


    public int minSubArrayLen2(int target, int[] nums) {
        int left = 0;
        int right = 0;

        int n = nums.length;

        int ans = n + 1;

        int tmp = 0;
        tmp += nums[left];
        while (right < n) {

            if (tmp >= taget) {
                ans = Math.min(ans, right - left + 1);
                if (ans == 1) {
                    return 1;
                }
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

        if (m < n)
            return "";

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
                if (map.get(c) == 0)
                    counter--;
            }
            right++;

            while (counter == 0) {
                char tmp = s.charAt(left);

                if (map.containsKey(tmp)) {
                    map.put(tmp, map.get(tmp) + 1);
                    if (map.get(tmp) > 0)
                        counter++;
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

        if (sum > s.length())
            return res;

        Map<String, Integer> wordsMap = new HashMap<>();

        String[] subStringS = new String[n - sum + 1];

        for (int i = 0; i < n - sum + 1; i++) {
            subStringS[i] = s.substring(i, i + sum);
        }

        for (int i = 0; i < subStringS.length; i++) {
            wordsMap.clear();

            int j = 0;

            while (j < words.length) {

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
            if (j == words.length) {
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

                if (board[i][j] == '.')
                    continue;
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
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummy.next;

    }

    public Node copyRandomList(Node head) {

        if (head == null)
            return head;

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

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public List<Integer> spiralOrder(int[][] matrix) {

        int top = 0;
        int left = 0;
        int right = matrix[0].length - 1;
        int bottom = matrix.length - 1;

        List<Integer> res = new ArrayList<>();

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;
            if (top > bottom)
                break;

            for (int i = top; i < bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;
            if (left > right)
                break;

            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
            }
            bottom--;

            if (top > bottom)
                break;

            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            left++;
        }
        return res;

    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            if (slow == fast)
                return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;

    }

    public ListNode detectCycle(ListNode head) {

        if (head == null)
            return null;

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

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right)
            return head;

        ListNode dummy = new ListNode(0);

        dummy.next = head;

        ListNode start = dummy;
        ListNode end = dummy;
        ListNode pre = dummy;

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
            ListNode tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
        }
        pre.next = prev;
        return dummy.next;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null)
            return head;

        ListNode pre = null;
        ListNode curr = head;

        while (curr != null) {
            curr = head.next;
            head.next = pre;
            pre = head;
            head = curr;
        }
        return pre;
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

        while (node != null) {
            node = node.next;
            pre = pre.next;
        }

        pre.next = pre.next.next;

        return dummy.next;
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        pre.next = head;
        ListNode current = head;

        while (current != null) {
            while (current.next != null && current.val == current.next.val)
                current = current.next;

            if (pre.next == current) {
                pre = pre.next;
            } else {
                pre.next = current.next;
            }

            current = current.next;

        }

        return dummy.next;
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null)
            return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        int n;
        for (n = 0; fast.next != null; n++) {
            fast = fast.next;
        }

        for (int i = n - k % n; i > 0; i--) {
            slow = slow.next;
        }

        fast.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;
        return dummy.next;

    }

    public ListNode partition(ListNode head, int x) {

        if (head == null)
            return null;

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

        smallHead.next = largeHead.next;
        large.next = null;
        return smallHead.next;

    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null)
            return p == q;

        if (p.val != q.val)
            return false;

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;

        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null)
            return true;
        if (t1 == null || t2 == null)
            return false;

        return (t1.val == t2.val) && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    private Map<Integer, Integer> inorderIndexMap;
    private int preorderIndex;
    private int postIndex;

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

    public TreeNode buildTreePost(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;
        inorderIndexMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

    }

    public TreeNode buildTreePost(int[] postorder, int left, int right) {
        if (left > right)
            return null;

        int rootVal = postorder[postIndex--];
        TreeNode root = new TreeNode(rootVal);

        root.right = buildTreePost(postorder, inorderIndexMap.get(rootVal) + 1, right);
        root.left = buildTreePost(postorder, left, inorderIndexMap.get(rootVal) - 1);

        return root;
    }

    public Node connect(Node root) {
        if (root == null)
            return null;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        Node node = queue.peek();
        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                node = queue.poll();
                node.next = queue.peek();
                if (node.left != null) {
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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        if (root.val = p.val)
            return p;
        if (root.val = q.val)
            return q;

        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        if (leftRes != null && rightRes != null)
            return root;

        if (leftRes != null)
            return leftRes;

        return rightRes;
    }

    int count = 0;

    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        count++;
        countNodes(root.left);
        countNodes(root.right);
        return count;
    }

    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    private int maxGain(TreeNode root) {
        if (root == null)
            return 0;

        int leftGain = Math.max(maxGain(root.left), 0);
        int rightGain = Math.max(maxGain(root.right), 0);
        int pathSum = leftGain + root.val + rightGain;

        maxSum = Math.max(maxSum, pathSum);

        return root.val + Math.max(leftGain, rightGain);

    }

    public int sumNumbers(TreeNode root) {
        return sumSubTree(root, 0);
    }

    private int sumSubTree(TreeNode node, int sum) {
        if (node == null)
            return 0;

        sum = sum * 10 + node.val;

        if (node.left == null && node.right == null) {
            return sum;
        }

        return sumSubTree(node.left, sum) + sumSubTree(node.right, sum);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;

        if (root.left == null && root.right == null && targetSum - root.val == 0)
            return true;

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    TreeNode prev = null;

    public void flatten(TreeNode root) {
        TreeNode cur = root;

        while (cur != null) {
            if (cur.left != null) {
                TreeNode p = cur.left;
                while (p.right != null) {
                    p = p.right;
                }
                p.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    public int numIslands(char[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0';
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
    }

    private int m, n;
    private int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    public void solve(char[][] board) {
        if (board == null || board.length == 0)
            return;
        this.m = board.length;
        this.n = board[0].length;

        for (int i = 0; i < m; i++) {
            dfs(i, 0, board);
            dfs(i, n - 1, board);
        }

        for (int j = 0; j < n; j++) {
            dfs(0, j, board);
            dfs(m - 1, j, board);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(int x, int y, char[][] board) {
        if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != 'O') {
            return;
        }

        board[x][y] = 'A';
        for (int[] dir : directions) {
            dfs(x + dir[0], y + dir[1], board);
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null)
            return node;

        Map<Node, Node> copyMap = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();

        queue.add(node);

        copyMap.put(node, new Node(node.val, new ArrayList<>()));

        while (!queue.isEmpty()) {

            Node n = queue.poll();

            for (Node neighbor : n.neighbors) {
                if (!copyMap.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    copyMap.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                }

                copyMap.get(n).neighbors.add(copyMap.get(neighbor));
            }

        }
        return copyMap.get(node);
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> edges = new ArrayList<>();

        int[] indeg = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
            ++indeg[info[0]];
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        int visited = 0;

        while (!queue.isEmpty()) {
            ++visited;
            int u = queue.poll();

            for (int v : edges.get(u)) {
                --indeg[v];
                if (indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        return visited == numCourses;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> edges = new ArrayList<>();

        int[] indeg = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
            ++indeg[info[0]];
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            int u = queue.poll();
            res.add(u);

            for (int v : edges.get(u)) {
                --indeg[v];
                if (indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        if (res.size() != numCourses) {
            return new int[] {};
        }

        int resArray = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            resArray[i] = res.get(i);
        }
        return resArray;

    }

    public int minMutation(String startGene, String endGene, String[] bank) {

        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        Set<String> visited = new HashSet<>();

        char[] chars = new char[] { 'A', 'C', 'G', 'T' };

        Queue<String> queue = new LinkedList<>();
        queue.offer(startGene);
        visited.add(startGene);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- == 0) {
                String curr = queue.poll();
                if (curr.equals(endGene))
                    return level;

                char[] currArray = curr.toCharArray();
                for (int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];

                    for (char c : chars) {
                        currArray[i] = c;
                        String next = new String(currArray);
                        if (!visited.contains(next) && bankSet.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }

                    currArray[i] = old;
                }
            }
            level++;
        }
        return -1;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }

        Set<String> wordSet = new HashSet<>(wordList);
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);

        int le = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }

            Set<String> temp = new HashSet<>();

            for (String word : beginSet) {
                char[] chars = word.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chars[i];
                        chars[i] = c;

                        String target = String.valueOf(chars);

                        if (endSet.contains(target)) {
                            return len + 1;
                        }

                        if (!beginSet.contains(target) && wordSet.contains(target)) {
                            temp.add(target);
                            wordSet.remove(target);
                        }
                        chars[i] = old;
                    }
                }

            }
            len++;
            beginSet = temp;

        }

        return 0;

    }

    private String[] lettermap = {

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
        String letter = lettermap[c - '0'];

        for (int i = 0; i < letter.length(); i++) {
            s.append(letter.charAt(i));
            findCombinations(digits, index + 1);
            s.deleteCharAt(s.length() - 1);
        }

    }

    public List<List<Integer>> combine(int n, int k) {

        List<List<Integer>> res = new ArrayList<>();

        dfsCombine(k, n, 1, new ArrayDeque<>(), res);

        return res;

    }

    void dfsCombine(int k, int n, int depth, Deque<Integer> path, List<List<Intger>> res) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = depth; i <= n; i++) {
            path.addLast(i);

            dfsCombine(k, n, i + 1, path, res);

            path.removeLast();
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();

        if (len == 0) {
            return res;
        }

        Deque<Integer> path = new LinkedList<>();
        boolean[] used = new boolean[len];

        dfsPermute(nums, len, 0, path, used, res);
        return res;
    }

    private void dfsPermute(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
i
            if (!used[i]) {

                path.addLast(nums[i]);
                used[i] = ture;
                dfsPermute(nums, len, depth + 1, path, used, res);
                used[i] = false;
                path.removeLast();

            }
        }
    }

    public List<List<Integer>> combinatioinSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();

        int len = candidates.length;

        Arrays.sort(candidates);

        dfsCsum(candidates, target, 0, len, new ArrayDeque<>(), res);

        return res;
    }

    private void dfsCsum(int[] candidates, int target, int begin, int length, Deque<Integer> path,
            List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i < length; i++) {
            if (target - candidates[i] < 0) {
                break;
            }

            path.addLast(candidates[i]);

            dfsCsum(candidates, target - candidates[i], i, length, path, res);

            path.removeLast();
        }
    }

    Set<Integer> colSet = new HashSet();
    Set<Integer> dia1 = new HashSet<>();
    Set<Integer> dia2 = new HashSet<>();

    public int totalQueens(int n) {
        return dfsQueens(n, 0);
    }

    private int dfsQueens(int n, int row) {
        if (row == n) {
            return 1;
        }

        int count = 0;

        for (int col = 0; col < n; col++) {
            if (colSet.contains(col) || dia1.contains(row + col) || dia2.contains(row - col)) {
                continue;
            }

            colSet.add(col);
            dia1.add(row + col);
            dia2.add(row - col);
            count += dfsQueens(n, row + 1);
            dia2.remove(row - col);
            dia1.remove(row + col);
            colSet.remove(col);
        }
        return count;
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        generate();

        return result;
    }

    private void generate(List<String> result, StringBuilder current, int left, int right) {
        if (left == 0 && right == 0) {
            result.add(current.toString());
            return;
        }

        if (left > 0) {
            current.append("(");
            generate(result, current, left - 1, right);
            current.deleteCharAt(current.length() - 1);
        }

        if (right > left) {
            current.append(")");
            generate(result, current, left, right - 1);
            current.deleteCharAt(current.length() - 1);
        }

    }

    public ListNode removeNthFromEnd1(ListNode head, int n) {
        int right = 0;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        ListNode node = head;

        while (node != null && right < n) {
            node = node.next;
            right++;
        }

        while (node != null) {
            node = node.next;
            pre = pre.next;
        }

        pre.next = pre.next.next;
        return dummy.next;
    }

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        dp[0][0] = 0;

        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = i;
        }

        for (int i = 0; i < n + 1; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                }
            }
        }

        return dp[m][n];
    }

    public int maxProfit3(int[] prices) {

        int n = prices.length;

        int buy1 = -prices[0];

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

        if (k > prices.length / 2) {
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
            for (int j = 1; j < prices.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + localMax);
                localMax = Math.max(localMax, dp[i - 1][j] - prices[j]);
            }

        }

        return dp[k][prices.length - 1];
    }

    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        int maxSquare = Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
            maxSquare = Math.max(dp[i][j], maxSquare);
        }
        return maxSquare;
    }

    public String longestPalindrome(String s) {

        int n = s.length();

        int start = 0;
        int end = 0;

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandFromCenter(s, i, i);
            int len2 = expandFromCenter(s, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > maxLength) {
                maxLength = len;
                start = i - (len - 1) / 2;
                end = i + len / 2;
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

    public int uniquePathWithObstacles(int[][] obstactleGrid) {

        int m = obstactleGrid.length;
        int n = obstactleGrid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            if (obstactleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            if (obstactleGrid[0][j] == 1) {
                break;
            }
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstactleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
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

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[] dp = new int[n];

        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + triangle.get(i).get(i);

            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + triangle.get(i).get(j);
            }
            dp[0] = dp[0] + triangle.get(i).get(0);
        }

        int minRes = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[i]);
        }
        return minRes;

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
            max = Math.max(dp[i], max);
        }

        return max;
    }

    public int coinChange(int[] coins, int amount) {

        if (amount == 0)
            return 0;

        int max = amount + 1;

        int[] dp = new int[amount + 1];

        Arrays.fill(dp, max);

        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];

    }

    public int change(int amount, int[] coins) {

        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    public int numIsLands2(char[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfsIsland(grid, i, j);
                }
            }
        }
        return ans;
    }

    private void dfsIsland(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= gird[0].length || grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0';
        dfsIsland(grid, i - 1, j);
        dfsIsland(grid, i + 1, j);
        dfsIsland(grid, i, j - 1);
        dfsIsland(grid, i, j + 1);
    }

    public int minMutation2(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        Set<String> visited = new HashSet<>();

        char[] chars = new char[] { 'A', 'C', 'G', 'T' };

        Queue<String> queue = new LinkedList<>();
        queue.offer(startGene);
        visited.add(startGene);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String curr = queue.poll();
                if (curr.equals(endGene))
                    return level;

                char[] currArray = curr.toCharArray();
                for (int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];
                    for (char c : chars) {
                        currArray[i] = c;
                        String next = new String(currArray);
                        if (!visited.contains(next) && bankSet.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }

                    }

                    currArray[i] = old;
                }
            }
            level++;
        }
        return -1;

    }

    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }

        Set<String> wordSet = new HashSet<>(wordList);
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);

        int len = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmp = beginSet;
                beginSet = endSet;
                endSet = tmp;
            }

            Set<String> temp = new HashSet<>();

            for (String word : beginSet) {
                char[] chs = word.toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    char old = chs[i];

                    for (char c = 'a'; c <= 'z'; c++) {
                        chs[i] = c;
                        String target = new String(chs);

                        if (endSet.contains(target)) {
                            return len + 1;
                        }
                        if (!beginSet.contains(target) && wordSet.contains(target)) {
                            temp.add(target);
                            wordSet.remove(target);
                        }
                    }

                    chs[i] = old;
                }
            }

            len++;
            beginSet = temp;
        }

        return 0;
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return buildBST(nums, 0, nums.length - 1);
    }

    private TreeNode buildBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;

        TreeNode node = new TreeNode(nums[mid]);
        node.left = buildBST(nums, left, mid - 1);
        node.right = buildBST(nums, mid + 1, right);

        return node;
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head, fast = head, prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;

        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        curr.next = (l1 != null) ? l1 : l2;

        return dummy.next;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;

        PriorityQueue<ListNode> queue = new PriorityQueue<>(
                (a, b) -> a.val - b.val);

        for (ListNode node : Lists) {
            if (node != null) {
                queue.offer(node);
            }S
        }

        while (!queue.isEmpty()) {
            ListNode temp = queue.poll();
            tail.next = temp;
            tail = tail.next;

            if (temp.next != null) {
                queue.offer(temp.next);
            }

        }
        return dummy.next;

    }

    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }

        }
        return nums[left];
    }

    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0)
            return new int[] { -1, -1 };

        int[] res = { -1, -1 };

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target)
                right = mid;
            else
                left = mid + 1;
        }

        if (nums[left] != target)
            return res;

        res[0] = left;

        left = 0;
        right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target)
                left = mid;
            else
                right = mid - 1;
        }

        res[1] = right;
        return res;

    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0;
        int col = n - 1;

        while (row < m && col >= 0) {
            int ele = matrix[row][col];
            if (ele == target) {
                return true;
            } else if (ele > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int start = nums[i];

            while (i + 1 < nums.length && nums[i + 1] == nums[i] + 1) {
                i++;
            }

            if (start != nums[i]) {
                res.add(start + "->" + nums[i]);
            } else {
                res.add(String.valueOf(start));
            }
        }
        return res;

    }

    public int findMinArrayShots(int[][] points) {
        Arrays.sort(points, (o1, o2) -> {
            if (o1[1] > o2[1])
                return 1;
            if (o1[1] < o2[1])
                return -1;
            else
                return 0;
        });

        int post = points[0][1];
        int ans = 1;

        for (int[] point : points) {
            if (point[0] > post) {
                ans++;
                post = point[1];
            }
        }
        return ans;
    }

    public int trap2(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int leftMax = 0;
        int rightMax = 0;

        int ans = 0;

        while (left <= right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            if (leftMax < rightMax) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right++;
            }
        }
        return ans;
    }

    public int minDistance2(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        dp[0][0] = 0;

        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
        return dp[m][n];
    }

    public double findMedian(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;

        return () / 2.0;
     }

    private int getKthNum(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {

        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        if (len1 > len2)
            return getKthNum(nums2, start2, end2, nums1, start1, end1, k);

        if (len1 == 0)
            return nums2[start2 - k + 1];
        if (k == 1)
            return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(k / 2, len1) - 1;
        int j = start2 + Math.min(k / 2, len2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKthNum(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKthNum(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }

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
                max = Math.max(max, dp[i]);
            }
        }

        return max;
    }

    public int lengthOfLongestSubString(String string) {

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

    public int[][] mergeInterval(int[][] intervals) {
        List<int[]> res = new ArrayList<>();

        Arrays.sort(intervals, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0])
                    return 1;
                if (o1[0] < o2[0])
                    return -1;
                return 0;
            }

        });

        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int[] interval : intervals) {
            if (end < interval[0]) {
                res.add(new int[] { start, end });
                start = interval[0];
                end = interval[1];
            } else {
                end = Math.max(end, intval[1]);
            }
        }

        res.add(new int{start, end});
    }


    public int longestCommonSubsequence(String text1, String text2) {

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        dp[0][0] = 0;

        int len1 = text1.length();
        int len2 = text2.length();

        for (int i = 1; i <= len1; i++) {
            dp[i][0] = 0;
        }

        for (int j = 1; j <= len2; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }

    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(gird[i][j]);
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        final int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        int time = 0;

        while (!queue.isEmpty() && freshCount > 0) {
            time++;
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] point = queue.poll();
                for (int[] dir : directions) {
                    int x = point[0] + dir[0];
                    int y = point[1] + dir[1];

                    if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
                        continue;
                    }

                    if (grid[x][y] == 1) {
                        grid[x][y] = 2;
                        freshCount--;
                        queue.offer(new int[] { x, y });
                    }
                }
            }
        }
        return freshCount == 0 ? time : -1;

    }

    int maxDiameterOfTree = 1;

    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return maxDiameterOfTree;
    }

    private int depth(TreeNode root) {
        if (root == null)
            return 0;
        int left = depth(root.left);
        int right = depth(root.right);

        maxDiameterOfTree = Math.max(maxDiameterOfTree, left + right);
        return Math.max(left, right) + 1;
    }


}
