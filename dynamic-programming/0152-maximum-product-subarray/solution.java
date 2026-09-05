// 152. Maximum Product Subarray
// https://leetcode.com/problems/maximum-product-subarray/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public int maxProduct(int[] nums) {
        int ans = nums[0];
        int tempMax = nums[0];
        int tempMin = nums[0];
        for(int i = 1; i<nums.length; i++)
        {
                int prevMax = tempMax;
                tempMax = Math.max(nums[i], Math.max(prevMax*nums[i], tempMin*nums[i]));
                tempMin = Math.min(nums[i], Math.min(prevMax*nums[i], tempMin*nums[i]));
                ans = Math.max(tempMax, ans);
        }
        return ans;
    }
}
