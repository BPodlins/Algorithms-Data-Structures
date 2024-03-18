# ***Topological Sort***

Topological sorting is a fundamental algorithm used to order the vertices of a directed graph such that for every directed edge 𝑢 → 𝑣,
vertex 𝑢 comes before vertex 𝑣 in the ordering. This concept finds extensive applications in various fields, including task scheduling,
dependency resolution, and compilation.

The algorithm for topological sorting typically involves performing a depth-first search (DFS) on the graph. During the DFS traversal, vertices are visited and marked as 'visited' once all their adjacent vertices have been explored.
The vertices are then added to the ordering array in reverse order of their finishing times, effectively producing a topological ordering.