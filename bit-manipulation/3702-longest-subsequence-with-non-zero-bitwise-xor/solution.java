// 3702. Longest Subsequence With Non-Zero Bitwise XOR
// https://leetcode.com/problems/longest-subsequence-with-non-zero-bitwise-xor/
// Medium | Java | Accepted 2026-08-15
// Runtime 2 ms | Memory 133.7 MB

class Solution {
    public int longestSubsequence(int[] nums) {
        int sol = nums[0]; 
        int zero = 0; //We can mathematically prove that if the entire nums array contains just zeroes, the answer will always be 0. If there is at least one element that's nonzero, then the answer will be nums.length-1
        if(nums[0]==0) 
        {
            zero++; //Count the first zero
        }
        for(int i = 1; i<nums.length; i++)
        {
            sol ^= nums[i]; //XOR all the elements in nums together
            if(nums[i]==0)
            {
                zero++; //Count all zeroes
            }
        }
        if(sol!=0) //If the XOR of all elements isn't 0, the longest subsequence is the entire array
        {
            return nums.length;
        }
        if(zero==nums.length) //If all elements are zero, the answer is 0
        {
            return 0;
        }
            return nums.length-1; //Else, return nums.length-1
    }
}
