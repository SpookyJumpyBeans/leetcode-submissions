// 121. Best Time to Buy and Sell Stock
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
// Easy | Java | Accepted 2025-12-19
// Runtime 2 ms | Memory 94.7 MB

class Solution {
    public int maxProfit(int[] prices) {
        int start = prices[0];
        int maxProf = 0;
        int ans = 0;
        for(int i = 1; i<prices.length; i++)
        {
            if(prices[i]-start>maxProf)
            {
                maxProf = prices[i]-start;
            }
            if(prices[i]-start<0)
            {
                start = prices[i]; 
            }
            ans = Math.max(maxProf, 0);
        }
        return ans;
    }
}
