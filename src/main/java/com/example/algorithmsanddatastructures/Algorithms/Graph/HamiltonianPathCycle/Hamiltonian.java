package com.example.algorithmsanddatastructures.Algorithms.Graph.HamiltonianPathCycle;

import java.util.Arrays;

public class Hamiltonian {

    // total number of vertices in the graph
    final int V = 5;
    int path[];

    // check if adding vertex v to the Hamiltonian Cycle at index 'pos' is safe
    boolean isSafe(int v, int graph[][], int path[], int pos) {
        // check if v is adjacent to the previously added vertex
        if (graph[path[pos - 1]][v] == 0)
            return false;

        // check if v has already been included in the path
        for (int i = 0; i < pos; i++) {
            if (path[i] == v)
                return false;
        }
        return true;
    }

    // recursive utility function to solve the Hamiltonian Cycle problem
    boolean hamCycleUtil(int graph[][], int path[], int pos) {
        // base case: if all vertices are included in the Hamiltonian Cycle
        if (pos == V) {
            // check if there is an edge from the last included vertex to the first vertex
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }

        // try different vertices as the next candidate in the Hamiltonian Cycle
        for (int v = 1; v < V; v++) {
            // check if vertex v can be added to the Hamiltonian Cycle
            if (isSafe(v, graph, path, pos)) {
                path[pos] = v;

                // recur to construct the rest of the path
                if (hamCycleUtil(graph, path, pos + 1))
                    return true;

                // if adding vertex v doesn't lead to a solution, remove it
                path[pos] = -1;
            }
        }
        // if no vertex can be added to the Hamiltonian Cycle constructed so far, return false
        return false;
    }

    // solve the Hamiltonian Cycle problem using backtracking
    int hamCycle(int graph[][]) {
        path = new int[V];
        for (int i = 0; i < V; i++) {
            path[i] = -1;
        }

        // start the path from vertex 0
        path[0] = 0;
        if (!hamCycleUtil(graph, path, 1)) {
            System.out.println("\nSolution does not exist");
            return 0;
        }

        printSolution(path);
        return 1;
    }

    // utility function to print the Hamiltonian Cycle
    void printSolution(int path[]) {
        System.out.println("Solution Exists: Following is one Hamiltonian Cycle");
        for (int i = 0; i < V; i++) {
            System.out.print(" " + path[i] + " ");
        }
        // print the first vertex again to complete the cycle
        System.out.println(" " + path[0] + " ");
    }

    // function to calculate the number of cycles
    static int cycles(int N) {
        int fact = 1, result = 0;

        result = N - 1;

        // calculate factorial
        int i = result;
        while (i > 0) {
            fact = fact * i;
            i--;
        }

        return fact / 2;
    }

    // check if a Hamiltonian Path exists in the graph
    static boolean hamiltonianPath(int adj[][], int N) {
        boolean dp[][] = new boolean[N][(1 << N)];

        // set all dp[i][(1 << i)] to true
        for (int i = 0; i < N; i++) {
            dp[i][(1 << i)] = true;
        }

        // iterate over each subset of nodes
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                // if the jth node is included in the current subset
                if ((i & (1 << j)) != 0) {
                    // find a neighbor of j also present in the current subset
                    for (int k = 0; k < N; k++) {
                        if ((i & (1 << k)) != 0 && adj[k][j] == 1 && j != k && dp[k][i ^ (1 << j)]) {
                            // update dp[j][i] to true
                            dp[j][i] = true;
                            break;
                        }
                    }
                }
            }
        }

        // traverse the vertices
        for (int i = 0; i < N; i++) {
            // if a Hamiltonian Path exists
            if (dp[i][(1 << N) - 1]) {
                return true;
            }
        }
        // otherwise, return false
        return false;
    }

    // method to run test cases
    void test(int graph[][]) {
        int res = hamCycle(graph);
        if (res == 0)
            System.out.println("The graph does not have a Hamiltonian path or cycle");
        else
            System.out.println("The graph has a Hamiltonian cycle");
    }
}
