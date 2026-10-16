// 2778. Sum of Squares of Special Elements 
// https://leetcode.com/problems/sum-of-squares-of-special-elements/
// Easy | Java | Accepted 2026-10-14
// Runtime 2 ms | Memory 46 MB

class Solution {
    public int sumOfSquares(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for(int i = 0; i<nums.length; i++)
        {
            if(n%(i+1)==0)
            {
                sum+=nums[i]*nums[i];
            }
        }
        return sum;
    }
}
