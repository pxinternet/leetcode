package leetCode;

import java.util.Arrays;

/**
 * LC2007 - 从双倍数组中还原原数组
 *
 * 题目（概要）：changed 由 original 及其每个元素的两倍组成（任意顺序），还原 original；若无法还原返回空数组。
 *
 * 解法说明：排序后用计数，从小到大：每个 num 必须有一个 2*num 配对，否则返回 []；0 需特殊处理（0*2=0）。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(maxVal)
 */
public class LC2007findOriginalArray {

    /** 从 changed 还原 original，无法还原时返回空数组 */
    public int[] findOriginalArray(int[] changed) {
        //长度和单数
        if (changed.length == 0 || changed.length % 2 == 1) return new int[]{};
        Arrays.sort(changed);

        int[] res = new int[changed.length / 2];

        int[] n = new int[100001];

        for (int index : changed) {
            n[index]++;
        }

        int index = 0;
        for (int num : changed) {
            if (n[num] == 0) continue;
            n[num]--; //解决0的问题
            if (num * 2 >= 100001 || n[num * 2] == 0) return new int[]{};
            res[index++] = num;
            n[num * 2]--;
        }
        return res;
    }
}
