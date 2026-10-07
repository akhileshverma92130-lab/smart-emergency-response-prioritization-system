package dp;

/**
 * 0/1 Knapsack Problem - Dynamic Programming
 * Used for: Ambulance equipment loading (weight/capacity constraints)
 *           Optimal resource allocation
 *
 * Time: O(n * W), Space: O(n * W) - can optimize to O(W)
 */
public class Knapsack01 {

    public static class Item {
        String name;
        int weight;    // Weight in kg
        int value;     // Priority/importance score

        public Item(String name, int weight, int value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }
    }

    public static class Result {
        int maxValue;
        boolean[] selected;  // Which items included

        Result(int n) {
            selected = new boolean[n];
        }
    }

    public static Result solve(Item[] items, int capacity) {
        int n = items.length;
        int[][] dp = new int[n + 1][capacity + 1];

        // Build DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (items[i-1].weight <= w) {
                    dp[i][w] = Math.max(
                            dp[i-1][w],                              // Don't take
                            items[i-1].value + dp[i-1][w - items[i-1].weight]  // Take
                    );
                } else {
                    dp[i][w] = dp[i-1][w];
                }
            }
        }

        // Backtrack to find selected items
        Result result = new Result(n);
        result.maxValue = dp[n][capacity];
        int w = capacity;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                result.selected[i-1] = true;
                w -= items[i-1].weight;
            }
        }

        return result;
    }

    // Space optimized version - O(W) space
    public static int solveOptimized(Item[] items, int capacity) {
        int[] dp = new int[capacity + 1];
        for (Item item : items) {
            for (int w = capacity; w >= item.weight; w--) {
                dp[w] = Math.max(dp[w], item.value + dp[w - item.weight]);
            }
        }
        return dp[capacity];
    }

    public static void printResult(Item[] items, Result result) {
        System.out.println("=== Knapsack Solution ===");
        System.out.println("Max Value: " + result.maxValue);
        System.out.println("Selected Items:");
        int totalWeight = 0;
        for (int i = 0; i < items.length; i++) {
            if (result.selected[i]) {
                System.out.println("  - " + items[i].name + " (Wt: " + items[i].weight +
                        ", Val: " + items[i].value + ")");
                totalWeight += items[i].weight;
            }
        }
        System.out.println("Total Weight: " + totalWeight);
    }
}