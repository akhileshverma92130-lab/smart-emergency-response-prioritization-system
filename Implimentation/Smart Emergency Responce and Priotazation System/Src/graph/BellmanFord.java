package graph;

import java.util.*;

/**
 * Bellman-Ford Algorithm
 * Time: O(V * E)
 * Handles negative weights, detects negative cycles
 * Used when road conditions might have "negative" weights (e.g., express lanes with time bonus)
 */
public class BellmanFord {

    public static class Result {
        double[] dist;
        int[] parent;
        boolean hasNegativeCycle;

        Result(int vertices) {
            dist = new double[vertices];
            parent = new int[vertices];
            Arrays.fill(dist, Double.POSITIVE_INFINITY);
            Arrays.fill(parent, -1);
            hasNegativeCycle = false;
        }
    }

    public static Result findShortestPath(Graph graph, int source) {
        Result result = new Result(graph.getVertices());
        result.dist[source] = 0;

        // Relax all edges V-1 times
        for (int i = 1; i < graph.getVertices(); i++) {
            for (Graph.Edge e : graph.getAllEdges()) {
                if (e.blocked) continue;
                if (result.dist[e.src] != Double.POSITIVE_INFINITY &&
                        result.dist[e.src] + e.weight < result.dist[e.dest]) {
                    result.dist[e.dest] = result.dist[e.src] + e.weight;
                    result.parent[e.dest] = e.src;
                }
            }
        }

        // Check for negative cycles
        for (Graph.Edge e : graph.getAllEdges()) {
            if (e.blocked) continue;
            if (result.dist[e.src] != Double.POSITIVE_INFINITY &&
                    result.dist[e.src] + e.weight < result.dist[e.dest]) {
                result.hasNegativeCycle = true;
                break;
            }
        }

        return result;
    }

    public static List<Integer> getPath(Result result, int dest) {
        List<Integer> path = new ArrayList<>();
        if (result.dist[dest] == Double.POSITIVE_INFINITY) return path;
        int curr = dest;
        while (curr != -1) {
            path.add(0, curr);
            curr = result.parent[curr];
        }
        return path;
    }
}