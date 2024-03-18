package com.example.algorithmsanddatastructures.Algorithms.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
public class Dijkstra {
    // epsilon for comparing double values
    // short explanation to why - https://www.linkedin.com/pulse/using-epsilon-floating-point-values-lana-reeve
    private static final double EPS = 1e-6;
    private final int n;
    private double[] dist;
    private Integer[] prev;
    private List<List<Edge>> graph;

    private Comparator<Node> comparator = (node1, node2) -> {
        if (Math.abs(node1.value - node2.value) < EPS){
            return 0;
        }
        return (node1.value - node2.value) > 0 ? +1 : -1;
    };

    //initialize the solver by providing the graph size and a starting node, use addEdge method to actually add the nodes
    public Dijkstra(int n) {
        this.n = n;
        createEmptyGraph();
    }

    public Dijkstra(int n, Comparator<Node> comparator) {
        this(n);
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        this.comparator = comparator;
    }


     // adds a directed edge to the graph.
    public void addEdge(int from, int to, int cost) {
        graph.get(from).add(new Edge(from, to, cost));
    }

    // method to retrieve the graph
    public List<List<Edge>> getGraph() {
        return graph;
    }

    /**
     * Reconstructs the shortest path (of nodes) from 'start' to 'end' inclusive.
     *
     * @return An array of nodes indexes of the shortest path from 'start' to 'end'. If 'start' and
     *     'end' are not connected then an empty array is returned.
     */

    // reconstructs the shortest path from starting node to end node (inclusively)
    public List<Integer> reconstructPath(int start, int end) {
        // checking for edge cases
        if (end < 0 || end >= n){
            throw new IllegalArgumentException("Invalid node index");
        }
        if (start < 0 || start >= n){
            throw new IllegalArgumentException("Invalid node index");
        }
        double dist = dijkstra(start, end);
        List<Integer> path = new ArrayList<>();
        if (dist == Double.POSITIVE_INFINITY){
            return path;
        }
        for (Integer at = end; at != null; at = prev[at]){
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    // proper dijkstra algorithm to run on a directed graph
    public double dijkstra(int start, int end) {
        // an array of the minimum distance to each node
        dist = new double[n];
        // we fill it with maximum double value, so when we compare and find a smaller one, it is replaced
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;

        // a priority queue of the next most promising node to visit.
        PriorityQueue<Node> pq = new PriorityQueue<>(2 * n, comparator);
        pq.offer(new Node(start, 0));

        // array used to track which nodes have already been visited.
        boolean[] visited = new boolean[n];
        prev = new Integer[n];

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            visited[node.id] = true;

            // we already found a better path before we got to processing this node, so we can ignore it.
            if (dist[node.id] < node.value) continue;

            List<Edge> edges = graph.get(node.id);
            for (Edge edge : edges) {
                // you cannot get a shorter path by revisiting a node you have already visited before.
                if (visited[edge.to]) continue;

                // relax edge by updating minimum cost if applicable.
                double newDist = dist[edge.from] + edge.cost;
                if (newDist < dist[edge.to]) {
                    prev[edge.to] = edge.from;
                    dist[edge.to] = newDist;
                    pq.offer(new Node(edge.to, dist[edge.to]));
                }
            }
            // once we've visited all the nodes spanning from the end
            // node we know we can return the minimum distance value to
            // the end node because it cannot get any better after this point.
            if (node.id == end) return dist[end];
        }
        // end node is unreachable
        return Double.POSITIVE_INFINITY;
    }

    // construct an empty graph with n nodes including the source and sink nodes.
    private void createEmptyGraph() {
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
    }
}

class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}

class Node {
    int id;
    double value;

    public Node(int id, double value) {
        this.id = id;
        this.value = value;
    }
}
