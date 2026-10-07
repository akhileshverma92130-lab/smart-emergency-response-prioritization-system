package graph;

import java.util.*;

/**
 * Kruskal's Minimum Spanning Tree
 * Time: O(E log E) - sorting edges
 * Uses Union-Find (Disjoint Set Union)
 * Alternative MST - good for sparse graphs
 */
public class KruskalMST {

    // Union-Find Data Structure
    private static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            if (rank[px] < rank[py]) parent[px] = py;
            else if (rank[px] > rank[py]) parent[py] = px;
            else { parent[py] = px; rank[px]++; }
            return true;
        }
    }

    public static class Result {
        List<Graph.Edge> mstEdges;
        double totalWeight;

        Result() {
            mstEdges = new ArrayList<>();
            totalWeight = 0;
        }
    }

    public static Result findMST(Graph graph) {
        Result result = new Result();
        List<Graph.Edge> edges = new ArrayList<>(graph.getAllEdges());

        // Sort by weight
        edges.sort((a, b) -> Double.compare(a.weight, b.weight));

        UnionFind uf = new UnionFind(graph.getVertices());

        for (Graph.Edge e : edges) {
            if (e.blocked) continue;
            if (uf.union(e.src, e.dest)) {
                result.mstEdges.add(e);
                result.totalWeight += e.weight;
                if (result.mstEdges.size() == graph.getVertices() - 1) break;
            }
        }

        return result;
    }

    public static void printMST(Graph graph, Result result) {
        System.out.println("=== Minimum Spanning Tree (Kruskal's) ===");
        System.out.println("Edge \t\t Weight");
        for (Graph.Edge e : result.mstEdges) {
            System.out.println(graph.getVertexName(e.src) + " - " +
                    graph.getVertexName(e.dest) + " \t " + String.format("%.2f", e.weight) + " km");
        }
        System.out.println("Total Weight: " + String.format("%.2f", result.totalWeight) + " km");
    }
}