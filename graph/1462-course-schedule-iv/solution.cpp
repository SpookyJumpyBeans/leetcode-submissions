// 1462. Course Schedule IV
// https://leetcode.com/problems/course-schedule-iv/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<bool> checkIfPrerequisite(int numCourses, vector<vector<int>>& prerequisites, vector<vector<int>>& queries) {
        vector<vector<int>> graph(numCourses);
        for(auto t : prerequisites)
        {
            graph[t[0]].push_back(t[1]);
        }
        vector<bool> ans;
        for(auto t : queries)
        {
            queue<int> track;
            track.push(t[0]);
            while(!track.empty())
            {
                int tt = track.front();
                track.pop();
                if(graph[tt].size()==0)
                {
                    ans.push_back(false);
                    break;
                }
                for(int ttt : graph[tt])
                {
                    if(ttt==t[1])
                    {
                        cout << ttt;
                    ans.push_back(true);
                    track = queue<int>();
                    break;
                    }
                    track.push(ttt);
                }
            }
        }
        return ans;
    }
};
