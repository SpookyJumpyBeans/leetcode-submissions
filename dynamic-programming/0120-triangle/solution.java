// 120. Triangle
// https://leetcode.com/problems/triangle/
// Medium | Java | Accepted 2026-09-17
// Runtime 1 ms | Memory 45.5 MB

class Solution {
    int[][] dp;
    List<List<Integer>> tri;
    public int minimumTotal(List<List<Integer>> triangle) {
        tri = triangle;
        //Use DP to find the minimum costing route
        //Since we have to keep track of what index we're currently on and also what level of the triangle we're on, use 2D DP
        dp = new int[triangle.size()][triangle.size()]; 
        for(int[] d : dp)
        {
            Arrays.fill(d, -100000); //Fill with -100000 since the min value used is -10000
        }
        return recurse(0, 0); //Recurse starting at the peak (ind = 0 and level is 0)
    }

    public int recurse(int ind, int curr)
    {
        if(curr==tri.size()) //If we reached the last level, return 0
        {
            return 0;
        }
        if(dp[ind][curr]!=-100000) //If we've already visited this index + level combo, return what's stored in the cache
        {
            return dp[ind][curr];
        }
        return dp[ind][curr] = Math.min(tri.get(curr).get(ind) + recurse(ind+1, curr+1), tri.get(curr).get(ind)+ recurse(ind, curr+1)); //Else, get the min of adding the current value at ind and curr with the result of recursing with ind+1 and ind with the next level and store it in the cache
    }
}
