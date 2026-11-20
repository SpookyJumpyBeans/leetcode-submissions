// 2567. Minimum Score by Changing Two Elements
// https://leetcode.com/problems/minimum-score-by-changing-two-elements/
// Medium | Java | Accepted 2026-11-19
// Runtime 19 ms | Memory 58.9 MB

class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort(nums);
        //Just like the changing 3 elements question
        //First sort the array
        //Either make the first 3 elements the same, the last 3 elements the same, or the first 2 elements and the last 2 elements the same
        return Math.min(nums[nums.length-1]-nums[2], Math.min(nums[nums.length-2] - nums[1], nums[nums.length-3] - nums[0]));
    }
}
