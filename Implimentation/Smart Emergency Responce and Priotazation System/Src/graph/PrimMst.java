package graph;

import java.util.*;

/**
 * Prim's Minimum Spanning Tree
 * Time: O(E log V)
 * Used for: Building minimum cost communication network between hospitals
 *           during disaster when normal comms are down
 */
public class PrimMst {

    public static class Result {
        int[] parent;
        double[] key;
        boolean[] inMST;
        double totalWeight;

        Result(int vertices) {
            parent = new int[vertices];
            key = new double[vertices];
            inMST = new boolean[vertices];
            Arrays.fill(key, Double.POSITIVE_INFINITY);
            Arrays.fill(parent, -1);
            totalWeight = 0;
        }
    }

    public static Result findMST(Graph graph, int start) {
        Result result = new Result(graph.getVertices());
        result.key[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(a.key, b.key));
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node uNode = pq.poll();
            int u = uNode.vertex;

            if (result.inMST[u]) continue;
            result.inMST[u] = true;
            result.totalWeight += uNode.key;

            for (Graph.Edge e : graph.getAdjList(u)) {
                int v = e.dest;
                if (e.blocked) continue;
                if (!result.inMST[v] && e.weight < result.key[v]) {
                    result.key[v] = e.weight;
                    result.parent[v] = u;
                    pq.offer(new Node(v, e.weight));
                }
            }
        }

        return result;
    }

    public static void printMST(Graph graph, Result result) {
        System.out.println("=== Minimum Spanning Tree (Prim's) ===");
        System.out.println("Edge \t\t Weight");
        for (int i = 1; i < graph.getVertices(); i++) {
            if (result.parent[i] != -1) {
                System.out.println(graph.getVertexName(result.parent[i]) + " - " +
                        graph.getVertexName(i) + " \t " + String.format("%.2f", result.key[i]) + " km");
            }
        }
        System.out.println("Total Weight: " + String.format("%.2f", result.totalWeight) + " km");
    }

    private static class Node {
        int vertex;
        double key;
        Node(int v, double k) { vertex = v; key = k; }
    }
}