package leetCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 LC126 - Word Ladder II
 要求返回从 beginWord 到 endWord 的所有最短转换序列。通常需要使用 BFS + 回溯（或双向 BFS + 构建父图）来获得最短路径集合。
 本方法目前仅做基础骨架处理（检查 endWord 是否存在），完整实现需构建层级父边并回溯得到所有最短路径。
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
