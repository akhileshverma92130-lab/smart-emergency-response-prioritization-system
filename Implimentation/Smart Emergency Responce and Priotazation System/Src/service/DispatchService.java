package service;

import Model.*;
import ds.*;
import graph.*;
import dp.*;
import backtrack.*;

import java.util.*;

/**
 * Main Dispatch Service - Integrates all DSA components
 * This is the brain of the Smart Emergency Response System.
 */
public class DispatchService {

    // ==============================
    // Data Structures
    // ==============================

    private MaxHeap emergencyQueue;
    private AVLTree hospitalIndex;
    private BST ambulanceRegistry;
    private Graph cityGraph;

    // Keep a list of ambulances for easy searching
    private List<Ambulance> ambulanceList;


    // ==============================
    // Algorithm Instances
    // ==============================

    private Dijkstra dijkstra;
    private BellmanFord bellmanFord;
    private FloydWarshall floydWarshall;
    private PrimMst primMST;
    private KruskalMST kruskalMST;

    private Knapsack01 knapsack;
    private ResourceAllocation resourceAlloc;

    private TSP tsp;
    private SubsetSum subsetSum;


    // ==============================
    // Constructor
    // ==============================

    public DispatchService(
            int maxEmergencies,
            int maxHospitals,
            int maxAmbulances,
            int graphVertices) {

        emergencyQueue =
                new MaxHeap(maxEmergencies);

        hospitalIndex =
                new AVLTree();

        ambulanceRegistry =
                new BST();

        ambulanceList =
                new ArrayList<>();

        cityGraph =
                new Graph(graphVertices);


        // Initialize algorithms

        dijkstra =
                new Dijkstra();

        bellmanFord =
                new BellmanFord();

        floydWarshall =
                new FloydWarshall();

        primMST =
                new PrimMst();

        kruskalMST =
                new KruskalMST();

        knapsack =
                new Knapsack01();

        resourceAlloc =
                new ResourceAllocation();

        tsp =
                new TSP(cityGraph);

        subsetSum =
                new SubsetSum();
    }


    // =========================================================
    // Emergency Management
    // =========================================================

    public void addEmergency(Emergency e) {

        emergencyQueue.insert(e);

        System.out.println(
                "[DISPATCH] New emergency added: "
                        + e
        );
    }


    public Emergency getNextEmergency() {

        return emergencyQueue.extractHighestPriority();
    }


    public Emergency peekEmergency() {

        return emergencyQueue.peek();
    }


    public int getPendingCount() {

        return emergencyQueue.size();
    }


    // =========================================================
    // Hospital Management
    // =========================================================

    public void addHospital(Hospital h) {

        hospitalIndex.insert(h);

        // Also add hospital to graph
        cityGraph.addVertex(
                h.getId(),
                h.getName(),
                h.getLocation()
        );
    }


    public Hospital getHospital(int id) {

        return hospitalIndex.search(id);
    }


    public List<Hospital> findNearbyHospitals(
            Location loc,
            double radiusKm) {

        List<Hospital> result =
                new ArrayList<>();

        hospitalIndex.findNearby(
                loc,
                radiusKm,
                result
        );

        return result;
    }


    public void printHospitals() {

        hospitalIndex.inorder();
    }


    // =========================================================
    // Ambulance Management
    // =========================================================

    public void addAmbulance(Ambulance a) {

        // Add ambulance to BST
        ambulanceRegistry.insert(a);

        // Also keep ambulance in list
        ambulanceList.add(a);

        // Add ambulance to graph
        cityGraph.addVertex(
                a.getId() + 1000,
                "AMB-" + a.getVehicleNumber(),
                a.getCurrentLocation()
        );
    }


    public Ambulance getAmbulance(int id) {

        return ambulanceRegistry.search(id);
    }


    public void printAvailableAmbulances() {

        ambulanceRegistry.findAvailable();
    }


    public void printAllAmbulances() {

        ambulanceRegistry.inorder();
    }


    // =========================================================
    // Graph / Routing
    // =========================================================

    public void addRoad(
            int src,
            int dest,
            double distanceKm,
            double timeMin) {

        cityGraph.addEdge(
                src,
                dest,
                distanceKm,
                timeMin
        );
    }


    public void blockRoad(
            int src,
            int dest) {

        cityGraph.blockRoad(
                src,
                dest
        );

        System.out.println(
                "[ALERT] Road blocked: "
                        + cityGraph.getVertexName(src)
                        + " <-> "
                        + cityGraph.getVertexName(dest)
        );
    }


    public void unblockRoad(
            int src,
            int dest) {

        cityGraph.unblockRoad(
                src,
                dest
        );
    }


    // =========================================================
    // Dijkstra - Shortest Path
    // =========================================================

    public Dijkstra.Result findShortestRoute(
            int src,
            int dest) {

        Dijkstra.Result result =
                Dijkstra.findShortestPath(
                        cityGraph,
                        src
                );

        return result;
    }


    public void printShortestRoute(
            int src,
            int dest) {

        Dijkstra.Result result =
                findShortestRoute(
                        src,
                        dest
                );

        Dijkstra.printPath(
                cityGraph,
                result,
                dest
        );
    }


    // =========================================================
    // Alternate Route
    // =========================================================

    public List<Integer> findAlternateRoute(
            int src,
            int dest,
            Set<Integer> avoid) {

        return DFS.findAlternatePath(
                cityGraph,
                src,
                dest,
                avoid
        );
    }


    // =========================================================
    // Floyd-Warshall
    // =========================================================

    public FloydWarshall.Result
    computeAllPairsShortestPath() {

        return FloydWarshall.computeAllPairs(
                cityGraph
        );
    }


    // =========================================================
    // Minimum Spanning Tree
    // =========================================================

    public void buildEmergencyNetwork(
            int startVertex) {

        System.out.println(
                "\n--- Building Emergency Communication Network ---"
        );


        // Prim's MST

        PrimMst.Result primResult =
                PrimMst.findMST(
                        cityGraph,
                        startVertex
                );

        PrimMst.printMST(
                cityGraph,
                primResult
        );


        // Kruskal's MST

        KruskalMST.Result kruskalResult =
                KruskalMST.findMST(
                        cityGraph
                );

        KruskalMST.printMST(
                cityGraph,
                kruskalResult
        );
    }


    // =========================================================
    // Dynamic Programming
    // =========================================================

    // Equipment loading in ambulance
    // using 0/1 Knapsack

    public Knapsack01.Result loadAmbulanceEquipment(
            Knapsack01.Item[] equipment,
            int capacityKg) {

        return Knapsack01.solve(
                equipment,
                capacityKg
        );
    }


    // Fleet allocation using Resource Allocation DP

    public ResourceAllocation.Result allocateFleet(
            ResourceAllocation.Zone[] zones,
            int totalAmbulances) {

        return ResourceAllocation.allocate(
                zones,
                totalAmbulances
        );
    }


    // =========================================================
    // Backtracking
    // =========================================================

    // Multi-stop ambulance route using TSP

    public TSP.Result planMultiStopRoute(
            int start,
            int[] emergencyLocations) {

        return tsp.solve(
                start,
                emergencyLocations
        );
    }


    // Resource partitioning using Subset Sum

    public SubsetSum.Result partitionResources(
            int[] resources,
            int target) {

        return SubsetSum.findSubset(
                resources,
                target
        );
    }


    // =========================================================
    // CORE DISPATCH LOGIC
    // =========================================================

    /**
     * Main dispatch algorithm.
     *
     * Uses:
     * Heap       -> Emergency priority
     * AVL Tree   -> Hospital search
     * Dijkstra   -> Shortest route
     * BST        -> Ambulance registry
     * DP         -> Resource optimization
     */
    public boolean dispatchAmbulanceToEmergency(
            Emergency emergency) {

        System.out.println(
                "\n>>> DISPATCHING: "
                        + emergency
        );


        // -------------------------------------------------
        // 1. Find nearby hospitals
        // -------------------------------------------------

        List<Hospital> nearby =
                findNearbyHospitals(
                        emergency.getLocation(),
                        20.0
                );


        if (nearby.isEmpty()) {

            System.out.println(
                    "[ERROR] No hospitals within 20km!"
            );

            return false;
        }


        // -------------------------------------------------
        // 2. Find best hospital
        // -------------------------------------------------

        Hospital bestHospital = null;

        Dijkstra.Result bestRoute = null;

        double bestDist =
                Double.POSITIVE_INFINITY;


        for (Hospital h : nearby) {

            // Check specialty and beds

            if (!h.canHandle(
                    emergency.getType())) {

                continue;
            }


            if (h.getAvailableBeds() == 0) {

                continue;
            }


            int emergencyVertex =
                    findNearestVertex(
                            emergency.getLocation()
                    );


            Dijkstra.Result route =
                    findShortestRoute(
                            emergencyVertex,
                            h.getId()
                    );


            if (route.dist[h.getId()]
                    < bestDist) {

                bestDist =
                        route.dist[h.getId()];

                bestHospital = h;

                bestRoute = route;
            }
        }


        if (bestHospital == null) {

            System.out.println(
                    "[ERROR] No suitable hospital with beds!"
            );

            return false;
        }


        // -------------------------------------------------
        // 3. Find nearest available ambulance
        // -------------------------------------------------

        Ambulance bestAmbulance = null;

        double minAmbulanceDist =
                Double.POSITIVE_INFINITY;


        int emergencyVertex =
                findNearestVertex(
                        emergency.getLocation()
                );


        /*
         * We no longer use:
         *
         * ambulanceRegistry.getRoot()
         *
         * because BST does not provide getRoot().
         *
         * Instead, we maintain ambulanceList.
         */

        for (Ambulance a : ambulanceList) {

            // Check availability

            if (!a.isAvailable()) {

                continue;
            }


            // Check required equipment

            if (!a.hasEquipment(
                    getRequiredEquipment(
                            emergency.getType()
                    ))) {

                continue;
            }


            int ambVertex =
                    a.getId() + 1000;


            Dijkstra.Result ambRoute =
                    findShortestRoute(
                            ambVertex,
                            emergencyVertex
                    );


            double ambulanceDistance =
                    ambRoute.dist[
                            emergencyVertex
                            ];


            if (ambulanceDistance
                    < minAmbulanceDist) {

                minAmbulanceDist =
                        ambulanceDistance;

                bestAmbulance = a;
            }
        }


        // -------------------------------------------------
        // 4. Check ambulance availability
        // -------------------------------------------------

        if (bestAmbulance == null) {

            System.out.println(
                    "[ERROR] No available ambulance "
                            + "with required equipment!"
            );

            return false;
        }


        // -------------------------------------------------
        // 5. Assign ambulance
        // -------------------------------------------------

        bestAmbulance.assign(
                emergency
        );


        emergency.setAssigned(true);


        // Admit patient

        bestHospital.admitPatient();


        // -------------------------------------------------
        // 6. Print result
        // -------------------------------------------------

        System.out.println(
                "[SUCCESS] Assigned "
                        + bestAmbulance.getVehicleNumber()
                        + " to "
                        + emergency.getType()
                        + " emergency"
        );


        System.out.println(
                "  Route: "
                        + cityGraph.getVertexName(
                        bestAmbulance.getId()
                                + 1000
                )
                        + " -> Emergency -> "
                        + bestHospital.getName()
        );


        System.out.println(
                "  Distance: "
                        + String.format(
                        "%.2f",
                        minAmbulanceDist
                                + bestDist
                )
                        + " km"
        );


        return true;
    }


    // =========================================================
    // Required Equipment
    // =========================================================

    private String getRequiredEquipment(
            String emergencyType) {

        switch (
                emergencyType.toLowerCase()
        ) {

            case "cardiac":
                return "Defibrillator";

            case "trauma":
                return "Trauma Kit";

            case "burn":
                return "Burn Kit";

            case "respiratory":
                return "Ventilator";

            default:
                return "Basic Life Support";
        }
    }


    // =========================================================
    // Find Nearest Graph Vertex
    // =========================================================

    private int findNearestVertex(
            Location loc) {

        int nearest = -1;

        double minDist =
                Double.POSITIVE_INFINITY;


        for (int i = 0;
             i < cityGraph.getVertices();
             i++) {

            Location vLoc =
                    cityGraph.getVertexLocation(i);


            if (vLoc != null) {

                double d =
                        loc.distanceTo(vLoc);


                if (d < minDist) {

                    minDist = d;

                    nearest = i;
                }
            }
        }


        return nearest;
    }


    // =========================================================
    // Getters for UI
    // =========================================================

    public Graph getCityGraph() {

        return cityGraph;
    }


    public MaxHeap getEmergencyQueue() {

        return emergencyQueue;
    }
}