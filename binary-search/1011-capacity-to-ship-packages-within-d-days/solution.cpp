// 1011. Capacity To Ship Packages Within D Days
// https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
    vector<int> w;
    int d;
public:
    int shipWithinDays(vector<int>& weights, int days) {
        w = weights;
        d = days;
        int l = -1;
        int r = 0;
        for(int i : weights)
        {
            l = max(i, l);
            r+=i;
        }
        while(l<r)
        {
            int mid = (l+r)/2;
            if(canCarg(mid))
            {
                r = mid;
            }
            else
            {
                l = mid+1;
            }
        }
        return l;
    }
    bool canCarg(int temp)
    {
        int count = 1;
        int add = 0;
        for(int weight : w)
        {
            if(add+weight>temp)
            {
                count++;
                add = weight;
            }
            else
            {
                add+=weight;
            }
        }
        return count <= d;
    }
};
