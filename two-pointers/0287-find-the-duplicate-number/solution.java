// 287. Find the Duplicate Number
// https://leetcode.com/problems/find-the-duplicate-number/
// Medium | Java | Accepted 2025-11-12
// Runtime 55 ms | Memory 79.1 MB

class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0; i<nums.length; i++)
        {
            int find = nums[i];
            int start = 0;
            int end = nums.length-1;
            int mid = (start+end)/2;
            while(start<=end)
            {
                mid = (start+end)/2;
                if(nums[mid]==find)
                {
                    break;
                }
                else if(nums[mid]<find)
                {
                    start = mid+1;
                }
                else 
                {
                    end = mid-1;
                }
            }
            if((mid-1>=0 && nums[mid-1]==find) || (mid+1 <nums.length && nums[mid+1]==find))
            {
                return find;
            }
        }
        return -1;
    }
}
