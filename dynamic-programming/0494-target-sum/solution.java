// 494. Target Sum
// https://leetcode.com/problems/target-sum/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    int[][] dp;
    int targ;
    int maxx;
    int[] num;
    public int findTargetSumWays(int[] nums, int target) {
        num = nums;
        targ = target;
        int max = 0;
        for(int k = 0; k<nums.length; k++)
        {
            max+=nums[k];
        }
        maxx = max;
        dp = new int[nums.length][2*max+1];
        for(int i = 0; i<dp.length; i++)
        {
            for(int j = 0; j<dp[0].length; j++)
            {
                dp[i][j] = -1;
            }
        }
        return recurse(0, 0);
    }

    public int recurse(int index, int sum)
    {
        if(index==num.length)
        {
            if(sum==targ)
            {
                return 1;
            }
            return 0;
        }
        if(dp[index][sum+maxx]!=-1)
        {
            return dp[index][sum+maxx];
        }
        int results = recurse(index+1, sum+num[index]) + recurse(index+1, sum-num[index]);
        dp[index][sum+maxx] = results;
        return dp[index][sum+maxx];
    }
}
