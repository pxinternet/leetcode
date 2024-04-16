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
        //这种一般是动态规划吧
        //这里重点是理解，dp[i]的括号长度要以i为结尾，只要这样才能合理增长；因此dp[i-1]的结尾需要是一个有效括号，所以才能往后找

        int maxLength = Integer.MIN_VALUE;

        int n = s.length();
        if (n < 2) return 0;
        int[] dp = new int[n];
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = ((i >= 2) ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] -1) == '(') {
                    dp[i] = dp[i-1] + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
            }
            maxLength = Math.max(dp[i],  maxLength);

        }

        return maxLength;

    }

    /**
     * 对于这个问题，我们也可以使用双指针的方法来解决。我们可以使用两个指针 left 和 right，分别从左到右和从右到左遍历字符串 s。在遍历过程中，我们记录遇到的左括号和右括号的数量。当左括号的数量等于右括号的数量时，我们更新最长有效括号的长度。当右括号的数量大于左括号的数量时，我们将左括号和右括号的数量都重置为 0。我们分别从左到右和从右到左遍历字符串 s，是为了处理像这样的情况：((())。在从左到右的遍历中，我们无法正确处理这个情况，但是在从右到左的遍历中，我们可以正确处理。
     * @param s
     * @return
     */

    /**
     * 这个双指针的方法的正确性可以通过以下方式来证明：
     * 从左到右遍历字符串 s，当遇到一个右括号时，如果当前的左括号数量大于或等于右括号数量，那么我们就找到了一个有效的括号字符串。这是因为每个右括号都可以和一个左括号匹配，形成一个有效的括号字符串。我们可以更新最长有效括号的长度。
     * 如果当前的右括号数量大于左括号数量，那么我们就无法找到一个有效的括号字符串，因为没有足够的左括号和右括号匹配。在这种情况下，我们需要将左括号和右括号的数量都重置为 0，然后继续遍历字符串 s。
     * 从右到左遍历字符串 s，这是为了处理像这样的情况：((())。在从左到右的遍历中，我们无法正确处理这个情况，但是在从右到左的遍历中，我们可以正确处理。这是因为在从右到左的遍历中，每个左括号都可以和一个右括号匹配，形成一个有效的括号字符串。
     * 在从右到左的遍历中，我们也需要记录左括号和右括号的数量，并在左括号数量等于右括号数量时更新最长有效括号的长度。如果左括号数量大于右括号数量，我们也需要将左括号和右括号的数量都重置为 0。
     * @param s
     * @return
     */
    public int longestValidParenthesesDoublePointer(String s) {
        int left = 0, right = 0, maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxLength = Math.max(maxLength, 2 * right);
            } else if (right >left) {
                left = right = 0;
            }
        }

        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxLength = Math.max(maxLength, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }
        return maxLength;
    }
}
