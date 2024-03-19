# ***Eulerian Paths and Circuits***

An Eulerian Path is a route in a graph that traverses every edge exactly once, while an Eulerian Circuit is a specific Eulerian Path that starts and ends on the same vertex.
In an Eulerian Path, each time we visit a vertex, we traverse through two unvisited edges with one endpoint being that vertex. Thus, all intermediate vertices in an Eulerian Path must possess even degrees. In contrast, for an Eulerian Cycle, any vertex can act as the middle vertex, necessitating all vertices to have even degrees.

## Definitions

 - Eulerian Cycle: An undirected graph possesses an Eulerian cycle if two conditions are met:
   1. All vertices with non-zero degree are interconnected.
   2. All vertices have even degrees.
 
    
 - Eulerian Path: An undirected graph has an Eulerian Path if:
    1. The same conditions as for the Eulerian Cycle apply.
    2. Either zero or two vertices have an odd degree, with all other vertices having an even degree. It's noteworthy that in an undirected graph, having only one vertex with an odd degree is not possible (since the sum of all degrees in an undirected graph is always even).

## Determining Eulerian Graphs:

To ascertain whether a given graph is Eulerian or not, we can rely on the following approach, which operates in polynomial time complexity O(V+E):

1. Check for Eulerian Cycle:
   - Verify if all vertices with non-zero degree are connected.
   - Ensure all vertices have even degrees.

2. Check for Eulerian Path:
   - Follow the conditions mentioned for the Eulerian Cycle.
   - Additionally, confirm that either zero or two vertices have odd degrees, while all others have even degrees.