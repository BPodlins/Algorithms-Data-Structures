package com.example.algorithmsanddatastructures.Algorithms.Graph.BFS;

import java.util.*;

public class BreadthFirstSearch {
    public static class Edge {
        int from, to, cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    private int n;
    private Integer[] prev;
    private List<List<Edge>> graph;

    public BreadthFirstSearch(List<List<Edge>> graph){
        if (graph == null){
            throw new IllegalArgumentException("Graph can not be null");
        }
        n = graph.size();
        this.graph = graph;
    }

    public List<Integer> reconstructPath(int start, int end) {
        bfs(start);
        List<Integer> path = new ArrayList<>();
        for (Integer at = end; at != null; at = prev[at]){
            path.add(at);
        }
        Collections.reverse(path);
        if (path.get(0) == start){
            return path;
        }
        path.clear(); //no path
        return path;
    }

    private void bfs(int start) {
        prev = new Integer[n];
        boolean[] visited = new boolean[n];
        Deque<Integer> queue = new ArrayDeque<>(n);

        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            List<Edge> edges = graph.get(node);

            for (Edge edge : edges) {
                if (!visited[edge.to]) {
                    visited[edge.to] = true;
                    prev[edge.to] = node;
                    queue.offer(edge.to);
                }
            }
        }
    }
}