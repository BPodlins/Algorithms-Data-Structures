# ***Dijkstra's Algorithm***

Dijkstra's Algorithm is like Google Maps for graphs. It helps you find the shortest route from one place to another in a network, whether it's roads or computer connections.
To use this algorithm the graph needs to be non-negative weighted.

## **The algorithm steps**
Imagine you're standing in the middle of a maze, and you need to find the quickest way out. Dijkstra's Algorithm is like your trusty GPS, guiding you through the maze step by step.
 - Start from a Point: You pick a starting point, maybe where you're standing in the maze.
 - Keep Track of Distances: Dijkstra keeps track of the distance from your starting point to every other point in the maze. At first, it marks the starting point as 0 and all others as infinity – because you haven't explored them yet.
 - Explore and Update: Now, you start exploring. You look at all the paths branching out from where you are. Dijkstra picks the shortest one and updates the distances to nearby points if it finds a quicker route.
 - Move Forward: You move to the next point with the shortest distance. Dijkstra repeats the process from there, looking at all the paths, picking the shortest ones, and updating distances.
 - Repeat Until Done: Keep moving and updating until you've checked every point in the maze or until you reach your destination.