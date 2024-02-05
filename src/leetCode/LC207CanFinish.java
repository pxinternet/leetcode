package leetCode;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LC207CanFinish {

    /**
     * //这是一个拓扑排序问题
     * //对于给定一个包含n个节点的有向图G,
     * 我们给出它的节点编号的一种排列，如果满足，对于G中的任意一条有向边[u,v],u的排列都出现在v的前面
     * 那么这个排列就是G的一个拓扑排序
     * 如果G存在环，那么G不存在拓扑排序
     * 如果G是有向无环图，那么拓扑排序不止一种
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */

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