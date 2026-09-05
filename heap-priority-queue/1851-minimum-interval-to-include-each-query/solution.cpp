// 1851. Minimum Interval to Include Each Query
// https://leetcode.com/problems/minimum-interval-to-include-each-query/
// Hard | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<int> minInterval(vector<vector<int>>& intervals, vector<int>& queries) {
        sort(intervals.begin(), intervals.end());
        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minPQ;
        vector<int> ans(queries.size());
        vector<pair<int, int>> sortedQ;
        int i = 0;
        for(int temp1 : queries)
        {
            sortedQ.push_back({temp1, i});
            i++;
        }
        sort(sortedQ.begin(), sortedQ.end());
        int pointer = 0;
        for(pair temp : sortedQ)
        {
            while(pointer < intervals.size() && intervals[pointer][0]<=temp.first)
            {
                minPQ.push({intervals[pointer][1] - intervals[pointer][0]+1, intervals[pointer][1]});
                pointer++;
            }
            while(!minPQ.empty() && minPQ.top().second<temp.first)
            {
                minPQ.pop();
            }
            if(minPQ.empty())
            {
                ans[temp.second] = -1;
            }
            else
            {
                ans[temp.second] = minPQ.top().first;
            }
        }
        return ans;
    }
};
