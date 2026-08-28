// 746. Min Cost Climbing Stairs
// https://leetcode.com/problems/min-cost-climbing-stairs/
// Easy | Java | Accepted 2026-01-10
// Runtime 0 ms | Memory 45.2 MB

class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length+1];
        dp[cost.length] = 0;
        for(int i = cost.length-1; i>=0; i--)
        {
            if(i==cost.length-1 || i==cost.length-2)
            {
                dp[i] = cost[i];
            }
            else
            {
            dp[i] = cost[i] + Math.min(dp[i+1], dp[i+2]);
            }
        }
        return Math.min(dp[0], dp[1]);
    }
}
