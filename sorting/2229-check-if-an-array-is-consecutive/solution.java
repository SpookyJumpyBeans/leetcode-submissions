// 2229. Check if an Array Is Consecutive
// https://leetcode.com/problems/check-if-an-array-is-consecutive/
// Easy | Java | Accepted 2026-10-21
// Runtime 33 ms | Memory 83.6 MB

class Solution {
    public boolean isConsecutive(int[] nums) {
        Arrays.sort(nums);
        int prev = nums[0]-1;
        for(int i : nums)
        {
            if(i-1!=prev)
            {
                return false;
            }
            prev = i;
        }
        return true;
    }
}
