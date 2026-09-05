// 912. Sort an Array
// https://leetcode.com/problems/sort-an-array/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<int> num;
    vector<int> sortArray(vector<int>& nums) {
        num = nums;
        sort(0, num.size()); 
        return num;
    }

    void sort(int start, int end)
    {
        if(start>=end-1)
        {
            return;
        }
        int i = start-1;
        for(int j = start; j<end-1; j++)
        {
            if(num[j]<=num[end-1])
            {
                i++;
                int temp = num[i];
                num[i] = num[j];
                num[j] = temp;
            }
        }
        int temp = num[i+1];
        num[i+1] = num[end-1];
        num[end-1] = temp;
        sort(start, i+1);
        sort(i+2,end);
    }
};
