package leetCode;

import java.util.Arrays;

/**
 * LC455 - 分发饼干
 *
 * 题目（概要）：g[i] 为第 i 个孩子的胃口，s[j] 为第 j 块饼干尺寸。每个孩子最多一块饼干，尺寸>=胃口才能满足。求最多满足多少孩子。
 *
 * 解法说明：
 * - 核心思想：贪心。排序后，用尽量小的饼干满足尽量大的胃口（或反过来；本实现从大到小，大饼干满足大胃口）。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(1)
 *
 * 示例：g=[1,2,3], s=[1,1] → 1；g=[1,2], s=[1,2,3] → 2
 */
public class LC455findContentChildren {

    /**
     * 贪心：排序后双指针，大饼干优先满足大胃口
     */
    public int findContentChildren(int[] g, int[] s) {

        Arrays.sort(g);
        Arrays.sort(s);

        int numOfChildren = 0;
        int index = s.length - 1;
        for (int i = g.length - 1; i >= 0; i--) {
            if (index >= 0 && g[i] <= s[index]) {
                numOfChildren++;
                index--;
            }
        }
        return numOfChildren;
    }
}
