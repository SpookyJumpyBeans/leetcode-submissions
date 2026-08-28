// 268. Missing Number
// https://leetcode.com/problems/missing-number/
// Easy | Java | Accepted 2026-01-08
// Runtime 0 ms | Memory 47.2 MB

class Solution {
    public int missingNumber(int[] nums) {
        int[] numsFull = new int[nums.length+1];
        int xorNums = 0;
        int xorFull = 0;
        for(int i = 0; i<numsFull.length; i++)
        {
            numsFull[i] = i;
            if(i<nums.length)
            {
                xorNums ^=nums[i];
            }
            xorFull ^= numsFull[i];
        }
        return xorNums ^ xorFull;

    }
}
