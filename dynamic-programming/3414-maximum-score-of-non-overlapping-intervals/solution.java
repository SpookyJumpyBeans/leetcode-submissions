// 3414. Maximum Score of Non-overlapping Intervals
// https://leetcode.com/problems/maximum-score-of-non-overlapping-intervals/
// Hard | Java | Accepted 2026-09-15
// Runtime 330 ms | Memory 171.7 MB

import java.util.*; // Imports standard Java utility classes (Lists, Arrays, Collections, etc.)

class Solution {
    
    // A helper class to bundle a calculated score with the indices that produced it
    class Result {
        long score; // The total weight/score of the selected intervals
        List<Integer> indices; // The original array indices of the selected intervals
        
        // Constructor to initialize a Result object
        public Result(long s, List<Integer> i) {
            score = s; // Set the score
            indices = i; // Set the indices list
        }
    }
    
    // 2D array to cache results for memoization: dp[current_index][intervals_picked]
    Result[][] dp;
    // Class-level reference to the intervals list so it can be accessed inside recurse()
    List<List<Integer>> interval; 
    
    // Main method to find the maximum weight of up to 4 non-overlapping intervals
    public int[] maximumWeight(List<List<Integer>> intervals) {
        interval = intervals; // Assign the input to the class-level variable
        
        // Loop through all intervals to append their original starting index
        for (int i = 0; i < interval.size(); i++) {
            // Adds the original index as the 4th element (index 3) of the inner list
            interval.get(i).add(i); 
        }
        
        // Initialize the DP table: rows = number of intervals, cols = 5 (for counts 0, 1, 2, 3, 4)
        dp = new Result[interval.size()][5];
        
        // Sort the intervals ascending based on their start times (index 0)
        interval.sort((a, b) -> Integer.compare(a.get(0), b.get(0)));
        
        // Start the recursion from interval index 0, with 0 intervals selected so far
        List<Integer> bestIndices = recurse(0, 0).indices;
        
        // Prepare the final primitive int array to match the expected return type
        int[] result = new int[bestIndices.size()];
        
        // Copy values from the ArrayList to the primitive array
        for (int i = 0; i < bestIndices.size(); i++) {
            result[i] = bestIndices.get(i);
        }
        
        // Sort the final indices array in ascending order before returning
        Arrays.sort(result);
        
        // Return the final optimally chosen original indices
        return result; 
    }   
    
    // Recursive function to explore taking or skipping the interval at 'ind'
    public Result recurse(int ind, int count) {
        // Base case: if we picked 4 intervals or ran out of intervals to check
        if (count == 4 || ind == interval.size()) {
            // Return 0 score and an empty list (no more intervals can be picked)
            return new Result(0, new ArrayList<>());
        }
        
        // Memoization check: if we already solved this exact subproblem, return the cached result
        if (dp[ind][count] != null) {
            return dp[ind][count];
        } 
        
        // Option 1 (SKIP): Move to the next interval (ind + 1) without incrementing 'count'
        Result skip = recurse(ind + 1, count);
        
        // Option 2 (CHOOSE): Find the next valid interval using binary search
        int l = ind + 1; // Left pointer for binary search starts at the next interval
        int r = interval.size() - 1; // Right pointer starts at the end of the list
        int ans = -1; // Variable to store the index of the next non-overlapping interval
        
        // Execute binary search to find the earliest interval that starts after the current one ends
        while (l <= r) {
            int mid = l + (r - l) / 2; // Calculate the midpoint to avoid integer overflow
            
            // If the start time of 'mid' is strictly greater than the end time of current 'ind'
            if (interval.get(mid).get(0) > interval.get(ind).get(1)) {
                ans = mid; // This is a valid next interval, record it
                r = mid - 1; // Try to find an even earlier valid interval by searching left
            } else {
                l = mid + 1; // Overlaps! Search right for an interval that starts later
            }
        }
        
        // Variables to hold the score and indices if we choose the current interval
        long newScore;
        List<Integer> newIndices = new ArrayList<>();

        // If binary search found a valid next interval
        if (ans != -1) {
            // Recursively calculate the best outcome from that next interval (incrementing count)
            Result take = recurse(ans, count + 1);
            // Total score = the future score + the current interval's weight (index 2)
            newScore = take.score + interval.get(ind).get(2);
            // Add the current interval's original index (index 3) to our list
            newIndices.add(interval.get(ind).get(3));
            // Add all the indices chosen by the future recursive calls
            newIndices.addAll(take.indices);
        } else {
            // If no valid next interval exists, our score is just this current interval's weight
            newScore = interval.get(ind).get(2);
            // We just add this single interval's original index to our list
            newIndices.add(interval.get(ind).get(3));
        }

        // Package the 'CHOOSE' scenario into a Result object
        Result choose = new Result(newScore, newIndices);
        
        // Compare the CHOOSE path vs the SKIP path and cache the better one
        if (choose.score > skip.score) {
            return dp[ind][count] = choose; // Caching and returning CHOOSE
        } else if (choose.score < skip.score) {
            return dp[ind][count] = skip; // Caching and returning SKIP
        }
        
        // TIE-BREAKER: If scores are perfectly equal, we want the lexicographically smaller indices
        // Cache and return the winner of the compareLists helper method
        return dp[ind][count] = compareLists(choose.indices, skip.indices) < 0 ? choose : skip;
    }
    
    // Helper method to compare two lists of indices lexicographically
    private int compareLists(List<Integer> a, List<Integer> b) {
        // BUG WARNING: Collections.sort() sorts the lists in-place. 
        // Since 'a' and 'b' are references to the lists cached in your DP table, 
        // this mutates your cached state and breaks future recursive lookups.
        // Fix: Create defensive copies first: List<Integer> copyA = new ArrayList<>(a);
        Collections.sort(a); 
        Collections.sort(b); 
        
        // Find the length of the shorter list to prevent IndexOutOfBounds exceptions
        int size = Math.min(a.size(), b.size());
        
        // Compare elements one by one
        for (int i = 0; i < size; i++) {
            int cmp = Integer.compare(a.get(i), b.get(i));
            // If they are not equal, return the comparison result (-1 or 1)
            if (cmp != 0) return cmp;
        }
        // If all elements up to 'size' are identical, the shorter list wins
        return Integer.compare(a.size(), b.size());
    }
}
