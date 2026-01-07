package leetCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 LC127 - Word Ladder（单词接龙）
 双向 BFS（bidirectional BFS）可以将搜索空间从 O(k^d) 降到接近 O(k^(d/2))，d 为深度。
 思路：从 beginWord 和 endWord 同时向中间扩展，每次优先扩展节点更少的那一端，遇到交集时返回层数。
*/
public class LC127ladderLength {

    // 计算最短转换序列长度（单词间每次变一个字母，所有中间单词必须在 wordList 中）
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)) {
            return 0;
        }

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
                    for (char c = 'a'; c < 'z'; c++) {
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
