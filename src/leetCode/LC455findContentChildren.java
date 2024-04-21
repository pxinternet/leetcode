package leetCode;

import java.util.Arrays;

public class LC455findContentChildren {

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
