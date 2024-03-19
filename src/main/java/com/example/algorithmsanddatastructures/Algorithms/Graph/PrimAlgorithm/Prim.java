package com.example.algorithmsanddatastructures.Algorithms.Graph.PrimAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {
    // inputs
    private final int numberOfNodes;
    private final List<List<Edge>> graph;

    // internal
    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private PriorityQueue<Edge> priorityQueue;

    // outputs
    private long minimumSpanningTreeCost;
    private Edge[] minimumSpanningTreeEdges;

    // constructor to initialize Prim's algorithm with a graph
    public Prim(List<List<Edge>> graph) {
        if (graph == null || graph.isEmpty()) throw new IllegalArgumentException("Invalid graph");
        this.numberOfNodes = graph.size();
        this.graph = graph;
    }

    // method to get the Minimum Spanning Tree (MST) of the input graph or null if no MST exists
    public Edge[] getMinimumSpanningTree() {
        computeMST();
        return mstExists ? minimumSpanningTreeEdges : null;
    }

    // method to get the Minimum Spanning Tree (MST) cost or null if no MST exists
    public Long getMinimumSpanningTreeCost() {
        computeMST();
        return mstExists ? minimumSpanningTreeCost : null;
    }

    // method to compute the Minimum Spanning Tree (MST) and its cost
    private void computeMST() {
        if (solved) return;
        solved = true;

        int edgeCount = 0;
        int m = numberOfNodes - 1;
        priorityQueue = new PriorityQueue<>();
        visited = new boolean[numberOfNodes];
        minimumSpanningTreeEdges = new Edge[m];

        // add initial set of edges to the priority queue starting at node 0
        addEdges(0);

        // loop until the MST is complete
        while (!priorityQueue.isEmpty() && edgeCount != m) {
            Edge edge = priorityQueue.poll();
            int nodeIndex = edge.to;

            // skip any edge pointing to an already visited node
            if (visited[nodeIndex]) continue;

            minimumSpanningTreeEdges[edgeCount++] = edge;
            minimumSpanningTreeCost += edge.cost;

            addEdges(nodeIndex);
        }

        // check if MST spans entire graph
        mstExists = (edgeCount == m);
    }

    // method to add edges to the priority queue
    private void addEdges(int nodeIndex) {
        visited[nodeIndex] = true;
        List<Edge> edges = graph.get(nodeIndex);
        for (Edge e : edges) {
            if (!visited[e.to]) {
                priorityQueue.offer(e);
            }
        }
    }

    /* Graph construction helpers. */

    // method to create an empty graph with n nodes
    static List<List<Edge>> createEmptyGraph(int n) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        return graph;
    }

    // method to add a directed edge to the graph
    static void addDirectedEdge(List<List<Edge>> graph, int from, int to, int cost) {
        graph.get(from).add(new Edge(from, to, cost));
    }

    // method to add an undirected edge to the graph
    static void addUndirectedEdge(List<List<Edge>> graph, int from, int to, int cost) {
        addDirectedEdge(graph, from, to, cost);
        addDirectedEdge(graph, to, from, cost);
    }
}

class Edge implements Comparable<Edge> {
    int from, to, cost;

    // constructor to initialize an edge with its endpoints and weight
    public Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    // compare edges based on their weights
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(cost, other.cost);
    }
}
