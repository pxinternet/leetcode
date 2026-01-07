package leetCode;

/**
 * LC53 最大子序和（Kadane）
 *
 * 思路：使用动态规划或在线算法维护以当前位置结尾的最大子数组和（pre），并在遍历过程中更新全局最大值。
 * 时间 O(n)，空间 O(1)（使用滚动变量）。
 */
public class LC53maxSubArray {

    /*
    文件: LC53MaxSubArray.java

    目的：在文件中加入对 LC53 "最大子序和 (Maximum Subarray)" 的详细中文解释、正确性证明与复杂度分析，
    以及若干直观数学推导和常见变体比较，便于阅读理解与面试复述。

    一、问题描述（简短回顾）
    给定一个整数数组 nums，找到一个具有最大和的连续子数组（至少包含一个元素），返回其最大和。

    二、算法思路（Kadane）
    1. 定义 currentSum(i) 为“以索引 i 结尾的最大连续子数组和”。
    2. 对于每个位置 i，存在两种选择：
       a) 将 nums[i] 单独作为新的子数组起点，得到和 nums[i]；
       b) 将 nums[i] 接在以 i-1 结尾的最优子数组后面，得到 currentSum(i-1) + nums[i]。
       因此递推：currentSum(i) = max(nums[i], currentSum(i-1) + nums[i])。
    3. 在遍历过程中维护 maxSum = max_{0<=j<=i} currentSum(j)，遍历结束后 maxSum 即为答案。

    三、正确性证明（详细）
    命题 P(i)：在处理到索引 i 时算法维护的 currentSum 等于“以 i 结尾的最大子数组和”。

    - 基础情况（i = 0）：
      按实现，currentSum(0) = nums[0]，显然这是以 0 结尾的最大子数组和（只有单个元素的选项）。

    - 归纳假设：假设对 k = 0..i-1 都成立，currentSum(k) 为以 k 结尾的最优和。
    - 归纳步骤：考虑索引 i。
      对于任意以 i 结尾的连续子数组，其形式为 nums[t] + nums[t+1] + ... + nums[i]（0 <= t <= i）。
      若 t = i，则和为 nums[i]；
      若 t <= i-1，则该子数组可以看作“以 i-1 结尾的某个连续子数组”加上 nums[i]。在所有以 i-1 结尾的子数组中，按归纳假设，currentSum(i-1) 是最大的；因此所有以 i 结尾且 t <= i-1 的候选和的最大值为 currentSum(i-1) + nums[i]。
      所以所有以 i 结尾的候选和的最大值恰为 max(nums[i], currentSum(i-1) + nums[i])。算法将 currentSum 更新为该值，因此命题 P(i) 成立。

    因此对于所有 i，currentSum(i) 都被正确计算。由于全局最大子数组必然以某个索引 j 结尾（0<=j<n），而算法维护 maxSum 为遍历过的 currentSum 的最大值，故最终返回的 maxSum 等于全局最大子数组和。

    补充：反证法视角
    假设存在一个最优连续子数组 S = nums[a..b]，其和为 OPT，且算法返回的 maxSum < OPT。
    由 S 以 b 结尾，按上面结论 currentSum(b) 应该等于 nums[a..b] 的最大和（即至少为 OPT）。但算法在处理 b 时会把 currentSum(b) 纳入 maxSum，因此最终 maxSum >= currentSum(b) >= OPT，矛盾。故不存在这样的 S。

    四、贪心直觉（另一视角，便于面试解释）
    - 若 currentSum(i-1) < 0，则把它加到 nums[i] 上只会变小（或不利），此时最好“丢弃”之前的部分，从 nums[i] 重新开始。
    - 若 currentSum(i-1) >= 0，则把它加上 nums[i] 将不减小和，因此继续扩展子数组是有益的。
    这就是为什么每步选择 max(nums[i], currentSum + nums[i]) 的合理性。

    五、复杂度
    - 时间复杂度：O(n)，单次线性扫描，每个元素 O(1) 操作。
    - 空间复杂度：O(1)，只需常数个变量（currentSum, maxSum）。

    六、边界与实现细节
    - 若数组全为负数，算法仍然正确，因为初始化使用 nums[0] 并按规则更新，结果为最大的（即最接近 0 的）负数。
    - 若可能出现整数溢出（元素与和超过 int 边界），可把 currentSum 与 maxSum 的类型改为 long。
    - 若需要还原最大子数组的起止位置，可在更新 currentSum 时记录起点（当选择 nums[i] 而非扩展时，起点 = i；当 maxSum 更新时，记录当前起点与终点）。

    七、替代方法与比较
    - 分治法（线段树/区间合并）：在每个区间返回四个量（区间和、前缀最大、后缀最大、区间最大），合并复杂度 O(n log n)，实现上更结构化但常数项较大。
    - 动态规划数组形式：用 dp[i] 表示以 i 结尾的最大和，dp[i] = max(nums[i], dp[i-1] + nums[i])，需要 O(n) 空间；Kadane 只是将其压缩到 O(1) 空间。
    - 与其他题（如 LC10 的正则匹配）无直接关系，LC53 是典型的一维前缀/局部最优转全局最优问题。

    八、举例（帮助理解）
    nums = [-2,1,-3,4,-1,2,1,-5,4]
    - 遍历过程 currentSum/maxSum:
      i=0: cs=-2, ms=-2
      i=1: cs=max(1, -2+1)=1, ms=1
      i=2: cs=max(-3,1-3)=-2, ms=1
      i=3: cs=max(4,-2+4)=4, ms=4
      i=4: cs=max(-1,4-1)=3, ms=4
      i=5: cs=max(2,3+2)=5, ms=5
      i=6: cs=max(1,5+1)=6, ms=6
      i=7: cs=max(-5,6-5)=1, ms=6
      i=8: cs=max(4,1+4)=5, ms=6
      最终答案 6（子数组 [4,-1,2,1]）

    九、实现（函数主体）
    下面给出一个简洁实现，保持代码原有结构，仅展示关键实现部分：
    */
    /*
     * maxSubArray(int[] nums)
     *
     * Correctness summary (concise, method-level):
     * - Invariant: at the start of each loop iteration for index i, `currentSum` holds the maximum
     *   subarray sum for any subarray that ends at index i-1 (before updating for i), and after
     *   computing the new currentSum it holds the max sum for subarrays ending at i.
     * - Transition: currentSum' = max(nums[i], currentSum + nums[i]) follows from enumerating
     *   choices for a subarray ending at i: either start at i, or extend a best subarray ending at i-1.
     * - Global optimum: maxSum is the maximum over all currentSum values seen; every optimal
     *   contiguous subarray ends at some index j, so it is considered when processing j.
     * - Edge cases: initialization with nums[0] handles all-negative arrays correctly.
     * - Complexity: O(n) time, O(1) extra space.
     *
     * For interview explanation: mention greedy intuition (drop negative prefix) and a short
     * inductive proof or contradiction as needed.
     */
    public int maxSubArray(int[] nums) {
        // 基本校验（按需保留）
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // 两个选择：从头开始（nums[i]）或延续之前的子数组（currentSum + nums[i]）
            currentSum = Math.max(nums[i], currentSum + nums[i]);

            // 更新全局最优
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }

    /*
     * maxSubArrayPre(int[] nums)
     *
     * Notes on equivalence and micro-optimizations:
     * - This is functionally identical to maxSubArray; variable naming `pre` emphasizes the
     *   rolling/previous-sum nature.
     * - Correctness and complexity identical to maxSubArray.
     * - Keep input validation consistent with project style (here IllegalArgumentException is used).
     */
    public int maxSubArrayPre(int[] nums) {
        // ...existing implementation...
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("nums 不能为空");
        int pre = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {

            pre = Math.max(nums[i], pre + nums[i]);
            maxSum = Math.max(pre, maxSum);
        }
        return maxSum;
    }

    /*
     * Optional utility: return max sum and the start/end indices of one optimal subarray.
     *
     * Correctness sketch:
     * - We maintain the same currentSum invariant. When we choose to start a new subarray at i
     *   (i.e., nums[i] > currentSum + nums[i]), set tempStart = i.
     * - Whenever maxSum is improved, record start=tempStart and end=i. The recorded segment
     *   corresponds to that occurrence of currentSum which equals the best ending-at-i value.
     * - Complexity: O(n) time, O(1) space (plus output).
     */
    public int[] maxSubArrayWithIndices(int[] nums) {
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("nums 不能为空");
        int currentSum = nums[0];
        int maxSum = nums[0];
        int start = 0, end = 0, tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > currentSum + nums[i]) {
                currentSum = nums[i];
                tempStart = i; // start new candidate
            } else {
                currentSum = currentSum + nums[i]; // extend
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }

        return new int[]{maxSum, start, end};
    }

    /*
     * Optional utility: long-safe variant to avoid overflow when numbers or sums may exceed int.
     *
     * Correctness: identical argument as int version, but use long for accumulators.
     */
    public long maxSubArrayLong(int[] nums) {
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("nums 不能为空");
        long currentSum = nums[0];
        long maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max((long) nums[i], currentSum + nums[i]);
            maxSum = Math.max(currentSum, maxSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        LC53maxSubArray solver = new LC53maxSubArray();
        int[] a = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println("maxSubArray (dp) = " + solver.maxSubArray(a));
        System.out.println("maxSubArray (opt) = " + solver.maxSubArrayPre(a));
        int[] res = solver.maxSubArrayWithIndices(a);
        System.out.println("maxSubArrayWithIndices = sum:" + res[0] + " start:" + res[1] + " end:" + res[2]);
        System.out.println("期望: 6");
    }
}
