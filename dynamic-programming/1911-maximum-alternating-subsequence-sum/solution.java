// 1911. Maximum Alternating Subsequence Sum
// https://leetcode.com/problems/maximum-alternating-subsequence-sum/
// Medium | Java | Accepted 2026-10-04
// Runtime 64 ms | Memory 119.1 MB

class Solution {
    // WHY A 2D ARRAY INSTEAD OF 1D?
    // In DP, your memoization table needs to capture the entire "state" of your current decision.
    // Here, the maximum sum you can get starting from a specific index depends on TWO things:
    // 1. The current index ('ind') in the array.
    // 2. Whether the next element you take acts as an "even" index (add to sum) or an "odd" index (subtract from sum).
    // If you used a 1D array, you would only store the result for the index. If you reached index 'i' 
    // needing to ADD, but the 1D array had saved a previous result where you reached index 'i' needing 
    // to SUBTRACT, you'd pull the wrong answer. The 2D array separates these two distinct realities.
    long[][] dp;

    public long maxAlternatingSum(int[] nums) {
        // Initialize the 2D array. 
        // dp[i][0] will store the max sum from index 'i' when we need to SUBTRACT the next picked element.
        // dp[i][1] will store the max sum from index 'i' when we need to ADD the next picked element.
        dp = new long[nums.length][2];
        
        // Fill the memoization table with -1 to indicate uncalculated states
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < 2; j++) {
                dp[i][j] = -1;
            }
        }
        
        // Start at index 0. 'false' indicates our first picked element acts as an "even" 
        // position in our subsequence, meaning it will be ADDED to our sum.
        return recurse(nums, 0, false);
    }

    public long recurse(int[] nums, int ind, boolean evenOdd) {
        // Base case: If we have reached the end of the array, there are no more numbers to add.
        if(ind == nums.length) {
            return 0;
        }
        
        // Check memoization: If this exact state (index + add/subtract flag) has been calculated before, return it.
        // We use a ternary operator to map the boolean to our array's 2nd dimension (true -> 0, false -> 1).
        if(dp[ind][evenOdd ? 0 : 1] != -1) {
            return dp[ind][evenOdd ? 0 : 1];
        }
        
        // OPTION 1: TAKE the current element.
        // If evenOdd is true, we act as an odd index, so we subtract nums[ind] and flip the flag for the next call.
        // If evenOdd is false, we act as an even index, so we add nums[ind] and flip the flag for the next call.
        long take = evenOdd ? recurse(nums, ind + 1, !evenOdd) - nums[ind] 
                            : nums[ind] + recurse(nums, ind + 1, !evenOdd);
                            
        // OPTION 2: SKIP the current element.
        // We move to the next index but keep the evenOdd flag exactly the same, 
        // because we haven't consumed our turn to add/subtract yet.
        long skip = recurse(nums, ind + 1, evenOdd);
        
        // Record the maximum of both choices in our DP table and return it.
        return dp[ind][evenOdd ? 0 : 1] = Math.max(take, skip);
    }
}
