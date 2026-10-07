package graph;

import java.util.*;

/**
 * Floyd-Warshall Algorithm
 * Time: O(V^3)
 * All-pairs shortest paths
 * Used for precomputing all hospital-to-hospital distances
 */
public class FloydWarshall {

    public static class Result {
        double[][] dist;
        int[][] next;  // For path reconstruction
        int vertices;

        Result(int vertices) {
            this.vertices = vertices;
            dist = new double[vertices][vertices];
            next = new int[vertices][vertices];
            for (int i = 0; i < vertices; i++) {
                Arrays.fill(dist[i], Double.POSITIVE_INFINITY);
                Arrays.fill(next[i], -1);
                dist[i][i] = 0;
            }
        }
    }

    public static Result computeAllPairs(Graph graph) {
        Result result = new Result(graph.getVertices());

        // Initialize with direct edges
        for (Graph.Edge e : graph.getAllEdges()) {
            if (e.blocked) continue;
            result.dist[e.src][e.dest] = e.weight;
            result.next[e.src][e.dest] = e.dest;
        }

        // Floyd-Warshall main loop
        for (int k = 0; k < graph.getVertices(); k++) {
            for (int i = 0; i < graph.getVertices(); i++) {
                for (int j = 0; j < graph.getVertices(); j++) {
                    if (result.dist[i][k] != Double.POSITIVE_INFINITY &&
                            result.dist[k][j] != Double.POSITIVE_INFINITY &&
                            result.dist[i][k] + result.dist[k][j] < result.dist[i][j]) {
                        result.dist[i][j] = result.dist[i][k] + result.dist[k][j];
                        result.next[i][j] = result.next[i][k];
                    }
                }
            }
        }

        return result;
    }

    public static List<Integer> getPath(Result result, int u, int v) {
        List<Integer> path = new ArrayList<>();
        if (result.next[u][v] == -1) return path;
        path.add(u);
        while (u != v) {
            u = result.next[u][v];
            path.add(u);
        }
        return path;
    }

    public static void printPath(Graph graph, Result result, int u, int v) {
        List<Integer> path = getPath(result, u, v);
        if (path.isEmpty()) {
            System.out.println("No path from " + graph.getVertexName(u) + " to " + graph.getVertexName(v));
            return;
        }
        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(graph.getVertexName(path.get(i)));
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println(" | Dist: " + String.format("%.2f", result.dist[u][v]) + " km");
    }
}