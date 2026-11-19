// 309. Best Time to Buy and Sell Stock with Cooldown
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
// Medium | Java | Accepted 2026-11-16
// Runtime 1 ms | Memory 43.3 MB

class Solution {
    int[][] dp;
    public int maxProfit(int[] prices) {
        dp = new int[prices.length][2];
        //0 is not holding a stock
        //1 is holding a stock
        //Need 2 states to keep track of the operations we can do when we arrive at an index
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        return recurse(prices, 0, 0);
    }
    public int recurse(int[] prices, int ind, int type)
    {
        if(ind>=prices.length) //Base case if we reach the end of the array, return 0
        {
            return 0;
        }
        if(dp[ind][type]!=-1) //Check the cache
        {
            return dp[ind][type];
        }
        int max = 0; //Max variable set at 0 incase we have negative profit
        if(type==0) //If we currently aren't holding any stock
        {
            int buy = recurse(prices, ind+1, 1) - prices[ind]; //Buy and subtract the current cost of the stock from the recursive call since buying means losing money
            int skip = recurse(prices, ind+1, 0); //Skip 
            max = Math.max(max, Math.max(buy, skip)); //Get the max of the two cases
        }
        else if(type==1) //If we currently are holding a stock
        {
            int sell = recurse(prices, ind+2, 0) + prices[ind]; //If we sell, we add the price of the stock to the recursive call since that calculates the net difference of a buy/sell and whether we made profit
            //We then increment the index by 2 to simulate the cooldown
            int skip = recurse(prices, ind+1, 1); //Skip 
            max = Math.max(max, Math.max(sell, skip));
        }
        return dp[ind][type] = max; //Cache the max and return
    }
}
