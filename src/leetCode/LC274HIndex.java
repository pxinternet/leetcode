package leetCode;

import java.util.Arrays;

public class LC274HIndex {

    public int hIndex(int[] citations) {
        //这种题，第一步先排序
        Arrays.sort(citations);
        int n = citations.length;
        int h = 0;

        int i = n -1;

        while (i >= 0 && citations[i] > h) {
            h++;
            i--;
        }
        return h;
    }
}
