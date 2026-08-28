// 674. Longest Continuous Increasing Subsequence
// https://leetcode.com/problems/longest-continuous-increasing-subsequence/
// Easy | Java | Accepted 2026-08-24
// Runtime 1 ms | Memory 46.9 MB

class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int len = 1;
        int ans = 1;
        for(int i = 1; i<nums.length; i++)
        {
            if(nums[i-1]<nums[i])
            {
                len++;
                ans = Math.max(ans, len);
            }
            else
            {
                len = 1;
            }
        }
        return ans;
    }
}
