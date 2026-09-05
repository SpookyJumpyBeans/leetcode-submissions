// 435. Non-overlapping Intervals
// https://leetcode.com/problems/non-overlapping-intervals/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    int eraseOverlapIntervals(vector<vector<int>>& intervals) {
        sort(intervals.begin(), intervals.end());
        int ind = 0;
        int prevEnd = intervals[0][1];
        int remove = 0;
        for(vector<int> temp : intervals)
        {
            cout << temp[1];
            if(ind==0)
            {
                ind++;
                continue;
            }
            else
            {
                if(temp[0]<prevEnd)
                {
                    cout << remove;
                    prevEnd = min(prevEnd, temp[1]);
                    remove++;
                }
                else
                {
                    prevEnd = temp[1];
                }
            }
        }
        return remove;
    }
};
