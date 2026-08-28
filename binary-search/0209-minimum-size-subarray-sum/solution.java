// 209. Minimum Size Subarray Sum
// https://leetcode.com/problems/minimum-size-subarray-sum/
// Medium | Java | Accepted 2025-09-23
// Runtime 51 ms | Memory 57 MB

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int min = 1000000;
        int l = 0;
        int r = 0;
        int sum = 0;
        while(r<nums.length)
        {
            sum+=nums[r];
            if(sum>=target)
            {
                while(sum-nums[l]>=target)
                {
                    sum-=nums[l];
                    l++;
                }
                System.out.println(r-l+1 + " " + min);
                min = Math.min(r-l+1, min);
            }
            r++;
        }
        if(min==1000000)
        {
            return 0;
        }
        return min;
        }
    }
