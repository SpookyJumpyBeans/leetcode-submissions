// 3550. Smallest Index With Digit Sum Equal to Index
// https://leetcode.com/problems/smallest-index-with-digit-sum-equal-to-index/
// Easy | Java | Accepted 2026-09-24
// Runtime 1 ms | Memory 45.7 MB

class Solution {
    public int smallestIndex(int[] nums) {
        int count = 0;
        for(int i = 0; i<nums.length; i++)
        {
            int num = nums[i];
            int sum = 0;
            while(num>0)
            {
                sum+=num%10;
                num/=10;
            }
            if(sum==i)
            {  
                return i;
            }
        }
        return -1;
    }
}
