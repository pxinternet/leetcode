package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LC207CanFinish - 课程表
 *
 * 题目（概要）：共有 numCourses 门课，prerequisites[i]=[a,b] 表示选 b 前须先选 a。判断能否完成所有课程。
 *
 * 解法说明：
 * - 问题等价于判断有向图是否有环。若有环则无法完成。
 * - DFS：三色标记，发现回边（指向灰节点）则存在环
 * - BFS：拓扑排序，计算入度，每次移除入度为 0 的节点；若处理的节点数等于总节点数则无环
 *
 * 时间复杂度：O(V+E)
 * 空间复杂度：O(V+E)
 *
 * 示例：numCourses=2, prerequisites=[[1,0]] → true；[[1,0],[0,1]] → false
 */
public class LC207CanFinish {

    List<List<Integer>> edges;
    int[] visited;
    boolean valid = true;

    public boolean canFinishDFS(int numCourses, int[][] prerequisites) {

        edges = new ArrayList<List<Integer>>();

        //初始化
        for (int i = 0; i < numCourses; ++i) {
            edges.add(new ArrayList<Integer>());
        }

        visited = new int[numCourses];

        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
        }

        for (int i = 0; i < numCourses && valid; ++i) {
            if (visited[i] == 0) {
                dfs(i);
            }
        }

        return valid;
    }

    public void dfs(int u) {
        visited[u] = 1;
        for (int v : edges.get(u)) {
            if (visited[v] == 0) {
                dfs(v);
                if (!valid) {
                    return;
                }
            } else if (visited[v] == 1) {
                valid = false;
                return;
            }
        }

        visited[u] = 2;
    }

    public boolean canFinishBFS(int numCourses, int[][] prerequisites) {

        List<List<Integer>> edges = new ArrayList<List<Integer>>();

        int[] indeg;

        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        indeg = new int[numCourses];

        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
            ++indeg[info[0]];
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        int visited = 0;

        while (!queue.isEmpty()) {
            ++visited;
            int u = queue.poll();
            for (int v : edges.get(u)) {
                --indeg[v];
                if (indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        return visited == numCourses;
    }

}