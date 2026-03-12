package leetCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LC126 - 单词接龙 II
 *
 * 题目（概要）：从 beginWord 到 endWord，每次变一个字母，中间词须在 wordList 中。求所有最短转换序列。
 *
 * 解法说明：
 * - 典型解法：BFS 分层 + 构建父图（每词的可达前驱），再 DFS 回溯得到所有最短路径。
 * - 本实现为基础骨架：检查 endWord 是否在字典中，完整实现需 BFS 建图 + 回溯。
 *
 * 时间复杂度：完整实现 O(n * k * 26)
 * 空间复杂度：O(n)
 *
 * 示例：begin="hit", end="cog", wordList=["hot","dot","dog","lot","log","cog"] → [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 */
public class LC126findLadders {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();

        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return res;
        }
        return res;
    }
}
