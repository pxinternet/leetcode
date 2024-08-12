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

import java.util.Arrays;

import java.awt.font.NumericShaper;

import java.lang.reflect.Array;

import java.util.ArrayList;

import java.security.spec.EdDSAParameterSpec;

import com.sun.security.jgss.ExtendedGSSContext;

import java.util.ArrayList;

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

    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;

        int max = amount + 1;
        int n  = coins.length;

        int[] dp = new int[amount + 1];

        Arrays.fill(dp, max);

        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.max(dp[i], dp[i - coin] + 1);
                }
            }
        }


        return dp[amount] > amount ? -1 : dp[amount];
    }


    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val) && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public void flatten(TreeNode root) {
        TreeNode prev = null;
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


    class LC130 {
        private int m, n;

        private int[][] direction = new int[][] {{1, 0}, {-1, 0}, {0,1}, {0, -1}};

        public void solve(char[][] board) {
            if (board == null || board.length == 0) {
                return;
            }
            this.m = board.length;
            this.n = board[0].length;

            for (int i = 0; i < m; i++) {
                dfs(i, 0, board);
                dfs(i, n - 1, board);
            }

            for (int i = 0; i < n; i++) {
                dfs(0, i, board);
                dfs(m - 1, i, board);
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
            for (int[] dir :direction) {
                dfs( x + dir[0], y + dir[1] , board);
            }
        }
    }


    class Coures1 {

        public boolean canFinish(int numCourses, int[][] prerequisites) {

            List<List<Integer>> edges = new ArrayList<>();

            int[] indeg = new int[numCourses];

            for (int i = 0; i < numCourses; i++) {
                edges.add(new ArrayList<>());
            }

            for (int[] info : prerequisties) {
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
    }


    class CourseOrder {

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

            List<Integer> resList = new ArrayList<>();

            while (!queue.isEmpty()) {
                int u = queue.poll();
                resList.add(u);

                for (int v : edges.get(u)) {
                    --indeg[v];
                    if (indeg[v] == 0) {
                        queue.offer(v);
                    }
                }
            }

            if (resList.size() != numCourses) {
                return new int[]{};
            }

            int[] res = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                res[i] = resList.get(i);
            }
            return res;

        }

    }

    class Codec {

        public String serialize(TreeNode node) {

            StringBuilder res = new StringBuilder();

            Queue<TreeNode> queue = new LinkedList<>();

            if (root == null) return res.toString();

            queue.add(root);

            while (!queue.isEmpty()) {
                int currentSize = queue.size();

                for (int i = 0; i < currentSize; i++) {
                    TreeNode node = queue.poll();

                    if (node == null) {
                        res.append("null");
                    } else {
                        res.append(node).append(",");
                        queue.add(node.left);
                        queue.add(node.right);
                    }
                }
            }
            return res.toString();
        }


        public TreeNode deserialize(String data) {
            if (data == null || data.isEmpty()) return null;

            String[] nodes = data.split(",");

            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            for (int i = 1; i < nodes.length; i++) {
                    TreeNode parent = queue.poll();

                    if (!nodes[i].equals("null")) {
                        TreeNode left = new TreeNode(Integer.parseInt(nodes[i]));
                        parent.left = left;
                        queue.add(left);
                    }

                    if (i + 1 < nodes.length && !nodes[i + 1].equals("null")) {
                        TreeNode right = new TreeNode(Integer.parseInt(nodes[++i]));
                        parent.right = right;
                        queue.add(right);
                    }
            }

            return root;
        }
    }






}


