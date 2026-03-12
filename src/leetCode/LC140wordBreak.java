package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC140 - 单词拆分 II
 *
 * 题目（概要）：给定 s 和 wordDict，返回所有可能的拆分方案，使 s 能拆成若干字典中的单词。
 *
 * 解法说明：
 * - 核心思想：回溯。尝试每个 word 作为前缀，若 s.startsWith(word) 则递归 s.substring(word.length())，路径用 StringBuilder 记录，回溯时 setLength 恢复。
 *
 * 时间复杂度：O(2^n) 最坏
 * 空间复杂度：O(n) 递归栈
 *
 * 边界与注意事项：
 * - s 为空时说明拆分完成，将 path 加入结果
 * - path 非空时需先加空格再接新单词
 *
 * 示例：s="catsanddog", wordDict=["cat","cats","and","sand","dog"] → ["cats and dog","cat sand dog"]
 */
public class LC140wordBreak {

    private List<String> allPath = new ArrayList<>();

    /**
     * 回溯枚举所有拆分方案
     *
     * @param s        字符串
     * @param wordDict 单词字典
     * @return 所有可行拆分（空格分隔）
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        allPath = new ArrayList<>();
        backtrack(s, wordDict, new StringBuilder());
        return allPath;
    }

    /**
     * 回溯：尝试每个 word 作为前缀，递归剩余部分
     *
     * 关键点：path 在递归前 append，回溯时 setLength(preLength) 恢复
     */
    private void backtrack(String s, List<String> wordDict, StringBuilder path) {
        if (s.isEmpty()) {
            allPath.add(path.toString().trim());
            return;
        }
        int preLength = path.length();
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                if (preLength > 0) path.append(" ");
                path.append(word);
                backtrack(s.substring(word.length()), wordDict, path);
                path.setLength(preLength);
            }
        }
    }
}
