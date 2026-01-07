package leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 30 - Substring with Concatenation of All Words
 *
 * 说明（问题）:
 * 给定一个字符串 s 和一组等长单词 words，找出 s 中所有由 words 中每个单词恰好出现一次且不夹杂其它字符的子串起始索引。
 * 例如 s = "barfoothefoobarman" words = ["foo","bar"] 返回 [0,9]
 *
 * 下面在代码内部会对每一段逻辑做非常详细的注释，说明为何这样做、边界情况与复杂度分析。
 */
public class LC30FindSubString {

    /**
     * 直接切片并逐词匹配的实现（易理解但不是最优）
     * 方法契约（contract）：
     * - 输入：String s（原字符串），String[] words（单词数组，题目约束：非空且所有单词长度相等）
     * - 输出：List<Integer>，返回所有满足条件的起始索引，按升序
     * - 错误模式：若输入为空或格式不对，返回空列表（而不是抛异常），这使得调用方更易处理
     *
     * 算法思路：
     * 1. 对 words 统计出现次数，构建目标计数表 wordCounts
     * 2. 对 s 中所有可能长度为 sum(words.length*wordLength) 的子串，逐个切片
     * 3. 每个候选子串，按 wordLength 切分并与 wordCounts 比较，使用 seenCounts 统计
     * 4. 若每个单词都恰好出现对应次数，则将起点加入结果
     *
     * 时间复杂度：O((n - totalLen) * words.length * wordLength)（粗略估计），实际为 O(n * m * k)（n = s.length, m = words.length, k = wordLength）
     * 空间复杂度：O(n)（由于此实现创建了 subStrings 数组）。这是为了讲解方便而牺牲的空间效率。
     */
    public List<Integer> findSubstring(String s, String[] words) {

        // 结果容器：保存满足条件的所有起始索引
        List<Integer> res = new ArrayList<>();

        // 输入校验：若 s 或 words 为 null，或 words 长度为0，直接返回空结果
        // 这是防御式编程，避免后续访问 words[0] 导致 NPE 或下标越界
        if (s == null || words == null || words.length == 0) return res;

        // 构建目标单词计数表 wordCounts：记录 words 中每个单词允许出现的次数
        Map<String, Integer> wordCounts = new HashMap<>();
        // seenCounts 用于在验证每个候选子串时记录该子串内单词的出现次数
        Map<String, Integer> seenCounts = new HashMap<>();

        // 假设题目保证所有单词长度相等，取第一个单词长度作为单词长度
        int wordLength = words[0].length();

        int n = s.length();
        int sum = 0; // 所有单词串联的总长度（totalLen = wordLength * words.length）
        for (String word : words) {
            // 额外防御性：如果发现某个单词长度与首单词不同，则返回空结果
            // 这个检查保证后续按固定长度切分是安全的
            if (word.length() != wordLength) return res;
            sum += word.length();
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        // 如果总长度比 s 长，说明 s 不可能包含任何符合条件的子串，直接返回
        if (sum > n) return res;

        // 温和的空间/时间权衡：本实现先把所有长度为 sum 的子串切出来到数组，再逐一检查
        // 这样做比较直观，但会占用额外 O(n) 的空间；面试或极限场景推荐使用优化版本
        String[] subStrings = new String[n - sum + 1];
        for (int i = 0; i < n - sum + 1; i++) {
            // substring(i, i + sum) 生成长度为 sum 的候选片段
            // 注意：Java substring 在新版本（>=7u6) 中会复制子串，不会保留原始字符串的大内存
            subStrings[i] = s.substring(i, i + sum);
        }

        // 对每一个候选子串，按 wordLength 切分并验证
        for (int i = 0; i < subStrings.length; i++) {
            // 重置当前窗口的单词出现计数（必须清空）
            seenCounts.clear();
            int j = 0; // 第 j 个单词（在当前 candidate 子串中）

            // 依次取出 candidate 中的每个 wordLength 的片段并检查
            while (j < words.length) {
                // 通过 j * wordLength 计算片段边界（在 candidate 中）
                String word = subStrings[i].substring(j * wordLength, (j + 1) * wordLength);

                if (wordCounts.containsKey(word)) {
                    // 如果该片段是 words 中的合法单词，检查当前窗口中出现次数是否超过允许次数
                    int allowed = wordCounts.getOrDefault(word, 0);
                    int current = seenCounts.getOrDefault(word, 0);
                    // 如果当前已经出现次数小于允许次数，则可以把它加入当前窗口计数
                    if (current < allowed) {
                        seenCounts.put(word, current + 1);
                    } else {
                        // 出现次数超出限制，当前起点 i 不满足，提前退出循环
                        // 例如 words = ["foo","foo","bar"]，candidate 包含三个 foo，则不合法
                        break;
                    }

                } else {
                    // 片段不是目标单词集合的一部分，当前起点不满足，提前退出循环
                    break;
                }
                j++; // 继续检查下一个单词位置
            }

            // 如果成功分割并验证完所有 words，则 j == words.length，记录起点 i
            if (j == words.length) {
                res.add(i);
            }
        }

        return res;
    }

    /**
     * 滑动窗口（按 wordLength 分组偏移）优化实现
     *
     * 详细解释（逐段）:
     * - 问题的关键是 words 中的单词长度相同，因此我们可以把字符串 s 看成若干个按 wordLength 对齐的单词序列（在不同偏移下）
     * - 对于每个偏移 offset (0..wordLength-1)，我们只在这些对齐的位置上滑动窗口，这样能确保我们每次检查的是完整单词
     * - 使用哈希表 seen 记录当前窗口内单词出现次数，使用 wordCounts 记录目标出现次数
     * - 使用 left 指针记录当前窗口左边界（始终保持按单词边界对齐），使用 count 记录当前窗口包含了多少个单词
     * - 对于每个新遇到的单词 word:
     *   1) 若 word 不在目标集合中，则窗口被打散，必须重置 seen/count，并把 left 移到 j + wordLength（即从单词后开始重新匹配）
     *   2) 若 word 在目标集合中，先把该单词计入 seen，并把 count++
     *   3) 若此时 seen[word] > wordCounts[word]，说明某个单词超额出现，需要通过移动 left（丢弃窗口左侧单词）来恢复合法性
     *   4) 当 count == words.length 时说明窗口恰好包含所有单词的合法排列，记录 left 为起点。
     *
     * 复杂度分析：
     * - 每个偏移 offset 的内部循环中，j 在索引空间上以步长 wordLength 前进，left 只会向右移动且总共移动不超过 n/wordLength 次
     * - 因此整体时间复杂度为 O(n * wordLength) 实际上简化为 O(n)
     */
    public List<Integer> findSubstringOptimized(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return res;

        int wordLength = words[0].length();
        for (String w : words) if (w.length() != wordLength) return res; // 保证每个单词等长

        int n = s.length();
        int totalLen = wordLength * words.length;
        if (n < totalLen) return res; // s 太短，无法包含全部单词

        // 目标单词计数
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String w : words) wordCounts.put(w, wordCounts.getOrDefault(w, 0) + 1);

        // 对每个偏移量分别滑动（0..wordLength-1）
        for (int offset = 0; offset < wordLength; offset++) {
            int left = offset; // 窗口左边界（按单词对齐）
            int count = 0; // 窗口内单词个数（即当前窗口覆盖的完整单词的个数）
            Map<String, Integer> seen = new HashMap<>();

            // j 以 wordLength 为步长移动到每个单词起点
            // j + wordLength <= n 保证 substring 不越界
            for (int j = offset; j + wordLength <= n; j += wordLength) {
                // 取当前单词（在 s 中）
                String word = s.substring(j, j + wordLength);

                if (wordCounts.containsKey(word)) {
                    // 这个单词是目标集合的一部分，计入 seen
                    seen.put(word, seen.getOrDefault(word, 0) + 1);
                    count++;

                    // 如果某个单词出现次数超过允许值，需要收缩左边界直到合格
                    // 例如 words = ["foo","bar"], 出现 "foo","foo","bar" 时，第一次出现过量
                    while (seen.get(word) > wordCounts.get(word)) {
                        // 取出窗口最左侧的单词并减少计数
                        String leftWord = s.substring(left, left + wordLength);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        // 左边界右移一个单词的长度
                        left += wordLength;
                        // 窗口内单词总数相应减少
                        count--;
                    }

                    // 如果窗口内的单词数量刚好等于 words.length，说明找到一个合法起点
                    if (count == words.length) {
                        res.add(left);
                        // 记录完当前窗口后，继续移动左边界以便探索下一个可能的窗口
                        String leftWord = s.substring(left, left + wordLength);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        left += wordLength;
                        count--;
                    }
                } else {
                    // 遇到不在 words 中的单词，窗口重置
                    // 将 left 移到 j + wordLength（下一单词之后），清空计数 seen 和 count
                    seen.clear();
                    count = 0;
                    left = j + wordLength;
                }
            }
        }

        return res;
    }

    // ===== 新增：详细跟踪调试方法（逐步打印每个 offset、每次 j 的内部状态与 left 收缩过程） =====
    /**
     * 对 findSubstringOptimized 的逐步跟踪版，打印详细的内部状态，便于理解算法。
     * - 对每个 offset(0..wordLength-1) 分别跟踪
     * - 每次 j 移动时打印当前 word、left、count、seen 和执行的操作
     * 此方法仅用于学习/调试，不用于高性能生产。
     */
    public void debugFindSubstringOptimized(String s, String[] words) {
        System.out.println("=== debugFindSubstringOptimized 开始 ===");
        if (s == null || words == null || words.length == 0) {
            System.out.println("输入为空，退出");
            return;
        }

        int wordLength = words[0].length();
        for (String w : words) if (w.length() != wordLength) {
            System.out.println("输入单词长度不一致，退出");
            return;
        }

        int n = s.length();
        int totalLen = wordLength * words.length;
        if (n < totalLen) {
            System.out.println("s 太短，无法包含所有单词，退出");
            return;
        }

        Map<String, Integer> wordCounts = new HashMap<>();
        for (String w : words) wordCounts.put(w, wordCounts.getOrDefault(w, 0) + 1);

        System.out.println("s = " + s);
        System.out.println("words = " + java.util.Arrays.toString(words));
        System.out.println("wordLength = " + wordLength + ", wordCounts = " + wordCounts);
        System.out.println();

        // 对每个偏移量分别跟踪
        for (int offset = 0; offset < wordLength; offset++) {
            System.out.println("--- offset = " + offset + " 开始 ---");
            int left = offset;
            int count = 0;
            Map<String, Integer> seen = new HashMap<>();

            // j 以单词长度为步长遍历
            for (int j = offset; j + wordLength <= n; j += wordLength) {
                String word = s.substring(j, j + wordLength);
                System.out.println("j=" + j + " word=\"" + word + "\" left=" + left + " count=" + count + " seen=" + seen);

                if (wordCounts.containsKey(word)) {
                    // 将当前 word 纳入 seen
                    seen.put(word, seen.getOrDefault(word, 0) + 1);
                    count++;
                    System.out.println("  -> word 在目标中，seen 更新为 " + seen + ", count=" + count);

                    // 若当前 word 导致超额，收缩左边界
                    while (seen.get(word) > wordCounts.get(word)) {
                        String leftWord = s.substring(left, left + wordLength);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        left += wordLength;
                        count--;
                        System.out.println("     收缩: 移除 leftWord=\"" + leftWord + "\" -> left=" + left + " seen=" + seen + " count=" + count);
                    }

                    // 如果窗口正好包含全部单词
                    if (count == words.length) {
                        System.out.println("  *** 找到匹配起点: " + left + " (窗口单词总数=" + count + ")");
                        // 为了继续搜索下一个可能位置，移动 left 一个单词
                        String leftWord = s.substring(left, left + wordLength);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        left += wordLength;
                        count--;
                        System.out.println("     记录后移动 left -> left=" + left + " seen=" + seen + " count=" + count);
                    }
                } else {
                    // 遇到无效单词，重置窗口
                    System.out.println("  -> word 不在目标中，窗口重置 (next left = " + (j + wordLength) + ")");
                    seen.clear();
                    count = 0;
                    left = j + wordLength;
                }
            }

            System.out.println("--- offset = " + offset + " 结束 ---\n");
        }

        System.out.println("=== debugFindSubstringOptimized 结束 ===");
    }

    /**
     * 简单的 main 演示（测试 findSubstring 和 findSubstringOptimized）
     * 我在这里保留了一些测试用例，便于快速本地运行和验证。主函数不会被自动测试框架调用（若集成测试，可另外编写测试文件）。
     */
    public static void main(String[] args) {
        LC30FindSubString solver = new LC30FindSubString();

        // 示例1：最常见的例子
        String s = "barfoothefoobarman";
        String[] words = new String[] {"foo", "bar"};
        List<Integer> ans = solver.findSubstring(s, words);
        System.out.println("原始实现：");
        System.out.println("s = " + s);
        System.out.println("words = [foo, bar]");
        System.out.println("result = " + ans); // 期望 [0,9]

        // 示例2：重复单词且不能组成完整序列
        s = "wordgoodgoodgoodbestword";
        words = new String[] {"word", "good", "best", "word"};
        ans = solver.findSubstring(s, words);
        System.out.println("s = " + s);
        System.out.println("words = [word,good,best,word]");
        System.out.println("result = " + ans); // 期望 []

        // 测试优化实现
        s = "barfoothefoobarman";
        words = new String[] {"foo", "bar"};
        ans = solver.findSubstringOptimized(s, words);
        System.out.println("优化实现：");
        System.out.println("s = " + s);
        System.out.println("words = [foo, bar]");
        System.out.println("result = " + ans); // 期望 [0,9]

        // 另一个例子，用于测试边界：包含重复单词但可合法匹配的位置
        s = "barfoofoobarthefoobarman"; // 包含 bar foo foo bar ... 组合
        words = new String[] {"bar", "foo", "foo"};
        ans = solver.findSubstringOptimized(s, words);
        System.out.println("s = " + s);
        System.out.println("words = [bar,foo,foo]");
        System.out.println("result = " + ans); // 期望包含可能的起点

        // ========== 调试演示：逐步打印内部状态 ==========
        System.out.println("\n详细跟踪演示 1：foo/bar 在 barfoothefoobarman (offset trace)");
        solver.debugFindSubstringOptimized("barfoothefoobarman", new String[] {"foo", "bar"});

        System.out.println("\n详细跟踪演示 2：bar,foo,foo 在 barfoofoobarthefoobarman (展示 j 的回溯与 left 收缩过程)");
        solver.debugFindSubstringOptimized("barfoofoobarthefoobarman", new String[] {"bar", "foo", "foo"});
    }

}
