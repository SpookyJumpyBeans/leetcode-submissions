// 997. Find the Town Judge
// https://leetcode.com/problems/find-the-town-judge/
// Easy | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    int findJudge(int n, vector<vector<int>>& trust) {
        map<int, list<int>> graph;
        set<int> keys;
        for(vector<int> temp : trust)
        {
            if(!keys.contains(temp[0]))
            {
            graph[temp[1]].push_back(temp[0]);
            keys.insert(temp[0]);
            }
        }
        if(graph.size()==1)
        {
            auto [judge, people] = *(graph.begin());
            return judge;
        }
        return -1;
    }
};
