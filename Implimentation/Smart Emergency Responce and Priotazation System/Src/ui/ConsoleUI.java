package ui;

import Model.*;
import service.DispatchService;
import ds.*;
import graph.*;
import dp.*;
import backtrack.*;
import java.util.*;

/**
 * Console-based UI for Emergency Response System
 * Menu-driven interface for demo/testing
 */
public class ConsoleUI {

    private DispatchService dispatch;
    private Scanner scanner;
    private int emergencyCounter = 1;
    private int hospitalCounter = 1;
    private int ambulanceCounter = 1;

    public ConsoleUI(DispatchService dispatch) {
        this.dispatch = dispatch;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        loadSampleData();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: addEmergency(); break;
                case 2: dispatchNextEmergency(); break;
                case 3: viewPendingEmergencies(); break;
                case 4: addHospital(); break;
                case 5: viewHospitals(); break;
                case 6: addAmbulance(); break;
                case 7: viewAmbulances(); break;
                case 8: addRoad(); break;
                case 9: viewRoadNetwork(); break;
                case 10: findShortestPath(); break;
                case 11: blockRoad(); break;
                case 12: runKnapsackDemo(); break;
                case 13: runResourceAllocationDemo(); break;
                case 14: runTSPDemo(); break;
                case 15: runSubsetSumDemo(); break;
                case 16: buildEmergencyNetwork(); break;
                case 0: running = false; System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n========================================");
        System.out.println("  SMART EMERGENCY RESPONSE SYSTEM");
        System.out.println("========================================");
        System.out.println("1.  Add Emergency");
        System.out.println("2.  Dispatch Next Emergency");
        System.out.println("3.  View Pending Emergencies");
        System.out.println("4.  Add Hospital");
        System.out.println("5.  View All Hospitals");
        System.out.println("6.  Add Ambulance");
        System.out.println("7.  View All Ambulances");
        System.out.println("8.  Add Road");
        System.out.println("9.  View Road Network");
        System.out.println("10. Find Shortest Path (Dijkstra)");
        System.out.println("11. Block Road (Test Alternate Route)");
        System.out.println("12. Knapsack Demo (Equipment Loading)");
        System.out.println("13. Resource Allocation Demo (DP)");
        System.out.println("14. TSP Demo (Multi-stop Route)");
        System.out.println("15. Subset Sum Demo");
        System.out.println("16. Build Emergency Network (MST)");
        System.out.println("0.  Exit");
    }

    private void loadSampleData() {
        // Sample hospitals
        dispatch.addHospital(new Hospital(1, "City General Hospital",
                new Location(28.6139, 77.2090, "Connaught Place"), 200,
                new String[]{"Cardiology", "Trauma", "General"}, true, true));
        dispatch.addHospital(new Hospital(2, "Apollo Hospital",
                new Location(28.5562, 77.1000, "Nehru Place"), 150,
                new String[]{"Cardiology", "Neurology", "Oncology"}, true, false));
        dispatch.addHospital(new Hospital(3, "AIIMS Trauma Center",
                new Location(28.5672, 77.2100, "Ansari Nagar"), 300,
                new String[]{"Trauma", "Burn", "Emergency"}, true, true));

        // Sample ambulances
        dispatch.addAmbulance(new Ambulance(1, "DL01AM1234",
                new Location(28.6200, 77.2100, "CP"),
                new String[]{"Defibrillator", "Ventilator", "Stretcher", "Oxygen"}, 2));
        dispatch.addAmbulance(new Ambulance(2, "DL01AM5678",
                new Location(28.5500, 77.1050, "Nehru Place"),
                new String[]{"Trauma Kit", "Stretcher", "Oxygen"}, 1));
        dispatch.addAmbulance(new Ambulance(3, "DL01AM9012",
                new Location(28.5700, 77.2150, "AIIMS"),
                new String[]{"Burn Kit", "Defibrillator", "Ventilator", "Stretcher"}, 2));

        // Sample roads (Delhi area - simplified)
        // Vertex IDs: 0=CP, 1=Nehru Place, 2=AIIMS, 3=India Gate, 4=Karol Bagh, 5=Dwarka
        dispatch.addRoad(0, 1, 12.5, 25);   // CP - Nehru Place
        dispatch.addRoad(0, 2, 8.2, 18);    // CP - AIIMS
        dispatch.addRoad(0, 3, 3.5, 10);    // CP - India Gate
        dispatch.addRoad(1, 2, 6.8, 15);    // Nehru Place - AIIMS
        dispatch.addRoad(2, 3, 5.2, 12);    // AIIMS - India Gate
        dispatch.addRoad(3, 4, 4.8, 12);    // India Gate - Karol Bagh
        dispatch.addRoad(4, 5, 18.5, 35);   // Karol Bagh - Dwarka
        dispatch.addRoad(1, 5, 22.0, 40);   // Nehru Place - Dwarka

        // Map ambulance vertices (1000+id)
        dispatch.getCityGraph().addVertex(1001, "AMB-DL01AM1234",
                new Location(28.6200, 77.2100, "CP"));
        dispatch.getCityGraph().addVertex(1002, "AMB-DL01AM5678",
                new Location(28.5500, 77.1050, "Nehru Place"));
        dispatch.getCityGraph().addVertex(1003, "AMB-DL01AM9012",
                new Location(28.5700, 77.2150, "AIIMS"));

        System.out.println("[SYSTEM] Sample data loaded!");
    }

    // ========== Menu Handlers ==========

    private void addEmergency() {
        System.out.println("\n--- Add New Emergency ---");
        System.out.print("Enter type (Cardiac/Accident/Trauma/Burn/Respiratory/General): ");
        String type = scanner.nextLine();

        System.out.print("Enter severity (1=Critical, 2=Serious, 3=Moderate, 4=Minor, 5=Low): ");
        int severity = getIntInput("");

        System.out.print("Enter description: ");
        String desc = scanner.nextLine();

        System.out.print("Enter location name: ");
        String locName = scanner.nextLine();
        System.out.print("Enter latitude: ");
        double lat = getDoubleInput("");
        System.out.print("Enter longitude: ");
        double lon = getDoubleInput("");

        Emergency e = new Emergency(emergencyCounter++,
                new Location(lat, lon, locName), severity, type, desc);
        dispatch.addEmergency(e);
    }

    private void dispatchNextEmergency() {
        Emergency e = dispatch.getNextEmergency();
        if (e == null) {
            System.out.println("No pending emergencies!");
            return;
        }
        dispatch.dispatchAmbulanceToEmergency(e);
    }

    private void viewPendingEmergencies() {
        System.out.println("\n--- Pending Emergencies ---");
        System.out.println("Count: " + dispatch.getPendingCount());
        // Note: MaxHeap.printQueue() destroys and restores - just for demo
        dispatch.getEmergencyQueue().printQueue();
    }

    private void addHospital() {
        System.out.println("\n--- Add Hospital ---");
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Location name: "); String locName = scanner.nextLine();
        System.out.print("Latitude: "); double lat = getDoubleInput("");
        System.out.print("Longitude: "); double lon = getDoubleInput("");
        System.out.print("Total beds: "); int beds = getIntInput("");
        System.out.print("Specialties (comma separated): ");
        String[] specs = scanner.nextLine().split(",");
        System.out.print("Has ICU? (y/n): "); boolean icu = scanner.nextLine().equalsIgnoreCase("y");
        System.out.print("Has Trauma Center? (y/n): "); boolean trauma = scanner.nextLine().equalsIgnoreCase("y");

        Hospital h = new Hospital(hospitalCounter++, name,
                new Location(lat, lon, locName), beds, specs, icu, trauma);
        dispatch.addHospital(h);
        System.out.println("Hospital added!");
    }

    private void viewHospitals() {
        dispatch.printHospitals();
    }

    private void addAmbulance() {
        System.out.println("\n--- Add Ambulance ---");
        System.out.print("Vehicle Number: "); String vn = scanner.nextLine();
        System.out.print("Location name: "); String locName = scanner.nextLine();
        System.out.print("Latitude: "); double lat = getDoubleInput("");
        System.out.print("Longitude: "); double lon = getDoubleInput("");
        System.out.print("Equipment (comma separated): ");
        String[] eq = scanner.nextLine().split(",");
        System.out.print("Max capacity (patients): "); int cap = getIntInput("");

        Ambulance a = new Ambulance(ambulanceCounter++, vn,
                new Location(lat, lon, locName), eq, cap);
        dispatch.addAmbulance(a);
        System.out.println("Ambulance added!");
    }

    private void viewAmbulances() {
        dispatch.printAllAmbulances();
        System.out.println();
        dispatch.printAvailableAmbulances();
    }

    private void addRoad() {
        System.out.println("\n--- Add Road ---");
        System.out.print("Source vertex ID: "); int src = getIntInput("");
        System.out.print("Dest vertex ID: "); int dest = getIntInput("");
        System.out.print("Distance (km): "); double dist = getDoubleInput("");
        System.out.print("Time (min): "); double time = getDoubleInput("");
        dispatch.addRoad(src, dest, dist, time);
        System.out.println("Road added!");
    }

    private void viewRoadNetwork() {
        dispatch.getCityGraph().printGraph();
    }

    private void findShortestPath() {
        System.out.println("\n--- Find Shortest Path ---");
        System.out.print("Source vertex: "); int src = getIntInput("");
        System.out.print("Dest vertex: "); int dest = getIntInput("");
        dispatch.printShortestRoute(src, dest);
    }

    private void blockRoad() {
        System.out.println("\n--- Block Road ---");
        System.out.print("Source vertex: "); int src = getIntInput("");
        System.out.print("Dest vertex: "); int dest = getIntInput("");
        dispatch.blockRoad(src, dest);
    }

    private void runKnapsackDemo() {
        System.out.println("\n--- Knapsack Demo: Ambulance Equipment Loading ---");
        Knapsack01.Item[] items = {
                new Knapsack01.Item("Defibrillator", 5, 100),
                new Knapsack01.Item("Ventilator", 15, 90),
                new Knapsack01.Item("Stretcher", 8, 60),
                new Knapsack01.Item("Oxygen Tank", 10, 70),
                new Knapsack01.Item("Trauma Kit", 3, 80),
                new Knapsack01.Item("Burn Kit", 4, 75),
                new Knapsack01.Item("Monitor", 6, 50),
                new Knapsack01.Item("Suction Pump", 2, 30)
        };
        int capacity = 30; // kg
        Knapsack01.Result result = dispatch.loadAmbulanceEquipment(items, capacity);
        Knapsack01.printResult(items, result);
    }

    private void runResourceAllocationDemo() {
        System.out.println("\n--- Resource Allocation Demo: Fleet Distribution ---");
        ResourceAllocation.Zone[] zones = {
                new ResourceAllocation.Zone("Central Delhi", 500000, 2, 5),
                new ResourceAllocation.Zone("South Delhi", 400000, 2, 4),
                new ResourceAllocation.Zone("West Delhi", 350000, 1, 3),
                new ResourceAllocation.Zone("East Delhi", 300000, 1, 3),
                new ResourceAllocation.Zone("North Delhi", 250000, 1, 2)
        };
        int totalAmbulances = 10;
        ResourceAllocation.Result result = dispatch.allocateFleet(zones, totalAmbulances);
        ResourceAllocation.printResult(zones, result);
    }

    private void runTSPDemo() {
        System.out.println("\n--- TSP Demo: Multi-Emergency Route ---");
        // Ambulance at CP (0) needs to visit emergencies at vertices 3, 4, then return
        int[] emergencies = {3, 4}; // India Gate, Karol Bagh
        TSP.Result result = dispatch.planMultiStopRoute(0, emergencies);
        TSP.printResult(dispatch.getCityGraph(), result);
    }

    private void runSubsetSumDemo() {
        System.out.println("\n--- Subset Sum Demo: Resource Partition ---");
        int[] resources = {2, 3, 5, 7, 8, 10}; // Ambulance counts per depot
        int target = 15; // Need exactly 15 for a zone
        SubsetSum.Result result = dispatch.partitionResources(resources, target);
        SubsetSum.printResult(resources, result);
    }

    private void buildEmergencyNetwork() {
        System.out.println("\n--- Build Emergency Communication Network ---");
        System.out.print("Start vertex: "); int start = getIntInput("");
        dispatch.buildEmergencyNetwork(start);
    }

    // Helpers
    private int getIntInput(String prompt) {
        if (!prompt.isEmpty()) System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter integer: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }

    private double getDoubleInput(String prompt) {
        if (!prompt.isEmpty()) System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter number: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}