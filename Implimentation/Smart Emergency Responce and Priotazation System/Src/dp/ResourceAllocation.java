package dp;

import java.util.*;

/**
 * Resource Allocation Problem - Dynamic Programming
 * Used for: Distributing limited ambulances across zones to maximize coverage
 *
 * State: dp[i][j] = max coverage using first i zones with j ambulances
 */
public class ResourceAllocation {

    public static class Zone {
        String name;
        int population;      // People to cover
        int requiredAmbulances; // Minimum needed
        int maxAmbulances;   // Maximum useful

        public Zone(String name, int population, int required, int max) {
            this.name = name;
            this.population = population;
            this.requiredAmbulances = required;
            this.maxAmbulances = max;
        }

        // Coverage function - diminishing returns
        public int coverage(int ambulances) {
            if (ambulances == 0) return 0;
            if (ambulances >= maxAmbulances) return population;
            // Diminishing returns: first ambulance covers most
            return (int) (population * (1 - Math.pow(0.7, ambulances)));
        }
    }

    public static class Result {
        int maxCoverage;
        int[] allocation;  // Ambulances per zone

        Result(int zones) {
            allocation = new int[zones];
        }
    }

    public static Result allocate(Zone[] zones, int totalAmbulances) {
        int n = zones.length;
        int[][] dp = new int[n + 1][totalAmbulances + 1];
        int[][] choice = new int[n + 1][totalAmbulances + 1];

        for (int i = 1; i <= n; i++) {
            for (int a = 0; a <= totalAmbulances; a++) {
                dp[i][a] = dp[i-1][a];  // 0 ambulances to this zone
                choice[i][a] = 0;

                for (int k = 1; k <= Math.min(a, zones[i-1].maxAmbulances); k++) {
                    int val = dp[i-1][a-k] + zones[i-1].coverage(k);
                    if (val > dp[i][a]) {
                        dp[i][a] = val;
                        choice[i][a] = k;
                    }
                }
            }
        }

        Result result = new Result(n);
        result.maxCoverage = dp[n][totalAmbulances];

        // Backtrack
        int a = totalAmbulances;
        for (int i = n; i >= 1; i--) {
            result.allocation[i-1] = choice[i][a];
            a -= choice[i][a];
        }

        return result;
    }

    public static void printResult(Zone[] zones, Result result) {
        System.out.println("=== Resource Allocation (DP) ===");
        System.out.println("Total Ambulances: " + Arrays.stream(result.allocation).sum());
        System.out.println("Max Coverage: " + result.maxCoverage + " people");
        System.out.println("\nAllocation:");
        for (int i = 0; i < zones.length; i++) {
            System.out.println("  " + zones[i].name + ": " + result.allocation[i] +
                    " ambulances -> covers " + zones[i].coverage(result.allocation[i]) + " people");
        }
    }
}