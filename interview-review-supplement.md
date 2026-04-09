# 面试高频题补充 — 你还缺的重要题目

> 基于 Blind 75、NeetCode 150、Grind 75 以及 2025-2026 各大公司真实面经，筛选出你的仓库中**尚未覆盖**的高频面试题。
> 每题附带：公司标签、考察核心、完整 Java 解法、复杂度分析。

**数据来源**: [NeetCode 150](https://neetcode.io/practice/practice/neetcode150) | [Blind 75](https://leetcode.com/problem-list/oizxjoit/) | [DSAPrep Microsoft 2026](https://www.dsaprep.dev/blog/microsoft-coding-interview-questions/) | [CodingInterviewAI](https://codinginterviewai.com/company-wise-leetcode-questions) | [Meta LeetCode 73题](https://medium.com/@johnadjanohoun/metas-most-asked-coding-interview-questions-the-complete-list-of-73-leetcode-problems-47e96767adc7) | [Amazon Top 120](https://leetcode.com/discuss/post/7474246/top-120-most-frequently-asked-amazon-sde-imwy/)

---

## 目录

1. [数组 & 哈希 — 缺失高频题](#1-数组--哈希--缺失高频题)
2. [双指针 & 滑动窗口 — 缺失高频题](#2-双指针--滑动窗口--缺失高频题)
3. [栈 — 缺失高频题](#3-栈--缺失高频题)
4. [链表 — 缺失高频题](#4-链表--缺失高频题)
5. [二叉树 — 缺失高频题](#5-二叉树--缺失高频题)
6. [图 — 缺失高频题](#6-图--缺失高频题)
7. [动态规划 — 缺失高频题](#7-动态规划--缺失高频题)
8. [贪心 & 区间 — 缺失高频题](#8-贪心--区间--缺失高频题)
9. [二分搜索 — 缺失高频题](#9-二分搜索--缺失高频题)
10. [堆 & 优先队列 — 缺失高频题](#10-堆--优先队列--缺失高频题)
11. [回溯 — 缺失高频题](#11-回溯--缺失高频题)
12. [Trie — 缺失高频题](#12-trie--缺失高频题)
13. [位运算 — 缺失高频题](#13-位运算--缺失高频题)
14. [数学 — 缺失高频题](#14-数学--缺失高频题)
15. [设计题 — 缺失高频题](#15-设计题--缺失高频题)
16. [Meta 专项补充](#16-meta-专项补充)
17. [Amazon 专项补充](#17-amazon-专项补充)
18. [Google 专项补充](#18-google-专项补充)
19. [Microsoft 专项补充](#19-microsoft-专项补充)
20. [Airbnb 专项补充](#20-airbnb-专项补充)

---

## 1. 数组 & 哈希 — 缺失高频题

### LC1 - Two Sum
> **Blind 75 / NeetCode 150** | 全部公司 | 面试第一题

**考察核心**: HashMap 一趟扫描 vs 暴力 O(n^2)；follow-up: 有序数组用双指针（你已有 LC167）

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(nums[i], i);
    }
    return new int[]{};
}
// 时间 O(n), 空间 O(n)
```

---

### LC217 - Contains Duplicate
> **Blind 75** | Amazon, Microsoft | Easy 热身题

**考察核心**: HashSet 判重，面试中常作为热身或 follow-up 引入

```java
public boolean containsDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int n : nums) {
        if (!set.add(n)) return true;
    }
    return false;
}
// 时间 O(n), 空间 O(n)
```

---

### LC242 - Valid Anagram
> **Blind 75** | Microsoft, Amazon | Easy

**考察核心**: 26位计数数组 vs 排序；follow-up: Unicode 字符怎么办？

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    for (int c : count) if (c != 0) return false;
    return true;
}
// 时间 O(n), 空间 O(1)
```

---

### LC347 - Top K Frequent Elements
> **NeetCode 150 / Blind 75** | Amazon, Microsoft, Meta | 你的 round3 中有实现

**考察核心**: 堆 O(nlogk) vs 桶排序 O(n) vs QuickSelect；与 LC215 对比

```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);

    // 桶排序 O(n)
    List<Integer>[] buckets = new List[nums.length + 1];
    for (var e : freq.entrySet()) {
        int f = e.getValue();
        if (buckets[f] == null) buckets[f] = new ArrayList<>();
        buckets[f].add(e.getKey());
    }

    int[] res = new int[k];
    int idx = 0;
    for (int i = buckets.length - 1; i >= 0 && idx < k; i--)
        if (buckets[i] != null)
            for (int n : buckets[i]) if (idx < k) res[idx++] = n;
    return res;
}
// 时间 O(n), 空间 O(n)
```

---

### LC271 - Encode and Decode Strings
> **NeetCode 150 / Blind 75** | Meta, Google

**考察核心**: 设计编解码协议：长度前缀 `len#str` 是最优解

```java
public String encode(List<String> strs) {
    StringBuilder sb = new StringBuilder();
    for (String s : strs) sb.append(s.length()).append('#').append(s);
    return sb.toString();
}

public List<String> decode(String s) {
    List<String> result = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
        int j = s.indexOf('#', i);
        int len = Integer.parseInt(s.substring(i, j));
        result.add(s.substring(j + 1, j + 1 + len));
        i = j + 1 + len;
    }
    return result;
}
// 时间 O(n), 空间 O(n)
```

---

### LC152 - Maximum Product Subarray
> **Blind 75 / NeetCode 150** | Microsoft, Amazon

**考察核心**: 与 LC53 对比 — 需要同时追踪最大和最小（负负得正），遇 0 重置

```java
public int maxProduct(int[] nums) {
    int max = nums[0], curMax = nums[0], curMin = nums[0];
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < 0) { int tmp = curMax; curMax = curMin; curMin = tmp; } // 交换
        curMax = Math.max(nums[i], curMax * nums[i]);
        curMin = Math.min(nums[i], curMin * nums[i]);
        max = Math.max(max, curMax);
    }
    return max;
}
// 时间 O(n), 空间 O(1)
```

---

### LC253 - Meeting Rooms II
> **NeetCode 150** | Microsoft, Airbnb, Google, Meta | 极高频

**考察核心**: 扫描线/排序分离 start/end，与 LC56 区间合并的区别

```java
public int minMeetingRooms(int[][] intervals) {
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    Arrays.sort(starts);
    Arrays.sort(ends);
    int rooms = 0, endPtr = 0;
    for (int s : starts) {
        if (s < ends[endPtr]) rooms++;
        else endPtr++;
    }
    return rooms;
}
// 时间 O(nlogn), 空间 O(n)
```

---

### LC36 - Valid Sudoku (你已有)

### LC75 - Sort Colors
> **NeetCode 150** | Microsoft, Meta | 经典三路快排

**考察核心**: 荷兰国旗问题 (Dutch National Flag)，一趟扫描 O(n) O(1)

```java
public void sortColors(int[] nums) {
    int lo = 0, mid = 0, hi = nums.length - 1;
    while (mid <= hi) {
        if (nums[mid] == 0) swap(nums, lo++, mid++);
        else if (nums[mid] == 1) mid++;
        else swap(nums, mid, hi--);
    }
}
private void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
// 时间 O(n), 空间 O(1)
```

---

## 2. 双指针 & 滑动窗口 — 缺失高频题

### LC125 - Valid Palindrome
> **Blind 75** | Microsoft, Meta | Easy

**考察核心**: 双指针+Character.isLetterOrDigit，注意大小写

```java
public boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        while (l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;
        while (l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;
        if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;
        l++; r--;
    }
    return true;
}
```

---

### LC424 - Longest Repeating Character Replacement
> **Blind 75 / NeetCode 150** | Google, Microsoft

**考察核心**: 滑动窗口 + maxFreq trick：窗口大小 - maxFreq <= k 时合法

```java
public int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int maxFreq = 0, maxLen = 0, left = 0;
    for (int right = 0; right < s.length(); right++) {
        maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
        // 窗口大小 - 最高频字符数 > k 时收缩
        while (right - left + 1 - maxFreq > k) {
            count[s.charAt(left++) - 'A']--;
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
// 时间 O(n), 空间 O(1)
```

---

### LC340 - Longest Substring with At Most K Distinct Characters
> **NeetCode 150** | Google, Microsoft, Airbnb

**考察核心**: 滑动窗口 + HashMap 维护不同字符数

```java
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0, maxLen = 0;
    for (int right = 0; right < s.length(); right++) {
        map.merge(s.charAt(right), 1, Integer::sum);
        while (map.size() > k) {
            char c = s.charAt(left++);
            map.merge(c, -1, Integer::sum);
            if (map.get(c) == 0) map.remove(c);
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

## 3. 栈 — 缺失高频题

### LC155 - Min Stack
> **Blind 75 / NeetCode 150** | Microsoft, Amazon | 你的 round2 中有实现

**考察核心**: 两个栈 or 一个栈存差值，O(1) getMin

```java
class MinStack {
    Deque<Integer> stack = new ArrayDeque<>();
    Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);
        minStack.push(minStack.isEmpty() ? val : Math.min(val, minStack.peek()));
    }
    public void pop() { stack.pop(); minStack.pop(); }
    public int top() { return stack.peek(); }
    public int getMin() { return minStack.peek(); }
}
```

---

### LC739 - Daily Temperatures
> **NeetCode 150** | Amazon, Microsoft

**考察核心**: 单调递减栈，类似 Next Greater Element 但存索引算天数差

```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
            int idx = stack.pop();
            result[idx] = i - idx;
        }
        stack.push(i);
    }
    return result;
}
// 时间 O(n), 空间 O(n)
```

---

### LC853 - Car Fleet
> **NeetCode 150** | Google

**考察核心**: 排序+单调栈思想：从终点倒序判断是否追上前车

```java
public int carFleet(int target, int[] position, int[] speed) {
    int n = position.length;
    int[][] cars = new int[n][2];
    for (int i = 0; i < n; i++) cars[i] = new int[]{position[i], speed[i]};
    Arrays.sort(cars, (a, b) -> b[0] - a[0]); // 按位置降序

    int fleets = 0;
    double lastTime = 0;
    for (int[] car : cars) {
        double time = (double)(target - car[0]) / car[1];
        if (time > lastTime) { // 不会被前面追上 → 新车队
            fleets++;
            lastTime = time;
        }
    }
    return fleets;
}
```

---

### LC1249 - Minimum Remove to Make Valid Parentheses
> **Meta 高频 Top 5** | Meta, Microsoft

**考察核心**: 栈记录非法括号索引，StringBuilder 移除

```java
public String minRemoveToMakeValid(String s) {
    Set<Integer> toRemove = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') stack.push(i);
        else if (s.charAt(i) == ')') {
            if (stack.isEmpty()) toRemove.add(i);
            else stack.pop();
        }
    }
    toRemove.addAll(stack);

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++)
        if (!toRemove.contains(i)) sb.append(s.charAt(i));
    return sb.toString();
}
```

---

## 4. 链表 — 缺失高频题

### LC287 - Find the Duplicate Number
> **NeetCode 150** | Google, Amazon

**考察核心**: Floyd 判圈法应用到数组（值作为next指针），O(1) 空间

```java
public int findDuplicate(int[] nums) {
    int slow = nums[0], fast = nums[0];
    do { slow = nums[slow]; fast = nums[nums[fast]]; } while (slow != fast);
    slow = nums[0];
    while (slow != fast) { slow = nums[slow]; fast = nums[fast]; }
    return slow;
}
// 时间 O(n), 空间 O(1)
```

---

### LC146 - LRU Cache (你已有 lrucache/LRUCache.java)

### LC876 - Middle of the Linked List
> **NeetCode 150** | Amazon, Microsoft | Easy

**考察核心**: 快慢指针找中点，LC143 的子步骤

```java
public ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
```

---

## 5. 二叉树 — 缺失高频题

### LC235 - LCA of BST
> **Blind 75 / NeetCode 150** | Microsoft, Amazon

**考察核心**: 利用 BST 性质：p,q 都小往左，都大往右，分叉点就是 LCA

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    while (root != null) {
        if (p.val < root.val && q.val < root.val) root = root.left;
        else if (p.val > root.val && q.val > root.val) root = root.right;
        else return root;
    }
    return null;
}
// 时间 O(h), 空间 O(1)
```

---

### LC572 - Subtree of Another Tree
> **Blind 75 / NeetCode 150** | Microsoft, Amazon

**考察核心**: 递归判断 isSameTree，时间 O(m*n)；进阶用序列化+KMP

```java
public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    if (root == null) return false;
    if (isSame(root, subRoot)) return true;
    return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
}

private boolean isSame(TreeNode a, TreeNode b) {
    if (a == null && b == null) return true;
    if (a == null || b == null) return false;
    return a.val == b.val && isSame(a.left, b.left) && isSame(a.right, b.right);
}
```

---

### LC543 - Diameter of Binary Tree
> **Blind 75 / NeetCode 150** | Meta, Amazon, Microsoft

**考察核心**: 全局 max 记录经过节点的最长路径，dfs 返回单侧深度

```java
int maxDiameter = 0;
public int diameterOfBinaryTree(TreeNode root) {
    depth(root);
    return maxDiameter;
}
private int depth(TreeNode node) {
    if (node == null) return 0;
    int left = depth(node.left), right = depth(node.right);
    maxDiameter = Math.max(maxDiameter, left + right);
    return 1 + Math.max(left, right);
}
```

---

### LC314 - Binary Tree Vertical Order Traversal
> **Meta Top 5 高频** | Meta, Microsoft

**考察核心**: BFS + column 编号，HashMap<Integer, List> 按列收集

```java
public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    Map<Integer, List<Integer>> map = new TreeMap<>();
    Queue<TreeNode> nodeQ = new LinkedList<>();
    Queue<Integer> colQ = new LinkedList<>();
    nodeQ.offer(root); colQ.offer(0);

    while (!nodeQ.isEmpty()) {
        TreeNode node = nodeQ.poll();
        int col = colQ.poll();
        map.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
        if (node.left != null) { nodeQ.offer(node.left); colQ.offer(col - 1); }
        if (node.right != null) { nodeQ.offer(node.right); colQ.offer(col + 1); }
    }
    result.addAll(map.values());
    return result;
}
```

---

### LC1650 - LCA of Binary Tree III (with parent pointer)
> **Meta 高频** | Meta

**考察核心**: 类似链表交叉——两指针走法，无需额外空间

```java
// Node 有 parent 指针
public Node lowestCommonAncestor(Node p, Node q) {
    Node a = p, b = q;
    while (a != b) {
        a = (a == null) ? q : a.parent;
        b = (b == null) ? p : b.parent;
    }
    return a;
}
// 时间 O(h), 空间 O(1)
```

---

## 6. 图 — 缺失高频题

### LC417 - Pacific Atlantic Water Flow
> **Blind 75 / NeetCode 150** | Google, Amazon

**考察核心**: 反向 DFS/BFS——从两个边界向内标记，求交集

```java
public List<List<Integer>> pacificAtlantic(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    boolean[][] pac = new boolean[m][n], atl = new boolean[m][n];

    for (int i = 0; i < m; i++) { dfs(heights, pac, i, 0); dfs(heights, atl, i, n - 1); }
    for (int j = 0; j < n; j++) { dfs(heights, pac, 0, j); dfs(heights, atl, m - 1, j); }

    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (pac[i][j] && atl[i][j]) result.add(List.of(i, j));
    return result;
}

private void dfs(int[][] h, boolean[][] visited, int i, int j) {
    visited[i][j] = true;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    for (int[] d : dirs) {
        int ni = i + d[0], nj = j + d[1];
        if (ni >= 0 && ni < h.length && nj >= 0 && nj < h[0].length
                && !visited[ni][nj] && h[ni][nj] >= h[i][j])
            dfs(h, visited, ni, nj);
    }
}
```

---

### LC695 - Max Area of Island
> **NeetCode 150** | Amazon, Microsoft

**考察核心**: LC200 的变种，DFS 计数面积

```java
public int maxAreaOfIsland(int[][] grid) {
    int max = 0;
    for (int i = 0; i < grid.length; i++)
        for (int j = 0; j < grid[0].length; j++)
            if (grid[i][j] == 1) max = Math.max(max, dfs(grid, i, j));
    return max;
}
private int dfs(int[][] g, int i, int j) {
    if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] == 0) return 0;
    g[i][j] = 0;
    return 1 + dfs(g, i+1, j) + dfs(g, i-1, j) + dfs(g, i, j+1) + dfs(g, i, j-1);
}
```

---

### LC743 - Network Delay Time
> **NeetCode 150** | Google, Amazon

**考察核心**: Dijkstra 最短路径模板

```java
public int networkDelayTime(int[][] times, int n, int k) {
    Map<Integer, List<int[]>> graph = new HashMap<>();
    for (int[] t : times) graph.computeIfAbsent(t[0], x -> new ArrayList<>()).add(new int[]{t[1], t[2]});

    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{k, 0});

    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        int u = cur[0], d = cur[1];
        if (d > dist[u]) continue;
        for (int[] edge : graph.getOrDefault(u, List.of())) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                pq.offer(new int[]{v, dist[v]});
            }
        }
    }

    int max = 0;
    for (int i = 1; i <= n; i++) {
        if (dist[i] == Integer.MAX_VALUE) return -1;
        max = Math.max(max, dist[i]);
    }
    return max;
}
```

---

### LC261 - Graph Valid Tree
> **Blind 75 / NeetCode 150** | Google, Microsoft

**考察核心**: n-1 条边 + 连通（Union-Find 或 DFS 检测环）

```java
public boolean validTree(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;

    for (int[] e : edges) {
        int px = find(parent, e[0]), py = find(parent, e[1]);
        if (px == py) return false; // 有环
        parent[px] = py;
    }
    return true;
}
private int find(int[] p, int x) {
    while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; }
    return x;
}
```

---

### LC323 - Number of Connected Components
> **Blind 75 / NeetCode 150** | Google, Microsoft

**考察核心**: Union-Find 基础应用

```java
public int countComponents(int n, int[][] edges) {
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    int count = n;
    for (int[] e : edges) {
        int px = find(parent, e[0]), py = find(parent, e[1]);
        if (px != py) { parent[px] = py; count--; }
    }
    return count;
}
private int find(int[] p, int x) {
    while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; }
    return x;
}
```

---

### LC787 - Cheapest Flights Within K Stops
> **NeetCode 150** | Google, Amazon | 你的 round2 中有 CheapestPrice

**考察核心**: Bellman-Ford 限制 k 轮 vs BFS+剪枝 vs Dijkstra+状态

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] prices = new int[n];
    Arrays.fill(prices, Integer.MAX_VALUE);
    prices[src] = 0;

    for (int i = 0; i <= k; i++) {
        int[] tmp = prices.clone();
        for (int[] f : flights) {
            if (prices[f[0]] == Integer.MAX_VALUE) continue;
            tmp[f[1]] = Math.min(tmp[f[1]], prices[f[0]] + f[2]);
        }
        prices = tmp;
    }
    return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];
}
// 时间 O(k * E), 空间 O(n)
```

---

### LC1570 - Dot Product of Two Sparse Vectors
> **Meta Top 3 高频** | Meta

**考察核心**: 稀疏表示（HashMap 或 pairs），双指针做内积

```java
class SparseVector {
    List<int[]> pairs; // [index, value]

    SparseVector(int[] nums) {
        pairs = new ArrayList<>();
        for (int i = 0; i < nums.length; i++)
            if (nums[i] != 0) pairs.add(new int[]{i, nums[i]});
    }

    public int dotProduct(SparseVector vec) {
        int result = 0, p = 0, q = 0;
        while (p < this.pairs.size() && q < vec.pairs.size()) {
            if (this.pairs.get(p)[0] == vec.pairs.get(q)[0])
                result += this.pairs.get(p++)[1] * vec.pairs.get(q++)[1];
            else if (this.pairs.get(p)[0] < vec.pairs.get(q)[0]) p++;
            else q++;
        }
        return result;
    }
}
```

---

## 7. 动态规划 — 缺失高频题

### LC416 - Partition Equal Subset Sum
> **Blind 75 / NeetCode 150** | Microsoft, Amazon | 你的 round5 中有 CanPartition

**考察核心**: 0/1背包问题，dp[j] 表示能否组成和 j

```java
public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int n : nums) sum += n;
    if (sum % 2 != 0) return false;
    int target = sum / 2;

    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int num : nums)
        for (int j = target; j >= num; j--)
            dp[j] = dp[j] || dp[j - num];
    return dp[target];
}
// 时间 O(n * target), 空间 O(target)
```

---

### LC518 - Coin Change II
> **NeetCode 150** | Amazon, Microsoft

**考察核心**: 完全背包求组合数，外层遍历 coin（避免重复计数）

```java
public int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins)
        for (int j = coin; j <= amount; j++)
            dp[j] += dp[j - coin];
    return dp[amount];
}
```

---

### LC647 - Palindromic Substrings
> **Blind 75 / NeetCode 150** | Microsoft, Amazon

**考察核心**: 中心扩展法 O(n^2)，与 LC5 同族

```java
public int countSubstrings(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
        count += expand(s, i, i);     // 奇数长度
        count += expand(s, i, i + 1); // 偶数长度
    }
    return count;
}
private int expand(String s, int l, int r) {
    int count = 0;
    while (l >= 0 && r < s.length() && s.charAt(l--) == s.charAt(r++)) count++;
    return count;
}
```

---

### LC312 - Burst Balloons
> **NeetCode 150** | Google

**考察核心**: 区间DP，dp[i][j] 表示戳破 (i,j) 之间气球的最大收益

```java
public int maxCoins(int[] nums) {
    int n = nums.length;
    int[] arr = new int[n + 2];
    arr[0] = arr[n + 1] = 1;
    for (int i = 0; i < n; i++) arr[i + 1] = nums[i];

    int[][] dp = new int[n + 2][n + 2];
    for (int len = 1; len <= n; len++) {
        for (int i = 1; i + len - 1 <= n; i++) {
            int j = i + len - 1;
            for (int k = i; k <= j; k++) {
                dp[i][j] = Math.max(dp[i][j],
                    dp[i][k-1] + arr[i-1] * arr[k] * arr[j+1] + dp[k+1][j]);
            }
        }
    }
    return dp[1][n];
}
```

---

### LC494 - Target Sum
> **NeetCode 150** | Google, Amazon

**考察核心**: 转化为 0/1 背包：sum(P) - sum(N) = target → sum(P) = (sum+target)/2

```java
public int findTargetSumWays(int[] nums, int target) {
    int sum = 0;
    for (int n : nums) sum += n;
    if ((sum + target) % 2 != 0 || sum < Math.abs(target)) return 0;
    int s = (sum + target) / 2;

    int[] dp = new int[s + 1];
    dp[0] = 1;
    for (int num : nums)
        for (int j = s; j >= num; j--)
            dp[j] += dp[j - num];
    return dp[s];
}
```

---

### LC1046 / LC973 - Last Stone Weight / K Closest Points
> **NeetCode 150** | Amazon, Meta

### LC973 - K Closest Points to Origin

**考察核心**: QuickSelect O(n) 平均 vs 最大堆 O(nlogk)

```java
public int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1]));
    for (int[] p : points) {
        maxHeap.offer(p);
        if (maxHeap.size() > k) maxHeap.poll();
    }
    return maxHeap.toArray(new int[0][]);
}
// 时间 O(nlogk), 空间 O(k)
```

---

## 8. 贪心 & 区间 — 缺失高频题

### LC763 - Partition Labels
> **NeetCode 150** | Amazon, Google

**考察核心**: 记录每个字符最后出现位置，贪心扩展区间

```java
public List<Integer> partitionLabels(String s) {
    int[] last = new int[26];
    for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;

    List<Integer> result = new ArrayList<>();
    int start = 0, end = 0;
    for (int i = 0; i < s.length(); i++) {
        end = Math.max(end, last[s.charAt(i) - 'a']);
        if (i == end) { result.add(end - start + 1); start = end + 1; }
    }
    return result;
}
```

---

### LC621 - Task Scheduler
> **NeetCode 150** | Microsoft, Amazon | 你的 round3 中有实现

**考察核心**: 贪心公式：(maxFreq-1)*(n+1) + countOfMax；或堆模拟

```java
public int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char t : tasks) freq[t - 'A']++;
    int maxFreq = 0, maxCount = 0;
    for (int f : freq) {
        if (f > maxFreq) { maxFreq = f; maxCount = 1; }
        else if (f == maxFreq) maxCount++;
    }
    return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + maxCount);
}
```

---

### LC252 - Meeting Rooms (Easy)
> **Blind 75** | Microsoft, Airbnb

**考察核心**: 排序后检查相邻区间是否重叠

```java
public boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    for (int i = 1; i < intervals.length; i++)
        if (intervals[i][0] < intervals[i - 1][1]) return false;
    return true;
}
```

---

## 9. 二分搜索 — 缺失高频题

### LC875 - Koko Eating Bananas
> **NeetCode 150** | Google, Amazon

**考察核心**: 答案二分模板：在可行解空间上二分，check 函数判断可行性

```java
public int minEatingSpeed(int[] piles, int h) {
    int lo = 1, hi = 0;
    for (int p : piles) hi = Math.max(hi, p);

    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        int hours = 0;
        for (int p : piles) hours += (p + mid - 1) / mid;
        if (hours <= h) hi = mid;
        else lo = mid + 1;
    }
    return lo;
}
```

---

### LC704 - Binary Search
> **NeetCode 150** | 全部公司 | Easy 模板题

```java
public int search(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return -1;
}
```

---

### LC981 - Time Based Key-Value Store (你已有)

---

## 10. 堆 & 优先队列 — 缺失高频题

### LC355 - Design Twitter
> **NeetCode 150** | 设计题

**考察核心**: HashMap + PriorityQueue + OOD 设计

```java
class Twitter {
    private int timestamp = 0;
    private Map<Integer, Set<Integer>> followMap = new HashMap<>();
    private Map<Integer, List<int[]>> tweetMap = new HashMap<>(); // userId -> [[time, tweetId]]

    public void postTweet(int userId, int tweetId) {
        tweetMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(new int[]{timestamp++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        Set<Integer> users = new HashSet<>(followMap.getOrDefault(userId, Set.of()));
        users.add(userId);
        for (int u : users)
            for (int[] t : tweetMap.getOrDefault(u, List.of())) pq.offer(t);

        List<Integer> feed = new ArrayList<>();
        while (!pq.isEmpty() && feed.size() < 10) feed.add(pq.poll()[1]);
        return feed;
    }

    public void follow(int followerId, int followeeId) {
        followMap.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        followMap.getOrDefault(followerId, Set.of()).remove(followeeId);
    }
}
```

---

### LC460 - LFU Cache
> **NeetCode 150** | Google, Microsoft | 极高频设计题

**考察核心**: HashMap + FreqMap + LinkedHashSet，O(1) 所有操作

```java
class LFUCache {
    int capacity, minFreq;
    Map<Integer, int[]> keyToValFreq = new HashMap<>();
    Map<Integer, LinkedHashSet<Integer>> freqToKeys = new HashMap<>();

    public LFUCache(int capacity) { this.capacity = capacity; }

    public int get(int key) {
        if (!keyToValFreq.containsKey(key)) return -1;
        int[] vf = keyToValFreq.get(key);
        updateFreq(key, vf[1]);
        vf[1]++;
        return vf[0];
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        if (keyToValFreq.containsKey(key)) {
            int[] vf = keyToValFreq.get(key);
            vf[0] = value;
            updateFreq(key, vf[1]);
            vf[1]++;
        } else {
            if (keyToValFreq.size() == capacity) {
                int evict = freqToKeys.get(minFreq).iterator().next();
                freqToKeys.get(minFreq).remove(evict);
                keyToValFreq.remove(evict);
            }
            keyToValFreq.put(key, new int[]{value, 1});
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFreq = 1;
        }
    }

    private void updateFreq(int key, int freq) {
        freqToKeys.get(freq).remove(key);
        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            if (minFreq == freq) minFreq++;
        }
        freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
    }
}
```

---

## 11. 回溯 — 缺失高频题

### LC77 - Combinations
> **NeetCode 150** | Microsoft

**考察核心**: C(n,k) 组合模板

```java
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), 1, n, k);
    return result;
}
private void backtrack(List<List<Integer>> res, List<Integer> path, int start, int n, int k) {
    if (path.size() == k) { res.add(new ArrayList<>(path)); return; }
    for (int i = start; i <= n - (k - path.size()) + 1; i++) { // 剪枝
        path.add(i);
        backtrack(res, path, i + 1, n, k);
        path.remove(path.size() - 1);
    }
}
```

---

### LC698 - Partition to K Equal Sum Subsets
> **NeetCode 150** | Google, Amazon

**考察核心**: LC473 升级版，k 路回溯 + 剪枝

```java
public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = 0;
    for (int n : nums) sum += n;
    if (sum % k != 0) return false;
    int target = sum / k;
    Arrays.sort(nums);
    return backtrack(nums, new int[k], nums.length - 1, target);
}

private boolean backtrack(int[] nums, int[] buckets, int idx, int target) {
    if (idx < 0) return true;
    for (int i = 0; i < buckets.length; i++) {
        if (buckets[i] + nums[idx] <= target) {
            buckets[i] += nums[idx];
            if (backtrack(nums, buckets, idx - 1, target)) return true;
            buckets[i] -= nums[idx];
        }
        if (buckets[i] == 0) break; // 剪枝：空桶等价
    }
    return false;
}
```

---

## 12. Trie — 缺失高频题

### LC211 - Design Add and Search Words
> **Blind 75 / NeetCode 150** | Meta, Microsoft

**考察核心**: Trie + DFS 处理通配符 `.`

```java
class WordDictionary {
    private WordDictionary[] children = new WordDictionary[26];
    private boolean isEnd;

    public void addWord(String word) {
        WordDictionary node = this;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new WordDictionary();
            node = node.children[c - 'a'];
        }
        node.isEnd = true;
    }

    public boolean search(String word) { return dfs(word, 0, this); }

    private boolean dfs(String word, int idx, WordDictionary node) {
        if (node == null) return false;
        if (idx == word.length()) return node.isEnd;
        char c = word.charAt(idx);
        if (c == '.') {
            for (WordDictionary child : node.children)
                if (dfs(word, idx + 1, child)) return true;
            return false;
        }
        return dfs(word, idx + 1, node.children[c - 'a']);
    }
}
```

---

### LC212 - Word Search II
> **Blind 75 / NeetCode 150** | Google, Amazon

**考察核心**: Trie + 矩阵 DFS 回溯，避免对每个单词重复搜索

```java
public List<String> findWords(char[][] board, String[] words) {
    TrieNode root = buildTrie(words);
    Set<String> result = new HashSet<>();
    for (int i = 0; i < board.length; i++)
        for (int j = 0; j < board[0].length; j++)
            dfs(board, i, j, root, result);
    return new ArrayList<>(result);
}

private void dfs(char[][] board, int i, int j, TrieNode node, Set<String> result) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return;
    char c = board[i][j];
    if (c == '#' || node.children[c - 'a'] == null) return;
    node = node.children[c - 'a'];
    if (node.word != null) result.add(node.word);

    board[i][j] = '#';
    dfs(board, i+1, j, node, result); dfs(board, i-1, j, node, result);
    dfs(board, i, j+1, node, result); dfs(board, i, j-1, node, result);
    board[i][j] = c;
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word;
}

private TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for (String w : words) {
        TrieNode node = root;
        for (char c : w.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new TrieNode();
            node = node.children[c - 'a'];
        }
        node.word = w;
    }
    return root;
}
```

---

## 13. 位运算 — 缺失高频题

### LC191 - Number of 1 Bits
> **Blind 75** | Microsoft

```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) { count++; n &= (n - 1); } // Brian Kernighan
    return count;
}
```

---

### LC338 - Counting Bits
> **Blind 75** | Microsoft

**考察核心**: dp[i] = dp[i >> 1] + (i & 1)

```java
public int[] countBits(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) dp[i] = dp[i >> 1] + (i & 1);
    return dp;
}
```

---

### LC371 - Sum of Two Integers (no + or -)
> **Blind 75** | Microsoft

**考察核心**: 位运算模拟加法：XOR求和 + AND求进位

```java
public int getSum(int a, int b) {
    while (b != 0) {
        int carry = (a & b) << 1;
        a = a ^ b;
        b = carry;
    }
    return a;
}
```

---

### LC268 - Missing Number
> **Blind 75** | Microsoft, Amazon

**考察核心**: XOR 所有索引和值，剩下的就是缺失的

```java
public int missingNumber(int[] nums) {
    int xor = nums.length;
    for (int i = 0; i < nums.length; i++) xor ^= i ^ nums[i];
    return xor;
}
```

---

## 14. 数学 — 缺失高频题

### LC202 - Happy Number
> **NeetCode 150** | Microsoft | Floyd 判圈

```java
public boolean isHappy(int n) {
    int slow = n, fast = getNext(n);
    while (fast != 1 && slow != fast) {
        slow = getNext(slow);
        fast = getNext(getNext(fast));
    }
    return fast == 1;
}
private int getNext(int n) {
    int sum = 0;
    while (n > 0) { sum += (n % 10) * (n % 10); n /= 10; }
    return sum;
}
```

---

### LC48 - Rotate Image (你已有)

### LC54 - Spiral Matrix (你已有)

### LC73 - Set Matrix Zeroes (你已有)

---

## 15. 设计题 — 缺失高频题

### LC380 - Insert Delete GetRandom O(1)
> **NeetCode 150** | Microsoft, Meta, Amazon

**考察核心**: HashMap + ArrayList，删除时与末尾交换

```java
class RandomizedSet {
    Map<Integer, Integer> valToIdx = new HashMap<>();
    List<Integer> list = new ArrayList<>();
    Random rand = new Random();

    public boolean insert(int val) {
        if (valToIdx.containsKey(val)) return false;
        valToIdx.put(val, list.size());
        list.add(val);
        return true;
    }

    public boolean remove(int val) {
        if (!valToIdx.containsKey(val)) return false;
        int idx = valToIdx.get(val);
        int last = list.get(list.size() - 1);
        list.set(idx, last);
        valToIdx.put(last, idx);
        list.remove(list.size() - 1);
        valToIdx.remove(val);
        return true;
    }

    public int getRandom() { return list.get(rand.nextInt(list.size())); }
}
```

---

### LC588 - Design In-Memory File System
> **NeetCode 150** | Airbnb, Microsoft | 你已有 memoryfile/

### LC362 - Design Hit Counter
> **NeetCode 150** | Microsoft, Amazon

**考察核心**: 环形数组（固定窗口 300s）或 Queue

```java
class HitCounter {
    private int[] times = new int[300];
    private int[] hits = new int[300];

    public void hit(int timestamp) {
        int idx = timestamp % 300;
        if (times[idx] != timestamp) { times[idx] = timestamp; hits[idx] = 1; }
        else hits[idx]++;
    }

    public int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++)
            if (timestamp - times[i] < 300) total += hits[i];
        return total;
    }
}
```

---

## 16. Meta 专项补充

> Meta 2025-2026 面经显示：DP 题减少，字符串/数组/图题增多，注重代码速度

| # | 题目 | 考察核心 | 难度 |
|---|------|---------|------|
| 1570 | Dot Product of Sparse Vectors | 稀疏表示+双指针内积 | Medium |
| 1249 | Min Remove to Make Valid Parentheses | 栈+StringBuilder | Medium |
| 314 | Binary Tree Vertical Order Traversal | BFS+列编号 | Medium |
| 1650 | LCA III (with parent pointer) | 双指针走法 | Medium |
| 408 | Valid Word Abbreviation | 双指针解析缩写 | Easy |
| 227 | Basic Calculator II | 栈处理 `+-*/` 无括号 | Medium |
| 791 | Custom Sort String | 自定义排序/计数排序 | Medium |
| 938 | Range Sum of BST | BST 剪枝递归 | Easy |
| 721 | Accounts Merge | Union-Find 合并邮箱 | Medium |
| 339 | Nested List Weight Sum | DFS 加权求和 | Medium |
| 346 | Moving Average from Data Stream | 环形数组/队列 | Easy |
| 523 | Continuous Subarray Sum | 前缀和 % k + HashMap | Medium |
| 1762 | Buildings With an Ocean View | 从右到左维护最大值 | Medium |
| 88 | Merge Sorted Array | 从后往前合并 | Easy |
| 426 | Convert BST to Sorted Doubly Linked List | 中序遍历+头尾连接 | Medium |

### LC227 - Basic Calculator II

```java
public int calculate(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    int num = 0;
    char op = '+';
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isDigit(c)) num = num * 10 + (c - '0');
        if ((!Character.isDigit(c) && c != ' ') || i == s.length() - 1) {
            switch (op) {
                case '+': stack.push(num); break;
                case '-': stack.push(-num); break;
                case '*': stack.push(stack.pop() * num); break;
                case '/': stack.push(stack.pop() / num); break;
            }
            op = c;
            num = 0;
        }
    }
    int result = 0;
    for (int n : stack) result += n;
    return result;
}
```

### LC721 - Accounts Merge

```java
public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, Integer> emailToId = new HashMap<>();
    Map<String, String> emailToName = new HashMap<>();
    int[] parent = new int[accounts.size()];
    for (int i = 0; i < parent.length; i++) parent[i] = i;

    for (int i = 0; i < accounts.size(); i++) {
        String name = accounts.get(i).get(0);
        for (int j = 1; j < accounts.get(i).size(); j++) {
            String email = accounts.get(i).get(j);
            emailToName.put(email, name);
            if (emailToId.containsKey(email))
                union(parent, i, emailToId.get(email));
            else
                emailToId.put(email, i);
        }
    }

    Map<Integer, TreeSet<String>> idToEmails = new HashMap<>();
    for (var e : emailToId.entrySet()) {
        int root = find(parent, e.getValue());
        idToEmails.computeIfAbsent(root, k -> new TreeSet<>()).add(e.getKey());
    }

    List<List<String>> result = new ArrayList<>();
    for (var e : idToEmails.entrySet()) {
        List<String> list = new ArrayList<>(e.getValue());
        list.add(0, emailToName.get(list.get(0)));
        result.add(list);
    }
    return result;
}

private int find(int[] p, int x) { while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; } return x; }
private void union(int[] p, int x, int y) { p[find(p, x)] = find(p, y); }
```

---

## 17. Amazon 专项补充

> Amazon 面经特点：OA 偏难，onsite 重 BFS/DFS + 实际系统场景 + Leadership Principles

| # | 题目 | 考察核心 | 难度 |
|---|------|---------|------|
| 937 | Reorder Data in Log Files | 自定义排序 Comparator | Medium |
| 973 | K Closest Points to Origin | QuickSelect / 堆 | Medium |
| 1167 | Min Cost to Connect Sticks | 贪心+最小堆 | Medium |
| 692 | Top K Frequent Words | 堆+自定义排序 | Medium |
| 819 | Most Common Word | HashMap+banned set | Easy |
| 572 | Subtree of Another Tree | 递归 isSameTree | Easy |
| 735 | Asteroid Collision | 栈模拟碰撞 | Medium |
| 1268 | Search Suggestions System | Trie+DFS / 排序+二分 | Medium |
| 348 | Design Tic-Tac-Toe | 行列对角线计数 O(1) | Medium |
| 1010 | Pairs of Songs with Total Duration % 60 | Two Sum 变种 (mod 60) | Medium |

### LC735 - Asteroid Collision

```java
public int[] asteroidCollision(int[] asteroids) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int a : asteroids) {
        boolean alive = true;
        while (alive && a < 0 && !stack.isEmpty() && stack.peek() > 0) {
            if (stack.peek() < -a) stack.pop();
            else if (stack.peek() == -a) { stack.pop(); alive = false; }
            else alive = false;
        }
        if (alive) stack.push(a);
    }
    int[] result = new int[stack.size()];
    for (int i = result.length - 1; i >= 0; i--) result[i] = stack.pop();
    return result;
}
```

### LC1268 - Search Suggestions System

```java
public List<List<String>> suggestedProducts(String[] products, String searchWord) {
    Arrays.sort(products);
    List<List<String>> result = new ArrayList<>();
    String prefix = "";
    int start = 0;
    for (char c : searchWord.toCharArray()) {
        prefix += c;
        // 二分查找第一个 >= prefix 的位置
        start = lowerBound(products, start, prefix);
        List<String> suggestions = new ArrayList<>();
        for (int i = start; i < Math.min(start + 3, products.length); i++) {
            if (products[i].startsWith(prefix)) suggestions.add(products[i]);
            else break;
        }
        result.add(suggestions);
    }
    return result;
}

private int lowerBound(String[] arr, int start, String prefix) {
    int lo = start, hi = arr.length;
    while (lo < hi) {
        int mid = (lo + hi) / 2;
        if (arr[mid].compareTo(prefix) < 0) lo = mid + 1;
        else hi = mid;
    }
    return lo;
}
```

---

## 18. Google 专项补充

> Google 面经特点：注重算法深度、最优解推导、复杂度证明

| # | 题目 | 考察核心 | 难度 |
|---|------|---------|------|
| 41 | First Missing Positive (你已有) | 原地哈希 | Hard |
| 312 | Burst Balloons | 区间DP | Hard |
| 494 | Target Sum | 0/1背包转化 | Medium |
| 875 | Koko Eating Bananas | 答案二分 | Medium |
| 417 | Pacific Atlantic Water Flow | 反向 DFS | Medium |
| 743 | Network Delay Time | Dijkstra | Medium |
| 261 | Graph Valid Tree | Union-Find / DFS | Medium |
| 853 | Car Fleet | 排序+单调栈思想 | Medium |
| 1584 | Min Cost to Connect All Points | Prim/Kruskal MST | Medium |
| 778 | Swim in Rising Water | 二分+BFS / Dijkstra | Hard |
| 269 | Alien Dictionary | 拓扑排序 | Hard |

### LC269 - Alien Dictionary

```java
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    for (String w : words) for (char c : w.toCharArray()) { graph.putIfAbsent(c, new HashSet<>()); indegree.putIfAbsent(c, 0); }

    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i], w2 = words[i + 1];
        if (w1.length() > w2.length() && w1.startsWith(w2)) return ""; // invalid
        for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
            if (w1.charAt(j) != w2.charAt(j)) {
                if (graph.get(w1.charAt(j)).add(w2.charAt(j)))
                    indegree.merge(w2.charAt(j), 1, Integer::sum);
                break;
            }
        }
    }

    Queue<Character> queue = new LinkedList<>();
    for (var e : indegree.entrySet()) if (e.getValue() == 0) queue.offer(e.getKey());
    StringBuilder sb = new StringBuilder();
    while (!queue.isEmpty()) {
        char c = queue.poll();
        sb.append(c);
        for (char next : graph.get(c))
            if (indegree.merge(next, -1, Integer::sum) == 0) queue.offer(next);
    }
    return sb.length() == indegree.size() ? sb.toString() : "";
}
```

### LC1584 - Min Cost to Connect All Points (Prim's MST)

```java
public int minCostConnectPoints(int[][] points) {
    int n = points.length;
    boolean[] visited = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // [node, cost]
    pq.offer(new int[]{0, 0});
    int totalCost = 0, edges = 0;

    while (edges < n) {
        int[] cur = pq.poll();
        if (visited[cur[0]]) continue;
        visited[cur[0]] = true;
        totalCost += cur[1];
        edges++;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int dist = Math.abs(points[cur[0]][0] - points[i][0]) + Math.abs(points[cur[0]][1] - points[i][1]);
                pq.offer(new int[]{i, dist});
            }
        }
    }
    return totalCost;
}
```

---

## 19. Microsoft 专项补充

> 基于 2025-2026 Microsoft SDE 面经，补充你缺失的高频题

| # | 题目 | 考察核心 | 难度 |
|---|------|---------|------|
| 1 | Two Sum | HashMap 一趟 | Easy |
| 155 | Min Stack | 辅助栈 O(1) getMin | Medium |
| 380 | Insert Delete GetRandom O(1) | HashMap+ArrayList | Medium |
| 75 | Sort Colors | 三路快排/荷兰国旗 | Medium |
| 152 | Maximum Product Subarray | 双 track max/min | Medium |
| 125 | Valid Palindrome | 双指针+字符判断 | Easy |
| 543 | Diameter of Binary Tree | DFS 返回深度，全局记录直径 | Easy |
| 572 | Subtree of Another Tree | 递归 isSameTree | Easy |
| 287 | Find the Duplicate Number | Floyd 判圈 | Medium |
| 739 | Daily Temperatures | 单调递减栈 | Medium |
| 362 | Design Hit Counter | 环形数组 | Medium |

---

## 20. Airbnb 专项补充

> Airbnb 独特面试题——偏实际业务场景

| # | 题目 | 考察核心 | 难度 |
|---|------|---------|------|
| 336 | Palindrome Pairs | Trie+回文判断 | Hard |
| 252 | Meeting Rooms | 排序+区间检查 | Easy |
| 253 | Meeting Rooms II | 扫描线/堆 | Medium |
| 269 | Alien Dictionary | 拓扑排序 | Hard |
| 10 | Regex Matching (你已有) | 二维DP | Hard |
| 31 | Next Permutation (你已有) | 找降序点+交换+反转 | Medium |
| 251 | Flatten 2D Vector | 迭代器设计 | Medium |
| 755 | Pour Water | 模拟题 | Medium |
| 787 | Cheapest Flights Within K Stops | Bellman-Ford | Medium |

### LC336 - Palindrome Pairs

```java
public List<List<Integer>> palindromePairs(String[] words) {
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < words.length; i++) map.put(words[i], i);

    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < words.length; i++) {
        String w = words[i];
        for (int j = 0; j <= w.length(); j++) {
            String left = w.substring(0, j), right = w.substring(j);
            // 如果 left 是回文，检查 reverse(right) 是否存在
            if (isPalindrome(left)) {
                String revRight = new StringBuilder(right).reverse().toString();
                if (map.containsKey(revRight) && map.get(revRight) != i)
                    result.add(List.of(map.get(revRight), i));
            }
            // 如果 right 是回文且非空，检查 reverse(left) 是否存在
            if (right.length() > 0 && isPalindrome(right)) {
                String revLeft = new StringBuilder(left).reverse().toString();
                if (map.containsKey(revLeft) && map.get(revLeft) != i)
                    result.add(List.of(i, map.get(revLeft)));
            }
        }
    }
    return result;
}

private boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) if (s.charAt(l++) != s.charAt(r--)) return false;
    return true;
}
```

---

## 总结：你还需要补充的题目清单

### 优先级 P0（Blind 75 核心缺失，必须补）

| # | 题目 | 类型 |
|---|------|------|
| 1 | Two Sum | 数组/HashMap |
| 217 | Contains Duplicate | 数组/HashSet |
| 242 | Valid Anagram | 字符串 |
| 125 | Valid Palindrome | 双指针 |
| 152 | Maximum Product Subarray | DP |
| 191 | Number of 1 Bits | 位运算 |
| 338 | Counting Bits | 位运算 |
| 268 | Missing Number | 位运算 |
| 371 | Sum of Two Integers | 位运算 |
| 235 | LCA of BST | 树 |
| 572 | Subtree of Another Tree | 树 |
| 543 | Diameter of Binary Tree | 树 |
| 417 | Pacific Atlantic Water Flow | 图/DFS |
| 261 | Graph Valid Tree | 图/Union-Find |
| 323 | Number of Connected Components | 图/Union-Find |
| 647 | Palindromic Substrings | DP |
| 416 | Partition Equal Subset Sum | DP/背包 |
| 424 | Longest Repeating Character Replacement | 滑动窗口 |
| 211 | Design Add and Search Words | Trie |
| 212 | Word Search II | Trie+回溯 |
| 271 | Encode and Decode Strings | 设计 |

### 优先级 P1（NeetCode 150 + 公司高频缺失）

| # | 题目 | 类型 | 公司 |
|---|------|------|------|
| 253 | Meeting Rooms II | 区间 | Microsoft, Airbnb, Google |
| 347 | Top K Frequent Elements | 堆/桶排序 | Amazon, Microsoft |
| 155 | Min Stack | 设计 | Microsoft |
| 739 | Daily Temperatures | 单调栈 | Amazon |
| 875 | Koko Eating Bananas | 二分 | Google |
| 743 | Network Delay Time | Dijkstra | Google |
| 787 | Cheapest Flights Within K Stops | 最短路径 | Google, Airbnb |
| 460 | LFU Cache | 设计 | Google, Microsoft |
| 380 | Insert Delete GetRandom O(1) | 设计 | Microsoft, Meta |
| 621 | Task Scheduler | 贪心 | Microsoft |
| 763 | Partition Labels | 贪心 | Amazon |
| 312 | Burst Balloons | 区间DP | Google |
| 494 | Target Sum | 背包DP | Google |
| 518 | Coin Change II | 背包DP | Amazon |
| 269 | Alien Dictionary | 拓扑排序 | Google, Airbnb |
| 1584 | Min Cost to Connect All Points | MST | Google |
| 336 | Palindrome Pairs | Trie+回文 | Airbnb |

### 优先级 P2（Meta 2025-2026 高频特色题）

| # | 题目 | 类型 |
|---|------|------|
| 1570 | Dot Product of Sparse Vectors | 稀疏向量 |
| 1249 | Min Remove to Make Valid Parentheses | 栈 |
| 314 | Binary Tree Vertical Order Traversal | BFS |
| 1650 | LCA III (with parent pointer) | 树 |
| 227 | Basic Calculator II | 栈 |
| 721 | Accounts Merge | Union-Find |
| 523 | Continuous Subarray Sum | 前缀和 |
| 1762 | Buildings With an Ocean View | 栈/数组 |
| 362 | Design Hit Counter | 设计 |

---

> **总补充量：约 60 道高频题**，覆盖了 Blind 75、NeetCode 150 和各公司 2025-2026 面经中你尚未练习的重要题目。
> 建议按 P0 → P1 → P2 的优先级逐步补充练习。

Sources:
- [NeetCode 150](https://neetcode.io/practice/practice/neetcode150)
- [Blind 75](https://leetcode.com/problem-list/oizxjoit/)
- [DSAPrep Microsoft 2026](https://www.dsaprep.dev/blog/microsoft-coding-interview-questions/)
- [CodingInterviewAI Company-Wise](https://codinginterviewai.com/company-wise-leetcode-questions)
- [Meta LeetCode 73题](https://medium.com/@johnadjanohoun/metas-most-asked-coding-interview-questions-the-complete-list-of-73-leetcode-problems-47e96767adc7)
- [Amazon Top 120 SDE-1](https://leetcode.com/discuss/post/7474246/top-120-most-frequently-asked-amazon-sde-imwy/)
- [Microsoft SDE-2 Recent 2025](https://leetcode.com/discuss/interview-question/6403987/Microsoft-SDE-2-Recent-questions-2025-or-Consolidated/)
- [Google 2026 Interview Prep Roadmap](https://gist.github.com/carefree-ladka/6d1722421f9e1e46bd2dbdb5ea1b4684)
- [Meta Variant Compilation](https://leetcode.com/discuss/post/6615244/meta-variant-compilation-by-codingwithmi-0pm7/)
- [Airbnb Coding Questions 2026](https://prachub.com/companies/airbnb/categories/coding-and-algorithms)
