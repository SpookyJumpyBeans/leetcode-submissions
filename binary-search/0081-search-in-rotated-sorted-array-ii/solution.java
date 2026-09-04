// 81. Search in Rotated Sorted Array II
// https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
// Medium | Java | Accepted 2026-09-04
// Runtime 0 ms | Memory 45.1 MB

class Solution {
    public boolean search(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while(start<=end)
        {
            int mid = start + (end-start)/2;
            if(nums[mid]==target)
            {
                return true;
            }
            if(nums[start]==nums[mid] && nums[end]==nums[mid])
            {
                start++; 
                end--;
                continue;
            }
            if(nums[start]<=nums[mid])
            {
                if(target>=nums[start] && target<nums[mid])
                {
                    end = mid-1;
                }
                else
                {
                    start = mid+1;
                }
            }
            else
            {
                if(target<=nums[end] && target>nums[mid])
                {
                    start = mid+1;
                }
                else
                {
                    end = mid-1;
                }
            }
        }
        return false;
    }
}
