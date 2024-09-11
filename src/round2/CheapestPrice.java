package round2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CheapestPrice {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, List<int[]>> graph = new HashMap<>();

        for (int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];

            graph.computeIfAbsent(from, x -> new ArrayList<>()).add(new int[] { to, price });
        }

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[] { src, 0, 0 });

        int[] prices = new int[n];
        Arrays.fill(prices, Integer.MAX_VALUE);

        prices[src] = 0;
        while (!queue.isEmpty()) {

            int[] cur = queue.poll();
            int city = cur[0];
            int cost = cur[1];
            int stop = cur[2];

            if (stop > K)
                continue;

            if (graph.containsKey(city)) {
                for (int[] next : graph.get(city)) {
                    int nextCity = next[0];
                    int nextCost = next[1];

                    if (cost + nextCost < prices[nextCity]) {
                        prices[nextCity] = cost + nextCost;
                        queue.offer(new int[] { nextCity, prices[nextCity], stop + 1 });
                    }
                }
            }

        }
        return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];


    }

}
