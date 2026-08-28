// 918. Maximum Sum Circular Subarray
// https://leetcode.com/problems/maximum-sum-circular-subarray/
// Medium | Java | Accepted 2026-08-18
// Runtime 5 ms | Memory 51.4 MB

class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int[] twice = new int[nums.length*2];
        int total = 0;
        for(int i = 0; i<twice.length; i++)
        {
            twice[i] = nums[i%nums.length];
            if(i<nums.length)
            {
                total+=nums[i];
            }
        }
        int max = twice[0];
        int min = twice[0];
        int currMax = twice[0];
        int currMin = twice[0];
        for(int i = 1; i<nums.length; i++)
        {
                currMax = Math.max(twice[i], currMax+twice[i]);
                currMin = Math.min(twice[i], currMin+twice[i]);
                max = Math.max(max, currMax);
                min = Math.min(min, currMin);
        }
        if(min==total)
        {
            return max;
        }
        min = total - min;
        return Math.max(max, min);
    }
}
