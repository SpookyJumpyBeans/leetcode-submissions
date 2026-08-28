// 33. Search in Rotated Sorted Array
// https://leetcode.com/problems/search-in-rotated-sorted-array/
// Medium | Java | Accepted 2025-11-04
// Runtime 0 ms | Memory 43 MB

class Solution {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        int max = 0;
        int minIndex = 0;
        int[] normalArr = new int[nums.length];
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
        int rot = start;
        start = 0;
        end = nums.length-1;
        while(start<=end)
        {
            int mid = (start+end)/2;
            int realmid = (mid+rot)%nums.length;
            if(nums[realmid]==target)
            {

                    return realmid;
            }
            else if(nums[realmid]>target)
            {
                end = mid-1;
            }
            else
            {
                start = mid+1;
            }
        }
        return -1;
    }
}
