// 213. House Robber II
// https://leetcode.com/problems/house-robber-ii/
// Medium | Java | Accepted 2026-08-09
// Runtime 0 ms | Memory 42.4 MB

class Solution {
    public int rob(int[] nums) {
        if(nums.length==1)
        {
            return nums[0];
        }
        if(nums.length==2)
        {
            return Math.max(nums[0], nums[1]);
        }
        int[] dp = new int[nums.length-1];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for(int i = 2; i<nums.length-1; i++)
        {
            dp[i] = Math.max(dp[i-2] + nums[i], dp[i-1]);
        }
        int[] dp2 = new int[nums.length-1];
        dp2[0] = nums[1];
        dp2[1] = Math.max(nums[1], nums[2]);
        for(int j = 2; j<nums.length-1; j++)
        {
            if(j+1>=nums.length)
            {
                break;
            }
            dp2[j] = Math.max(dp2[j-2] + nums[j+1], dp2[j-1]);
        }
        return Math.max(dp[dp.length-1], dp2[dp2.length-1]);
    }
}
