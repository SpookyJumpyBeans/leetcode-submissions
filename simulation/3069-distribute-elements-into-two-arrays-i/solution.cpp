// 3069. Distribute Elements Into Two Arrays I
// https://leetcode.com/problems/distribute-elements-into-two-arrays-i/
// Easy | C++ | Accepted 2026-08-21
// Runtime 0 ms | Memory 23.6 MB

class Solution {
public:
    vector<int> resultArray(vector<int>& nums) {
        vector<int> arr1;
        vector<int> arr2;
        for(int i = 0; i<nums.size(); i++)
        {
            if(i==0)
            {
                arr1.push_back(nums[i]);
            }
            else if(i==1)
            {
                arr2.push_back(nums[i]);
            }
            else
            {
                arr1.back()>arr2.back() ? arr1.push_back(nums[i]) : arr2.push_back(nums[i]);
            }
        }
        arr1.insert(arr1.end(), arr2.begin(), arr2.end());
        return arr1;
    }
};
