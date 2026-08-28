// 153. Find Minimum in Rotated Sorted Array
// https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
// Medium | Java | Accepted 2025-11-04
// Runtime 0 ms | Memory 43.8 MB

class Solution {
    public int findMin(int[] nums) {
        int start = 0;
        int end = nums.length-1;
        int max = 0;
        while(start<end)
        {
            int mid = (start+end)/2;
            if(nums[mid]>nums[end])
            {
                start = mid+1;
            }
            else
            {
                end = mid;
            }
        }
        return nums[(start+end)/2];
    }
}
