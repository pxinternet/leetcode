# 面试算法复习指南

> 基于你的 LeetCode 刷题仓库（377+ Java 文件），按面试高频度排序、按类型归类，重点标注 Microsoft / Airbnb 等公司常考题。

---

## 目录

1. [Tier 1 - 必刷高频题（面试出现率 > 70%）](#tier-1---必刷高频题)
2. [Tier 2 - 常见题（面试出现率 40-70%）](#tier-2---常见题)
3. [Tier 3 - 进阶题（面试出现率 20-40%）](#tier-3---进阶题)
4. [按类型归类总览](#按类型归类总览)
5. [公司专项：Microsoft](#公司专项microsoft)
6. [公司专项：Airbnb](#公司专项airbnb)
7. [公司专项：Google / Meta / Amazon](#公司专项google--meta--amazon)
8. [系统设计编码题](#系统设计编码题)
9. [多线程 & 并发题](#多线程--并发题)
10. [衍生题目与解答](#衍生题目与解答)
11. [速查：算法模板](#速查算法模板)
12. [复习计划建议](#复习计划建议)

---

## Tier 1 - 必刷高频题

> 这些题目在各大公司面试中反复出现，必须做到闭眼写出。

### 1.1 数组 & 双指针

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 11 | Container With Most Water | 双指针 | Microsoft, Amazon | **贪心选择为何正确？能否证明？** | [LC11MaxArea.java](src/leetCode/LC11MaxArea.java) |
| 15 | 3Sum | 排序+双指针 | Microsoft, Airbnb, Meta | **如何去重？时间复杂度分析** | [LC15threeSum](src/leetCode/Top150.java) |
| 16 | 3Sum Closest | 排序+双指针 | Microsoft | **与3Sum的区别，如何维护最优解** | [LC16threeSumClosest.java](src/leetCode/LC16threeSumClosest.java) |
| 42 | Trapping Rain Water | 双指针/单调栈 | Microsoft, Google, Amazon | **三种解法对比：DP/双指针/单调栈** | [LC42Trap.java](src/leetCode/LC42Trap.java) |
| 53 | Maximum Subarray | Kadane's | Microsoft, Amazon | **Kadane本质是什么DP？如何输出子数组？** | [LC53maxSubArray.java](src/leetCode/LC53maxSubArray.java) |
| 56 | Merge Intervals | 排序+合并 | Microsoft, Airbnb, Google | **排序后一趟扫描，区间合并模板** | [LC56merge.java](src/leetCode/LC56merge.java) |
| 128 | Longest Consecutive Sequence | HashSet | Microsoft, Google | **为何用Set而非排序？O(n)证明** | [LC128LongestConsecutiv.java](src/leetCode/LC128LongestConsecutiv.java) |
| 238 | Product of Array Except Self | 前缀积 | Microsoft, Amazon | **不用除法如何做？前后缀拆分思想** | [LC238productExceptSelf.java](src/leetCode/LC238productExceptSelf.java) |
| 283 | Move Zeroes | 双指针 | Microsoft, Meta | **稳定性要求，最少交换次数** | [LC283Movezero.java](src/leetCode/LC283Movezero.java) |
| 41 | First Missing Positive | 原地哈希 | Microsoft, Google | **O(1)空间核心trick：数组本身当哈希表** | [LC41firstMissingPositive.java](src/leetCode/LC41firstMissingPositive.java) |
| 560 | Subarray Sum Equals K | 前缀和+HashMap | Meta, Microsoft | **前缀和+哈希计数，不能用滑动窗口（有负数）** | [LC560subarraySum.java](src/leetCode/LC560subarraySum.java) |

### 1.2 字符串 & 滑动窗口

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 3 | Longest Substring Without Repeating | 滑动窗口 | 全部公司 | **窗口何时收缩？HashMap vs 数组计数** | [LC3.java](src/leetCode/LC3.java) |
| 76 | Minimum Window Substring | 滑动窗口 | Airbnb, Microsoft, Meta | **双指针模板：expand右+shrink左，formed计数** | [LC76minWindow.java](src/leetCode/LC76minWindow.java) |
| 438 | Find All Anagrams | 滑动窗口 | Microsoft, Amazon | **定长窗口优化，26位数组比较** | [LC438findAnagrams.java](src/leetCode/LC438findAnagrams.java) |
| 567 | Permutation in String | 滑动窗口 | Microsoft | **定长窗口+字符频率匹配** | [LC567checkInclusion.java](src/leetCode/LC567checkInclusion.java) |
| 49 | Group Anagrams | HashMap | Airbnb, Microsoft | **排序作key vs 计数数组作key** | [LC49groupAnagrams.java](src/leetCode/LC49groupAnagrams.java) |
| 5 | Longest Palindromic Substring | 中心扩展/DP | Microsoft, Amazon | **中心扩展 O(n^2) vs Manacher O(n)** | [LC05LongPalindrome.java](src/leetCode/LC05LongPalindrome.java) |
| 151 | Reverse Words | 字符串 | Microsoft | **先整体反转再逐词反转 vs split** | [LC151reverseWords.java](src/leetCode/LC151reverseWords.java) |
| 30 | Substring with Concatenation | 滑动窗口+HashMap | Microsoft | **多起点滑动窗口，单词级别移动** | [LC30FindSubString.java](src/leetCode/LC30FindSubString.java) |

### 1.3 链表

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 206 | Reverse Linked List | 迭代/递归 | 全部公司 | **迭代三指针 vs 递归，必须两种都会** | [LC206.java](src/leetCode/LC206.java) |
| 21 | Merge Two Sorted Lists | 合并 | Microsoft, Amazon | **dummy head 技巧，递归写法** | [LC21mergeTwoList.java](src/leetCode/LC21mergeTwoList.java) |
| 23 | Merge K Sorted Lists | 堆/分治 | Microsoft, Airbnb, Amazon | **小顶堆 O(Nlogk) vs 分治 O(Nlogk)，复杂度对比** | [LC23mergeKList.java](src/leetCode/LC23mergeKList.java) |
| 141 | Linked List Cycle | 快慢指针 | Microsoft, Amazon | **Floyd判圈：为何快指针走2步？找入口怎么做？** | [LC141HasCycle.java](src/leetCode/LC141HasCycle.java) |
| 143 | Reorder List | 快慢+反转+合并 | Microsoft, Meta | **三步走：找中点+反转后半+交替合并** | [LC143ReorderList.java](src/leetCode/LC143ReorderList.java) |
| 138 | Copy List with Random Pointer | HashMap/O(1)空间 | Microsoft, Amazon | **HashMap法 vs 交错复制法，空间优化** | [LC138copyRandomList.java](src/leetCode/LC138copyRandomList.java) |
| 25 | Reverse Nodes in K-Group | 链表 | Microsoft, Amazon | **分组反转：计数+反转+递归/迭代连接** | [LC25reverseKGroup.java](src/leetCode/LC25reverseKGroup.java) |
| 19 | Remove Nth From End | 双指针 | Microsoft | **一趟扫描：快指针先走n步** | [LC19removeNthFromEnd.java](src/leetCode/LC19removeNthFromEnd.java) |
| 148 | Sort List | 归并排序 | Microsoft | **链表归并：O(nlogn)时间 O(1)空间** | [LC148sortList.java](src/leetCode/LC148sortList.java) |
| 2 | Add Two Numbers | 链表 | Microsoft, Amazon | **进位处理，链表长度不等** | [LC2.java](src/leetCode/LC2.java) |
| 92 | Reverse Linked List II | 链表 | Microsoft | **区间反转：找前驱+反转+重连** | [LC92ReverseBetween.java](src/leetCode/LC92ReverseBetween.java) |
| 160 | Intersection of Two Linked Lists | 双指针 | Microsoft, Amazon | **双指针走法的数学证明** | [LC160inerSecList.java](src/leetCode/LC160inerSecList.java) |

### 1.4 二叉树

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 102 | Binary Tree Level Order | BFS | Microsoft, Amazon | **BFS模板，层级分隔技巧** | [LC102levelOrder.java](src/leetCode/LC102levelOrder.java) |
| 236 | Lowest Common Ancestor | DFS | Microsoft, Airbnb, Meta | **后序遍历返回非null节点，变种：BST/带parent/节点不存在** | [LC236lowestCommonAncestor.java](src/leetCode/LC236lowestCommonAncestor.java) |
| 124 | Binary Tree Max Path Sum | DFS | Microsoft, Google | **全局变量记录max，dfs返回单侧最大贡献** | [LC124maxPathSum.java](src/leetCode/LC124maxPathSum.java) |
| 105 | Construct Tree (Pre+In) | 递归+HashMap | Microsoft, Amazon | **HashMap加速查找inorder位置，递归边界** | [LC105buildTree.java](src/leetCode/LC105buildTree.java) |
| 297 | Serialize/Deserialize Tree | BFS/DFS | Microsoft, Airbnb, Google | **前序+null标记 vs BFS层序，编码设计** | [LC297serializeTree.java](src/leetCode/LC297serializeTree.java) |
| 226 | Invert Binary Tree | 递归 | 全部公司 | **递归/迭代/BFS三种方式** | [LC226invertTree.java](src/leetCode/LC226invertTree.java) |
| 98 | Validate BST | 中序遍历 | Microsoft, Amazon | **中序遍历严格递增 vs 传递上下界** | [LC98isValidBST.java](src/leetCode/LC98isValidBST.java) |
| 199 | Right Side View | BFS | Microsoft, Meta | **BFS每层最后一个 vs DFS先右后左** | [LC199rightSideView.java](src/leetCode/LC199rightSideView.java) |
| 230 | Kth Smallest in BST | 中序遍历 | Microsoft, Airbnb | **中序第k个，follow-up: 频繁查询如何优化？** | [LC230kthSmallest.java](src/leetCode/LC230kthSmallest.java) |
| 103 | Zigzag Level Order | BFS | Microsoft, Amazon | **奇偶层交替方向，deque双端处理** | [LC103zigzagLevelOrder.java](src/leetCode/LC103zigzagLevelOrder.java) |
| 114 | Flatten to Linked List | DFS | Microsoft | **后序遍历（右-左-根）+ 全局prev指针** | [LC114Flatten.java](src/leetCode/LC114Flatten.java) |
| 108 | Sorted Array to BST | 递归 | Microsoft, Amazon | **二分选中点为根，递归建左右子树** | [LC108sortedArrayToBST.java](src/leetCode/LC108sortedArrayToBST.java) |
| 337 | House Robber III | 树形DP | Microsoft | **dfs返回[不偷当前, 偷当前]** | [LC337rob.java](src/leetCode/LC337rob.java) |

### 1.5 图 & BFS/DFS

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 200 | Number of Islands | DFS/BFS | 全部公司 | **矩阵DFS模板：边界+visited处理，Union-Find变种** | [LC200numIslands.java](src/leetCode/LC200numIslands.java) |
| 207 | Course Schedule | 拓扑排序 | Microsoft, Amazon | **BFS拓扑（Kahn）vs DFS检测环，三色标记** | [LC207CanFinish.java](src/leetCode/LC207CanFinish.java) |
| 210 | Course Schedule II | 拓扑排序 | Microsoft, Airbnb | **输出拓扑序列，与207的区别** | [LC210FindOrder.java](src/leetCode/LC210FindOrder.java) |
| 133 | Clone Graph | DFS+HashMap | Microsoft, Meta | **DFS/BFS+visited map防重复克隆** | [LC133cloneGraph.java](src/leetCode/LC133cloneGraph.java) |
| 127 | Word Ladder | BFS | Airbnb, Amazon | **BFS最短路径，双向BFS优化** | [LC127ladderLength.java](src/leetCode/LC127ladderLength.java) |
| 130 | Surrounded Regions | DFS/BFS | Microsoft | **反向思维：从边界向内标记不被围的O** | [LC130solve.java](src/leetCode/LC130solve.java) |
| 994 | Rotting Oranges | 多源BFS | Microsoft, Amazon | **多源BFS：所有烂橘子同时入队** | [LC994OrangesRotting.java](src/leetCode/LC994OrangesRotting.java) |
| 329 | Longest Increasing Path in Matrix | DFS+记忆化 | Microsoft, Google | **记忆化DFS + 拓扑排序两种解法** | [LC329longestIncreasingPath.java](src/matrix/LC329longestIncreasingPath.java) |

### 1.6 动态规划

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 322 | Coin Change | 完全背包 | Microsoft, Amazon | **状态转移：dp[i] = min(dp[i], dp[i-coin]+1)，初始化细节** | [LC322CoinChange.java](src/leetCode/LC322CoinChange.java) |
| 300 | Longest Increasing Subsequence | DP+二分 | Microsoft, Google | **O(n^2) DP vs O(nlogn) 贪心+二分（patience sorting）** | [LC300lengthOfLIS.java](src/leetCode/LC300lengthOfLIS.java) |
| 139 | Word Break | DP | Microsoft, Airbnb, Amazon | **dp[i]表示前i个字符能否拆分，字典查询优化** | [LC139wordBreak.java](src/leetCode/LC139wordBreak.java) |
| 198 | House Robber | DP | Microsoft, Airbnb | **dp[i] = max(dp[i-1], dp[i-2]+nums[i])，空间优化到O(1)** | [LC198rob.java](src/leetCode/LC198rob.java) |
| 221 | Maximal Square | DP | Microsoft, Airbnb | **dp[i][j] = min(左,上,左上)+1，理解为何取min** | [LC221maximalSquare.java](src/leetCode/LC221maximalSquare.java) |
| 62 | Unique Paths | DP | Microsoft, Amazon | **二维DP/组合数学 C(m+n-2, m-1)** | [LC62.java](src/leetCode/LC62.java) |
| 121 | Best Time to Buy/Sell Stock | DP/贪心 | 全部公司 | **维护历史最低价，一趟扫描** | [LC121MAXProfit.java](src/leetCode/LC121MAXProfit.java) |
| 72 | Edit Distance | DP | Microsoft, Google | **经典二维DP，三种操作对应三个子问题** | [LC72minDistance.java](src/leetCode/LC72minDistance.java) |
| 1143 | Longest Common Subsequence | DP | Microsoft, Google | **经典二维DP，空间优化到一维** | [LC1143longestCommonSubswquence.java](src/dp/LC1143longestCommonSubswquence.java) |
| 70 | Climbing Stairs | DP | 全部公司 | **斐波那契变种，空间O(1)优化** | [LC70climbStairs.java](src/leetCode/LC70climbStairs.java) |

### 1.7 二分搜索

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 33 | Search in Rotated Sorted Array | 二分 | Microsoft, Amazon | **判断哪半段有序，缩小搜索范围** | [LC33Search.java](src/search/LC33Search.java) |
| 34 | Find First/Last Position | 二分 | Microsoft, Meta | **两次二分找左右边界，lo<=hi vs lo<hi** | [LC34.java](src/leetCode/LC34.java) |
| 153 | Find Minimum in Rotated Array | 二分 | Microsoft, Amazon | **与右端点比较缩小范围** | [LC153.java](src/leetCode/LC153.java) |
| 162 | Find Peak Element | 二分 | Microsoft, Google | **二分可用于非有序数组！往更大邻居走** | [LC162.java](src/leetCode/LC162.java) |
| 4 | Median of Two Sorted Arrays | 二分 | Microsoft, Google, Amazon | **二分较短数组，O(log(min(m,n)))** | [LC4FindMedianSortedArrays.java](src/leetCode/LC4FindMedianSortedArrays.java) |
| 215 | Kth Largest Element | 快速选择/堆 | Microsoft, Airbnb, Meta | **QuickSelect O(n)平均 vs 堆 O(nlogk)** | [LC215.java](src/leetCode/LC215.java) |
| 240 | Search a 2D Matrix II | 双指针 | Microsoft, Amazon | **从右上角出发：小了下移，大了左移** | [LC240.java](src/LC240.java) |
| 74 | Search a 2D Matrix | 二分 | Microsoft | **展平为一维二分，或两次二分** | [LC74searchMatrix.java](src/leetCode/LC74searchMatrix.java) |
| 69 | Sqrt(x) | 二分 | Microsoft | **二分查找模板，注意整数溢出** | [LC69mySqrt.java](src/leetCode/LC69mySqrt.java) |

### 1.8 栈 & 单调栈

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 20 | Valid Parentheses | 栈 | 全部公司 | **经典栈应用，三种括号匹配** | [LC20.java](src/interview/LC20.java) |
| 84 | Largest Rectangle in Histogram | 单调栈 | Microsoft, Google | **单调递增栈，遇到更小元素时结算** | [LC84largestRectangleArea.java](src/leetCode/LC84largestRectangleArea.java) |
| 239 | Sliding Window Maximum | 单调队列 | Microsoft, Amazon | **单调递减队列，窗口滑动时维护队首最大** | [LC239.java](src/leetCode/LC239.java) |
| 224 | Basic Calculator | 栈 | Microsoft, Airbnb | **栈处理括号嵌套，符号翻转** | [LC224calculate.java](src/leetCode/LC224calculate.java) |
| 150 | Reverse Polish Notation | 栈 | Microsoft | **后缀表达式求值，栈的基本应用** | [LC150.java](src/leetCode/LC150.java) |
| 71 | Simplify Path | 栈 | Microsoft | **栈处理路径中的 `.` 和 `..`** | [LC71simplifyPath.java](src/leetCode/LC71simplifyPath.java) |

---

## Tier 2 - 常见题

### 2.1 回溯 (Backtracking)

| # | 题目 | 公司标签 | 考察核心 | 代码 |
|---|------|----------|----------|------|
| 46 | Permutations | Microsoft, Amazon | **全排列模板，used数组标记** | [LC46Permute.java](src/dfs/LC46Permute.java) |
| 47 | Permutations II | Microsoft | **去重：排序+同层剪枝（nums[i]==nums[i-1] && !used[i-1]）** | [LC47permuteUnique.java](src/dfs/LC47permuteUnique.java) |
| 78 | Subsets | Microsoft, Amazon | **子集模板：每层都加入result** | [LC78subsets.java](src/dfs/LC78subsets.java) |
| 90 | Subsets II | Microsoft | **带重复元素的子集去重** | [LC90SubsetsWithDup.java](src/dfs/LC90SubsetsWithDup.java) |
| 39 | Combination Sum | Microsoft, Airbnb | **可重复选取，start不加1** | [LC39comnbinationSum.java](src/dfs/LC39comnbinationSum.java) |
| 40 | Combination Sum II | Microsoft | **不可重复选取+去重** | [LC40.java](src/leetCode/LC40.java) |
| 22 | Generate Parentheses | Microsoft, Airbnb | **左括号数<n可加左，左>右可加右** | [LC22generateParenthesis.java](src/dfs/LC22generateParenthesis.java) |
| 51 | N-Queens | Microsoft | **列/对角线/反对角线三维冲突检测** | [LC51nq.java](src/dfs/LC51nq.java) |
| 79 | Word Search | Microsoft, Amazon | **矩阵DFS回溯，原地标记visited** | [LC79exist.java](src/leetCode/LC79exist.java) |
| 131 | Palindrome Partitioning | Microsoft, Google | **预处理回文DP表 + 回溯切割** | [LC131partition.java](src/leetCode/LC131partition.java) |
| 17 | Letter Combinations | Microsoft, Airbnb | **电话键盘映射+DFS** | [LC17letterCombinations.java](src/dfs/LC17letterCombinations.java) |
| 216 | Combination Sum III | Microsoft | **限定个数k+目标和n** | [LC216combinationSum3.java](src/dfs/LC216combinationSum3.java) |

### 2.2 贪心 (Greedy)

| # | 题目 | 公司标签 | 考察核心 | 代码 |
|---|------|----------|----------|------|
| 134 | Gas Station | Microsoft, Amazon | **总油>=总耗必有解，从净油量最低点的下一站出发** | [LC134CanCompleteCircuit.java](src/leetCode/LC134CanCompleteCircuit.java) |
| 135 | Candy | Microsoft, Google | **两趟扫描：左到右+右到左** | [LC135Candy.java](src/leetCode/LC135Candy.java) |
| 45 | Jump Game II | Microsoft, Amazon | **BFS层级思想：当前能到的最远+下一层最远** | [LC45jump.java](src/leetCode/LC45jump.java) |
| 55 | Jump Game | Microsoft, Amazon | **维护能到达的最远位置** | [LC55canJump.java](src/leetCode/LC55canJump.java) |
| 435 | Non-overlapping Intervals | Microsoft | **按end排序贪心，最多保留不重叠** | [LC435eraseOverlapIntervals.java](src/leetCode/LC435eraseOverlapIntervals.java) |
| 406 | Queue Reconstruction by Height | Microsoft, Google | **按身高降序+k升序排，依次插入** | [LC406reconstructQueue.java](src/leetCode/LC406reconstructQueue.java) |
| 452 | Min Arrows to Burst Balloons | Microsoft | **区间贪心变种，按end排序** | [LC452findMinArrowShots.java](src/leetCode/LC452findMinArrowShots.java) |
| 122 | Best Time to Buy/Sell Stock II | 全部公司 | **只要今天比昨天高就卖，贪心正确性** | [LC122MAXProfit2.java](src/leetCode/LC122MAXProfit2.java) |

### 2.3 进阶动态规划

| # | 题目 | 公司标签 | 考察核心 | 代码 |
|---|------|----------|----------|------|
| 10 | Regular Expression Matching | Microsoft, Google | **`.*`匹配任意：dp[i][j]状态转移分析** | [LC10isMatch.java](src/leetCode/LC10isMatch.java) |
| 44 | Wildcard Matching | Microsoft, Google | **与LC10区别：`*`匹配任意字符串** | [LC44isMath.java](src/leetCode/LC44isMath.java) |
| 123 | Buy/Sell Stock III | Microsoft | **至多两次交易，状态机DP** | [LC123MaxProfit.java](src/leetCode/LC123MaxProfit.java) |
| 188 | Buy/Sell Stock IV | Microsoft | **至多k次交易的通用状态机** | [LC188MaxProfit.java](src/leetCode/LC188MaxProfit.java) |
| 309 | Buy/Sell Stock with Cooldown | Microsoft | **带冷却期的状态机DP** | [LC309maxProfit.java](src/leetCode/LC309maxProfit.java) |
| 140 | Word Break II | Airbnb, Microsoft | **DFS+记忆化输出所有切割方案** | [LC140wordBreak.java](src/leetCode/LC140wordBreak.java) |
| 115 | Distinct Subsequences | Microsoft | **二维DP: dp[i][j]表示s[0..i]中t[0..j]的出现次数** | [LC115numDistinct.java](src/leetCode/LC115numDistinct.java) |
| 354 | Russian Doll Envelopes | Google | **排序+LIS，第二维降序的trick** | [LC354maxEnvelopes.java](src/dp/LC354maxEnvelopes.java) |
| 85 | Maximal Rectangle | Microsoft, Google | **逐行转化为LC84直方图问题** | [LC85maximalRectangle.java](src/leetCode/LC85maximalRectangle.java) |
| 91 | Decode Ways | Microsoft, Amazon | **类似爬楼梯，处理0的边界** | [LC91numDecoding.java](src/leetCode/LC91numDecoding.java) |
| 213 | House Robber II | Airbnb | **环形DP：分两次计算[0..n-2]和[1..n-1]** | [LC213rob.java](src/leetCode/LC213rob.java) |

### 2.4 设计题 & 数据结构

| # | 题目 | 公司标签 | 考察核心 | 代码 |
|---|------|----------|----------|------|
| 146 | LRU Cache | 全部公司 | **双向链表+HashMap，O(1) get/put** | [LRUCache.java](src/lrucache/LRUCache.java) |
| 295 | Find Median from Data Stream | Microsoft, Amazon | **大顶堆+小顶堆，平衡两堆大小** | [LC295MedianFinder.java](src/leetCode/LC295MedianFinder.java) |
| 981 | Time Based Key-Value Store | Microsoft, Google | **HashMap<String, TreeMap> 或 二分查找** | [LC981.java](src/leetCode/LC981.java) |
| 208 | Implement Trie | Microsoft, Google | **Trie节点结构：children[26] + isEnd** | [TireNode.java](src/round3/TireNode.java) |
| - | LRU with TTL | Airbnb, Microsoft | **LRU + 过期时间，惰性删除** | [LRUWithTTL.java](src/round3/LRUWithTTL.java) |
| - | Thread-Safe LRU | Microsoft | **ReadWriteLock / ConcurrentHashMap** | [ThreadSafeLRUCache.java](src/lrucache/ThreadSafeLRUCache.java) |

---

## Tier 3 - 进阶题

| # | 题目 | 算法 | 公司标签 | 考察核心 | 代码 |
|---|------|------|----------|----------|------|
| 218 | Skyline Problem | 堆+扫描线 | Microsoft, Google | **事件排序+最大堆维护当前最高** | [LC218.java](src/leetCode/LC218.java) |
| 332 | Reconstruct Itinerary | 欧拉路径 | Google, Airbnb | **Hierholzer算法，后序插入结果** | [LC332findItinerary.java](src/dfs/LC332findItinerary.java) |
| 753 | Cracking the Safe | 欧拉路径 | Google | **De Bruijn序列，DFS欧拉路径** | [LC753crackSafe.java](src/dfs/LC753crackSafe.java) |
| 273 | Integer to English Words | 字符串 | Microsoft, Amazon | **千位分段处理，注意 edge case（零、百位间隔）** | [LC273nums2word.java](src/leetCode/LC273nums2word.java) |
| 68 | Text Justification | 字符串 | Airbnb, Microsoft | **逐行贪心+左对齐/两端对齐规则** | [LC68fullJustify.java](src/leetCode/LC68fullJustify.java) |
| 407 | Trapping Rain Water II | 堆+BFS | Google | **最小堆BFS，从外围向内灌水** | [LC407trap.java](src/leetCode/LC407trap.java) |
| 126 | Word Ladder II | BFS+回溯 | Airbnb | **BFS建图+DFS回溯输出所有最短路径** | [LC126findLadders.java](src/leetCode/LC126findLadders.java) |
| 834 | Sum of Distances in Tree | 换根DP | Google | **两趟DFS：自底向上+自顶向下换根** | [LC834TreeDistanceSum.java](src/leetCode/LC834TreeDistanceSum.java) |
| 928 | Minimize Malware Spread II | Union-Find | Google | **Union-Find + 连通分量分析** | [LC928minMalwareSpread.java](src/leetCode/LC928minMalwareSpread.java) |
| 514 | Freedom Trail | DFS/DP | Google | **环形DP，记忆化搜索** | [LC514findRotateSteps.java](src/dfs/LC514findRotateSteps.java) |
| 472 | Concatenated Words | DFS/Trie | Amazon | **排序后用Word Break判断每个词** | [LC472findAllConcatenaatedWordsInADict.java](src/dfs/LC472findAllConcatenaatedWordsInADict.java) |

---

## 按类型归类总览

### 数组 & 哈希
[LC11](src/leetCode/LC11MaxArea.java) | [LC15](src/leetCode/Top150.java) | [LC16](src/leetCode/LC16threeSumClosest.java) | [LC41](src/leetCode/LC41firstMissingPositive.java) | [LC42](src/leetCode/LC42Trap.java) | [LC49](src/leetCode/LC49groupAnagrams.java) | [LC53](src/leetCode/LC53maxSubArray.java) | [LC56](src/leetCode/LC56merge.java) | [LC57](src/leetCode/LC57insert.java) | [LC66](src/leetCode/LC66plusOne.java) | [LC73](src/leetCode/LC73setZeros.java) | [LC80](src/leetCode/LC80removeDuplicates.java) | [LC128](src/leetCode/LC128LongestConsecutiv.java) | [LC167](src/leetCode/LC167twoSum.java) | [LC189](src/leetCode/LC189rotate.java) | [LC229](src/leetCode/LC229majorityElement.java) | [LC238](src/leetCode/LC238productExceptSelf.java) | [LC274](src/leetCode/LC274HIndex.java) | [LC283](src/leetCode/LC283Movezero.java) | [LC289](src/leetCode/LC289gameOfLife.java) | [LC560](src/leetCode/LC560subarraySum.java)

### 字符串
[LC3](src/leetCode/LC3.java) | [LC5](src/leetCode/LC05LongPalindrome.java) | [LC6](src/leetCode/LC6convert.java) | [LC7](src/leetCode/LC7reverse.java) | [LC8](src/leetCode/LC8myAtoi.java) | [LC12](src/leetCode/LC12intToRoman.java) | [LC30](src/leetCode/LC30FindSubString.java) | [LC32](src/leetCode/LC32longestValidParentheses.java) | [LC65](src/leetCode/LC65isNumber.java) | [LC67](src/leetCode/LC67addBinary.java) | [LC68](src/leetCode/LC68fullJustify.java) | [LC71](src/leetCode/LC71simplifyPath.java) | [LC87](src/leetCode/LC87isScramble.java) | [LC151](src/leetCode/LC151reverseWords.java) | [LC166](src/leetCode/LC166fractionToDecimal.java) | [LC273](src/leetCode/LC273nums2word.java) | [LC316](src/leetCode/LC316removeDuplicateLetters.java) | [LC402](src/leetCode/LC402removeKdigits.java) | [LC438](src/leetCode/LC438findAnagrams.java) | [LC567](src/leetCode/LC567checkInclusion.java) | [LC670](src/leetCode/LC670MaximumSwap.java)

### 滑动窗口
[LC3](src/leetCode/LC3.java) | [LC30](src/leetCode/LC30FindSubString.java) | [LC76](src/leetCode/LC76minWindow.java) | [LC239](src/leetCode/LC239.java) | [LC438](src/leetCode/LC438findAnagrams.java) | [LC567](src/leetCode/LC567checkInclusion.java)

### 双指针
[LC11](src/leetCode/LC11MaxArea.java) | [LC15](src/leetCode/Top150.java) | [LC16](src/leetCode/LC16threeSumClosest.java) | [LC42](src/leetCode/LC42Trap.java) | [LC80](src/leetCode/LC80removeDuplicates.java) | [LC141](src/leetCode/LC141HasCycle.java) | [LC167](src/leetCode/LC167twoSum.java) | [LC283](src/leetCode/LC283Movezero.java) | [LC392](src/leetCode/LC392isSubsequence.java)

### 链表
[LC2](src/leetCode/LC2.java) | [LC19](src/leetCode/LC19removeNthFromEnd.java) | [LC21](src/leetCode/LC21mergeTwoList.java) | [LC23](src/leetCode/LC23mergeKList.java) | [LC24](src/leetCode/LC24SwapPairs.java) | [LC25](src/leetCode/LC25reverseKGroup.java) | [LC61](src/leetCode/LC61rotateRight.java) | [LC82](src/leetCode/LC82DeleteDuplicate.java) | [LC83](src/leetCode/LC83DeleteDuplicates.java) | [LC86](src/leetCode/LC86partition.java) | [LC92](src/leetCode/LC92ReverseBetween.java) | [LC138](src/leetCode/LC138copyRandomList.java) | [LC141](src/leetCode/LC141HasCycle.java) | [LC143](src/leetCode/LC143ReorderList.java) | [LC148](src/leetCode/LC148sortList.java) | [LC160](src/leetCode/LC160inerSecList.java) | [LC206](src/leetCode/LC206.java)

### 二叉树
[LC98](src/leetCode/LC98isValidBST.java) | [LC99](src/tree/LC99recoverTree.java) | [LC100](src/leetCode/LC100isSameTree.java) | [LC101](src/leetCode/LC101isSymmetric.java) | [LC102](src/leetCode/LC102levelOrder.java) | [LC103](src/leetCode/LC103zigzagLevelOrder.java) | [LC104](src/leetCode/LC104maxDepth.java) | [LC105](src/leetCode/LC105buildTree.java) | [LC106](src/leetCode/LC106buildTree.java) | [LC108](src/leetCode/LC108sortedArrayToBST.java) | [LC110](src/leetCode/LC110isBalanced.java) | [LC111](src/leetCode/LC111minDepth.java) | [LC112](src/leetCode/LC112hasPathSum.java) | [LC113](src/tree/LC113pathSum.java) | [LC114](src/leetCode/LC114Flatten.java) | [LC117](src/leetCode/LC117Connect.java) | [LC124](src/leetCode/LC124maxPathSum.java) | [LC129](src/leetCode/LC129sumNumbers.java) | [LC199](src/leetCode/LC199rightSideView.java) | [LC222](src/leetCode/LC222countNodes.java) | [LC226](src/leetCode/LC226invertTree.java) | [LC230](src/leetCode/LC230kthSmallest.java) | [LC236](src/leetCode/LC236lowestCommonAncestor.java) | [LC257](src/leetCode/LC257binaryTreePath.java) | [LC297](src/leetCode/LC297serializeTree.java) | [LC337](src/leetCode/LC337rob.java) | [LC437](src/tree/LC437pathSum.java) | [LC508](src/leetCode/LC508findFrequentTreeSum.java) | [LC637](src/leetCode/LC637averageOflLevels.java) | [LC988](src/leetCode/LC988smallestFromLeaf.java) | [LC1367](src/leetCode/LC1367isSubPath.java) | [LC2096](src/leetCode/LC2096getDirections.java)

### 图 & 拓扑排序
[LC127](src/leetCode/LC127ladderLength.java) | [LC130](src/leetCode/LC130solve.java) | [LC133](src/leetCode/LC133cloneGraph.java) | [LC200](src/leetCode/LC200numIslands.java) | [LC207](src/leetCode/LC207CanFinish.java) | [LC210](src/leetCode/LC210FindOrder.java) | [LC332](src/dfs/LC332findItinerary.java) | [LC433](src/leetCode/LC433minMutation.java) | [LC749](src/dfs/LC749containVirus.java) | [LC928](src/leetCode/LC928minMalwareSpread.java) | [LC994](src/leetCode/LC994OrangesRotting.java)

### DFS & 回溯
[LC17](src/dfs/LC17letterCombinations.java) | [LC22](src/dfs/LC22generateParenthesis.java) | [LC37](src/leetCode/LC37solveSudoku.java) | [LC39](src/dfs/LC39comnbinationSum.java) | [LC40](src/leetCode/LC40.java) | [LC46](src/dfs/LC46Permute.java) | [LC47](src/dfs/LC47permuteUnique.java) | [LC51](src/dfs/LC51nq.java) | [LC52](src/leetCode/LC52totalNQueens.java) | [LC78](src/dfs/LC78subsets.java) | [LC79](src/leetCode/LC79exist.java) | [LC90](src/dfs/LC90SubsetsWithDup.java) | [LC126](src/leetCode/LC126findLadders.java) | [LC131](src/leetCode/LC131partition.java) | [LC216](src/dfs/LC216combinationSum3.java) | [LC472](src/dfs/LC472findAllConcatenaatedWordsInADict.java) | [LC473](src/leetCode/LC473square.java) | [LC514](src/dfs/LC514findRotateSteps.java) | [LC753](src/dfs/LC753crackSafe.java) | [LC980](src/leetCode/LC980.java)

### 动态规划
[LC5](src/leetCode/LC05LongPalindrome.java) | [LC10](src/leetCode/LC10isMatch.java) | [LC32](src/leetCode/LC32longestValidParentheses.java) | [LC44](src/leetCode/LC44isMath.java) | [LC53](src/leetCode/LC53maxSubArray.java) | [LC55](src/leetCode/LC55canJump.java) | [LC62](src/leetCode/LC62.java) | [LC63](src/leetCode/LC63.java) | [LC64](src/leetCode/LC64minPathSum.java) | [LC70](src/leetCode/LC70climbStairs.java) | [LC72](src/leetCode/LC72minDistance.java) | [LC85](src/leetCode/LC85maximalRectangle.java) | [LC87](src/leetCode/LC87isScramble.java) | [LC91](src/leetCode/LC91numDecoding.java) | [LC97](src/leetCode/LC97isInterleave.java) | [LC115](src/leetCode/LC115numDistinct.java) | [LC120](src/leetCode/LC120MinimumTotal.java) | [LC121](src/leetCode/LC121MAXProfit.java) | [LC122](src/leetCode/LC122MAXProfit2.java) | [LC123](src/leetCode/LC123MaxProfit.java) | [LC132](src/leetCode/LC132minCut.java) | [LC139](src/leetCode/LC139wordBreak.java) | [LC140](src/leetCode/LC140wordBreak.java) | [LC188](src/leetCode/LC188MaxProfit.java) | [LC198](src/leetCode/LC198rob.java) | [LC213](src/leetCode/LC213rob.java) | [LC221](src/leetCode/LC221maximalSquare.java) | [LC241](src/leetCode/Pratice.java) | [LC300](src/leetCode/LC300lengthOfLIS.java) | [LC309](src/leetCode/LC309maxProfit.java) | [LC322](src/leetCode/LC322CoinChange.java) | [LC329](src/matrix/LC329longestIncreasingPath.java) | [LC337](src/leetCode/LC337rob.java) | [LC354](src/dp/LC354maxEnvelopes.java) | [LC376](src/leetCode/LC376wiggleMaxLength.java) | [LC377](src/dfs/LC377combinationSum.java) | [LC403](src/leetCode/LC403.java) | [LC714](src/leetCode/LC714maxProfit.java) | [LC1143](src/dp/LC1143longestCommonSubswquence.java) | [LC1277](src/leetCode/LC1277countSquares.java)

### 二分搜索
[LC4](src/leetCode/LC4FindMedianSortedArrays.java) | [LC33](src/search/LC33Search.java) | [LC34](src/leetCode/LC34.java) | [LC69](src/leetCode/LC69mySqrt.java) | [LC74](src/leetCode/LC74searchMatrix.java) | [LC153](src/leetCode/LC153.java) | [LC162](src/leetCode/LC162.java) | [LC215](src/leetCode/LC215.java) | [LC240](src/LC240.java) | [LC300](src/leetCode/LC300lengthOfLIS.java) | [LC354](src/dp/LC354maxEnvelopes.java)

### 栈 & 单调栈
[LC20](src/interview/LC20.java) | [LC71](src/leetCode/LC71simplifyPath.java) | [LC84](src/leetCode/LC84largestRectangleArea.java) | [LC150](src/leetCode/LC150.java) | [LC224](src/leetCode/LC224calculate.java) | [LC239](src/leetCode/LC239.java) | [LC316](src/leetCode/LC316removeDuplicateLetters.java) | [LC402](src/leetCode/LC402removeKdigits.java) | [LC496](src/leetCode/LC496nextGreaterElement.java) | [LC503](src/leetCode/LC503nextGreaterElements.java) | [LC556](src/leetCode/LC556nextGreaterElement.java) | [LC1475](src/leetCode/LC1475finalPrices.java) | [LC2289](src/leetCode/LC2289totalSteps.java)

### 堆 / 优先队列
[LC23](src/leetCode/LC23mergeKList.java) | [LC215](src/leetCode/LC215.java) | [LC218](src/leetCode/LC218.java) | [LC239](src/leetCode/LC239.java) | [LC295](src/leetCode/LC295MedianFinder.java) | [LC407](src/leetCode/LC407trap.java) | [LC630](src/leetCode/LC630ScheduleCourse.java)

### 贪心
[LC45](src/leetCode/LC45jump.java) | [LC55](src/leetCode/LC55canJump.java) | [LC122](src/leetCode/LC122MAXProfit2.java) | [LC134](src/leetCode/LC134CanCompleteCircuit.java) | [LC135](src/leetCode/LC135Candy.java) | [LC376](src/leetCode/LC376wiggleMaxLength.java) | [LC392](src/leetCode/LC392isSubsequence.java) | [LC402](src/leetCode/LC402removeKdigits.java) | [LC406](src/leetCode/LC406reconstructQueue.java) | [LC435](src/leetCode/LC435eraseOverlapIntervals.java) | [LC452](src/leetCode/LC452findMinArrowShots.java) | [LC455](src/leetCode/LC455findContentChildren.java) | [LC860](src/leetCode/LC860lemonadeChange.java) | [LC1005](src/leetCode/LC1005largestSumAfterKNegations.java) | [LC2589](src/leetCode/LC2589findMinimumTime.java)

### 位运算
[LC137](src/leetCode/LC137SingleNumber.java) | [LC201](src/leetCode/LC201rangeBitwiseAnd.java)

### 前缀和 / 差分数组
[LC304](src/leetCode/LC304NumMatrix.java) | [LC560](src/leetCode/LC560subarraySum.java) | [LC1109](src/leetCode/LC1109corpFlightBooking.java)

---

## 公司专项：Microsoft

> **面试风格**: 注重基础数据结构（链表、树、图），喜欢考查二分搜索变种和设计题。代码质量要求高，注重 edge case 处理和 clean code。

### 考察核心能力

| 能力维度 | 考察要点 | 高频题号 |
|----------|----------|----------|
| **链表操作** | 反转、合并、深拷贝，要求迭代+递归两种写法 | [LC206](src/leetCode/LC206.java), [LC23](src/leetCode/LC23mergeKList.java), [LC138](src/leetCode/LC138copyRandomList.java), [LC25](src/leetCode/LC25reverseKGroup.java) |
| **二分搜索变种** | 旋转数组、查找边界、矩阵搜索 | [LC33](src/search/LC33Search.java), [LC34](src/leetCode/LC34.java), [LC153](src/leetCode/LC153.java), [LC240](src/LC240.java) |
| **树的遍历** | BFS/DFS，LCA 几乎必考 | [LC236](src/leetCode/LC236lowestCommonAncestor.java), [LC102](src/leetCode/LC102levelOrder.java), [LC297](src/leetCode/LC297serializeTree.java) |
| **DP状态设计** | 能清楚定义状态和转移方程 | [LC322](src/leetCode/LC322CoinChange.java), [LC139](src/leetCode/LC139wordBreak.java), [LC72](src/leetCode/LC72minDistance.java) |
| **设计题** | OOD + 手写数据结构 | [LRUCache](src/lrucache/LRUCache.java), [ThreadSafeLRU](src/lrucache/ThreadSafeLRUCache.java) |
| **字符串处理** | 实际场景，注重 edge case | [LC273](src/leetCode/LC273nums2word.java), [LC224](src/leetCode/LC224calculate.java) |

### 必刷 Top 20（按频率排序）

1. [LRUCache.java](src/lrucache/LRUCache.java) — **LC146 LRU Cache** — 双向链表+HashMap，手写实现
2. [LC200numIslands.java](src/leetCode/LC200numIslands.java) — **LC200 Number of Islands** — DFS/BFS 矩阵遍历
3. [LC56merge.java](src/leetCode/LC56merge.java) — **LC56 Merge Intervals** — 排序后合并区间
4. [LC236lowestCommonAncestor.java](src/leetCode/LC236lowestCommonAncestor.java) — **LC236 LCA** — 后序遍历找公共祖先
5. [LC53maxSubArray.java](src/leetCode/LC53maxSubArray.java) — **LC53 Maximum Subarray** — Kadane 算法
6. [LC206.java](src/leetCode/LC206.java) — **LC206 Reverse Linked List** — 迭代和递归两种写法
7. [LC23mergeKList.java](src/leetCode/LC23mergeKList.java) — **LC23 Merge K Sorted Lists** — 最小堆或分治
8. [LC297serializeTree.java](src/leetCode/LC297serializeTree.java) — **LC297 Serialize/Deserialize Tree** — BFS 或前序遍历
9. [LC138copyRandomList.java](src/leetCode/LC138copyRandomList.java) — **LC138 Copy List with Random Pointer** — HashMap 或交错复制
10. [LC33Search.java](src/search/LC33Search.java) — **LC33 Search in Rotated Array** — 分段二分
11. [LC124maxPathSum.java](src/leetCode/LC124maxPathSum.java) — **LC124 Binary Tree Max Path Sum** — DFS 后序遍历
12. [LC322CoinChange.java](src/leetCode/LC322CoinChange.java) — **LC322 Coin Change** — 完全背包 DP
13. [LC76minWindow.java](src/leetCode/LC76minWindow.java) — **LC76 Minimum Window Substring** — 滑动窗口+哈希计数
14. [LC215.java](src/leetCode/LC215.java) — **LC215 Kth Largest Element** — QuickSelect O(n) 平均
15. [LC207CanFinish.java](src/leetCode/LC207CanFinish.java) — **LC207 Course Schedule** — 拓扑排序 BFS/DFS
16. [LC139wordBreak.java](src/leetCode/LC139wordBreak.java) — **LC139 Word Break** — DP + 前缀匹配
17. [LC273nums2word.java](src/leetCode/LC273nums2word.java) — **LC273 Integer to English Words** — 分段处理字符串
18. [LC224calculate.java](src/leetCode/LC224calculate.java) — **LC224 Basic Calculator** — 栈处理括号和运算
19. [LC295MedianFinder.java](src/leetCode/LC295MedianFinder.java) — **LC295 Find Median from Data Stream** — 大小堆
20. [LC72minDistance.java](src/leetCode/LC72minDistance.java) — **LC72 Edit Distance** — 经典二维DP

---

## 公司专项：Airbnb

> **面试风格**: 偏向实际场景题，常考设计类编码题和字符串处理，代码质量要求极高，重视 OOD 和可扩展性。

### 考察核心能力

| 能力维度 | 考察要点 | 高频题号 |
|----------|----------|----------|
| **实际场景设计** | AutoComplete、分页、LRU+TTL、日历冲突 | [AutoCompleteSystem.java](src/round3/AutoCompleteSystem.java), [Pagination.java](src/round3/Pagination.java), [LRUWithTTL.java](src/round3/LRUWithTTL.java) |
| **字符串精确处理** | 严格按spec实现，edge case覆盖 | [LC68](src/leetCode/LC68fullJustify.java), [LC273](src/leetCode/LC273nums2word.java) |
| **图遍历 & 路径** | BFS最短路径，欧拉路径 | [LC127](src/leetCode/LC127ladderLength.java), [LC332](src/dfs/LC332findItinerary.java) |
| **DP + 回溯** | 切割/分词问题，输出所有方案 | [LC139](src/leetCode/LC139wordBreak.java), [LC140](src/leetCode/LC140wordBreak.java) |
| **区间问题** | 合并、冲突检测、日历预订 | [LC56](src/leetCode/LC56merge.java), [LC435](src/leetCode/LC435eraseOverlapIntervals.java) |

### 必刷 Top 15

1. [LC68fullJustify.java](src/leetCode/LC68fullJustify.java) — **LC68 Text Justification** — Airbnb 经典题，逐行贪心填充
2. [LC224calculate.java](src/leetCode/LC224calculate.java) — **LC224 Basic Calculator** — 栈+递归处理表达式
3. [LC56merge.java](src/leetCode/LC56merge.java) — **LC56 Merge Intervals** — 区间合并
4. [LC297serializeTree.java](src/leetCode/LC297serializeTree.java) — **LC297 Serialize/Deserialize Tree** — 编解码设计
5. [LC139wordBreak.java](src/leetCode/LC139wordBreak.java) — **LC139 Word Break** — DP
6. [LC140wordBreak.java](src/leetCode/LC140wordBreak.java) — **LC140 Word Break II** — DFS+记忆化输出所有方案
7. [LC236lowestCommonAncestor.java](src/leetCode/LC236lowestCommonAncestor.java) — **LC236 LCA** — 递归 DFS
8. [LC127ladderLength.java](src/leetCode/LC127ladderLength.java) — **LC127 Word Ladder** — BFS 最短路径
9. [LC76minWindow.java](src/leetCode/LC76minWindow.java) — **LC76 Min Window Substring** — 滑动窗口
10. [LC210FindOrder.java](src/leetCode/LC210FindOrder.java) — **LC210 Course Schedule II** — 拓扑排序
11. [LC22generateParenthesis.java](src/dfs/LC22generateParenthesis.java) — **LC22 Generate Parentheses** — DFS 回溯
12. [LC221maximalSquare.java](src/leetCode/LC221maximalSquare.java) — **LC221 Maximal Square** — DP
13. [LC198rob.java](src/leetCode/LC198rob.java) / [LC213rob.java](src/leetCode/LC213rob.java) — **House Robber I/II** — 环形 DP
14. [LC332findItinerary.java](src/dfs/LC332findItinerary.java) — **LC332 Reconstruct Itinerary** — 欧拉路径
15. [LC230kthSmallest.java](src/leetCode/LC230kthSmallest.java) — **LC230 Kth Smallest in BST** — 中序遍历

---

## 公司专项：Google / Meta / Amazon

### Google 高频 — 核心考察：算法深度、复杂度分析能力、最优解推导

| # | 题目 | 核心考察 | 代码 |
|---|------|---------|------|
| 329 | Longest Increasing Path in Matrix | **记忆化DFS vs 拓扑排序，复杂度证明** | [LC329longestIncreasingPath.java](src/matrix/LC329longestIncreasingPath.java) |
| 84 | Largest Rectangle in Histogram | **单调栈核心应用，扩展到LC85** | [LC84largestRectangleArea.java](src/leetCode/LC84largestRectangleArea.java) |
| 218 | The Skyline Problem | **扫描线+堆，事件驱动思想** | [LC218.java](src/leetCode/LC218.java) |
| 10 | Regular Expression Matching | **二维DP，`*`号的状态转移分析** | [LC10isMatch.java](src/leetCode/LC10isMatch.java) |
| 834 | Sum of Distances in Tree | **换根DP：O(n)的两趟DFS** | [LC834TreeDistanceSum.java](src/leetCode/LC834TreeDistanceSum.java) |
| 753 | Cracking the Safe | **De Bruijn序列 + 欧拉路径** | [LC753crackSafe.java](src/dfs/LC753crackSafe.java) |
| 407 | Trapping Rain Water II | **3D接雨水：最小堆+BFS** | [LC407trap.java](src/leetCode/LC407trap.java) |
| 4 | Median of Two Sorted Arrays | **二分较短数组，O(log(min(m,n)))** | [LC4FindMedianSortedArrays.java](src/leetCode/LC4FindMedianSortedArrays.java) |
| 72 | Edit Distance | **经典DP面试题，三种操作的递推** | [LC72minDistance.java](src/leetCode/LC72minDistance.java) |

### Meta 高频 — 核心考察：代码速度、沟通能力、实用算法

| # | 题目 | 核心考察 | 代码 |
|---|------|---------|------|
| 236 | LCA | **后序DFS，变种：BST/多节点/带parent** | [LC236lowestCommonAncestor.java](src/leetCode/LC236lowestCommonAncestor.java) |
| 199 | Right Side View | **BFS vs DFS两种方式对比** | [LC199rightSideView.java](src/leetCode/LC199rightSideView.java) |
| 560 | Subarray Sum Equals K | **前缀和+HashMap，为什么不能滑动窗口** | [LC560subarraySum.java](src/leetCode/LC560subarraySum.java) |
| 238 | Product of Array Except Self | **前后缀积，不用除法** | [LC238productExceptSelf.java](src/leetCode/LC238productExceptSelf.java) |
| 143 | Reorder List | **三步走：找中点+反转+合并** | [LC143ReorderList.java](src/leetCode/LC143ReorderList.java) |
| 215 | Kth Largest | **QuickSelect vs 堆，选择依据** | [LC215.java](src/leetCode/LC215.java) |
| 283 | Move Zeroes | **双指针partition变种** | [LC283Movezero.java](src/leetCode/LC283Movezero.java) |
| 34 | Find First/Last Position | **两次二分找左右边界** | [LC34.java](src/leetCode/LC34.java) |

### Amazon 高频 — 核心考察：BFS/DFS矩阵题、实际系统场景

| # | 题目 | 核心考察 | 代码 |
|---|------|---------|------|
| 200 | Number of Islands | **矩阵DFS/BFS模板** | [LC200numIslands.java](src/leetCode/LC200numIslands.java) |
| 994 | Rotting Oranges | **多源BFS模板** | [LC994OrangesRotting.java](src/leetCode/LC994OrangesRotting.java) |
| 23 | Merge K Sorted Lists | **堆 vs 分治，复杂度对比** | [LC23mergeKList.java](src/leetCode/LC23mergeKList.java) |
| 127 | Word Ladder | **BFS最短路径，双向BFS优化** | [LC127ladderLength.java](src/leetCode/LC127ladderLength.java) |
| 297 | Serialize/Deserialize Tree | **编解码设计，多种序列化方式** | [LC297serializeTree.java](src/leetCode/LC297serializeTree.java) |
| 273 | Integer to English Words | **字符串处理，千位分段** | [LC273nums2word.java](src/leetCode/LC273nums2word.java) |
| 53 | Maximum Subarray | **Kadane算法 + 分治法** | [LC53maxSubArray.java](src/leetCode/LC53maxSubArray.java) |
| 91 | Decode Ways | **类爬楼梯DP，0的边界处理** | [LC91numDecoding.java](src/leetCode/LC91numDecoding.java) |

---

## 系统设计编码题

> 你的仓库中有丰富的系统设计编码实现，这些在 Senior/Staff+ 面试中非常有价值。

### 已实现的设计题

| 题目 | 考察核心 | 代码 |
|------|----------|------|
| LRU Cache | **双向链表+HashMap, O(1) get/put，手写不借助LinkedHashMap** | [LRUCache.java](src/lrucache/LRUCache.java) |
| LRU with TTL | **增加过期时间，惰性删除 vs 主动清理** | [LRUWithTTL.java](src/round3/LRUWithTTL.java) / [TTLLRU.java](src/interview/TTLLRU.java) |
| Thread-Safe LRU | **ReadWriteLock vs ConcurrentHashMap + 分段锁** | [ThreadSafeLRUCache.java](src/lrucache/ThreadSafeLRUCache.java) |
| Singly Linked List LRU | **单链表实现LRU的挑战：删除O(n)** | [SinglyLinkedListLRUCache.java](src/lrucache/SinglyLinkedListLRUCache.java) |
| Rate Limiter (Token Bucket) | **AtomicLong + CAS 无锁实现** | [RateLimiter.java](src/round3/RateLimiter.java) |
| Rate Limiter (多种策略) | **Token Bucket / Leaky Bucket / Sliding Window 对比** | [ratelimiter/](src/ratelimiter/) |
| Circuit Breaker | **三态切换：Closed->Open->HalfOpen，失败率统计** | [CircuitBreaker.java](src/CircuitBreaker/CircuitBreaker.java) |
| AutoComplete System | **Trie + LRU Cache + 权重排序** | [AutoCompleteSystem.java](src/round3/AutoCompleteSystem.java) |
| Task Scheduler | **贪心/堆调度，CPU空闲最小化** | [TaskScheduler.java](src/round3/TaskScheduler.java) |
| Elevator Scheduler | **状态机 + 调度算法（SCAN/LOOK）** | [ElevatorScheduler.java](src/round3/ElevatorScheduler.java) |
| In-Memory File System | **Trie结构目录树，支持CRUD** | [InMemorySystem.java](src/leetCode/InMemorySystem.java) |
| Producer-Consumer Queue | **BlockingQueue / wait-notify 模式** | [MessageQueue.java](src/queue/MessageQueue.java) |
| Thread-Safe Blocking Queue | **ReentrantLock + Condition** | [ThreadSafeBlockingQueue.java](src/queue/ThreadSafeBlockingQueue.java) |
| Debouncer / Throttler | **ScheduledExecutor 延迟执行** | [Debouncer.java](src/debouncer/Debouncer.java) / [Throttler.java](src/debouncer/Throttler.java) |
| Traffic Controller | **并发流量控制** | [TrafficController.java](src/Traffic/TrafficController.java) |
| Calculator | **栈实现表达式求值（含括号优先级）** | [Calculator.java](src/round3/Calculator.java) |
| Codec (Serialize/Deserialize) | **编解码设计** | [Codec.java](src/round3/Codec.java) |
| Top K Movies | **外部排序/堆/MapReduce** | [TopKMovies.java](src/movies/TopKMovies.java) |

### 面试常问的系统设计编码题（建议补充）
- **LFU Cache** (LC460) — 最不经常使用缓存
- **分布式 ID 生成器** (Snowflake)
- **一致性哈希** (Consistent Hashing)
- **布隆过滤器** (Bloom Filter)
- **延迟队列** (Delay Queue)

---

## 多线程 & 并发题

| # | 题目 | 核心技术 | 考察核心 | 代码 |
|---|------|----------|----------|------|
| 1114 | Print in Order | CountDownLatch/Semaphore | **线程同步的多种实现方式** | [LC1114.java](src/thread/LC1114.java) |
| 1115 | Print FooBar Alternately | Condition/Semaphore | **交替执行：信号量 vs 条件变量** | [LC1115.java](src/thread/LC1115.java) |
| 1117 | Building H2O | Barrier/Semaphore | **CyclicBarrier + Semaphore 组合** | [LC1117H2O.java](src/thread/LC1117H2O.java) |
| - | 交替打印 1-2-3 (多种实现) | ReentrantLock+Condition | **Lock/Condition 精确唤醒** | [Thread123Condition.java](src/thread/Thread123Condition.java) |
| - | 交替打印 (synchronized) | synchronized+wait/notify | **wait/notify 基础模型** | [Thread123Sync.java](src/thread/Thread123Sync.java) |
| - | 交替打印 (ReentrantLock) | ReentrantLock | **公平锁 vs 非公平锁** | [Thread123ReentrantLock.java](src/thread/Thread123ReentrantLock.java) |
| - | 交替打印奇偶 | synchronized | **两线程协作** | [ThreadPrint.java](src/thread/ThreadPrint.java) |
| - | CyclicBarrier 测试 | CyclicBarrier | **屏障同步，所有线程到达后一起继续** | [CyclicTest.java](src/thread/CyclicTest.java) |
| - | 多线程 WordCount | Thread Pool | **线程池 + Map-Reduce 思想** | [WordCountMultiThread.java](src/bigdata/WordCountMultiThread.java) |

### 面试高频并发知识点
1. `synchronized` vs `ReentrantLock` — 可中断、可超时、公平锁、多Condition
2. `volatile` — 可见性保证，不保证原子性，禁止指令重排
3. `CAS` 与 `AtomicXxx` — 无锁编程，ABA问题及解决
4. `CountDownLatch` vs `CyclicBarrier` vs `Semaphore` — 一次性/可重用/许可证
5. `ThreadPool` — 核心参数7个，4种拒绝策略
6. 死锁四条件 & 避免方法

---

## 衍生题目与解答

### 衍生 1：基于 [LC3](src/leetCode/LC3.java) (Longest Substring Without Repeating Characters)

**衍生题：至多包含 K 个不同字符的最长子串 (LC340)** — Microsoft, Google 高频

```java
// 给定字符串 s 和整数 k，找到至多包含 k 个不同字符的最长子串长度
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {
        map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);

        while (map.size() > k) {
            char c = s.charAt(left);
            map.put(c, map.get(c) - 1);
            if (map.get(c) == 0) map.remove(c);
            left++;
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

**衍生题：最小覆盖子串变种 — 最小窗口包含所有字符至少 K 次**

```java
public String minWindowKTimes(String s, String t, int k) {
    int[] need = new int[128], have = new int[128];
    int required = 0;
    for (char c : t.toCharArray()) {
        if (need[c] == 0) required++;
        need[c] = k;
    }

    int left = 0, formed = 0, minLen = Integer.MAX_VALUE, start = 0;
    for (int right = 0; right < s.length(); right++) {
        have[s.charAt(right)]++;
        if (need[s.charAt(right)] > 0 && have[s.charAt(right)] == need[s.charAt(right)])
            formed++;

        while (formed == required) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            have[s.charAt(left)]--;
            if (need[s.charAt(left)] > 0 && have[s.charAt(left)] < need[s.charAt(left)])
                formed--;
            left++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
}
```

---

### 衍生 2：基于 [LC200](src/leetCode/LC200numIslands.java) (Number of Islands)

**衍生题：LC695 - 岛屿的最大面积** — Amazon, Microsoft

```java
public int maxAreaOfIsland(int[][] grid) {
    int maxArea = 0;
    for (int i = 0; i < grid.length; i++)
        for (int j = 0; j < grid[0].length; j++)
            if (grid[i][j] == 1)
                maxArea = Math.max(maxArea, dfs(grid, i, j));
    return maxArea;
}

private int dfs(int[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0)
        return 0;
    grid[i][j] = 0;
    return 1 + dfs(grid, i+1, j) + dfs(grid, i-1, j) + dfs(grid, i, j+1) + dfs(grid, i, j-1);
}
```

**衍生题：LC305 - 岛屿数量 II（动态加点 + Union-Find）** — Google

```java
public List<Integer> numIslands2(int m, int n, int[][] positions) {
    int[] parent = new int[m * n];
    int[] rank = new int[m * n];
    Arrays.fill(parent, -1);
    List<Integer> result = new ArrayList<>();
    int count = 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

    for (int[] pos : positions) {
        int r = pos[0], c = pos[1], id = r * n + c;
        if (parent[id] != -1) { result.add(count); continue; }

        parent[id] = id;
        count++;

        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1], nid = nr * n + nc;
            if (nr >= 0 && nr < m && nc >= 0 && nc < n && parent[nid] != -1) {
                int px = find(parent, id), py = find(parent, nid);
                if (px != py) {
                    if (rank[px] < rank[py]) parent[px] = py;
                    else if (rank[px] > rank[py]) parent[py] = px;
                    else { parent[py] = px; rank[px]++; }
                    count--;
                }
            }
        }
        result.add(count);
    }
    return result;
}

private int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

---

### 衍生 3：基于 [LC56](src/leetCode/LC56merge.java) (Merge Intervals)

**衍生题：LC986 - 区间列表的交集** — Meta

```java
public int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < A.length && j < B.length) {
        int lo = Math.max(A[i][0], B[j][0]);
        int hi = Math.min(A[i][1], B[j][1]);
        if (lo <= hi) result.add(new int[]{lo, hi});
        if (A[i][1] < B[j][1]) i++; else j++;
    }
    return result.toArray(new int[0][]);
}
```

**衍生题：LC253 - 会议室 II（最少需要几个会议室）** — Microsoft, Airbnb, Google

```java
public int minMeetingRooms(int[][] intervals) {
    int[] starts = new int[intervals.length], ends = new int[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    Arrays.sort(starts);
    Arrays.sort(ends);

    int rooms = 0, endPtr = 0;
    for (int i = 0; i < starts.length; i++) {
        if (starts[i] < ends[endPtr]) rooms++;
        else endPtr++;
    }
    return rooms;
}
```

---

### 衍生 4：基于 [LRUCache](src/lrucache/LRUCache.java) (LRU Cache)

**衍生题：LFU Cache (LC460)** — Microsoft, Google

```java
class LFUCache {
    int capacity, minFreq;
    Map<Integer, int[]> keyToValFreq;
    Map<Integer, LinkedHashSet<Integer>> freqToKeys;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        keyToValFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
    }

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
                LinkedHashSet<Integer> minSet = freqToKeys.get(minFreq);
                int evict = minSet.iterator().next();
                minSet.remove(evict);
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

### 衍生 5：基于 [LC236](src/leetCode/LC236lowestCommonAncestor.java) (LCA)

**衍生题：LC1644 - LCA II（节点可能不存在）** — Meta, Microsoft

```java
boolean foundP = false, foundQ = false;

public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    TreeNode result = dfs(root, p, q);
    return (foundP && foundQ) ? result : null;
}

private TreeNode dfs(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null) return null;
    TreeNode left = dfs(node.left, p, q);
    TreeNode right = dfs(node.right, p, q);
    // 注意：必须先递归再判断，确保两个节点都被搜索到
    if (node == p) { foundP = true; return node; }
    if (node == q) { foundQ = true; return node; }
    if (left != null && right != null) return node;
    return left != null ? left : right;
}
```

**衍生题：LC1676 - LCA IV（多个节点的 LCA）** — Meta

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
    Set<TreeNode> set = new HashSet<>(Arrays.asList(nodes));
    return dfs(root, set);
}

private TreeNode dfs(TreeNode node, Set<TreeNode> set) {
    if (node == null || set.contains(node)) return node;
    TreeNode left = dfs(node.left, set);
    TreeNode right = dfs(node.right, set);
    if (left != null && right != null) return node;
    return left != null ? left : right;
}
```

---

### 衍生 6：基于 [LC322](src/leetCode/LC322CoinChange.java) (Coin Change)

**衍生题：LC518 - Coin Change II（组合数）** — Amazon

```java
// 求组合数而非最少硬币数，注意外层遍历coin避免重复计数
public int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins)          // 外层遍历coin = 组合数
        for (int j = coin; j <= amount; j++)
            dp[j] += dp[j - coin];
    return dp[amount];
}
```

**衍生题：LC377 - Combination Sum IV（排列数）** — 已实现 [LC377](src/dfs/LC377combinationSum.java)

```java
// 注意和 LC518 的区别：外层遍历amount = 排列数
public int combinationSum4(int[] nums, int target) {
    int[] dp = new int[target + 1];
    dp[0] = 1;
    for (int j = 1; j <= target; j++)   // 外层遍历amount = 排列数
        for (int num : nums)
            if (j >= num) dp[j] += dp[j - num];
    return dp[target];
}
```

---

### 衍生 7：基于 [LC215](src/leetCode/LC215.java) (Kth Largest Element)

**衍生题：LC347 - Top K Frequent Elements** — Amazon, Microsoft — 已实现 [TopKFrequentElements.java](src/round3/TopKFrequentElements.java)

```java
// 桶排序 O(n) 解法
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);

    List<Integer>[] buckets = new List[nums.length + 1];
    for (var entry : freq.entrySet()) {
        int f = entry.getValue();
        if (buckets[f] == null) buckets[f] = new ArrayList<>();
        buckets[f].add(entry.getKey());
    }

    int[] result = new int[k];
    int idx = 0;
    for (int i = buckets.length - 1; i >= 0 && idx < k; i--)
        if (buckets[i] != null)
            for (int n : buckets[i]) { result[idx++] = n; if (idx == k) break; }
    return result;
}
```

**衍生题：LC973 - K Closest Points to Origin** — Meta, Amazon

```java
// QuickSelect O(n) 平均
public int[][] kClosest(int[][] points, int k) {
    quickSelect(points, 0, points.length - 1, k);
    return Arrays.copyOf(points, k);
}

private void quickSelect(int[][] pts, int lo, int hi, int k) {
    if (lo >= hi) return;
    int pivot = dist(pts[hi]), i = lo;
    for (int j = lo; j < hi; j++)
        if (dist(pts[j]) <= pivot) swap(pts, i++, j);
    swap(pts, i, hi);
    if (i == k - 1) return;
    else if (i < k - 1) quickSelect(pts, i + 1, hi, k);
    else quickSelect(pts, lo, i - 1, k);
}

private int dist(int[] p) { return p[0]*p[0] + p[1]*p[1]; }
private void swap(int[][] p, int i, int j) { int[] t = p[i]; p[i] = p[j]; p[j] = t; }
```

---

### 衍生 8：基于 [RateLimiter](src/round3/RateLimiter.java) (Rate Limiter)

**衍生题：滑动窗口限流器** — 对比你的 [Token Bucket 实现](src/ratelimiter/TokenBucketRateLimiter.java)

```java
class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final long windowSizeMs;
    private final Deque<Long> timestamps = new ArrayDeque<>();

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
    }

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > windowSizeMs)
            timestamps.pollFirst();

        if (timestamps.size() < maxRequests) {
            timestamps.addLast(now);
            return true;
        }
        return false;
    }
}
```

**衍生题：分布式限流器（Redis Lua 脚本思路）**

```lua
-- Redis Lua 实现滑动窗口限流
local key = KEYS[1]
local limit = tonumber(ARGV[1])
local window = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

redis.call('ZREMRANGEBYSCORE', key, 0, now - window)
local count = redis.call('ZCARD', key)

if count < limit then
    redis.call('ZADD', key, now, now .. math.random())
    redis.call('EXPIRE', key, window / 1000)
    return 1
else
    return 0
end
```

---

## 速查：算法模板

### 滑动窗口模板
```java
int left = 0;
Map<Character, Integer> window = new HashMap<>();
for (int right = 0; right < s.length(); right++) {
    window.merge(s.charAt(right), 1, Integer::sum);
    while (/* 窗口不满足条件 */) {
        char c = s.charAt(left++);
        window.merge(c, -1, Integer::sum);
        if (window.get(c) == 0) window.remove(c);
    }
    // 更新答案
}
```

### 二分搜索模板（查找左边界）
```java
int lo = 0, hi = nums.length - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
return lo; // 第一个 >= target 的位置
```

### DFS 回溯模板
```java
void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, int start) {
    result.add(new ArrayList<>(path)); // 或在满足条件时添加
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i-1]) continue; // 去重
        path.add(nums[i]);
        backtrack(result, path, nums, i + 1);
        path.remove(path.size() - 1);
    }
}
```

### 拓扑排序模板 (BFS - Kahn's Algorithm)
```java
int[] indegree = new int[n];
// 建图并计算入度...
Queue<Integer> queue = new LinkedList<>();
for (int i = 0; i < n; i++) if (indegree[i] == 0) queue.offer(i);
List<Integer> order = new ArrayList<>();
while (!queue.isEmpty()) {
    int node = queue.poll();
    order.add(node);
    for (int next : graph.get(node))
        if (--indegree[next] == 0) queue.offer(next);
}
// order.size() == n 则无环
```

### Union-Find 模板（路径压缩 + 按秩合并）
```java
int[] parent, rank;
void init(int n) {
    parent = new int[n]; rank = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
}
int find(int x) {
    if (parent[x] != x) parent[x] = find(parent[x]); // 路径压缩
    return parent[x];
}
void union(int x, int y) {
    int px = find(x), py = find(y);
    if (px == py) return;
    if (rank[px] < rank[py]) parent[px] = py;
    else if (rank[px] > rank[py]) parent[py] = px;
    else { parent[py] = px; rank[px]++; }
}
```

### 单调栈模板（下一个更大元素）
```java
int[] result = new int[nums.length];
Arrays.fill(result, -1);
Deque<Integer> stack = new ArrayDeque<>(); // 存索引
for (int i = 0; i < nums.length; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        result[stack.pop()] = nums[i];
    }
    stack.push(i);
}
```

### 多源 BFS 模板
```java
Queue<int[]> queue = new LinkedList<>();
// 所有源点同时入队
for (/* each source */) queue.offer(new int[]{r, c});
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
int step = 0;
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        int[] cur = queue.poll();
        for (int[] d : dirs) {
            int nr = cur[0]+d[0], nc = cur[1]+d[1];
            if (/* in bounds && not visited */) {
                // mark visited
                queue.offer(new int[]{nr, nc});
            }
        }
    }
    step++;
}
```

---

## 复习计划建议

### Week 1：基础高频（每天 4-5 题）
- Day 1: 数组双指针 — [LC11](src/leetCode/LC11MaxArea.java), [LC15](src/leetCode/Top150.java), [LC42](src/leetCode/LC42Trap.java), [LC53](src/leetCode/LC53maxSubArray.java), [LC283](src/leetCode/LC283Movezero.java)
- Day 2: 滑动窗口 — [LC3](src/leetCode/LC3.java), [LC76](src/leetCode/LC76minWindow.java), [LC438](src/leetCode/LC438findAnagrams.java), [LC567](src/leetCode/LC567checkInclusion.java)
- Day 3: 链表 — [LC206](src/leetCode/LC206.java), [LC21](src/leetCode/LC21mergeTwoList.java), [LC23](src/leetCode/LC23mergeKList.java), [LC141](src/leetCode/LC141HasCycle.java), [LC143](src/leetCode/LC143ReorderList.java)
- Day 4: 二叉树基础 — [LC102](src/leetCode/LC102levelOrder.java), [LC226](src/leetCode/LC226invertTree.java), [LC236](src/leetCode/LC236lowestCommonAncestor.java), [LC105](src/leetCode/LC105buildTree.java), [LC98](src/leetCode/LC98isValidBST.java)
- Day 5: 二分搜索 — [LC33](src/search/LC33Search.java), [LC34](src/leetCode/LC34.java), [LC153](src/leetCode/LC153.java), [LC162](src/leetCode/LC162.java), [LC240](src/LC240.java)
- Day 6: 基础 DP — [LC322](src/leetCode/LC322CoinChange.java), [LC300](src/leetCode/LC300lengthOfLIS.java), [LC139](src/leetCode/LC139wordBreak.java), [LC198](src/leetCode/LC198rob.java), [LC121](src/leetCode/LC121MAXProfit.java)
- Day 7: 复习 + 模拟面试

### Week 2：中级题型
- Day 1: BFS/DFS — [LC200](src/leetCode/LC200numIslands.java), [LC207](src/leetCode/LC207CanFinish.java), [LC210](src/leetCode/LC210FindOrder.java), [LC994](src/leetCode/LC994OrangesRotting.java), [LC127](src/leetCode/LC127ladderLength.java)
- Day 2: 回溯 — [LC46](src/dfs/LC46Permute.java), [LC78](src/dfs/LC78subsets.java), [LC39](src/dfs/LC39comnbinationSum.java), [LC22](src/dfs/LC22generateParenthesis.java), [LC79](src/leetCode/LC79exist.java)
- Day 3: 栈 & 单调栈 — [LC84](src/leetCode/LC84largestRectangleArea.java), [LC239](src/leetCode/LC239.java), [LC224](src/leetCode/LC224calculate.java), [LC20](src/interview/LC20.java), [LC150](src/leetCode/LC150.java)
- Day 4: 进阶 DP — [LC72](src/leetCode/LC72minDistance.java), [LC10](src/leetCode/LC10isMatch.java), [LC123](src/leetCode/LC123MaxProfit.java), [LC140](src/leetCode/LC140wordBreak.java)
- Day 5: 贪心 — [LC56](src/leetCode/LC56merge.java), [LC134](src/leetCode/LC134CanCompleteCircuit.java), [LC135](src/leetCode/LC135Candy.java), [LC45](src/leetCode/LC45jump.java), [LC55](src/leetCode/LC55canJump.java)
- Day 6: 堆 & 设计 — [LRU](src/lrucache/LRUCache.java), [LC295](src/leetCode/LC295MedianFinder.java), [LC215](src/leetCode/LC215.java), [LC23](src/leetCode/LC23mergeKList.java)
- Day 7: 复习 + 模拟面试

### Week 3：系统设计编码 + 公司专项
- Day 1: LRU/LFU 手写 — [LRU](src/lrucache/LRUCache.java), [LRU+TTL](src/round3/LRUWithTTL.java), [ThreadSafeLRU](src/lrucache/ThreadSafeLRUCache.java)
- Day 2: Rate Limiter + Circuit Breaker — [RateLimiter](src/round3/RateLimiter.java), [多策略限流](src/ratelimiter/), [CircuitBreaker](src/CircuitBreaker/CircuitBreaker.java)
- Day 3: Trie + AutoComplete — [Trie](src/round3/TireNode.java), [AutoComplete](src/round3/AutoCompleteSystem.java)
- Day 4: 多线程题 — [LC1114](src/thread/LC1114.java), [LC1115](src/thread/LC1115.java), [LC1117](src/thread/LC1117H2O.java)
- Day 5: Microsoft 专项 — [LC273](src/leetCode/LC273nums2word.java), [LC297](src/leetCode/LC297serializeTree.java), [LC124](src/leetCode/LC124maxPathSum.java), [LC76](src/leetCode/LC76minWindow.java)
- Day 6: Airbnb 专项 — [LC68](src/leetCode/LC68fullJustify.java), [LC332](src/dfs/LC332findItinerary.java), [LC221](src/leetCode/LC221maximalSquare.java), [LC140](src/leetCode/LC140wordBreak.java)
- Day 7: 全真模拟面试

---

> **总计覆盖：200+ 道 LeetCode 题 + 17 个系统设计编码实现 + 9 道多线程题 + 8 组衍生题**
>
> 你的仓库已经覆盖了面试中 80%+ 的高频题型。重点补充：LC253 (Meeting Rooms II)、LC347 (Top K Frequent)、LC460 (LFU Cache)、LC695 (Max Area of Island)、LC973 (K Closest Points) 这几道衍生高频题即可。
