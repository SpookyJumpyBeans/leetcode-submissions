// 35. Search Insert Position
// https://leetcode.com/problems/search-insert-position/
// Easy | Java | Accepted 2026-08-25
// Runtime 0 ms | Memory 44.8 MB

class Solution {
    public int searchInsert(int[] nums, int target) {
        int l = 0;
        int r = nums.length-1;
        while(l<=r)
        {
            int mid = l + (r-l)/2;
            if(nums[mid]==target)
            {
                return mid;
            }
            else if(nums[mid]<target)
            {
                l = mid+1;
            }
            else
            {
                r = mid-1;
            }
        }
        return l;
    }
}
