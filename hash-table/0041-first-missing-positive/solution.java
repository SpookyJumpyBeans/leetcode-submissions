// 41. First Missing Positive
// https://leetcode.com/problems/first-missing-positive/
// Hard | Java | Accepted 2026-09-03
// Runtime 21 ms | Memory 76.6 MB

class Solution {
    public int firstMissingPositive(int[] nums) {
        int count = 0;
        for(int i = 0; i<nums.length; i++)
        {
            if(nums[i]>0)
            {
                count++;
            }
        }
        int[] nonNeg = new int[count];
        int ind = 0;
        for(int i = 0; i<nums.length; i++)
        {
            if(nums[i]>0)
            {
                nonNeg[ind] = nums[i];
                ind++;
            }
        }
        Arrays.sort(nonNeg);
        int start = 1;
        for(int i = 0; i<nonNeg.length; i++)
        {
            if(nonNeg[i]!=start)
            {
                return start;
            }
            else
            {
                while(i+1<nonNeg.length && nonNeg[i+1]==start)
                {
                    i++;
                }
                start++;
            }
        }
        return start;
    }
}
