// 1480. Running Sum of 1d Array
// https://leetcode.com/problems/running-sum-of-1d-array/
// Easy | Java | Accepted 2022-08-20
// Runtime 0 ms | Memory 42.8 MB

class Solution {
    public int[] runningSum(int[] nums) {
        int[] joe = new int[nums.length];
        for(int i = 0; i<nums.length; i++)
        { 
            if(i==0)
            {
                joe[i] = nums[i];
            }
            else
            {
                joe[i] = nums[i] + nums[i-1];
                nums[i] = joe[i];
            }
        }
        return joe;
    }
}
