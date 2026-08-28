// 238. Product of Array Except Self
// https://leetcode.com/problems/product-of-array-except-self/
// Medium | Java | Accepted 2024-09-05
// Runtime 2 ms | Memory 55.2 MB

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        int num0 = 0;
        for(int k = 0; k<nums.length; k++)
        {
            if(nums[k]==0)
            {
                num0++;
            }
        }
        int prod = 1;
        if(num0>1)
        {
            prod = 0;
        }
        boolean bruh = false;
        for(int i = 0; i<nums.length; i++)
        {
            if(nums[i]==0)
            {
                bruh = true;
            }
            else
            {
            prod*=nums[i];
            }
        }
        for(int j = 0; j<nums.length; j++)
        {
            if(bruh && nums[j]==0)
            {
                ans[j] = prod;
            }
            else if(bruh && nums[j]!=0)
            {
                ans[j] = 0;
            }
            else
            {
                ans[j] = prod/nums[j];
            }
        }
        return ans;
    }
}
