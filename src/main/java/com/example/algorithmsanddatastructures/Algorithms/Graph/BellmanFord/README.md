# ***Bellman-Ford Algorithm***

The Bellman-Ford algorithm is a widely used algorithm for finding the shortest paths from a single source vertex to all other vertices in a weighted directed graph, including graphs with negative edge weights. 
The algorithm operates by iteratively relaxing the edges of the graph, adjusting the estimated distance from the source vertex to each destination vertex. Through multiple iterations, Bellman-Ford gradually refines these distance estimates until convergence is reached, ensuring that the shortest path from the source to every other vertex is correctly calculated.

## Walkthrough

1. Initialization:
    - Initialize an array to store the shortest distance from the source vertex to every other vertex in the graph.
    - Set the distance of the source vertex to 0 and all other vertices to infinity.
2. Relaxation:
    - Repeat relaxation for numNodes - 1 times, where numNodes is the number of vertices in the graph. This ensures that the shortest paths are correctly computed.
    - Relaxation involves checking each edge (u, v) in the graph and updating the distance dist[v] if a shorter path is found from the source vertex to v through u.
    - The relaxation condition is: dist[v] > dist[u] + weight(u, v), where weight(u, v) is the weight of the edge from vertex u to vertex v.
3. Negative Cycle Detection:
    - After performing relaxation numNodes - 1 times, check for the presence of negative cycles in the graph.
    - To detect negative cycles, iterate through all edges in the graph one more time. If a shorter path is found from the source vertex to any vertex v through u after the numNodes - 1 iterations, then a negative cycle exists in the graph.
    - If a negative cycle is detected, it means that the graph has no shortest paths, as the distance to some vertices becomes infinitely negative due to the cycle.
4. Output:
   - If no negative cycle is detected, the array containing the shortest distances from the source vertex to every other vertex in the graph is returned.
   In summary, the Bellman-Ford algorithm starts by initializing distances and then iteratively relaxes edges to find the shortest paths. It handles negative edge weights and can detect negative cycles, making it a powerful tool for finding shortest paths in graphs.