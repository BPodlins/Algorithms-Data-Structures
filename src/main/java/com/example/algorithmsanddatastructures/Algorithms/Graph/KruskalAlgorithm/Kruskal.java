package com.example.algorithmsanddatastructures.Algorithms.Graph.KruskalAlgorithm;

import java.util.List;
import java.util.PriorityQueue;

public class Kruskal {
    // inputs
    private int numberOfNodes;
    private List<Edge> edges;

    // internal
    private boolean solved;
    private boolean mstExists;

    // outputs
    private Edge[] minimumSpanningTree;
    private long minimumSpanningTreeCost;

    // constructor to initialize Kruskal's algorithm with a list of edges and the number of nodes
    public Kruskal(List<Edge> edges, int numberOfNodes) {
        if (edges == null || numberOfNodes <= 1) throw new IllegalArgumentException("Invalid input parameters");
        this.edges = edges;
        this.numberOfNodes = numberOfNodes;
    }

    // method to get the Minimum Spanning Tree (MST) of the input graph or null if no MST exists
    public Edge[] getMinimumSpanningTree() {
        runKruskal();
        return mstExists ? minimumSpanningTree : null;
    }

    // method to get the Minimum Spanning Tree (MST) cost or null if no MST exists
    public Long getMinimumSpanningTreeCost() {
        runKruskal();
        return mstExists ? minimumSpanningTreeCost : null;
    }

    // method to run Kruskal's algorithm
    private void runKruskal() {
        if (solved) return;

        // create a priority queue to store edges sorted by their weights
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(edges);

        // initialize a Union-Find data structure to keep track of connected components
        UnionFind unionFind = new UnionFind(numberOfNodes);

        int index = 0;
        minimumSpanningTree = new Edge[numberOfNodes - 1];

        // process edges in increasing order of weights until we find the minimum spanning tree
        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();

            // skip this edge if it forms a cycle in the MST
            if (unionFind.connected(edge.u, edge.v)) continue;

            // include this edge in the MST
            unionFind.union(edge.u, edge.v);
            minimumSpanningTreeCost += edge.cost;
            minimumSpanningTree[index++] = edge;

            // stop early if we found a MST that includes all nodes
            if (unionFind.size(0) == numberOfNodes) break;
        }

        // check if a minimum spanning tree exists
        mstExists = (unionFind.size(0) == numberOfNodes);
        solved = true;
    }
}

class Edge implements Comparable<Edge> {
    int u, v, cost;

    // constructor to initialize an edge with its endpoints and weight
    public Edge(int u, int v, int cost) {
        this.u = u;
        this.v = v;
        this.cost = cost;
    }

    // compare edges based on their weights
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(cost, other.cost);
    }
}

// Union-Find data structure to perform efficient union and find operations
class UnionFind {
    private int[] parent;
    private int[] size;

    // constructor to initialize Union-Find data structure with 'n' nodes
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // method to find the root of the set containing node 'p' with path compression
    public int find(int p) {
        int root = p;
        while (root != parent[root]) root = parent[root];
        // path compression
        while (p != root) {
            int next = parent[p];
            parent[p] = root;
            p = next;
        }
        return root;
    }

    // method to check if nodes 'p' and 'q' are connected in the same set
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // method to return the size of the set containing node 'p'
    public int size(int p) {
        return size[find(p)];
    }

    // method to merge the sets containing nodes 'p' and 'q'
    public void union(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);
        if (root1 == root2) return;
        // union by size
        if (size[root1] < size[root2]) {
            size[root2] += size[root1];
            parent[root1] = root2;
        } else {
            size[root1] += size[root2];
            parent[root2] = root1;
        }
    }
}
