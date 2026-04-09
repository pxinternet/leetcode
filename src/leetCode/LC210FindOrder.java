package leetCode;

import java.util.*;

/**
 * LC210 - 课程表 II
 *
 * 题目概要：共 numCourses 门课，prerequisites[i]=[a,b] 表示修 a 前需先修 b。求一种合法修课顺序，无法完成则返回空数组。
 *
 * 解法说明：拓扑排序（BFS + 入度）。建图：b -> a 表示 b 是 a 的前驱；入度为 0 的节点先入队，
 * 每次出队加入结果并对其后继减入度，入度变为 0 则入队。若结果数量不等于课程数则存在环，返回空。
 *
 * 时间复杂度：O(V + E)
 * 空间复杂度：O(V)
 */
public class LC210FindOrder {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> edges = new ArrayList<>();
        int[] indeg = new int[numCourses];
        for (int i = 0; i < numCourses; i++) edges.add(new ArrayList<>());

        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
            ++indeg[info[0]];
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) queue.offer(i);
        }

        List<Integer> res = new LinkedList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            res.add(u);
            for (int v : edges.get(u)) {
                --indeg[v];
                if (indeg[v] == 0) queue.offer(v);
            }
        }

        if (res.size() != numCourses) return new int[]{};

        int[] arrres = new int[numCourses];
        for (int i = 0; i < numCourses; i++) arrres[i] = res.get(i);
        return arrres;
    }
}
