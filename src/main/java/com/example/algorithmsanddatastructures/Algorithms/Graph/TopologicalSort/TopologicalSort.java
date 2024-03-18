package com.example.algorithmsanddatastructures.Algorithms.Graph.TopologicalSort;

import java.util.*;

public class TopologicalSort {

    // perform depth-first search to obtain topological ordering of the graph.
    private static int dfs(int i, int at, boolean[] visited, int[] ordering, Map<Integer, List<Edge>> graph) {
        visited[at] = true;
        List<Edge> edges = graph.getOrDefault(at, Collections.emptyList());

        // explore all outgoing edges from the current node
        for (Edge edge : edges) {
            if (!visited[edge.to]) {
                // recursively perform DFS on unvisited neighboring nodes
                i = dfs(i, edge.to, visited, ordering, graph);
            }
        }

        // place the current node into the ordering array
        ordering[i] = at;
        return i - 1;
    }

    // find topological ordering of nodes in a directed acyclic graph.
    public static int[] topologicalSort(Map<Integer, List<Edge>> graph, int numNodes) {
        int[] ordering = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        int i = numNodes - 1;

        // start DFS traversal from each unvisited node
        for (int at = 0; at < numNodes; at++) {
            if (!visited[at]) {
                i = dfs(i, at, visited, ordering, graph);
            }
        }
        return ordering;
    }

    // find the shortest path to all nodes starting from a given node in a DAG.
    public static Integer[] dagShortestPath(Map<Integer, List<Edge>> graph, int start, int numNodes) {
        int[] topsort = topologicalSort(graph, numNodes);
        Integer[] dist = new Integer[numNodes];
        dist[start] = 0;

        // traverse nodes in topological order
        for (int i = 0; i < numNodes; i++) {
            int nodeIndex = topsort[i];
            if (dist[nodeIndex] != null) {
                List<Edge> adjacentEdges = graph.getOrDefault(nodeIndex, Collections.emptyList());

                // update distances to reachable nodes via outgoing edges
                for (Edge edge : adjacentEdges) {
                    int newDist = dist[nodeIndex] + edge.weight;
                    if (dist[edge.to] == null) {
                        dist[edge.to] = newDist;
                    } else {
                        dist[edge.to] = Math.min(dist[edge.to], newDist);
                    }
                }
            }
        }
        return dist;
    }
}

class Edge {
    int from, to, weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
