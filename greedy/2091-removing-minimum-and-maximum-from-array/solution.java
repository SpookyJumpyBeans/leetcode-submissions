// 2091. Removing Minimum and Maximum From Array
// https://leetcode.com/problems/removing-minimum-and-maximum-from-array/
// Medium | Java | Accepted 2026-08-30
// Runtime 2 ms | Memory 86.5 MB

class Solution {
    public int minimumDeletions(int[] nums) {
        int min = Integer.MAX_VALUE;
        int minInd = 0;
        int max = Integer.MIN_VALUE;
        int maxInd = 0;
        for(int i = 0; i<nums.length; i++)
        {
            if(nums[i]<min)
            {
                min = nums[i];
                minInd = i;
            }
            if(nums[i]>max)
            {
                max = nums[i];
                maxInd = i;
            }
        }
        int frontOnly = Math.max(minInd, maxInd)+1;
        int backOnly = nums.length-Math.min(minInd, maxInd);
        int frontAndBack = Math.min(minInd, maxInd)+1+(nums.length-Math.max(minInd, maxInd));
        return Math.min(backOnly, Math.min(frontOnly, frontAndBack));
    }
}
