// 26. Remove Duplicates from Sorted Array
// https://leetcode.com/problems/remove-duplicates-from-sorted-array/
// Easy | Java | Accepted 2026-09-01
// Runtime 1 ms | Memory 47 MB

class Solution {
    public int removeDuplicates(int[] nums) {
      int j = 0;
      for(int i = j+1; i<nums.length; i++)
      {
        if(nums[i]!=nums[j])
        {
            j++;
            nums[j] = nums[i];
        }
      }
      return j+1;
    }
}
