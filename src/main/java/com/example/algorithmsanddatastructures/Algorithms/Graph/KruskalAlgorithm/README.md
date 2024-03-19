# Kruskal's Algorithm

Kruskal's Algorithm is a greedy algorithm that finds a minimum spanning tree (MST) for a connected weighted graph. It finds an MST by repeatedly adding the shortest edge that does not form a cycle in the graph until all vertices are included.

## Description

The algorithm works as follows:

1. Sort all the edges in non-decreasing order of their weight.
2. Initialize an empty graph as the MST.
3. Iterate through the sorted edges and add each edge to the MST if it does not form a cycle with the edges already in the MST.

Kruskal's Algorithm is efficient for sparse graphs as it has a time complexity of O(E log E), where E is the number of edges.