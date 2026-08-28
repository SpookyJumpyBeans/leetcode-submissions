// 136. Single Number
// https://leetcode.com/problems/single-number/
// Easy | Java | Accepted 2026-01-07
// Runtime 1 ms | Memory 46.9 MB

class Solution {
    public int singleNumber(int[] nums) {
        int xor = 0;
        for(int i = 0; i<nums.length; i++)
        {
           xor^=nums[i];
        }
        return xor;
    }
}
