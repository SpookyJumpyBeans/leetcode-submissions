// 128. Longest Consecutive Sequence
// https://leetcode.com/problems/longest-consecutive-sequence/
// Medium | Java | Accepted 2024-09-06
// Runtime 561 ms | Memory 56.3 MB

class Solution {
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);
        int max = 1;
        int temp = 0;
        if(nums.length==0)
        {
            return 0;
        }
        for(int i = 0; i<nums.length-1; i++)
        {
            System.out.println(temp);
            if((nums[i]+1==nums[i+1]&&temp==0))
            {
                temp+=2;
            }
            else if(nums[i]+1==nums[i+1])
            {
                temp++;
            }
            else if(nums[i+1]==nums[i])
            {
                temp+=0;
            }
            else
            {
                max = Math.max(max, temp);
                temp = 0;
            }
        }
        return Math.max(max, temp);
    }
}
