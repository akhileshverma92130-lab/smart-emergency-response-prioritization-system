import service.DispatchService;
import ui.ConsoleUI;

/**
 * Main entry point for Smart Emergency Response Prioritization System
 *
 * DSA Project - Unit 1 to 4 Implementation
 * Topics: Trees, Graphs, Dynamic Programming, Backtracking
 *
 * @author [Your Name]
 * @roll [Your Roll Number]
 * @section [Your Section]
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("  Smart Emergency Response Prioritization System");
        System.out.println("  DSA Project - Java Implementation");
        System.out.println("============================================\n");

        // Initialize system with reasonable capacities
        DispatchService dispatch = new DispatchService(
                100,   // max emergencies
                50,    // max hospitals
                50,    // max ambulances
                20     // graph vertices (intersections/landmarks)
        );

        // Start console UI
        ConsoleUI ui = new ConsoleUI(dispatch);
        ui.start();
    }
}