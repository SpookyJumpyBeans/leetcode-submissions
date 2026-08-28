// 15. 3Sum
// https://leetcode.com/problems/3sum/
// Medium | C++ | Accepted 2026-07-31
// Runtime 47 ms | Memory 29 MB

class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        vector<vector<int>> sol;
        sort(nums.begin(), nums.end());
        int k = nums.size()-1;
        for(int i = 0; i<nums.size()-2; i++)
        {
            if(i>0 && nums[i-1]==nums[i])
            {
                continue;
            }
            int j = i+1;
            k = nums.size()-1;
            while(j<k)
            {
                if(nums[i] + nums[j] + nums[k]>0)
                {
                    k--;
                }
                else if(nums[i]+nums[j]+nums[k]<0)
                {
                    j++;
                }
                else 
                {
                    sol.push_back(vector<int>{nums[i], nums[j], nums[k]});
                    while(j<k && j+1<nums.size() && nums[j+1]==nums[j])
                    {
                        j++;
                    }
                    j++;
                    while(j<k && k>i && nums[k-1] == nums[k])
                    {
                        k--;
                    }
                    k--;
                }
            }
        }
        return sol;
    }
};
