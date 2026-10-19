// 805. Split Array With Same Average
// https://leetcode.com/problems/split-array-with-same-average/
// Hard | Java | Accepted 2026-10-16
// Runtime 110 ms | Memory 84 MB

class Solution {
    // Memoization table to store previously computed states and avoid redundant work.
    // Key format: "index,remaining_sum,remaining_elements"
    HashMap<String, Boolean> memo = new HashMap<>();
    int n;
    int sumArr = 0;
    
    public boolean splitArraySameAverage(int[] nums) {
        // Calculate the total sum of the array to find the global average
        for(int i : nums) {
            sumArr += i;
        }
        n = nums.length;
        
        /* 
         * MATHEMATICAL PROOF & INTUITION:
         * 
         * 1. Why do we only track one subset instead of two?
         * If the array is split into subsets A and B, and avg(A) == avg(B), 
         * then avg(A) MUST equal the average of the entire array.
         * 
         * Let S = total sum of nums, N = total length of nums.
         * Let S_A = sum of subset A, k = length of subset A.
         * 
         * We want: avg(A) = avg(B)
         * S_A / k = (S - S_A) / (N - k)
         * 
         * Cross-multiply to remove fractions: 
         * S_A * (N - k) = k * (S - S_A)
         * 
         * Expand both sides: 
         * (S_A * N) - (S_A * k) = (k * S) - (k * S_A)
         * 
         * Add (S_A * k) to both sides (they cancel out): 
         * S_A * N = k * S
         * 
         * Isolate S_A: 
         * S_A = (S * k) / N
         * 
         * Conclusion 1: The sum of subset A is entirely predetermined by its length (k). 
         * We don't need to track B at all. We just check if we can build a subset that 
         * hits this exact sum.
         * 
         * 2. Why do we only check lengths up to N/2?
         * If we split an array of length N into two non-empty subsets A and B, 
         * their lengths k and (N - k) must add up to N.
         * It is mathematically impossible for BOTH subsets to have a length strictly 
         * greater than N/2. At least one subset will always have a size <= N/2.
         * Since finding a valid subset A automatically guarantees the remaining 
         * elements form a valid subset B, we are 100% guaranteed to find a valid 
         * split (if one exists) by only checking subset sizes from 1 to N/2.
         */
        for(int i = 1; i <= n/2; i++) {
            // Early pruning: The target sum for a subset of size 'i' is (total_sum * i) / n.
            // Since array elements are integers, the target sum MUST be a perfect integer.
            if((sumArr * i) % n == 0) {
                // Try to find exactly 'i' elements that sum up to the required target sum
                boolean res = recurse(nums, 0, sumArr * i / n, i);
                if(res) {
                    return true;
                }
            }
        }
        return false;
    }

    // Top-down DP (DFS) to find if we can pick exactly 'k' elements that sum to 'sum'
    public boolean recurse(int[] nums, int ind, int sum, int k) {
        // Base case: Success. We picked exactly 'k' elements and hit our target sum perfectly.
        if(sum == 0 && k == 0) {
            return true;
        }
        
        // Base cases: Failure. 
        // 1. Reached the end of the array without finishing.
        // 2. The remaining sum dropped below 0 (since nums are non-negative, we can't recover).
        // 3. We picked more elements than we are allowed to (k < 0).
        if(ind == nums.length || sum < 0 || k < 0) {
            return false;
        }
        
        // Construct the unique state key for memoization
        String key = ind + "," + sum + "," + k;
        
        // If we have already solved this exact subproblem, return the cached result
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Recursive transitions (Pick vs. Skip):
        // 1. Pick the current element: Move to next index, subtract its value from target sum, decrement needed elements (k-1).
        // 2. Skip the current element: Move to next index, keep target sum and needed elements the same.
        boolean res = recurse(nums, ind + 1, sum - nums[ind], k - 1) || recurse(nums, ind + 1, sum, k);
        
        // Save the computed result for this state before returning
        memo.put(key, res);
        return memo.get(key);
    }
}
