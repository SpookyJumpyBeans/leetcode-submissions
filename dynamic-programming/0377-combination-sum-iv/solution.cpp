// 377. Combination Sum IV
// https://leetcode.com/problems/combination-sum-iv/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
vector<int> arr;
vector<int> dp;
int targ;
    int combinationSum4(vector<int>& nums, int target) {
        arr = nums;
        targ = target;
        dp.assign(target+1, -1);
       return recurse(targ);
    }

    int recurse(int left)
    {
        if(left<0)
        {
            return 0;
        }
        if(dp[left]!=-1)
        {
            return dp[left];
        }
        if(left==0)
        {
            return 1;
        }
        int locWays = 0;
        int temp = left;
        for(int i = 0; i<arr.size(); i++)
        {
            temp-=arr[i];
            locWays+=recurse(temp);
            temp+=arr[i];
        }
        dp[left] = locWays;
        return dp[left];
    }
};
