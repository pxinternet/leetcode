package round2; // 包声明：本类属于 round2 包

import java.util.ArrayList; // 引入 ArrayList，用于邻接表的列表实现
import java.util.Arrays; // 引入 Arrays，用于填充和操作数组（例如 fill）
import java.util.HashMap; // 引入 HashMap，用于图的邻接表映射
import java.util.LinkedList; // 引入 LinkedList，用作队列实现
import java.util.List; // 引入 List 接口
import java.util.Map; // 引入 Map 接口
import java.util.Queue; // 引入 Queue 接口

public class CheapestPrice { // 类定义：计算从 src 到 dst 在至多 K 次中转下的最便宜票价

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
