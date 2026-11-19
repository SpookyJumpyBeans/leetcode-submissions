// 2219. Maximum Sum Score of Array
// https://leetcode.com/problems/maximum-sum-score-of-array/
// Medium | Java | Accepted 2026-11-14
// Runtime 2 ms | Memory 66.9 MB

class Solution {
    public long maximumSumScore(int[] nums) {
        long sum = 0;
        for(int i : nums)
        {
            sum+=i;
        }
        //Sliding window solution
        //Keep track of the window of first i+1 elements of num
        //Keep track of the window of last n-1 elements of num
        long max = Long.MIN_VALUE;
        int p = 0;
        long curr = 0;
        while(p<nums.length)
        {
            curr+=nums[p];           
            max = Math.max(max, Math.max(curr, sum));
            sum-=nums[p];
            p++;
        }
        return max;
    }
}
