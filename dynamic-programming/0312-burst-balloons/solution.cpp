// 312. Burst Balloons
// https://leetcode.com/problems/burst-balloons/
// Hard | C++ | Accepted on NeetCode 2026-09-04

class Solution {
    vector<int> num;
    vector<vector<int>> dp;
public:
    int maxCoins(vector<int>& nums) {
        num = nums;
        num.insert(num.begin(), 1);
        num.insert(num.end(), 1);
        dp.assign(num.size(), vector<int>(num.size(), -1));
        return recurse(1, num.size()-2);
    }

    int recurse(int l, int r)
    {
        if(l>r)
        {
            return 0;
        }
        if(dp[l][r]!=-1)
        {
            return dp[l][r];
        }
        int maxx = 0;
        for(int i = l; i<=r; i++)
        {
            int left = recurse(l, i-1);
            int right = recurse(i+1, r);
            int local = num[l-1] * num[i] * num[r+1];
            int total = left + local + right;
            maxx = max(maxx, total);
        }
        return dp[l][r] = maxx;
    }
};
