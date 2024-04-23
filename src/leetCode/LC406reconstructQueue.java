package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC406reconstructQueue {

    public int[][] reconstructQueue(int[][] people) {

        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[1]);
        List<int[]> list = new ArrayList<>();

        for(int[] p : people) {
            list.add(p[1], p);
        }

        return list.toArray(new int[list.size()][2]);
    }
}
