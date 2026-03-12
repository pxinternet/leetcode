package round2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * CheapestPrice - K 站中转内最便宜航班票价（LeetCode 787）
 *
 * 题目（概要）：n 个城市、航班列表 [from,to,price]，求从 src 到 dst 在最多 K 次中转下的最便宜花费；无法到达返回 -1。
 *
 * 算法原理：
 * - BFS + 松弛：按"边数"分层扩展，同一层内若到达某城市花费更小则更新并入队。
 * - 限制 stop<=K 保证中转次数不超。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：建图（邻接表），prices 数组初始为无穷大，prices[src]=0。
 * - 步骤 2：BFS，队列元素 (city, costSoFar, stops)；若 stops>K 则跳过。
 * - 步骤 3：对每条边 (city, nextCity, price)，若 cost+price<prices[nextCity] 则更新并入队 (nextCity, cost+price, stops+1)。
 * - 步骤 4：返回 prices[dst]，无穷大则 -1。
 *
 * 关键洞察：与 Dijkstra 不同，需按边数限制扩展；BFS 每层对应"多一条边"，天然满足边数递增。
 *
 * 时间复杂度：O(V + E × K)
 * 空间复杂度：O(V)
 *
 * 示例：n=3, flights=[[0,1,100],[1,2,100],[0,2,500]], src=0, dst=2, K=1 → 200
 */
public class CheapestPrice {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) { // 方法：输入城市数量 n、航班列表 flights、出发 src、目的地 dst、最多中转次数 K，返回最低花费或 -1
        Map<Integer, List<int[]>> graph = new HashMap<>(); // 使用邻接表表示图：key=出发城市，value=List<int[]> 每个 int[] 表示 {to, price}

        for (int[] flight : flights) { // 遍历所有的航班数据，将其插入到邻接表中
            int from = flight[0]; // 取出航班的出发城市索引
            int to = flight[1]; // 取出航班的到达城市索引
            int price = flight[2]; // 取出航班的票价

            graph.computeIfAbsent(from, x -> new ArrayList<>()).add(new int[] { to, price }); // 将 (to, price) 加入到 from 对应的邻接列表中
        }

        Queue<int[]> queue = new LinkedList<>(); // 使用队列进行广度优先遍历（BFS），队列元素为 int[]：{city, costSoFar, stops}

        queue.offer(new int[] { src, 0, 0 }); // 将起点入队：当前城市 src，当前花费 0，已停留/中转次数 0

        int[] prices = new int[n]; // 创建数组记录到每个城市的当前最小已知花费
        Arrays.fill(prices, Integer.MAX_VALUE); // 初始化为无穷大（表示暂不可达）

        prices[src] = 0; // 到达起点的花费为 0
        while (!queue.isEmpty()) { // 当队列不为空时继续 BFS

            int[] cur = queue.poll(); // 出队当前节点信息
            int city = cur[0]; // 当前城市索引
            int cost = cur[1]; // 从起点到当前城市的累积花费
            int stop = cur[2]; // 到达当前城市所使用的边数（中转次数）

            if (stop > K) // 如果已使用的中转次数超过允许的 K，则跳过该路径（不再扩展）
                continue; // 直接跳到下一个队列元素

            if (graph.containsKey(city)) { // 如果当前城市有出发航班（即在邻接表中存在）
                for (int[] next : graph.get(city)) { // 遍历从当前城市出发的每一条边
                    int nextCity = next[0]; // 取出邻接城市索引
                    int nextCost = next[1]; // 取出该条边的票价

                    if (cost + nextCost < prices[nextCity]) { // 如果通过当前路径到达 nextCity 的花费比已知最小值更小
                        prices[nextCity] = cost + nextCost; // 更新到 nextCity 的最小花费
                        queue.offer(new int[] { nextCity, prices[nextCity], stop + 1 }); // 将 nextCity 入队，同时中转次数加 1
                    }
                }
            }

        }
        return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst]; // 若目的地仍不可达返回 -1，否则返回最小花费


    }

}
