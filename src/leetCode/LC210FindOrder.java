package leetCode;

import sun.security.util.ArrayUtil;

import java.util.*;

public class LC210FindOrder {

    public int[] findOrder(int numCourses, int[][] prerequisites) {

        //广度优先算入度
        List<List<Integer>> edges = new ArrayList<>();
        int[] indeg = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
            ++indeg[info[0]];
        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> res = new LinkedList<>();
        while(!queue.isEmpty()) {
            int u = queue.poll();
            res.add(u);
            for (int v : edges.get(u)) {
                --indeg[v];
                if (indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        if (res.size() != numCourses) {
            return new int[]{};
        }

        int[] arrres = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            arrres[i] = res.get(i);
        }
        return arrres;
    }
}
