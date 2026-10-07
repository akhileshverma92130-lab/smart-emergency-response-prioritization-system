package graph;

import java.util.*;

/**
 * Depth First Search
 * Used for: Finding connected components, alternate routes when roads blocked
 */
public class DFS {

    public static void dfs(Graph graph, int start, boolean[] visited) {
        visited[start] = true;
        System.out.print(graph.getVertexName(start) + " ");
        for (Graph.Edge e : graph.getAdjList(start)) {
            if (!e.blocked && !visited[e.dest]) {
                dfs(graph, e.dest, visited);
            }
        }
    }

    // Find all reachable nodes from start (connected component)
    public static List<Integer> getConnectedComponent(Graph graph, int start) {
        boolean[] visited = new boolean[graph.getVertices()];
        List<Integer> component = new ArrayList<>();
        dfsCollect(graph, start, visited, component);
        return component;
    }

    private static void dfsCollect(Graph graph, int u, boolean[] visited, List<Integer> comp) {
        visited[u] = true;
        comp.add(u);
        for (Graph.Edge e : graph.getAdjList(u)) {
            if (!e.blocked && !visited[e.dest]) {
                dfsCollect(graph, e.dest, visited, comp);
            }
        }
    }

    // Find alternate path avoiding blocked roads (simple DFS path finding)
    public static List<Integer> findAlternatePath(Graph graph, int start, int dest, Set<Integer> avoid) {
        boolean[] visited = new boolean[graph.getVertices()];
        List<Integer> path = new ArrayList<>();
        if (dfsPath(graph, start, dest, visited, path, avoid)) {
            return path;
        }
        return new ArrayList<>(); // Empty = no path
    }

    private static boolean dfsPath(Graph graph, int u, int dest, boolean[] visited,
                                   List<Integer> path, Set<Integer> avoid) {
        if (avoid.contains(u)) return false;
        visited[u] = true;
        path.add(u);
        if (u == dest) return true;

        for (Graph.Edge e : graph.getAdjList(u)) {
            if (!e.blocked && !visited[e.dest]) {
                if (dfsPath(graph, e.dest, dest, visited, path, avoid)) return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }
}