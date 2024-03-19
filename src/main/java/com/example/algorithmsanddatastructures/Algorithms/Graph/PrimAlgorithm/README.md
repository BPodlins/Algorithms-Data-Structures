# Prim's Algorithm

Prim's Algorithm is a greedy algorithm used to find the Minimum Spanning Tree (MST) of a connected, undirected graph. The MST is a subset of the edges of the graph that forms a tree and includes all the vertices, with the minimum possible total edge weight.

## Overview

Prim's Algorithm operates by starting with an arbitrary vertex and incrementally growing the MST by adding the cheapest edge that connects a vertex in the MST to a vertex outside the MST. This process continues until all vertices are included in the MST.

## Implementation Details

### Inputs

- The graph is represented as a collection of vertices and edges.
- Each edge is associated with a weight or cost.

### Outputs

- The Minimum Spanning Tree (MST), represented as a collection of edges.
- The total cost of the MST.

### Algorithm Steps

1. Start with an arbitrary vertex as the initial MST.
2. Repeat the following steps until all vertices are included in the MST:
    - Select the edge with the minimum weight that connects a vertex in the MST to a vertex outside the MST.
    - Add the selected edge to the MST.
    - Mark the connected vertex as part of the MST.
3. Once all vertices are included in the MST, the algorithm terminates.

### Time Complexity

The time complexity of Prim's Algorithm depends on the data structures used for graph representation and edge selection. With an adjacency list representation and a priority queue for selecting edges, Prim's Algorithm typically runs in O(V log V + E log V) time, where V is the number of vertices and E is the number of edges.
