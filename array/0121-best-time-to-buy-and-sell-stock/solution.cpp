// 121. Best Time to Buy and Sell Stock
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
// Easy | C++ | Accepted 2026-08-04
// Runtime 6 ms | Memory 97.3 MB

class Solution {
public:
    int maxProfit(vector<int>& prices) {
        int maxx = -1;
        int minn = 1000000;
        int maxP = 0;
        for(int i = 0; i<prices.size(); i++)
        {
            if(minn!=1000000 && maxx!=-1)
            {
                maxP = max(maxP, maxx-minn);
            }
            maxx = max(maxx, prices[i]);
            if(prices[i]<minn)
            {
            minn = min(minn, prices[i]);
            maxx = -1;
            }
        }
        return max(maxx-minn, maxP);
    }
};
