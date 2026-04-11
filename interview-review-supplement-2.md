# 面试高频题补充 (第二批) — 2025-2026 最新趋势

> 基于 2025-2026 最新 FAANG 面经、LeetCode 热题频率统计，筛选出前两份文档中**尚未覆盖**的高频面试题。
> 重点补充：并查集、二分答案、Meta/Amazon/Google 最新高频题、迭代器设计题。

**数据来源**: 2025-2026 LeetCode Discuss 面经 | Blind 75 / NeetCode 150 / Grind 169 | Meta E4-E5 面经汇总 | Amazon SDE-2 Top 100 | Google L4-L5 面经

---

## 目录

1. [Meta 2025-2026 高频专题](#1-meta-2025-2026-高频专题)
2. [并查集 (Union Find)](#2-并查集-union-find)
3. [二分答案 (Binary Search on Answer)](#3-二分答案-binary-search-on-answer)
4. [单调栈进阶](#4-单调栈进阶)
5. [前缀和进阶](#5-前缀和进阶)
6. [迭代器 & 设计题](#6-迭代器--设计题)
7. [树的进阶高频题](#7-树的进阶高频题)
8. [BFS 进阶](#8-bfs-进阶)
9. [区间进阶](#9-区间进阶)
10. [字符串 & 栈](#10-字符串--栈)
11. [DP 进阶高频](#11-dp-进阶高频)
12. [贪心进阶](#12-贪心进阶)
13. [Amazon 2025-2026 专项](#13-amazon-2025-2026-专项)
14. [Google 2025-2026 专项](#14-google-2025-2026-专项)
15. [总结：优先级清单](#15-总结优先级清单)

---

## 1. Meta 2025-2026 高频专题

> Meta (Facebook) 面试在 2025-2026 出现了明显的高频题集中化趋势，以下题目在多次面经中反复出现。

### LC528 - Random Pick with Weight
> **Meta Top 5** | 前缀和 + 二分查找 | Medium

**考察核心**: 加权随机采样 — 构建前缀和数组，用二分查找定位随机数落入的区间

```java
class Solution {
    private int[] prefixSum;
    private Random random;

    public Solution(int[] w) {
        random = new Random();
        prefixSum = new int[w.length];
        prefixSum[0] = w[0];
        for (int i = 1; i < w.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + w[i];
        }
    }

    public int pickIndex() {
        int target = random.nextInt(prefixSum[prefixSum.length - 1]) + 1;
        // 二分查找第一个 >= target 的位置
        int lo = 0, hi = prefixSum.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefixSum[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
// 构造 O(n), pickIndex O(logn)
```

**面试追问**:
- 为什么用 `nextInt(total) + 1` 而不是 `nextInt(total)`？→ 保证 target 在 [1, total] 范围
- 能否用 TreeMap 实现？→ 可以，floorKey 或 ceilingKey

---

### LC636 - Exclusive Time of Functions
> **Meta Top 10** | 栈 | Medium

**考察核心**: 函数调用栈的时间统计 — 用栈模拟函数调用，遇到 end 弹栈并计算时间

```java
public int[] exclusiveTime(int n, List<String> logs) {
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    int prevTime = 0;

    for (String log : logs) {
        String[] parts = log.split(":");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        int time = Integer.parseInt(parts[2]);

        if (type.equals("start")) {
            if (!stack.isEmpty()) {
                result[stack.peek()] += time - prevTime;
            }
            stack.push(id);
            prevTime = time;
        } else { // end
            result[stack.pop()] += time - prevTime + 1;
            prevTime = time + 1;
        }
    }
    return result;
}
// 时间 O(n), 空间 O(n)
```

---

### LC1762 - Buildings With an Ocean View
> **Meta Top 5** | 单调栈/逆序扫描 | Medium

**考察核心**: 从右往左扫描，维护右侧最大高度，当前大于最大才能看到海

```java
public int[] findBuildings(int[] heights) {
    List<Integer> result = new ArrayList<>();
    int maxSoFar = 0;
    for (int i = heights.length - 1; i >= 0; i--) {
        if (heights[i] > maxSoFar) {
            result.add(i);
            maxSoFar = heights[i];
        }
    }
    Collections.reverse(result);
    return result.stream().mapToInt(Integer::intValue).toArray();
}
// 时间 O(n), 空间 O(1) 不算输出
```

---

### LC523 - Continuous Subarray Sum
> **Meta Top 10** | 前缀和 + HashMap | Medium

**考察核心**: 前缀和对 k 取余，如果两个位置余数相同，则中间子数组和是 k 的倍数。注意子数组长度 >= 2。

```java
public boolean checkSubarraySum(int[] nums, int k) {
    // key: prefix_sum % k, value: 最早出现的 index
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1); // 空前缀余数为 0
    int prefixSum = 0;
    for (int i = 0; i < nums.length; i++) {
        prefixSum += nums[i];
        int remainder = prefixSum % k;
        if (map.containsKey(remainder)) {
            if (i - map.get(remainder) >= 2) return true;
        } else {
            map.put(remainder, i);
        }
    }
    return false;
}
// 时间 O(n), 空间 O(min(n, k))
```

---

### LC791 - Custom Sort String
> **Meta 高频** | 计数排序 | Medium

**考察核心**: 按照自定义顺序排序字符串 — 计数法 O(n)

```java
public String customSortString(String order, String s) {
    int[] count = new int[26];
    for (char c : s.toCharArray()) count[c - 'a']++;

    StringBuilder sb = new StringBuilder();
    // 按 order 中的顺序输出
    for (char c : order.toCharArray()) {
        while (count[c - 'a']-- > 0) sb.append(c);
    }
    // 输出 order 中没有的字符
    for (int i = 0; i < 26; i++) {
        while (count[i]-- > 0) sb.append((char) ('a' + i));
    }
    return sb.toString();
}
// 时间 O(n), 空间 O(1)
```

---

### LC339 - Nested List Weight Sum
> **Meta / Airbnb** | DFS | Medium

**考察核心**: 嵌套列表按深度加权求和 — DFS 递归，每深一层权重 +1

```java
public int depthSum(List<NestedInteger> nestedList) {
    return dfs(nestedList, 1);
}

private int dfs(List<NestedInteger> list, int depth) {
    int sum = 0;
    for (NestedInteger ni : list) {
        if (ni.isInteger()) {
            sum += ni.getInteger() * depth;
        } else {
            sum += dfs(ni.getList(), depth + 1);
        }
    }
    return sum;
}
// 时间 O(n), 空间 O(depth)
```

**Follow-up LC364 - Nested List Weight Sum II**: 反向加权（越深的权重越小）
→ 先求最大深度 maxDepth，然后用 `(maxDepth - depth + 1)` 作为权重

---

### LC346 - Moving Average from Data Stream
> **Meta** | 队列 | Easy

**考察核心**: 定长滑动窗口求平均值 — 队列维护窗口

```java
class MovingAverage {
    private Queue<Integer> queue;
    private int size;
    private double sum;

    public MovingAverage(int size) {
        this.queue = new LinkedList<>();
        this.size = size;
        this.sum = 0;
    }

    public double next(int val) {
        if (queue.size() == size) {
            sum -= queue.poll();
        }
        queue.offer(val);
        sum += val;
        return sum / queue.size();
    }
}
// next: O(1) 时间, O(size) 空间
```

---

### LC658 - Find K Closest Elements
> **Meta / Google** | 二分查找 | Medium

**考察核心**: 二分找左边界 — 在 `[0, n-k]` 范围内二分，比较 `x - arr[mid]` 和 `arr[mid+k] - x`

```java
public List<Integer> findClosestElements(int[] arr, int k, int x) {
    int lo = 0, hi = arr.length - k;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        // 如果 x 离 arr[mid+k] 更近（或相等），左边界右移
        if (x - arr[mid] > arr[mid + k] - x) {
            lo = mid + 1;
        } else {
            hi = mid;
        }
    }
    List<Integer> result = new ArrayList<>();
    for (int i = lo; i < lo + k; i++) result.add(arr[i]);
    return result;
}
// 时间 O(log(n-k) + k), 空间 O(1)
```

---

## 2. 并查集 (Union Find)

> 并查集是面试中的重要数据结构，你的仓库几乎没有覆盖（仅 LC928 用到）。以下是核心题目。

### Union Find 模板

```java
class UnionFind {
    private int[] parent, rank;
    private int count; // 连通分量数

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]); // 路径压缩
        return parent[x];
    }

    public boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false;
        // 按秩合并
        if (rank[px] < rank[py]) { int tmp = px; px = py; py = tmp; }
        parent[py] = px;
        if (rank[px] == rank[py]) rank[px]++;
        count--;
        return true;
    }

    public int getCount() { return count; }
}
// find: 近 O(1) (阿克曼反函数), union: 近 O(1)
```

---

### LC547 - Number of Provinces (Friend Circles)
> **Blind 75 / NeetCode 150** | Union Find / DFS | Medium

**考察核心**: 经典连通分量问题 — DFS 或 Union Find 均可

```java
// 解法1: Union Find
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    UnionFind uf = new UnionFind(n);
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (isConnected[i][j] == 1) uf.union(i, j);
        }
    }
    return uf.getCount();
}

// 解法2: DFS
public int findCircleNumDFS(int[][] isConnected) {
    int n = isConnected.length;
    boolean[] visited = new boolean[n];
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(isConnected, visited, i);
            count++;
        }
    }
    return count;
}

private void dfs(int[][] isConnected, boolean[] visited, int i) {
    visited[i] = true;
    for (int j = 0; j < isConnected.length; j++) {
        if (isConnected[i][j] == 1 && !visited[j]) {
            dfs(isConnected, visited, j);
        }
    }
}
// 时间 O(n^2), 空间 O(n)
```

---

### LC684 - Redundant Connection
> **NeetCode 150** | Union Find | Medium

**考察核心**: 无向图中找多余的边 — 逐条加边，第一条导致环的边就是答案

```java
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    UnionFind uf = new UnionFind(n + 1); // 节点从 1 开始
    for (int[] edge : edges) {
        if (!uf.union(edge[0], edge[1])) {
            return edge; // 已经在同一集合，加这条边会成环
        }
    }
    return new int[0];
}
// 时间 O(n * α(n)) ≈ O(n), 空间 O(n)
```

---

### LC990 - Satisfiability of Equality Equations
> **Google / Microsoft** | Union Find | Medium

**考察核心**: 先处理所有 `==` 关系做 union，再检查所有 `!=` 关系是否矛盾

```java
public boolean equationsPossible(String[] equations) {
    UnionFind uf = new UnionFind(26);
    // 第一遍：处理等式
    for (String eq : equations) {
        if (eq.charAt(1) == '=') {
            uf.union(eq.charAt(0) - 'a', eq.charAt(3) - 'a');
        }
    }
    // 第二遍：检查不等式
    for (String eq : equations) {
        if (eq.charAt(1) == '!') {
            if (uf.find(eq.charAt(0) - 'a') == uf.find(eq.charAt(3) - 'a')) {
                return false;
            }
        }
    }
    return true;
}
// 时间 O(n * α(26)) ≈ O(n), 空间 O(1)
```

---

## 3. 二分答案 (Binary Search on Answer)

> 「二分答案」是一种重要的二分搜索范式：答案在某个范围内单调，二分判断答案是否可行。

### LC1011 - Capacity To Ship Packages Within D Days
> **Amazon Top 20 / Google** | 二分答案 | Medium

**考察核心**: 二分运载能力，check 函数判断能否在 D 天内运完

```java
public int shipWithinDays(int[] weights, int days) {
    int lo = 0, hi = 0;
    for (int w : weights) {
        lo = Math.max(lo, w); // 至少能装最重的一个
        hi += w;              // 最多一天装完所有
    }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (canShip(weights, days, mid)) hi = mid;
        else lo = mid + 1;
    }
    return lo;
}

private boolean canShip(int[] weights, int days, int capacity) {
    int daysNeeded = 1, currentLoad = 0;
    for (int w : weights) {
        if (currentLoad + w > capacity) {
            daysNeeded++;
            currentLoad = 0;
        }
        currentLoad += w;
    }
    return daysNeeded <= days;
}
// 时间 O(n * log(sum)), 空间 O(1)
```

---

### LC410 - Split Array Largest Sum
> **Google Top 20** | 二分答案 / DP | Hard

**考察核心**: 与 LC1011 几乎相同的模板！二分最大子数组和，判断能否分成 <= k 组

```java
public int splitArray(int[] nums, int k) {
    int lo = 0, hi = 0;
    for (int n : nums) {
        lo = Math.max(lo, n);
        hi += n;
    }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (canSplit(nums, k, mid)) hi = mid;
        else lo = mid + 1;
    }
    return lo;
}

private boolean canSplit(int[] nums, int k, int maxSum) {
    int groups = 1, currentSum = 0;
    for (int n : nums) {
        if (currentSum + n > maxSum) {
            groups++;
            currentSum = 0;
        }
        currentSum += n;
    }
    return groups <= k;
}
// 时间 O(n * log(sum)), 空间 O(1)
```

**面试技巧**: LC1011 和 LC410 本质上是同一道题，掌握这个模板可以解一类问题。

---

### LC1539 - Kth Missing Positive Number
> **Meta / Amazon** | 二分查找 | Easy

**考察核心**: arr[i] 之前缺失的正整数个数 = arr[i] - (i + 1)，二分查找

```java
public int findKthPositive(int[] arr, int k) {
    int lo = 0, hi = arr.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        // arr[mid] 之前缺失了 arr[mid] - (mid + 1) 个数
        if (arr[mid] - (mid + 1) < k) lo = mid + 1;
        else hi = mid;
    }
    // lo 是第一个「缺失数 >= k」的位置
    // 答案 = lo + k（lo 个实际存在的数 + k 个缺失的数）
    return lo + k;
}
// 时间 O(logn), 空间 O(1)
```

---

## 4. 单调栈进阶

### LC907 - Sum of Subarray Minimums
> **Amazon / Google** | 单调栈 | Medium

**考察核心**: 对每个元素，求它作为最小值的子数组个数 — 用单调递增栈找左右边界

```java
public int sumSubarrayMins(int[] arr) {
    int MOD = 1_000_000_007;
    int n = arr.length;
    // left[i]: 左边第一个 < arr[i] 的距离（含自身）
    // right[i]: 右边第一个 <= arr[i] 的距离（含自身）
    int[] left = new int[n], right = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    // 计算 left
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) stack.pop();
        left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
        stack.push(i);
    }

    stack.clear();

    // 计算 right
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) stack.pop();
        right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
        stack.push(i);
    }

    long result = 0;
    for (int i = 0; i < n; i++) {
        result = (result + (long) arr[i] * left[i] % MOD * right[i]) % MOD;
    }
    return (int) result;
}
// 时间 O(n), 空间 O(n)
```

**面试追问**: 为什么左边用 `>=` 右边用 `>`？→ 避免重复计算相同元素

---

## 5. 前缀和进阶

### LC525 - Contiguous Array
> **Meta Top 15** | 前缀和 + HashMap | Medium

**考察核心**: 将 0 视为 -1，问题转化为「和为 0 的最长子数组」— 前缀和 + 首次出现位置

```java
public int findMaxLength(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);
    int maxLen = 0, prefixSum = 0;
    for (int i = 0; i < nums.length; i++) {
        prefixSum += nums[i] == 0 ? -1 : 1;
        if (map.containsKey(prefixSum)) {
            maxLen = Math.max(maxLen, i - map.get(prefixSum));
        } else {
            map.put(prefixSum, i);
        }
    }
    return maxLen;
}
// 时间 O(n), 空间 O(n)
```

---

### LC303 - Range Sum Query - Immutable
> **基础必备** | 前缀和 | Easy

**考察核心**: 前缀和模板题 — `sumRange(l, r) = prefix[r+1] - prefix[l]`

```java
class NumArray {
    private int[] prefix;

    public NumArray(int[] nums) {
        prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
// 构造 O(n), 查询 O(1)
```

---

## 6. 迭代器 & 设计题

### LC173 - Binary Search Tree Iterator
> **Meta / Microsoft** | 栈迭代 | Medium

**考察核心**: 用栈模拟中序遍历 — 初始化把左链全压栈，next() 弹栈并处理右子树

```java
class BSTIterator {
    private Deque<TreeNode> stack = new ArrayDeque<>();

    public BSTIterator(TreeNode root) {
        pushLeft(root);
    }

    public int next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}
// next: 均摊 O(1), hasNext: O(1), 空间 O(h)
```

---

### LC341 - Flatten Nested List Iterator
> **Meta / Airbnb** | 栈 | Medium

**考察核心**: 用栈逆序压入嵌套列表，hasNext() 时展开栈顶直到是整数

```java
public class NestedIterator implements Iterator<Integer> {
    private Deque<NestedInteger> stack;

    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new ArrayDeque<>();
        // 逆序压栈
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            List<NestedInteger> list = stack.pop().getList();
            for (int i = list.size() - 1; i >= 0; i--) {
                stack.push(list.get(i));
            }
        }
        return !stack.isEmpty();
    }
}
// next: O(1), hasNext: 均摊 O(1)
```

---

### LC622 - Design Circular Queue
> **Amazon** | 数组设计 | Medium

**考察核心**: 环形数组 — head/tail 指针取模实现循环

```java
class MyCircularQueue {
    private int[] data;
    private int head, tail, size, capacity;

    public MyCircularQueue(int k) {
        data = new int[k];
        capacity = k;
        head = 0;
        tail = 0;
        size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;
        data[tail] = value;
        tail = (tail + 1) % capacity;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;
        head = (head + 1) % capacity;
        size--;
        return true;
    }

    public int Front() { return isEmpty() ? -1 : data[head]; }
    public int Rear() { return isEmpty() ? -1 : data[(tail - 1 + capacity) % capacity]; }
    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == capacity; }
}
// 所有操作 O(1)
```

---

### LC1472 - Design Browser History
> **Amazon 高频** | 栈 / 数组 | Medium

**考察核心**: 用数组 + 指针模拟浏览器前进后退

```java
class BrowserHistory {
    private List<String> history;
    private int current;

    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        current = 0;
    }

    public void visit(String url) {
        // 清除 forward history
        while (history.size() > current + 1) {
            history.remove(history.size() - 1);
        }
        history.add(url);
        current++;
    }

    public String back(int steps) {
        current = Math.max(0, current - steps);
        return history.get(current);
    }

    public String forward(int steps) {
        current = Math.min(history.size() - 1, current + steps);
        return history.get(current);
    }
}
// visit O(1) 均摊, back/forward O(1)
```

---

### LC716 - Max Stack
> **Amazon / Microsoft** | 双栈 / TreeMap + 双向链表 | Hard

**考察核心**: 支持 peekMax() 和 popMax() — 简单版用双栈，follow-up 用 TreeMap + 双向链表

```java
// 简单版：双栈实现 (popMax 为 O(n))
class MaxStack {
    private Deque<Integer> stack;
    private Deque<Integer> maxStack;

    public MaxStack() {
        stack = new ArrayDeque<>();
        maxStack = new ArrayDeque<>();
    }

    public void push(int x) {
        stack.push(x);
        maxStack.push(maxStack.isEmpty() ? x : Math.max(x, maxStack.peek()));
    }

    public int pop() {
        maxStack.pop();
        return stack.pop();
    }

    public int top() { return stack.peek(); }
    public int peekMax() { return maxStack.peek(); }

    public int popMax() {
        int max = peekMax();
        Deque<Integer> temp = new ArrayDeque<>();
        while (stack.peek() != max) {
            temp.push(pop());
        }
        pop(); // 弹出 max
        while (!temp.isEmpty()) push(temp.pop());
        return max;
    }
}
// push/pop/top/peekMax: O(1), popMax: O(n)
// Follow-up: TreeMap + 双向链表可实现所有操作 O(logn)
```

---

## 7. 树的进阶高频题

### LC863 - All Nodes Distance K in Binary Tree
> **Meta / Amazon** | BFS + 建图 | Medium

**考察核心**: 先 DFS 建立父节点映射，然后从 target 开始 BFS 找距离为 K 的节点

```java
public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    buildParentMap(root, null, parentMap);

    // BFS from target
    Queue<TreeNode> queue = new LinkedList<>();
    Set<TreeNode> visited = new HashSet<>();
    queue.offer(target);
    visited.add(target);
    int distance = 0;

    while (!queue.isEmpty()) {
        if (distance == k) {
            List<Integer> result = new ArrayList<>();
            for (TreeNode node : queue) result.add(node.val);
            return result;
        }
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            // 向三个方向扩展：左子、右子、父节点
            for (TreeNode next : new TreeNode[]{node.left, node.right, parentMap.get(node)}) {
                if (next != null && !visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
        distance++;
    }
    return new ArrayList<>();
}

private void buildParentMap(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> map) {
    if (node == null) return;
    map.put(node, parent);
    buildParentMap(node.left, node, map);
    buildParentMap(node.right, node, map);
}
// 时间 O(n), 空间 O(n)
```

---

### LC1448 - Count Good Nodes in Binary Tree
> **Amazon Top 10 / Microsoft** | DFS | Medium

**考察核心**: DFS 传递路径上的最大值，当前值 >= 路径最大值即为 good node

```java
public int goodNodes(TreeNode root) {
    return dfs(root, Integer.MIN_VALUE);
}

private int dfs(TreeNode node, int maxSoFar) {
    if (node == null) return 0;
    int count = node.val >= maxSoFar ? 1 : 0;
    int newMax = Math.max(maxSoFar, node.val);
    return count + dfs(node.left, newMax) + dfs(node.right, newMax);
}
// 时间 O(n), 空间 O(h)
```

---

## 8. BFS 进阶

### LC542 - 01 Matrix
> **Amazon / Google** | 多源 BFS | Medium

**考察核心**: 从所有 0 出发做多源 BFS — 与 LC994 烂橘子同一模式

```java
public int[][] updateMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length;
    Queue<int[]> queue = new LinkedList<>();

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 0) queue.offer(new int[]{i, j});
            else mat[i][j] = Integer.MAX_VALUE;
        }
    }

    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : dirs) {
            int ni = cell[0] + d[0], nj = cell[1] + d[1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && mat[ni][nj] > mat[cell[0]][cell[1]] + 1) {
                mat[ni][nj] = mat[cell[0]][cell[1]] + 1;
                queue.offer(new int[]{ni, nj});
            }
        }
    }
    return mat;
}
// 时间 O(m*n), 空间 O(m*n)
```

---

### LC1091 - Shortest Path in Binary Matrix
> **Amazon / Google** | BFS | Medium

**考察核心**: 标准 BFS 求最短路径 — 8 方向移动

```java
public int shortestPathBinaryMatrix(int[][] grid) {
    int n = grid.length;
    if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;

    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0});
    grid[0][0] = 1; // 标记为已访问（同时记录距离）

    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        int dist = grid[cell[0]][cell[1]];
        if (cell[0] == n - 1 && cell[1] == n - 1) return dist;

        for (int[] d : dirs) {
            int ni = cell[0] + d[0], nj = cell[1] + d[1];
            if (ni >= 0 && ni < n && nj >= 0 && nj < n && grid[ni][nj] == 0) {
                grid[ni][nj] = dist + 1;
                queue.offer(new int[]{ni, nj});
            }
        }
    }
    return -1;
}
// 时间 O(n^2), 空间 O(n^2)
```

---

## 9. 区间进阶

### LC986 - Interval List Intersections
> **Meta / Google** | 双指针 | Medium

**考察核心**: 两个有序区间列表求交集 — 双指针，取交集后移动 end 较小的那个

```java
public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < firstList.length && j < secondList.length) {
        int lo = Math.max(firstList[i][0], secondList[j][0]);
        int hi = Math.min(firstList[i][1], secondList[j][1]);
        if (lo <= hi) result.add(new int[]{lo, hi});
        // 移动 end 较小的那个指针
        if (firstList[i][1] < secondList[j][1]) i++;
        else j++;
    }
    return result.toArray(new int[0][]);
}
// 时间 O(m+n), 空间 O(1) 不算输出
```

---

### LC1288 - Remove Covered Intervals
> **Google** | 排序 + 贪心 | Medium

**考察核心**: 按左端点升序、右端点降序排序，然后贪心统计未被覆盖的区间

```java
public int removeCoveredIntervals(int[][] intervals) {
    // 按左端点升序；左端点相同时按右端点降序
    Arrays.sort(intervals, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
    int count = 0, maxRight = 0;
    for (int[] interval : intervals) {
        if (interval[1] > maxRight) {
            count++;
            maxRight = interval[1];
        }
    }
    return count;
}
// 时间 O(nlogn), 空间 O(1)
```

---

## 10. 字符串 & 栈

### LC394 - Decode String
> **Meta / Amazon / Google** | 栈 | Medium | 极高频

**考察核心**: 用两个栈分别存数字和字符串，遇到 `]` 时弹栈拼接

```java
public String decodeString(String s) {
    Deque<Integer> numStack = new ArrayDeque<>();
    Deque<StringBuilder> strStack = new ArrayDeque<>();
    StringBuilder current = new StringBuilder();
    int num = 0;

    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            num = num * 10 + (c - '0');
        } else if (c == '[') {
            numStack.push(num);
            strStack.push(current);
            current = new StringBuilder();
            num = 0;
        } else if (c == ']') {
            int repeat = numStack.pop();
            StringBuilder prev = strStack.pop();
            for (int i = 0; i < repeat; i++) prev.append(current);
            current = prev;
        } else {
            current.append(c);
        }
    }
    return current.toString();
}
// 时间 O(n * maxRepeat), 空间 O(n)
```

---

### LC678 - Valid Parenthesis String
> **Amazon / Google** | 贪心 | Medium

**考察核心**: `*` 可以是 `(`、`)` 或空。用两个变量 lo/hi 追踪可能的未匹配 `(` 范围。

```java
public boolean checkValidString(String s) {
    int lo = 0, hi = 0; // lo: 最少可能的 '(' 数, hi: 最多可能的 '(' 数
    for (char c : s.toCharArray()) {
        if (c == '(') { lo++; hi++; }
        else if (c == ')') { lo--; hi--; }
        else { lo--; hi++; } // '*' 三种情况
        if (hi < 0) return false; // ')' 太多
        lo = Math.max(lo, 0); // lo 不能为负
    }
    return lo == 0;
}
// 时间 O(n), 空间 O(1)
```

---

### LC844 - Backspace String Compare
> **Google / Microsoft** | 双指针 | Easy

**考察核心**: O(1) 空间做法：从后往前遍历，遇 `#` 计数跳过

```java
public boolean backspaceCompare(String s, String t) {
    int i = s.length() - 1, j = t.length() - 1;
    int skipS = 0, skipT = 0;
    while (i >= 0 || j >= 0) {
        // 找到 s 中下一个有效字符
        while (i >= 0) {
            if (s.charAt(i) == '#') { skipS++; i--; }
            else if (skipS > 0) { skipS--; i--; }
            else break;
        }
        // 找到 t 中下一个有效字符
        while (j >= 0) {
            if (t.charAt(j) == '#') { skipT++; j--; }
            else if (skipT > 0) { skipT--; j--; }
            else break;
        }
        if (i >= 0 && j >= 0 && s.charAt(i) != t.charAt(j)) return false;
        if ((i >= 0) != (j >= 0)) return false;
        i--; j--;
    }
    return true;
}
// 时间 O(n+m), 空间 O(1)
```

---

## 11. DP 进阶高频

### LC1235 - Maximum Profit in Job Scheduling
> **Google / Amazon Top 15** | DP + 二分查找 | Hard

**考察核心**: 按结束时间排序，dp[i] = 取或不取第 i 个任务的最大利润，二分找上一个不冲突的任务

```java
public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
    int n = startTime.length;
    int[][] jobs = new int[n][3];
    for (int i = 0; i < n; i++) jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
    Arrays.sort(jobs, (a, b) -> a[1] - b[1]); // 按结束时间排序

    // dp[i] = 考虑前 i 个任务的最大利润
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        // 不选第 i 个任务
        dp[i] = dp[i - 1];
        // 选第 i 个任务：二分找最后一个 endTime <= jobs[i-1].startTime 的任务
        int prev = binarySearch(jobs, i - 1, jobs[i - 1][0]);
        dp[i] = Math.max(dp[i], dp[prev] + jobs[i - 1][2]);
    }
    return dp[n];
}

private int binarySearch(int[][] jobs, int end, int targetStart) {
    int lo = 0, hi = end;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (jobs[mid][1] <= targetStart) lo = mid + 1;
        else hi = mid;
    }
    return lo;
}
// 时间 O(nlogn), 空间 O(n)
```

---

### LC256 - Paint House
> **Meta** | DP | Medium

**考察核心**: 三种颜色的线性 DP，每个房子选与上一个不同的颜色的最小开销

```java
public int minCost(int[][] costs) {
    int n = costs.length;
    // dp[i][c] = 涂到第 i 个房子，颜色为 c 的最小花费
    int r = 0, g = 0, b = 0; // 空间优化
    for (int i = 0; i < n; i++) {
        int nr = costs[i][0] + Math.min(g, b);
        int ng = costs[i][1] + Math.min(r, b);
        int nb = costs[i][2] + Math.min(r, g);
        r = nr; g = ng; b = nb;
    }
    return Math.min(r, Math.min(g, b));
}
// 时间 O(n), 空间 O(1)
// Follow-up LC265: k 种颜色 → 维护最小和次小值，O(nk) → O(n)
```

---

## 12. 贪心进阶

### LC1762 (类似) - Buildings With an Ocean View 变种

已在 Meta 专题中给出，这里补充一个关联的经典贪心题：

### LC134 - Gas Station (你已有，补充 follow-up 解法)

**Follow-up**: 如果需要返回所有可能的起点？
→ 如果总油量 >= 总消耗，答案唯一，就是上面求出的起点。

---

## 13. Amazon 2025-2026 专项

> Amazon SDE 面试在 2025-2026 趋势：BFS/DFS 矩阵题、二分答案、设计题。

### 补充高频题速查

| # | 题目 | 核心算法 | 频率 |
|---|------|---------|------|
| 1448 | Count Good Nodes in Binary Tree | DFS | 极高 |
| 542 | 01 Matrix | 多源 BFS | 极高 |
| 1011 | Capacity To Ship Packages | 二分答案 | 高 |
| 1091 | Shortest Path in Binary Matrix | BFS 8方向 | 高 |
| 1235 | Max Profit in Job Scheduling | DP + 二分 | 高 |
| 622 | Design Circular Queue | 环形数组 | 高 |
| 1472 | Design Browser History | 数组指针 | 高 |
| 716 | Max Stack | 双栈/TreeMap | 中高 |
| 394 | Decode String | 栈 | 极高 |
| 678 | Valid Parenthesis String | 贪心 | 高 |

---

## 14. Google 2025-2026 专项

> Google L4-L5 面试趋势：二分答案、图论进阶、区间 DP、数学推导。

### 补充高频题速查

| # | 题目 | 核心算法 | 频率 |
|---|------|---------|------|
| 410 | Split Array Largest Sum | 二分答案 | 极高 |
| 1235 | Max Profit in Job Scheduling | DP + 二分 | 极高 |
| 907 | Sum of Subarray Minimums | 单调栈 | 高 |
| 986 | Interval List Intersections | 双指针 | 高 |
| 1288 | Remove Covered Intervals | 排序贪心 | 中高 |
| 844 | Backspace String Compare | 双指针 | 高 |
| 990 | Satisfiability of Equality Equations | Union Find | 中高 |
| 1539 | Kth Missing Positive Number | 二分 | 中高 |
| 658 | Find K Closest Elements | 二分 | 高 |
| 863 | All Nodes Distance K | BFS + 建图 | 高 |

---

## 15. 总结：优先级清单

### P0 — 必须补充（多次面经验证，2025-2026 极高频）

| # | 题目 | 类型 | 公司 |
|---|------|------|------|
| 528 | Random Pick with Weight | 前缀和+二分 | Meta |
| 394 | Decode String | 栈 | Meta, Amazon, Google |
| 636 | Exclusive Time of Functions | 栈 | Meta |
| 1762 | Buildings With an Ocean View | 逆序扫描 | Meta |
| 523 | Continuous Subarray Sum | 前缀和+HashMap | Meta |
| 863 | All Nodes Distance K | BFS+建图 | Meta, Amazon |
| 1448 | Count Good Nodes | DFS | Amazon |
| 542 | 01 Matrix | 多源 BFS | Amazon |
| 1011 | Capacity To Ship Packages | 二分答案 | Amazon |
| 410 | Split Array Largest Sum | 二分答案 | Google |
| 547 | Number of Provinces | Union Find | 基础必备 |
| 684 | Redundant Connection | Union Find | NeetCode 150 |
| 1235 | Max Profit in Job Scheduling | DP+二分 | Google, Amazon |

### P1 — 建议补充（高频，能力提升明显）

| # | 题目 | 类型 | 公司 |
|---|------|------|------|
| 791 | Custom Sort String | 计数排序 | Meta |
| 339 | Nested List Weight Sum | DFS | Meta, Airbnb |
| 525 | Contiguous Array | 前缀和 | Meta |
| 658 | Find K Closest Elements | 二分 | Meta, Google |
| 907 | Sum of Subarray Minimums | 单调栈 | Amazon, Google |
| 173 | BST Iterator | 栈迭代 | Meta, Microsoft |
| 341 | Flatten Nested List Iterator | 栈 | Meta, Airbnb |
| 986 | Interval List Intersections | 双指针 | Meta, Google |
| 990 | Satisfiability of Equality Equations | Union Find | Google |
| 678 | Valid Parenthesis String | 贪心 | Amazon |
| 844 | Backspace String Compare | 双指针 | Google |
| 256 | Paint House | DP | Meta |
| 1539 | Kth Missing Positive Number | 二分 | Meta |

### P2 — 选做（设计题 + 低频但有价值）

| # | 题目 | 类型 | 公司 |
|---|------|------|------|
| 346 | Moving Average from Data Stream | 队列 | Meta |
| 622 | Design Circular Queue | 环形数组 | Amazon |
| 716 | Max Stack | 双栈/TreeMap | Amazon |
| 1472 | Design Browser History | 数组 | Amazon |
| 1091 | Shortest Path in Binary Matrix | BFS | Amazon |
| 1288 | Remove Covered Intervals | 排序贪心 | Google |
| 303 | Range Sum Query - Immutable | 前缀和 | 基础 |

---

## 算法模板速查（本批次新增）

### 并查集模板
```
find(x): 路径压缩 → parent[x] = find(parent[x])
union(x,y): 按秩合并 → 矮树挂高树
应用: 连通分量、冗余边检测、等式满足性
```

### 二分答案模板
```
lo = 最小可能答案, hi = 最大可能答案
while (lo < hi):
    mid = lo + (hi - lo) / 2
    if canAchieve(mid): hi = mid
    else: lo = mid + 1
return lo

关键: 设计 canAchieve(x) 判定函数，证明单调性
```

### 多源 BFS 模板
```
1. 所有源点入队
2. 标记已访问
3. BFS 层序扩展
应用: LC542 (01 Matrix), LC994 (Rotting Oranges), LC286 (Walls and Gates)
```

### 迭代器模板 (BST/嵌套列表)
```
栈存储待处理节点
hasNext(): 展开栈顶直到可用
next(): 弹出栈顶返回
```

---

> **本批次新增：约 30 道高频题**，覆盖并查集、二分答案、Meta/Amazon/Google 2025-2026 最新趋势。
> 建议按 P0 → P1 → P2 优先级逐步练习，与前两份文档配合使用。
> 
> 三份文档合计覆盖：**~170+ 道面试高频题**，基本覆盖 Blind 75 + NeetCode 150 + 各公司 Top 面经。
