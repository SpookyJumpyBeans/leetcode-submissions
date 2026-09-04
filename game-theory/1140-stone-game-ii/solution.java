// 1140. Stone Game II
// https://leetcode.com/problems/stone-game-ii/
// Medium | Java | Accepted 2026-09-02
// Runtime 3 ms | Memory 43.7 MB

class Solution {
    // 2D array for memoization to store results of overlapping subproblems.
    // dp[ind][m] stores the max stones a player can get starting from index 'ind' with M = 'm'.
    int[][] dp;
    
    // Prefix sum array used to efficiently calculate the sum of any remaining stones in O(1) time.
    int[] pref;

    public int stoneGameII(int[] piles) {
        // Initialize DP cache. piles.length + 1 is used for the M dimension because 
        // M can grow up to the total number of piles.
        dp = new int[piles.length][piles.length + 1];
        
        // Fill the cache with -1 to indicate uncomputed states.
        for(int[] r : dp) {
            Arrays.fill(r, -1);
        }
        
        // Initialize prefix sum array. It's sized piles.length + 1 for 1-based indexing 
        // to make range sum calculations easier and avoid out-of-bounds errors at index 0.
        pref = new int[piles.length + 1];
        
        // Build the prefix sum array. pref[i+1] contains the total sum of piles[0] through piles[i].
        for(int i = 0; i < piles.length; i++) {
            pref[i + 1] = pref[i] + piles[i];
        }
        
        // Start the game from the first pile (index 0) with the initial M value of 1.
        return recurse(0, 1);
    }

    public int recurse(int ind, int maxPiles) {
        // Cache Check: If this specific state (index and M value) has already been calculated, 
        // return the saved answer immediately to prevent redundant recursive calls.
        if(dp[ind][maxPiles] != -1) {
            return dp[ind][maxPiles];
        }
        
        // Base Case: If the current player is allowed to take all the remaining piles 
        // (current index + max allowed choices >= total number of piles).
        // Note: pref.length - 1 equals the original piles.length.
        if(ind + 2 * maxPiles >= pref.length - 1) {
            // Return the sum of all remaining stones from 'ind' to the end of the array.
            return pref[pref.length - 1] - pref[ind];
        }
        
        // Initialize the maximum score for the current player to the smallest possible integer.
        int max = Integer.MIN_VALUE;
        
        // The current player can choose to take 'i' piles, where 'i' ranges from 1 to 2 * M.
        for(int i = 1; i <= 2 * maxPiles; i++) {
            
            // The core Minimax Game Theory logic:
            // 1. pref[pref.length - 1] - pref[ind] calculates the TOTAL stones currently left on the board.
            // 2. recurse(...) calculates the MAXIMUM stones the NEXT player will get from the remaining piles.
            // 3. We subtract the next player's optimal score from the total remaining stones to get OUR score for this choice.
            // 4. We update 'max' to keep track of the best possible choice we can make out of all valid 'i' values.
            max = Math.max(max, pref[pref.length - 1] - pref[ind] - recurse(ind + i, Math.max(i, maxPiles)));
        }
        
        // Save the best possible outcome for this state into the cache and return it.
        return dp[ind][maxPiles] = max;
    }
}
