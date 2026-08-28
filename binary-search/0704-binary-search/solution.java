// 704. Binary Search
// https://leetcode.com/problems/binary-search/
// Easy | Java | Accepted 2025-10-29
// Runtime 0 ms | Memory 46 MB

class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length-1;
        int idx = -1;
        while(l<=r)
        {
            int mid = (l+r)/2;
            if(nums[mid]==target)
            {
                idx = mid;
                break;
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
        return idx;
    }
}
