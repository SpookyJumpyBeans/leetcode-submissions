// 18. 4Sum
// https://leetcode.com/problems/4sum/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<vector<int>> fourSum(vector<int>& nums, int target) {
        if(nums.size()<4)
        {
            return vector<vector<int>>();
        }
        vector<vector<int>> sol;
        sort(nums.begin(), nums.end());
        for(int start = 0; start<nums.size()-3; start++)
        {
            if(start>0 && nums[start]==nums[start-1])
            {
                continue;
            }
            for(int start1 = start+1; start1<nums.size()-2; start1++)
            {
                if(start1>start+1 && nums[start1]==nums[start1-1])
                {
                    continue;
                }
            int i = start1+1;
            int j = nums.size()-1;
            while(i<j)
            {
                long long sum = (long long)nums[start]+nums[start1]+nums[i]+nums[j];
            if(sum>target)
            {
                j--;
            }
            else if(sum<target)
            {
                i++;
            }
            else
            {
                sol.push_back(vector<int>{nums[i], nums[j], nums[start], nums[start1]});
                while(i+1<j && nums[i]==nums[i+1])
                {
                    i++;
                }
                i++;
                while(j-1>i && nums[j]==nums[j-1])
                {
                    j--;
                }
                j--;
            }
            }
            }
        }
        return sol;
    }
};
