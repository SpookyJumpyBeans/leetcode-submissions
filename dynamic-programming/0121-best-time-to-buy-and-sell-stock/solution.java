// 121. Best Time to Buy and Sell Stock
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
// Easy | Java | Accepted 2026-09-04
// Runtime 1 ms | Memory 94.6 MB

class Solution {
    public int maxProfit(int[] prices) {
        int maxProf = 0;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i<prices.length; i++)
        {
           min = Math.min(prices[i], min);
           maxProf = Math.max(prices[i]-min, maxProf);
        }
        return maxProf;
    }
}
