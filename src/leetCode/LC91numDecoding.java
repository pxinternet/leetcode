package leetCode;

/**
 * LC91 - 解码方法
 *
 * 题目（概要）：给定仅包含数字的字符串 s，'1'-'9' 可单独解码为 A-I，'10'-'26' 可两位解码为 J-Z。
 * 求将 s 解码成字母串的方案总数。如 "12" 可解码为 "AB" 或 "L"，共 2 种。
 *
 * 算法原理：
 * - 无后效性：s[0..i) 的解码方案数只依赖于 s[0..i-1) 和 s[0..i-2)，与更早的决策无关。
 * - 完备性：每个合法解码方案都可将末尾 1 个或 2 个数字视为最后一个字母，对应 dp[i-1] 或 dp[i-2] 的某一分支。
 * - 设计动机：子问题重叠、最优子结构（方案数=各分支方案数之和），适合 DP。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：dp[0]=1，空串作为 base case（「已解码完」算 1 种）。
 * - 步骤 2：对 i=1..n，枚举「最后一位数字」的两种解释：单字符（1-9）或双字符（10-26）。
 * - 步骤 3：合法则从 dp[i-1]/dp[i-2] 累加，注意双字符需 i>1。
 *
 * 关键洞察：
 * - 前导 '0' 如 "06" 使单字符分支不成立；"10" 只能双字符解码。
 * - 与爬楼梯类似，但转移有条件（单/双字符合法性），不是简单 dp[i]=dp[i-1]+dp[i-2]。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，可优化为 O(1) 滚动变量
 *
 * 边界与注意事项：
 * - dp[0]=1：空串视为 1 种方案；前导 '0' 单字符不合法；双字符需 i>1 避免越界。
 *
 * 示例：s="12" → 2（"AB" 或 "L"）；s="226" → 3
 */
public class LC91numDecoding {

    /**
     * DP 求解解码方案数（数组版本）
     *
     * 关键点逐行说明：
     * - dp[i] 对应 s 的前 i 个字符，索引 0..i-1
     * - 循环中 a 为当前最后一位数字，b 为最后两位组成的两位数
     * - 单字符有效则从 dp[i-1] 转移；双字符有效则从 dp[i-2] 转移
     *
     * @param s 数字字符串
     * @return 解码方案总数
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = s.length();

        // dp[i] 表示 s[0..i) 的解码方案数，dp[0]=1 为 base case
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            // 当前最后一位数字 s[i-1]
            int a = s.charAt(i - 1) - '0';
            // 单字符解码：'1'-'9' 合法，'0' 不能单独解码
            if (a >= 1 && a <= 9) {
                dp[i] += dp[i - 1];
            }

            // 双字符解码：s[i-2]s[i-1] 须在 10-26 之间
            if (i > 1) {
                int b = (s.charAt(i - 2) - '0') * 10 + a;
                if (b >= 10 && b <= 26) {
                    dp[i] += dp[i - 2];
                }
            }
        }
        return dp[n];
    }

    /**
     * DP 空间优化版本：仅保留 dp[i-2] 和 dp[i-1]，用 f2、f1、res 滚动
     *
     * 关键点：res 每次循环必须重新从 0 累加，否则会重复计算
     *
     * @param s 数字字符串
     * @return 解码方案总数
     */
    public int numDecodingsMemoryImprove(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = s.length();

        // f2 = dp[i-2], f1 = dp[i-1], res = dp[i]
        int f2 = 0;
        int f1 = 1;
        int res = 0;

        for (int i = 1; i <= n; i++) {
            // 重要：res 每次循环都要清零，再从单字符、双字符两条分支累加
            res = 0;
            int a = s.charAt(i - 1) - '0';
            if (a >= 1 && a <= 9) res += f1;

            if (i > 1) {
                int b = (s.charAt(i - 2) - '0') * 10 + a;
                if (b >= 10 && b <= 26) res += f2;
            }
            // 滚动：为下一轮准备 f2=f1, f1=res
            f2 = f1;
            f1 = res;
        }
        return res;
    }
}
