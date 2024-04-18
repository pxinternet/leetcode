package leetCode;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LC2007findOriginalArray {

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
