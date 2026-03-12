package leetCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LC127ladderLength - 单词接龙
 *
 * 题目（概要）：从 beginWord 到 endWord，每次变一个字母，中间单词须在 wordList 中，求最短转换序列长度。
 *
 * 解法说明：双向 BFS，从 begin 和 end 同时扩展，每次扩展较小一端，遇交集即返回。
 *
 * 时间复杂度：O(k^d) 降为约 O(k^(d/2))，k 为分支因子
 * 空间复杂度：O(n)
 *
 * 示例：begin="hit", end="cog", list=["hot","dot","dog","lot","log","cog"] → 5
 */
public class LC127ladderLength {

    /**
     * 双向 BFS 求最短转换序列长度（含 begin 和 end）
     *
     * 关键点：每次从较小的一端扩展；每词逐位替换 26 个字母，在 wordSet 中则加入下一层
     *
     * @param beginWord 起始词
     * @param endWord   目标词
     * @param wordList  可用词典
     * @return 最短序列长度（词数），不可达返回 0
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;

        Set<String> wordSet = new HashSet<>(wordList);
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);

        int len = 1; // 初始转换次数

        while(!beginSet.isEmpty() && !endSet.isEmpty()) {
            // 保证从较小的一端扩展以减少分支
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

                        // 找到交集，返回当前层数 + 1
                        if (endSet.contains(target)) {
                            return len + 1;
                        }

                        // 未访问且在字典中，则加入下一层待扩展集合
                        if (!beginSet.contains(target) && wordSet.contains(target)) {
                            temp.add(target);
                            wordSet.remove(target); // 避免重复访问
                        }

                        chs[i] = old; // 恢复原字符
                    }
                }
            }
            beginSet = temp;
            len++;
        }

        return 0;
    }
}
