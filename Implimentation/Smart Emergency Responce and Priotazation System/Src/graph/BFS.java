package graph;

import java.util.*;

/**
 * Breadth First Search
 * Used for: Shortest path in unweighted graph, level-order traversal
 *           Finding nearest hospital in terms of hops
 */
public class BFS {

    public static class Result {
        int[] parent;
        int[] distance;
        boolean[] visited;

        Result(int vertices) {
            parent = new int[vertices];
            distance = new int[vertices];
            visited = new boolean[vertices];
            Arrays.fill(parent, -1);
            Arrays.fill(distance, -1);
        }
    }

    public static Result bfs(Graph graph, int start) {
        Result result = new Result(graph.getVertices());
        Queue<Integer> queue = new LinkedList<>();

        result.visited[start] = true;
        result.distance[start] = 0;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Graph.Edge e : graph.getAdjList(u)) {
                if (e.blocked) continue;
                int v = e.dest;
                if (!result.visited[v]) {
                    result.visited[v] = true;
                    result.distance[v] = result.distance[u] + 1;
                    result.parent[v] = u;
                    queue.offer(v);
                }
            }
        }
        return result;
    }

    public static List<Integer> getPath(Result result, int dest) {
        List<Integer> path = new ArrayList<>();
        if (result.distance[dest] == -1) return path;
        int curr = dest;
        while (curr != -1) {
            path.add(0, curr);
            curr = result.parent[curr];
        }
        return path;
    }

    // Find nearest hospital from emergency location
    public static int findNearestHospital(Graph graph, int start, Set<Integer> hospitalVertices) {
        Result result = bfs(graph, start);
        int nearest = -1;
        int minDist = Integer.MAX_VALUE;
        for (int h : hospitalVertices) {
            if (result.distance[h] != -1 && result.distance[h] < minDist) {
                minDist = result.distance[h];
                nearest = h;
            }
        }
        return nearest;
    }
}