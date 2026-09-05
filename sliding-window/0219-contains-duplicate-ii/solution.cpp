// 219. Contains Duplicate II
// https://leetcode.com/problems/contains-duplicate-ii/
// Easy | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    bool containsNearbyDuplicate(vector<int>& nums, int k) {
        unordered_set<int> seen;
        for(int i = 0; i<nums.size(); i++)
        {
            if(i>k)
            {
                seen.erase(nums[i-k-1]);
            }
            if(seen.contains(nums[i]))
            {
                return true;
            }
            seen.insert(nums[i]);
        }
        return false;
    }
};
