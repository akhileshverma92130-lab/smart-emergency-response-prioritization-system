package backtrack;

import java.util.*;

/**
 * Subset Sum Problem - Backtracking
 * Used for: Partitioning resources (ambulances, equipment) among zones
 *           Checking if exact resource allocation possible
 */
public class SubsetSum {

    public static class Result {
        boolean found;
        List<Integer> subset;  // Indices of selected items

        Result() {
            subset = new ArrayList<>();
        }
    }

    public static Result findSubset(int[] nums, int target) {
        Result result = new Result();
        List<Integer> current = new ArrayList<>();
        backtrack(nums, target, 0, 0, current, result);
        return result;
    }

    private static void backtrack(int[] nums, int target, int index, int sum,
                                  List<Integer> current, Result result) {
        if (result.found) return;  // Already found one solution

        if (sum == target) {
            result.found = true;
            result.subset = new ArrayList<>(current);
            return;
        }

        if (sum > target || index >= nums.length) return;

        // Include current element
        current.add(index);
        backtrack(nums, target, index + 1, sum + nums[index], current, result);
        current.remove(current.size() - 1);

        // Exclude current element
        backtrack(nums, target, index + 1, sum, current, result);
    }

    // Find all subsets that sum to target
    public static List<List<Integer>> findAllSubsets(int[] nums, int target) {
        List<List<Integer>> all = new ArrayList<>();
        backtrackAll(nums, target, 0, 0, new ArrayList<>(), all);
        return all;
    }

    private static void backtrackAll(int[] nums, int target, int index, int sum,
                                     List<Integer> current, List<List<Integer>> all) {
        if (sum == target) {
            all.add(new ArrayList<>(current));
            return;
        }
        if (sum > target || index >= nums.length) return;

        current.add(index);
        backtrackAll(nums, target, index + 1, sum + nums[index], current, all);
        current.remove(current.size() - 1);

        backtrackAll(nums, target, index + 1, sum, current, all);
    }

    public static void printResult(int[] nums, Result result) {
        System.out.println("=== Subset Sum ===");
        System.out.println("Target: " + target);
        if (result.found) {
            System.out.print("Subset found: ");
            int sum = 0;
            for (int idx : result.subset) {
                System.out.print(nums[idx] + " ");
                sum += nums[idx];
            }
            System.out.println("= " + sum);
        } else {
            System.out.println("No subset sums to target");
        }
    }

    private static int target; // For print method
}