package com.example.algorithmsanddatastructures.Algorithms.Graph.EulerianPathCycle;

import java.util.Iterator;
import java.util.LinkedList;

public class Euler {

    // number of vertices
    private int V;

    // array  of lists for Adjacency List Representation
    private LinkedList<Integer> adj[];

    Euler(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i) {
            adj[i] = new LinkedList();
        }
    }

    // function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);  // add w to v's list.
        adj[w].add(v); // the graph is undirected
    }

    // a function used by DFS
    void DFSUtil(int v,boolean visited[]) {
        // mark the current node as visited
        visited[v] = true;

        // recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n]){
                DFSUtil(n, visited);
            }
        }
    }

    // method to check if all non-zero degree vertices are connected. It mainly does DFS traversal starting from
    boolean isConnected() {
        // mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        int i;
        for (i = 0; i < V; i++){
            visited[i] = false;
        }

        // find a vertex with non-zero degree
        for (i = 0; i < V; i++) {
            if (!adj[i].isEmpty()) {
                break;
            }
        }

        // if there are no edges in the graph, return true
        if (i == V)
            return true;

        // start DFS traversal from a vertex with non-zero degree
        DFSUtil(i, visited);

        // check if all non-zero degree vertices are visited
        for (i = 0; i < V; i++) {
            if (!visited[i] && !adj[i].isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /* The function returns one of the following values
       0 --> If graph is not Eulerian
       1 --> If graph has an Euler path (Semi-Eulerian)
       2 --> If graph has an Euler Circuit (Eulerian)  */
    int isEulerian() {
        // Check if all non-zero degree vertices are connected
        if (isConnected() == false)
            return 0;

        // Count vertices with odd degree
        int odd = 0;
        for (int i = 0; i < V; i++)
            if (adj[i].size()%2!=0)
                odd++;

        // If count is more than 2, then graph is not Eulerian
        if (odd > 2)
            return 0;

        // If odd count is 2, then semi-eulerian.
        // If odd count is 0, then eulerian
        // Note that odd count can never be 1 for undirected graph
        return (odd==2)? 1 : 2;
    }

    // function to run test cases
    void test() {
        int res = isEulerian();
        if (res == 0)
            System.out.println("graph is not Eulerian");
        else if (res == 1)
            System.out.println("graph has a Euler path");
        else
            System.out.println("graph has a Euler cycle");
    }
}
