// 1062. Longest Repeating Substring
// https://leetcode.com/problems/longest-repeating-substring/
// Medium | Java | Accepted 2026-10-25
// Runtime 37 ms | Memory 54.8 MB

class Solution {
    /* 
     * INTUITION: Substring DP vs Subsequence DP
     * 
     * In a typical Subsequence DP, the recursive function acts as a "Searcher". 
     * If characters don't match, it branches (e.g., skip i or skip j) to keep looking 
     * for matches later in the string. A single call to recurse(0, 0) searches everything.
     * 
     * In a Substring DP (like this one), characters must be strictly contiguous. 
     * This means the recursive function can only act as a "Measurer". If characters 
     * don't match, the contiguous streak is instantly dead, and it must return 0. 
     * Because the recursion cannot "skip" ahead to find new starting points, we must 
     * wrap it in nested for-loops to manually test every possible valid starting pair (i, j).
     * 
     * dp[i][j] stores the length of the longest matching contiguous sequence 
     * that starts exactly at index i and index j.
     */

    int[][] dp;
    
    public int longestRepeatingSubstring(String s) {
        // Initialize memoization table
        dp = new int[s.length()][s.length()];
        for(int[] d : dp) {
            Arrays.fill(d, -1);
        }
        
        int max = 0;
        
        // The "Searcher": manually test every possible pair of starting indices.
        // We set j = i + 1 to ensure we are comparing two distinct starting positions.
        for(int i = 0; i < s.length(); i++) {
            for(int j = i + 1; j < s.length(); j++) {
                // Keep track of the maximum length found across all starting pairs
                max = Math.max(recurse(s, i, j), max);
            }
        }
        
        return max;
    }
    
    // The "Measurer": How long do the strings match if we start exactly at i and j?
    public int recurse(String s, int i, int j) {
       // Base case 1: Out of bounds OR characters do not match.
       // Because it's a substring, a mismatch means the streak is broken instantly.
       if(j >= s.length() || s.charAt(i) != s.charAt(j)) {
            return 0;
       }
       
       // Base case 2: Return already computed result to avoid redundant work
       if(dp[i][j] != -1) {
            return dp[i][j];
       }
       
       int count = 0;
       
       // If the characters match, add 1 to the streak and move both pointers forward
       if(s.charAt(i) == s.charAt(j)) {
            count = 1 + recurse(s, i + 1, j + 1);
       }
       
       // Memoize and return the measured length
       return dp[i][j] = count;
    }
}
