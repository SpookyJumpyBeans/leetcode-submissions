// 189. Rotate Array
// https://leetcode.com/problems/rotate-array/
// Medium | Java | Accepted 2026-08-18
// Runtime 11 ms | Memory 275.4 MB

class Solution {
    public void rotate(int[] nums, int k) {
        int[] doub = new int[nums.length*2];
        for(int i = 0; i<doub.length; i++)
        {
            doub[i] = nums[i%nums.length];
        }
        int ind = 0;
        for(int i = nums.length-(k%nums.length); i<2*nums.length-(k%nums.length); i++)
        {
            nums[ind] = doub[i];
            ind++;
        }
    }
}
