package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.awt.font.NumericShaper;

import leetCode.ListNode;

import java.util.Map;

import java.awt.HeadlessException;

import javax.swing.RootPaneContainer;

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

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        return  1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }


    public List<Integer> spiralOrder(int[][] matrix) {

        int top = 0;
        int left = 0;
        int right = matrix[0].length - 1;
        int bottom = matrix.length - 1;


        List<Integer> res = new ArrayList<>();

        while(top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;
            if (top > bottom) break;

            for (int i = top; i < bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;
            if (left > right) break;

            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
            }
            bottom--;

            if (top > bottom) break;

            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            left++;
        }
        return res;

    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            if (slow == fast) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;

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

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) return head;

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

        while(right >= 0) {
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
        if (head == null) return head;

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

    publc ListNode reverseKGroup(ListNode head, int k) {
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
        ListNode pre  = dummy;
        pre.next = head;
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

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        int n;
        for (n = 0; fast.next != null; n++) {
            fast = fast.next;
        }

        for (int i = n - k % n; i >0; i--) {
            slow = slow.next;
        }

        fast.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;
        return dummy.next;

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

        smallHead.next = largeHead.next;
        large.next = null;
        return smallHead.next;

    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;

        if (p.val != q.val) return false;

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }


    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;

        return (t1.val == t2.val) && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }


    private Map<Integer, Integer> inorderIndexMap;
    private int preorderIndex;
    private int postIndex;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        inorderIndexMap = new HashMap<>();

        for (int i = 0; i < inorder.length;i++) {
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
        if (left > right) return null;

        int rootVal = postorder[postIndex--];
        TreeNode root = new TreeNode(rootVal);

        root.right = buildTreePost(postorder, inorderIndexMap.get(rootVal) + 1, right);
        root.left = buildTreePost(postorder, left, inorderIndexMap.get(rootVal) - 1);

        return root;
    }

    public Node connect(Node root) {
        if (root == null) return null;

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
        if (root == null) return null;
        if (root.val = p.val) return p;
        if (root.val = q.val) return q;

        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        if (leftRes != null && rightRes != null) return root;

        if (leftRes != null) return leftRes;

        return rightRes;
    }

    int count = 0;
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
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
        if (root == null) return 0;

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
        if (node == null) return 0;

        sum = sum * 10 + node.val;

        if (node.left == null && node.right == null) {
            return sum;
        }

        return sumSubTree(node.left, sum) + sumSubTree(node.right, sum);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        if (root.left == null && root.right == null && targetSum - root.val == 0) return true;

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

}
