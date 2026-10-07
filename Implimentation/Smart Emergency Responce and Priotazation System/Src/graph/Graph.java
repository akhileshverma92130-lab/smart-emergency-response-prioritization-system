package graph;

import Model.Location;
import java.util.*;

/**
 * Graph representation for City Road Network
 * Adjacency List implementation
 * Vertices = Intersections/Landmarks
 * Edges = Roads with distance/time weights
 */
public class Graph {
    private int vertices;
    private Map<Integer, String> vertexNames;  // ID -> Location name
    private Map<Integer, Location> vertexLocations;
    private List<Edge>[] adjList;
    private List<Edge> allEdges;  // For Kruskal

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList[vertices];
        this.vertexNames = new HashMap<>();
        this.vertexLocations = new HashMap<>();
        this.allEdges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public static class Edge {
        int src, dest;
        double weight;      // Distance in km
        double time;        // Time in minutes
        boolean blocked;    // Road blockage

        public Edge(int src, int dest, double weight, double time) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
            this.time = time;
            this.blocked = false;
        }
    }

    public void addVertex(int id, String name, Location loc) {
        vertexNames.put(id, name);
        vertexLocations.put(id, loc);
    }

    public void addEdge(int src, int dest, double weight, double time) {
        Edge e1 = new Edge(src, dest, weight, time);
        Edge e2 = new Edge(dest, src, weight, time); // Undirected
        adjList[src].add(e1);
        adjList[dest].add(e2);
        allEdges.add(e1); // Only add once for Kruskal
    }

    public void blockRoad(int src, int dest) {
        for (Edge e : adjList[src]) {
            if (e.dest == dest) e.blocked = true;
        }
        for (Edge e : adjList[dest]) {
            if (e.dest == src) e.blocked = true;
        }
    }

    public void unblockRoad(int src, int dest) {
        for (Edge e : adjList[src]) {
            if (e.dest == dest) e.blocked = false;
        }
        for (Edge e : adjList[dest]) {
            if (e.dest == src) e.blocked = false;
        }
    }

    // Getters
    public int getVertices() { return vertices; }
    public List<Edge> getAdjList(int v) { return adjList[v]; }
    public List<Edge> getAllEdges() { return allEdges; }
    public String getVertexName(int v) { return vertexNames.get(v); }
    public Location getVertexLocation(int v) { return vertexLocations.get(v); }

    public void printGraph() {
        System.out.println("=== City Road Network ===");
        for (int i = 0; i < vertices; i++) {
            System.out.print(vertexNames.get(i) + " -> ");
            for (Edge e : adjList[i]) {
                if (!e.blocked) {
                    System.out.print(vertexNames.get(e.dest) + "(" + e.weight + "km) ");
                }
            }
            System.out.println();
        }
    }
}