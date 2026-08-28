// 2246. Longest Path With Different Adjacent Characters
// https://leetcode.com/problems/longest-path-with-different-adjacent-characters/
// Hard | C++ | Accepted 2026-08-04
// Runtime 251 ms | Memory 205.5 MB

class Solution {
public:
    vector<vector<int>> graphh;
    string p;
    int ans;
    int longestPath(vector<int>& parent, string s) {
        vector<vector<int>> graph(s.size());
        for(int i = 0; i<parent.size(); i++)
        {
            if(parent[i]!=-1)
            {
                graph[parent[i]].push_back(i);
            }
        }
        graphh = graph;
        p = s;
        dfs(0);
        return ans;
    }

    int dfs(int node)
    {
        int longest = 0;
        int secondLongest = 0;
        for(int child : graphh[node])
        {
            int pathLen = dfs(child);
            if(p[child]!=p[node])
            {
                if(pathLen>longest)
                {
                    secondLongest = longest;
                    longest = pathLen;
                }
                else if(pathLen>secondLongest)
                {
                    secondLongest = pathLen;
                }
            }
        }
        ans = max(ans, longest+secondLongest+1);
        return longest+1;
    }
};
