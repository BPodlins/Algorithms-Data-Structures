import java.util.ArrayList;
import java.util.List;

public class BellmanFord {


    // finds the shortest path from a starting node to all other nodes in the graph.
    // detects negative cycles in the graph and sets the minimum cost for nodes within them to Double.NEGATIVE_INFINITY.
    // V - number of adjacency nodes

    public static double[] bellmanFord(List<Edge>[] graph, int V, int start) {
        // initialize distances to all nodes to infinity except for the start node which is set to zero
        double[] dist = new double[V];
        java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;

        // relax edges repeatedly to find the shortest paths
        for (int i = 0; i < V - 1; i++) {
            for (int u = 0; u < V; u++) {
                if (dist[u] != Double.POSITIVE_INFINITY) {
                    for (Edge edge : graph[u]) {
                        if (dist[u] + edge.cost < dist[edge.to]) {
                            dist[edge.to] = dist[u] + edge.cost;
                        }
                    }
                }
            }
        }

        // run algorithm a second time to detect negative cycles
        for (int i = 0; i < V - 1; i++) {
            for (int u = 0; u < V; u++) {
                if (dist[u] != Double.POSITIVE_INFINITY) {
                    for (Edge edge : graph[u]) {
                        if (dist[u] + edge.cost < dist[edge.to]) {
                            dist[edge.to] = Double.NEGATIVE_INFINITY;
                        }
                    }
                }
            }
        }

        //return -> array containing the shortest distances from the start node to every other node
        return dist;
    }

    // create a graph with V vertices
    public static List<Edge>[] createGraph(final int V) {
        List<Edge>[] graph = new List[V];
        for (int i = 0; i < V; i++) {
            graph[i] = new ArrayList<>();
        }
        return graph;
    }

    // function to add edges to the graph
    public static void addEdge(List<Edge>[] graph, int from, int to, double cost) {
        graph[from].add(new Edge(from, to, cost));
    }
}

class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
