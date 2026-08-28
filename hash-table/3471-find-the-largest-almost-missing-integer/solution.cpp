// 3471. Find the Largest Almost Missing Integer
// https://leetcode.com/problems/find-the-largest-almost-missing-integer/
// Easy | C++ | Accepted 2026-08-18
// Runtime 0 ms | Memory 28.7 MB

class Solution {
public:
    int largestInteger(vector<int>& nums, int k) {
       bool flagS = false;
       bool flagE = false;
       int start = nums[0];
       int end = nums[nums.size()-1];
       if(k==1)
       {
         vector<int> counts(51);
         for(int i : nums)
         {
            counts[i]++;
         }
         int max = -1;
         for(int i = 0; i<counts.size(); i++)
         {
            if(counts[i]==1)
            {
                max = i;
            }
         }
         return max;
       }
       if(nums.size() ==k)
       {
        int m = 0;
        for(int i : nums)
        {
            m = max(i, m);
        }
        return m;
       }
       if(start==end)
       {
        return -1;
       }
       for(int i = 1; i<nums.size()-1; i++)
       {
        if(nums[i]==start)
        {
            flagS = true;
        }
        if(nums[i]==end)
        {
            flagE = true;
        }
        if(flagE&&flagS)
        {
            return -1;
        }
       }
       if(!flagS && !flagE)
       {
               return end > start ? end : start;
       }
       if(!flagS && flagE)
       {
            return start;
       }
       return end;
    }
};
