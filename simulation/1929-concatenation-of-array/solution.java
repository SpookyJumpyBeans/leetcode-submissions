// 1929. Concatenation of Array
// https://leetcode.com/problems/concatenation-of-array/
// Easy | Java | Accepted 2026-09-01
// Runtime 1 ms | Memory 47.1 MB

class Solution {
    public int[] getConcatenation(int[] nums) {
        int[] doub = new int[nums.length*2];
        for(int i = 0; i<doub.length; i++)
        {
            doub[i] = nums[i%nums.length];
        }
        return doub;
    }
}
