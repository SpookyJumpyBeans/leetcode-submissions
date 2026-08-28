// 53. Maximum Subarray
// https://leetcode.com/problems/maximum-subarray/
// Medium | C++ | Accepted 2022-10-06
// Runtime 340 ms | Memory 67.8 MB

class Solution {
public:
    int maxSubArray(vector<int>& nums) {
        int currentMax = nums[0];
        int actualMax = nums[0];
        for(int i = 1; i<nums.size(); i++)
        {
            currentMax = max(nums[i],nums[i] + currentMax);
            if(currentMax>actualMax)
            {
                actualMax = currentMax;
            }
        }
        return actualMax;
    }
};
