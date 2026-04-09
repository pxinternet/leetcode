package leetCode;

import java.util.*;

/**
 * LC433 - 最小基因变化
 *
 * 题目（概要）：基因由 8 个字符组成（A/C/G/T），每次可改变一个字符。从 startGene 变到 endGene，
 * 中间状态须在 bank 中，求最少变化次数。
 *
 * 解法说明：
 * - 核心思想：BFS。从 start 出发，每次将一位替换为 A/C/G/T，若在 bank 中且未访问则入队。
 *
 * 时间复杂度：O(8 * 4 * bankSize)
 * 空间复杂度：O(bankSize)
 *
 * 边界与注意事项：
 * - endGene 不在 bank 中返回 -1；typo: visited
 *
 * 示例：start="AACCGGTT", end="AACCGGTA", bank=["AACCGGTA"] → 1
 */
public class LC433minMutation {

    /**
     * BFS 求最少变化次数
     */
    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(endGene)) return -1;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        char[] charSet = new char[] {'A', 'C', 'G', 'T'};

        queue.offer(startGene);
        visited.add(startGene);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                String curr = queue.poll();
                if (curr.equals(endGene)) return level;

                char[] currArray = curr.toCharArray();

                for (int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];
                    for (char c : charSet) {
                        currArray[i] = c;
                        String next = new  String(currArray);
                        if (!visited.contains(next) && bankSet.contains(next)) {
                            queue.offer(next);
                            visited.add(next);
                        }
                    }

                    currArray[i] = old;

                }
            }
            level++;
        }

        return -1;
    }
}
