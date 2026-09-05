// 300. Longest Increasing Subsequence
// https://leetcode.com/problems/longest-increasing-subsequence/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    int[] num;
    int[] valid;
    int maxx = 0;
    public int lengthOfLIS(int[] nums) {
        num = nums;
        valid = new int[nums.length];
        for(int i = 0; i<nums.length; i++)
        {
            maxx=Math.max(maxx,recurse(i));
        }
        return maxx;
    }

    public int recurse(int curr)
    {
        int max = 1;
        for(int i = curr+1; i<num.length; i++)
        {
            if(num[curr]<num[i])
            {
            max = Math.max(1 + recurse(i), max);
            }
        }
        return max;
    }
}
