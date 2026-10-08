# Smart Emergency Response Prioritization System

A menu-driven Java console application that prioritizes emergency cases by severity and urgency, models hospitals and roads as a graph, and uses data structures and algorithms from **Units 1 to 4 of Data Structures and Algorithms – II** to route ambulances and use limited emergency resources efficiently.

| | |
|---|---|
| **Course** | Data Structures and Algorithms – II (CCSE0301) |
| **Program** | B.Tech CSE-A, NIET Greater Noida |
| **Faculty** | Mr. Shamshad Ali |
| **Type** | Individual PBL project |
| **SDG** | SDG 3 – Good Health and Well Being |
| **Language** | Java (console application) |

---

## 1. Problem Statement

In an emergency, several cases can arrive at the same time while ambulances, medical teams and hospital capacity are limited. If cases are handled in the order they arrive, a critical patient may wait behind a minor one, and a poorly chosen route or a blocked road can cost valuable minutes.

This project addresses three questions:

1. **Who should be served first?** Cases are ordered by severity and urgency.
2. **How should the ambulance get there?** Hospitals, emergency locations and roads are modelled as a weighted graph, and the shortest route is found (with alternate routes when a road is blocked).
3. **How should limited resources be used?** Equipment, ambulances and teams are selected and allocated under capacity constraints.

## 2. Objectives

- Prioritize emergency cases based on severity and urgency and handle critical cases first.
- Reduce delay in emergency response by finding short routes between locations and hospitals.
- Keep a record of hospitals, ambulances and road connections.
- Test alternate routes when a road is blocked.
- Use available resources efficiently under capacity limits.
- Apply the syllabus topics of Units 1 to 4 to a realistic problem.

## 3. Features

The program starts with a console menu:

| Option | Feature | What it does |
|-------:|---------|--------------|
| 1 | Add Emergency | Registers a new emergency case with its priority details |
| 2 | Dispatch Next Emergency | Removes and serves the highest-priority pending case |
| 3 | View Pending Emergencies | Lists the cases that are still waiting |
| 4 | Add Hospital | Adds a hospital as a location in the network |
| 5 | View All Hospitals | Lists the registered hospitals |
| 6 | Add Ambulance | Adds an ambulance to the fleet |
| 7 | View All Ambulances | Lists the registered ambulances |
| 8 | Add Road | Connects two locations with a road of a given distance |
| 9 | View Road Network | Prints the road network |
| 10 | Find Shortest Path (Dijkstra) | Finds the shortest route between two locations |
| 11 | Block Road (Test Alternate Route) | Marks a road as blocked so the routing algorithms must find another way |
| 12 | Knapsack Demo (Equipment Loading) | Chooses equipment to load under a capacity limit |
| 13 | Resource Allocation Demo (DP) | Allocates limited resources using Dynamic Programming |
| 14 | TSP Demo (Multi-stop Route) | Finds an efficient order for visiting several locations |
| 15 | Subset Sum Demo | Picks a combination of resources that fits a given limit |
| 16 | Build Emergency Network (MST) | Builds a minimum-cost network between locations (Prim's and Kruskal's) |
| 0 | Exit | Closes the program |

Blocked roads are ignored by the graph algorithms (Dijkstra, Bellman-Ford, Floyd-Warshall, Prim's and Kruskal's), so rerouting and the spanning tree both respond to a blocked road.

---

## 4. DSA Concepts Used (Unit-wise)

### Unit 1 – Trees and Priority-Based Processing

| Concept | Where it is used in the project | Time complexity |
|---------|---------------------------------|-----------------|
| Priority Queue | Holds pending emergencies so the most critical case is dispatched first (option 2) | Insert O(log n), remove-highest O(log n), peek O(1) |
| Binary Heap | Underlying structure that keeps the highest-priority case at the top | Insert / delete O(log n) |
| Heap Sort | Arranges emergency requests in priority order | O(n log n) |
| Binary Tree | Hierarchical organization of patient / emergency records | Traversal O(n) |
| Binary Search Tree (BST) | Searching, inserting and deleting records | Average O(log n), worst O(n) |
| AVL Tree | Keeps records balanced so operations stay efficient | O(log n) |
| Tree Traversals | Processing and displaying tree-based records | O(n) |

### Unit 2 – Graphs

The city is a weighted graph: **locations and hospitals are vertices, roads are edges, and road length (km) is the edge weight.**

| Concept | Where it is used in the project | Time complexity |
|---------|---------------------------------|-----------------|
| Graph terminology | Vertices = locations / hospitals, edges = roads, weights = distance | – |
| Adjacency List | Stores road connections compactly; used by the traversal and shortest-path code | Space O(V + E) |
| Adjacency Matrix | Quick check whether two locations are directly connected | Edge lookup O(1), space O(V²) |
| BFS | Explores nearby locations level by level | O(V + E) |
| DFS | Explores connected locations and road relationships | O(V + E) |
| Connected Components | Finds regions of the network that are connected or cut off | O(V + E) |
| Dijkstra's Algorithm | Shortest route between two locations (option 10) | O((V + E) log V) with a binary heap |
| Bellman-Ford | Shortest routes when edge weights may be negative (for example, time-bonus lanes); detects negative cycles | O(V · E) |
| Floyd-Warshall | Precomputes the shortest distance between every pair of hospitals / locations | O(V³), space O(V²) |
| Prim's Algorithm | Minimum-cost network between hospitals, using a priority queue | O(E log V) |
| Kruskal's Algorithm | Same goal; sorts edges and joins them using Union-Find | O(E log E) |
| Union-Find (Disjoint Set) | Used by Kruskal's to detect cycles (path compression and union by rank) | Nearly O(1) per operation |

### Unit 3 – Dynamic Programming

| Concept | Where it is used in the project | Time complexity |
|---------|---------------------------------|-----------------|
| Dynamic Programming | Optimizes decisions about limited resources by reusing solutions of subproblems | Problem-dependent |
| 0/1 Knapsack | Chooses which equipment to load within a capacity limit (option 12) | O(n · W) |
| Resource Allocation Problem | Distributes limited ambulances, teams and other resources among cases (option 13) | Depends on the formulation |

### Unit 4 – Backtracking and Branch and Bound

| Concept | Where it is used in the project | Time complexity |
|---------|---------------------------------|-----------------|
| Backtracking | Explores possible resource assignments and feasible combinations, undoing choices that fail the constraints | Worst case exponential |
| Branch and Bound | Discards assignments or routes that cannot beat the best solution found so far | Worst case exponential |
| Travelling Salesman Problem | Finds an efficient order for visiting several emergency locations (option 14) | O(n!) worst case for the exact approach |
| Sum of Subsets | Selects a combination of resources that fits a given limit (option 15) | O(2ⁿ) worst case |

### Concepts studied but not used

Transitive Closure, Longest Common Subsequence, Matrix Chain Multiplication, Graph Colouring, N-Queen Problem and Hamiltonian Cycle were studied in the syllabus but are not applied in this project, because they do not fit the emergency-response problem.

---

## 5. How the Pieces Work Together

1. **Intake.** An emergency is added with its severity and urgency and enters the priority queue.
2. **Dispatch.** The highest-priority case is removed from the queue.
3. **Routing.** The graph algorithms find the route from the ambulance to the location and on to a hospital. If a road is blocked, the algorithms skip it and return an alternate route.
4. **Resource decisions.** Knapsack and Dynamic Programming choose equipment and allocate resources within limits. Backtracking, Branch and Bound, TSP and Subset Sum handle combination and multi-stop problems.
5. **Network planning.** Prim's and Kruskal's build a minimum-cost connection between hospitals when normal communication is down.

## 6. Project Structure

```
Src/
├── backtrack/   Backtracking and Branch and Bound algorithms
├── dp/          Dynamic Programming algorithms (Knapsack, resource allocation)
├── ds/          Core data structures (priority queue, heap, trees)
├── graph/       Graph, Dijkstra, Bellman-Ford, Floyd-Warshall, Prim's, Kruskal's
├── Model/       Entity classes (emergency, hospital, ambulance, ...)
├── service/     Application logic connecting the modules
├── ui/          Console menu
└── Main.java    Entry point
data/            Input data
```

## 7. How to Run

**Requirements:** a Java Development Kit (JDK). IntelliJ IDEA is optional.

**In IntelliJ IDEA**
1. Clone the repository and open the project folder.
2. Mark `Src` as the Sources Root if it is not already.
3. Open `Main.java` and click Run.

**From the command line**

```
javac -d out -sourcepath Src Src/Main.java
java -cp out Main
```

**Suggested first run**
1. Add a few hospitals (option 4) and ambulances (option 6).
2. Add roads between locations (option 8) and check them with option 9.
3. Add several emergencies (option 1) with different priorities and use option 2 to see them served in priority order.
4. Run option 10 for the shortest path, then option 11 to block a road and run option 10 again to see the alternate route.
5. Try the demos in options 12 to 16.

## 8. Complexity Summary

| Algorithm | Time | Notes |
|-----------|------|-------|
| BFS / DFS / Connected Components | O(V + E) | Adjacency list |
| Dijkstra | O((V + E) log V) | Non-negative weights, binary heap |
| Bellman-Ford | O(V · E) | Handles negative weights |
| Floyd-Warshall | O(V³) | All pairs |
| Prim's MST | O(E log V) | Priority queue |
| Kruskal's MST | O(E log E) | Sorting plus Union-Find |
| 0/1 Knapsack | O(n · W) | Dynamic Programming |
| Backtracking / Branch and Bound | Exponential (worst case) | Pruning reduces the work in practice |
| TSP (exact) | O(n!) worst case | Small number of stops |
| Sum of Subsets | O(2ⁿ) worst case | |

## 9. Limitations and Future Work

- The system runs on sample data entered through the console; real emergency data was not available.
- TSP, Backtracking and Subset Sum are exponential in the worst case, so they suit a small number of locations or resources.
- Planned for the final review: testing with a wider range of emergency scenarios, performance analysis of the algorithms, and tighter integration of resource allocation with case prioritization.

## 10. References

- *Smart transportation solutions for faster emergency medical services response using an enhanced whale optimization algorithm*, IET Intelligent Transport Systems, 2024. https://doi.org/10.1049/itr2.12555
- *A generalized three-stage optimization model for emergency medical services under uncertainties: Integrating rescue station locations, ambulance deployment, and vehicle dispatch*, Transportation Research Part E. https://trid.trb.org/View/2614748

## 11. Author

**Akhilesh Verma**
B.Tech CSE, NIET Greater Noida (Dr. A.P.J. Abdul Kalam Technical University, Lucknow)
Faculty: Mr. Shamshad Ali
GitHub: [akhileshverma92130-lab](https://github.com/akhileshverma92130-lab)
