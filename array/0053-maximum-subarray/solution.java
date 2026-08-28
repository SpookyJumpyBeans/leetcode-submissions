// 53. Maximum Subarray
// https://leetcode.com/problems/maximum-subarray/
// Medium | Java | Accepted 2026-01-12
// Runtime 1 ms | Memory 80.4 MB

class Solution {
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int count = 0;
        for(int i = 0; i<nums.length; i++)
        {
            count+=nums[i];
            max = Math.max(count, max);
            if(count<0)
            {
                count = 0;
            }
        }
        return max;
    }
}
