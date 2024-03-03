package com.example.algorithmsanddatastructures.Algorithms.Graph.DFS;

import java.util.List;
import java.util.Map;

public class DepthFirstAlgorithm {
    static class Edge {
        int from, to, cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static long dfs(int at, boolean[] visited, Map<Integer, List<Edge>> graph) {

        //base case (node visited)
        if (visited[at]){
            return 0L;
        }

        visited[at] = true;
        long count = 1;

        List<Edge> edges = graph.get(at);
        if (edges != null) {
            for (Edge edge : edges) {
                count += dfs(edge.to, visited, graph);
            }
        }

        return count;
    }

}
