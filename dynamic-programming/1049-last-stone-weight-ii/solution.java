// 1049. Last Stone Weight II
// https://leetcode.com/problems/last-stone-weight-ii/
// Medium | Java | Accepted 2026-09-06
// Runtime 2 ms | Memory 44.5 MB

class Solution {
    int[][] dp;
    int[] s;
    int target = 0;
    public int lastStoneWeightII(int[] stones) {
        //The intuition is basically using the same concept from Stone Game, where we are calculating the absolute difference
        //We can just put the stones in two big groups and return the result of the subtraction of these two groups instead of simulating the smashing at every step 
        //To get the minimum score, we want both groups to be as close to the total sum of all stones in the array divided by 2
        s = stones;
        for(int i : stones) //First find the total sum of all stones
        {
            target+=i;
        }
        dp = new int[stones.length][target+1]; //Make the 2d cache
        //This stores the maximum score we can generate that is <= target/2 using the stones from index to the end of the array
        int total = target;
        for(int i = 0; i<dp.length; i++)
        {
            Arrays.fill(dp[i], -1);
        }
        target/=2; //The target is the total sum/2
        int ans = recurse(0, 0); //Maximize one group's sum to be as close to total sum/2 as possible
        return total-2*ans; //This is the simplified form of the true subtraction of group 1 and group 2
        //Group 1 is the result of the recursion 
        //Group 2 is the result of subtracting the sum of group 1 from the total sum
        //Group 2 - Group 1 = Total Sum - recurse(0,0) - recurse(0,0) = Total Sum - 2*recurse(0,0)
    }

    public int recurse(int ind, int curr)
    {
        if(ind>=s.length) //If the index is out of bounds, return 0 since there's nothing to add
        {
            return 0;
        }
        if(dp[ind][curr]!=-1) //Use the cache
        {
            return dp[ind][curr];
        }
        int skip = recurse(ind+1, curr); //Skip this stone
        int take = Integer.MIN_VALUE; 
        if(curr+s[ind]<=target) //Add this stone to the current sum if adding it will result in a current sum that's <= target
        {
            take = s[ind] + recurse(ind+1, curr+s[ind]);
        }
        int max = Math.max(skip, take); //Take the max of skipping/taking the stone at the current index
        //Since we have the check to ensure all sums will be <= target, taking the max gives us the closest group of stones we can compile to the total sum/2
        return dp[ind][curr] = max;
    }
}
