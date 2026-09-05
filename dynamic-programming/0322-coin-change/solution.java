// 322. Coin Change
// https://leetcode.com/problems/coin-change/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        for(int k = 1; k<dp.length; k++)
        {
            dp[k] = amount+1;
        }
        dp[0] = 0;
        for(int i = 1; i<=amount; i++)
        {
            for(int j = 0; j<coins.length; j++)
            {
                if(coins[j]<=i)
                {
                    dp[i] = Math.min(dp[i], 1+dp[i-coins[j]]);
                }
            }
        }
        return dp[amount] < amount+1 ? dp[amount] : -1;
    }
}
