package round2;

/**
 * ShortestPalindrome - 求给定字符串通过在前面添加最少字符得到回文串的方案
 *
 * 这个类包含两种常见解法：
 * 1) 递归匹配法（方法名为 shortesPalindrome，注意原始实现中方法名存在拼写错误 shortesPalindrome->shortestPalindrome 的可能意图；此处保留原名并在注释中说明）
 *    思路：从右向左用双指针尝试匹配字符串的前缀；找到最大的前缀（从开头开始）是回文前缀的长度 j。
 *    然后把后缀（从 j 到末尾）反转并加到前面，再对前缀递归处理。
 *    该方法直观但最坏情况下复杂度为 O(n^2)。
 *
 * 2) KMP 辅助匹配法（方法 shortestPalindrome）：
 *    思路：构造字符串 newStr = s + "#" + reverse(s)，对 newStr 求 KMP 的 prefix 函数。
 *    prefix 数组的最后一个值表示 s 的前缀与 reverse(s) 后缀的最长匹配长度，即 s 的最长 "回文前缀" 的长度。
 *    根据这个长度直接计算需要在前面添加的字符，从而在线性时间内得到答案。
 *
 * 代码里还包含 computePrefix 方法用于构造 KMP 的前缀函数（也叫 failure 函数或 lps 数组）。
 *
 * 示例：
 * - s = "aacecaaa" -> shortestPalindrome(s) 返回 "aaacecaaa"
 * - s = "abcd"     -> shortestPalindrome(s) 返回 "dcbabcd"
 */
public class ShortestPalindrome {

    /*
     * 递归匹配法（原实现方法名为 shortesPalindrome，保留原名以避免调用方受影响）
     *
     * 算法说明（逐行解释）：
     * 1. int n = s.length();
     *    - 取得字符串长度。如果 s 为空或长度为 0，则下面的逻辑在外层应当处理（当前实现假定调用方传入合法字符串）。
     * 2. int j = 0;
     *    - j 用来记录从左端开始与右端比对时已经匹配上的字符数，最终 j 表示最大的回文前缀长度。
     * 3. for (int i = n - 1; i >= 0; i--) { if (s.charAt(i) == s.charAt(j)) { j++; } }
     *    - 从右向左遍历 i，和从左向右的 j 指针进行字符匹配；每次遇到相等的字符就把 j++。
     *    - 这个循环结束后，j 表示从左端开始与右端匹配上字符的数量，等价于字符串中最长的、以索引 0 开头的回文前缀的长度。
     *      为什么成立：当我们从右向左扫描时，只在字符匹配时才移动左指针 j，因此最终 j 能到达的位置就是从左边开始与右边某些位置"顺序匹配"的最大长度。
     *      该技巧用于在 O(n) 内找到最长回文前缀的长度，但并不保证最坏情况为 O(n)（外层递归会引起额外代价）。
     * 4. if (j == n) { return s; }
     *    - 如果 j 等于字符串长度，说明整个字符串本身就是回文串，直接返回原串。
     * 5. String suffix = s.substring(j);
     *    - 取出从位置 j 到末尾的后缀（这是不属于回文前缀的部分），需要把它的反转加到前面来扩展回文。
     * 6. StringBuilder sb = new StringBuilder(suffix).reverse();
     *    - 反转后缀，准备作为前缀添加到结果最前面。
     * 7. return sb.toString() + shortesPalindrome(s.substring(0, j)) + suffix;
     *    - 对前缀 s[0..j-1] 递归求解最短回文并放在中间：
     *      最终构造：reverse(suffix) + shortestPalindrome(prefix) + suffix
     *    - 直观上，这样可以确保构造出的字符串是回文，且尽量短：
     *      - reverse(suffix) + prefix + suffix 是一种对称结构，若 prefix 已经被处理为最短回文，则整体为最短。
     *
     * 正确性理由（高层）：
     * - 通过寻找从左边开始的最长回文前缀 prefix，剩余的 suffix 必须被反转并放到前面以保证 palindrome 的对称性。
     * - 递归对 prefix 再做相同的处理可以确保整体添加字符数最少（贪心 + 分治思想）。
     *
     * 复杂度：
     * - 时间复杂度：最坏情况下为 O(n^2)，例如当字符串由相同字符组成或者近似回文但每次递归只减少一个字符时，递归层数可达到 O(n)，每层做 substring/比较耗费 O(n)。
     * - 空间复杂度：取决于递归深度（最坏 O(n)）以及中间创建的字符串和 StringBuilder 的额外空间。
     *
     * 注意：
     * - 该方法名拼写为 shortesPalindrome（缺少 t），在公共 API 中建议修正为 shortestPalindromeRecursive 或类似名称以避免混淆。
     */
    public String shortesPalindrome(String s) {

        int n = s.length();
        int j = 0;

        // 从右向左遍历 i，并跟从左向右的 j 做匹配
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == s.charAt(j)) {
                // 只有在匹配时才前移 j，最终 j 表示匹配的长度
                j++;
            }
        }

        // 如果整个字符串都是回文的，直接返回
        if (j == n) {
            return s;
        }

        // suffix 是不在回文前缀内的后缀，需要把它反转并放到前面
        String suffix = s.substring(j);
        StringBuilder sb = new StringBuilder(suffix).reverse();

        // 递归处理前缀部分，并拼接：reverse(suffix) + shortestPalindrome(prefix) + suffix
        return sb.toString() + shortesPalindrome(s.substring(0, j)) + suffix;

    }

    /*
     * KMP 方法 —— 在线性时间构造最短回文
     *
     * 思路回顾：若我们令 reversedS = reverse(s)，并构造 newStr = s + "#" + reversedS，
     * 则在 newStr 上计算 KMP 的 prefix 函数（前缀函数、最长相等的真前缀-后缀长度）
     * newStr 最后一个位置的 prefix 值表示 s 的前缀与 reversedS 的后缀的最长匹配长度，
     * 该长度恰好是 s 的最长回文前缀（以索引 0 开始的最长回文）。
     *
     * 举例（s = "aacecaaa"）：
     * reversedS = "aaacecaa"；
     * newStr = "aacecaaa#aaacecaa"；对 newStr 求 prefix，最后得到 matched = 7，说明 s 的前 7 个字符是回文前缀。
     * 所以需要添加 reversedS 的前缀（长度为 reversedS.length - matched）到 s 前面。
     */
    public String shortestPalindrome(String s) {

        // 反转字符串 s
        String reversedS = new StringBuilder(s).reverse().toString();

        // 用一个特殊字符 '#' 连接，保证不会跨界匹配（该字符假定不在 s 中，也可用其他不会出现在 s 中的分隔符）
        String newStr = s + "#" + reversedS;

        // 计算 KMP 的 prefix（lps）数组
        int[] prefix = computePrefix(newStr);

        // prefix 数组最后一个位置的值代表 s 的最长回文前缀长度
        int matched = prefix[newStr.length() - 1];

        // reversedS 的前面部分（长度为 reversedS.length - matched）需要被添加到 s 的前面
        String toAdd = reversedS.substring(0, reversedS.length() - matched);

        return toAdd + s;
    }

    /**
     * 计算 KMP 的前缀函数（prefix / lps 数组）
     * prefix[i] 表示 s[0..i] 的最长相等的真前缀与后缀的长度
     *
     * 逐行解释：
     * - int n = s.length(); int[] prefix = new int[n];
     *   初始化数组与长度。
     * - int j = 0; 用 j 表示当前匹配的前缀长度（也就是前一个位置 prefix[i-1] 的值），
     *   对每个 i，我们会尝试扩展或回溯 j。
     * - for (int i = 1; i < n; i++) { ... }
     *   从 i=1 开始，因为 prefix[0] 恒为 0（单字符无法有真前缀）。
     * - while (j > 0 && s.charAt(i) != s.charAt(j)) { j = prefix[j - 1]; }
     *   如果当前字符不匹配，则根据 prefix 表回溯 j 到次短的可能匹配长度，直到匹配或回退为 0。
     * - if (s.charAt(i) == s.charAt(j)) { j++; }
     *   若匹配则扩展 j。
     * - prefix[i] = j; 记录当前 i 的前缀值。
     *
     * 复杂度：O(n)
     */
    private int[] computePrefix(String s) {
        int n = s.length();
        int[] prefix = new int[n];

        int j = 0;

        for (int i = 1; i < n; i++) {
            // 回溯：当 s[i] 与 s[j] 不匹配时，尝试使用已知的更短相等前缀
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = prefix[j - 1];
            }
            // 如果匹配则 j++ 表示前缀可以延长
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }

}
