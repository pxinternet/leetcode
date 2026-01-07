package leetCode;

/**
 * 对于这个问题，我们可以使用动态规划的方法来解决。我们可以创建一个数组 dp，其中 dp[i] 表示以 i 结尾的最长有效括号的长度。然后我们可以遍历字符串 s，并更新 dp 数组。  以下是详细的步骤：
 * 初始化 dp 数组为 0。
 * 遍历字符串 s。对于每个字符 s[i]：
 * 如果 s[i] 是 '('，那么 dp[i] 为 0，因为以 '(' 结尾的字符串不可能是一个有效的括号字符串。
 * 如果 s[i] 是 ')'，那么我们需要检查 s[i-1]：
 * 如果 s[i-1] 是 '('，那么 dp[i] = dp[i-2] + 2。 这个的意思是前两个的长度增加一个新括号
 * 如果 s[i-1] 是 ')' 并且 s[i-dp[i-1]-1] 是 '('，那么 dp[i] = dp[i-1] + dp[i-dp[i-1]-2] + 2。
 * 在遍历过程中，记录 dp 数组的最大值，这就是最长有效括号的长度。
 */
public class LC32longestValidParentheses {

    public int longestValidParentheses(String s) {
        // 保护性检查：处理 null 或长度太短的情况
        if (s == null) return 0;

        // 这里使用动态规划：dp[i] 表示"以 i 结尾的最长有效括号长度"
        int maxLength = 0; // 修复：初始化为 0（之前写成 Integer.MIN_VALUE 是错误的）

        int n = s.length();
        if (n < 2) return 0;
        int[] dp = new int[n];
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                // 情况一：...() 直接匹配
                if (s.charAt(i - 1) == '(') {
                    dp[i] = ((i >= 2) ? dp[i - 2] : 0) + 2;
                }
                // 情况二：...)) 需要看前面有效串能否被当前 ')' 扩展
                else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
            }
            // 维护最大值
            maxLength = Math.max(dp[i], maxLength);

        }

        return maxLength;

    }

    /**
     * 双指针（两次扫描）方法：
     * - 第一次从左到右扫，计数 left 为左括号数量，right 为右括号数量。
     *   当 left == right 时，说明从起点到当前位置形成了一个合法的括号串，更新最大长度。
     *   当 right > left 时，说明当前前缀非法（多余右括号），需要重置计数。
     * - 第二次从右到左扫，思想对称：计数仍然用 left/right，但这次当 left > right 时需要重置（因为多余左括号）。
     * 该方法时间复杂度 O(n)，空间 O(1)。
     *
     * 常见 bug 与修复：
     *  - 需要处理 s 为 null 的情况（否则会 NPE）。
     *  - 在左右两次扫描时，计数和重置条件必须对称（左->右 以 right>left 重置，右->左 以 left>right 重置）。
     *
     * @param s 输入字符串，只包含 '(' 和 ')'（按题设）
     * @return 最长有效括号子串的长度
     */
    public int longestValidParenthesesDoublePointer(String s) {
        // 防御式编程：处理 null 或空串
        if (s == null || s.length() == 0) return 0;

        int left = 0, right = 0, maxLength = 0;
        // 从左到右扫描
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                // 左右匹配，构成一个合法串，长度为 2*right（或 2*left）
                maxLength = Math.max(maxLength, 2 * right);
            } else if (right > left) {
                // 右括号多，前缀失效，重置
                left = right = 0;
            }
        }

        // 从右到左扫描（对称处理多余左括号的情况）
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxLength = Math.max(maxLength, 2 * left);
            } else if (left > right) {
                // 左括号多，后缀失效，重置
                left = right = 0;
            }
        }
        return maxLength;
    }

    // 增加一个 main 用于测试上面两个方法（包含若干代表性用例）
    public static void main(String[] args) {
        LC32longestValidParentheses solver = new LC32longestValidParentheses();
        String[] tests = new String[]{
                "()(())",   // 6
                ")()())",   // 4
                "(()",      // 2
                "",         // 0
                "(()())",   // 6
                "((()))",   // 6
                "())",      // 2
                "()(()"     // 2
        };

        System.out.println("测试 longestValidParentheses (DP) 和 longestValidParenthesesDoublePointer (双指针)：");
        for (String t : tests) {
            int dpAns = solver.longestValidParentheses(t);
            int dp2Ans = solver.longestValidParenthesesDoublePointer(t);
            System.out.printf("input=\"%s\"\tDP=%d\t双指针=%d\n", t, dpAns, dp2Ans);
        }
    }
}
