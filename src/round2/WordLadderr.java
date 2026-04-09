package round2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * WordLadderr - 单词接龙（LeetCode 127 变体，双向 BFS）
 *
 * 题目（概要）：给定起始词、结束词和词表，每次只能改变一个字母，求从起始到结束的最短转换序列长度。
 * 若无法到达返回 0。
 *
 * 算法原理：
 * - 双向 BFS：从 begin 和 end 同时扩展，每次扩展较小的一侧，减少搜索空间。
 * - 每次替换一个字母为 a-z，检查是否在另一侧或词表中。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：endWord 不在词表则返回 0；初始化 beginSet、endSet，len=1。
 * - 步骤 2：每轮若 beginSet 更大则与 endSet 交换（始终扩展较小侧）。
 * - 步骤 3：对 beginSet 中每个词，枚举每一位替换为 a-z，若在 endSet 中则返回 len+1。
 * - 步骤 4：若新词在词表中则加入 temp 并从词表移除；beginSet=temp，len++。
 *
 * 关键洞察：双向 BFS 每轮扩展较小集合可减少复杂度；从词表移除已访问词避免重复。
 *
 * 时间复杂度：O(M²×N)，M 为词长，N 为词表大小
 * 空间复杂度：O(N)
 *
 * 示例：beginWord="hit", endWord="cog", wordList=["hot","dot","dog","lot","log","cog"] → 5
 */
public class WordLadderr {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);
        int len = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }

            Set<String> temp = new HashSet<>();

            for (String word : beginSet) {
                char[] chs = word.toCharArray();

                for (int i = 0; i < chs.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chs[i];

                        chs[i] = c;

                        String target = String.valueOf(chs);

                        if (endSet.contains(target)) {
                            return len + 1;
                        }

                        if (!beginSet.contains(target) && wordSet.contains(target)) {
                            temp.add(target);
                            wordSet.remove(target);
                        }

                        chs[i] = old;
                    }
                }
            }

            len++;
            beginSet = temp;
        }
        return len++;
    }
}
