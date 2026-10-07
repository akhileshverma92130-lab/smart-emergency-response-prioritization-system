package backtrack;

import graph.Graph;
import graph.Dijkstra;
import java.util.*;

/**
 * Traveling Salesman Problem - Backtracking with Branch & Bound
 * Used for: Ambulance routing - visit multiple emergencies in one trip
 *           Return to base/hospital
 *
 * Time: O(n!) worst case, but branch & bound prunes significantly
 */
public class TSP {

    private Graph graph;
    private int[] bestPath;
    private double bestCost;
    private int[] currentPath;
    private boolean[] visited;
    private int startVertex;

    public TSP(Graph graph) {
        this.graph = graph;
    }

    public Result solve(int start, int[] destinations) {
        this.startVertex = start;
        int n = destinations.length;
        this.bestPath = new int[n + 1];
        this.currentPath = new int[n + 1];
        this.visited = new boolean[graph.getVertices()];
        this.bestCost = Double.POSITIVE_INFINITY;

        currentPath[0] = start;
        visited[start] = true;

        // We need to visit all destinations and return to start
        // Create a list: start -> dest1 -> dest2 -> ... -> destN -> start
        int[] verticesToVisit = new int[n + 2];
        verticesToVisit[0] = start;
        System.arraycopy(destinations, 0, verticesToVisit, 1, n);
        verticesToVisit[n + 1] = start;

        tspRecursive(1, verticesToVisit, 0, destinations.length + 1);

        Result result = new Result();
        result.path = bestPath.clone();
        result.cost = bestCost;
        return result;
    }

    private void tspRecursive(int level, int[] verticesToVisit, double currentCost, int totalStops) {
        if (level == totalStops) {
            // All destinations visited, return to start
            double returnCost = getDistance(currentPath[level - 1], startVertex);
            if (returnCost != Double.POSITIVE_INFINITY) {
                double totalCost = currentCost + returnCost;
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                    System.arraycopy(currentPath, 0, bestPath, 0, level);
                    bestPath[level] = startVertex;
                }
            }
            return;
        }

        // Try each unvisited destination
        for (int i = 1; i < verticesToVisit.length - 1; i++) {
            int v = verticesToVisit[i];
            if (!visited[v]) {
                double dist = getDistance(currentPath[level - 1], v);
                if (dist == Double.POSITIVE_INFINITY) continue;

                // Branch & Bound: prune if current cost already exceeds best
                if (currentCost + dist >= bestCost) continue;

                visited[v] = true;
                currentPath[level] = v;
                tspRecursive(level + 1, verticesToVisit, currentCost + dist, totalStops);
                visited[v] = false;
            }
        }
    }

    private double getDistance(int u, int v) {
        // Use Dijkstra for actual shortest path distance
        Dijkstra.Result dr = Dijkstra.findShortestPath(graph, u);
        return dr.dist[v];
    }

    public static class Result {
        int[] path;
        double cost;
    }

    public static void printResult(Graph graph, Result result) {
        if (result.path == null || result.path.length == 0) {
            System.out.println("No valid route found!");
            return;
        }
        System.out.println("=== TSP Route (Backtracking + Branch & Bound) ===");
        System.out.print("Route: ");
        for (int i = 0; i < result.path.length; i++) {
            System.out.print(graph.getVertexName(result.path[i]));
            if (i < result.path.length - 1) System.out.print(" -> ");
        }
        System.out.println("\nTotal Distance: " + String.format("%.2f", result.cost) + " km");
    }
}