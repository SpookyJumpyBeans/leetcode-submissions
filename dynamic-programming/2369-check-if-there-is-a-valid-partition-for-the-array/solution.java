// 2369. Check if There is a Valid Partition For The Array
// https://leetcode.com/problems/check-if-there-is-a-valid-partition-for-the-array/
// Medium | Java | Accepted 2026-10-02
// Runtime 8 ms | Memory 97 MB

class Solution {
    // dp array to store memoized results. 0 = unvisited, 1 = true, 2 = false
    /*A 1D state is sufficient because future valid partitions are entirely independent of past partition sizes.

    Initial logic and parameters are validated: reaching index i with a valid prefix effectively resets the problem constraints. Standard processing applies to the remaining suffix of the array based strictly on the values at nums[i], nums[i+1], and nums[i+2]. 2D states are exclusively reserved for problems where past decisions explicitly restrict future capacities or operations (e.g., remaining knapsack weight or a limited number of moves), which does not occur in this array partitioning logic. 
    */
    int[] dp;
    
    public boolean validPartition(int[] nums) {
        dp = new int[nums.length];
        return recurse(0, nums);
    }

    public boolean recurse(int ind, int[] nums)
    {
        // Base case: If we successfully reach the end of the array, the partition is valid
        if(ind>=nums.length)
        {
            return true;
        }
        
        // Memoization check: Return previously computed result for this index
        if(dp[ind]!=0)
        {
            return dp[ind]==1 ? true : false;
        }
        
        boolean possible = false;
        
        // Condition 1: 2 equal elements
        if(ind+1<nums.length && nums[ind+1]==nums[ind])
        {
            possible |= recurse(ind+2, nums);
        }
        
        // Condition 2: 3 equal elements
        if(ind+1<nums.length && ind+2<nums.length && nums[ind+2]==nums[ind+1] && nums[ind+1]==nums[ind])
        {
            possible |= recurse(ind+3, nums);
        }
        
        // Condition 3: 3 consecutive increasing elements
        if(ind+1<nums.length && ind+2<nums.length && nums[ind+2]-1==nums[ind+1] && nums[ind+1]-1==nums[ind])
        {
            possible |= recurse(ind+3, nums);
        }
        
        // Store the result in dp array (1 for true, 2 for false) and return
        dp[ind] = possible ? 1 : 2;
        return possible;
    }
}
