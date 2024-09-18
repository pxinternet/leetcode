package dfs;

import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.PriorityQueue;

import java.util.PriorityQueue;

public class LC332findItinerary {

    public List<String> findItinerary(List<List<String>> tickets) {

        Map<String, PriorityQueue<String>> graph = new HashMap<>();

        for (List<String> ticket : tickets) {
            graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
        }

        List<String> result = new LinkedList<>();

        dfs("JFK", graph, result);

        return result;
    }

    private void dfs(String airport, Map<String, PriorityQueue<String>> graph, List<String> result) {

        PriorityQueue<String> destinations = graph.get(airport);

        while (destinations != null && !destinations.isEmpty()) {
            dfs(destinations.poll(), graph, result);
        }

        result.add(0, airport);
    }

}
