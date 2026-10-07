package graph;

import java.util.*;

/**
 * Dijkstra's Shortest Path Algorithm
 * Time: O((V+E) log V) with Priority Queue
 * Single source shortest path - non-negative weights
 */
public class Dijkstra {

    public static class Result {
       public double[] dist;
        public int[] parent;
        public int source;

        public Result(int vertices, int source) {
            dist = new double[vertices];
            parent = new int[vertices];
            this.source = source;
            Arrays.fill(dist, Double.POSITIVE_INFINITY);
            Arrays.fill(parent, -1);
        }
    }

    public static Result findShortestPath(Graph graph, int source) {
        Result result = new Result(graph.getVertices(), source);
        result.dist[source] = 0;

        // Min-heap: (distance, vertex)
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(a.dist, b.dist));
        pq.offer(new Node(source, 0));

        boolean[] visited = new boolean[graph.getVertices()];

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Graph.Edge e : graph.getAdjList(u)) {
                if (e.blocked) continue; // Skip blocked roads

                int v = e.dest;
                double alt = result.dist[u] + e.weight;

                if (alt < result.dist[v]) {
                    result.dist[v] = alt;
                    result.parent[v] = u;
                    pq.offer(new Node(v, alt));
                }
            }
        }

        return result;
    }

    // Reconstruct path from source to destination
    public static List<Integer> getPath(Result result, int dest) {
        List<Integer> path = new ArrayList<>();
        if (result.dist[dest] == Double.POSITIVE_INFINITY) {
            return path; // No path
        }
        int curr = dest;
        while (curr != -1) {
            path.add(0, curr);
            curr = result.parent[curr];
        }
        return path;
    }

    // Print path with location names
    public static void printPath(Graph graph, Result result, int dest) {
        List<Integer> path = getPath(result, dest);
        if (path.isEmpty()) {
            System.out.println("No path to " + graph.getVertexName(dest));
            return;
        }
        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(graph.getVertexName(path.get(i)));
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println(" | Distance: " + String.format("%.2f", result.dist[dest]) + " km");
    }

    private static class Node {
        int vertex;
        double dist;
        Node(int v, double d) { vertex = v; dist = d; }
    }
}