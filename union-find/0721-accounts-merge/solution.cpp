// 721. Accounts Merge
// https://leetcode.com/problems/accounts-merge/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<vector<string>> accountsMerge(vector<vector<string>>& accounts) {
        unordered_map<string, vector<string>> graph;
        unordered_map<string, string> emailName;
        for(vector<string> a : accounts)
        {
            string name = a[0];
            string first = a[1];
            emailName[first] = name;
            for(int i = 2; i<a.size(); i++)
            {
                graph[first].push_back(a[i]);
                graph[a[i]].push_back(first);
                emailName[a[i]] = name;
            }
        }
        unordered_set<string> visited;
        vector<vector<string>> ans;
        queue<string> bfs;
        for(const auto& pairs : emailName)
        {
            string email = pairs.first;
            string name = pairs.second;
            if(!visited.contains(email))
            {
                vector<string> merged;
                bfs.push(email);
                while(!bfs.empty())
                {
                     visited.insert(bfs.front());
                    vector<string> neighbors = graph[bfs.front()];
                    merged.push_back(bfs.front());
                    bfs.pop();
                    for(string t : neighbors)
                    {
                        if(!visited.contains(t))
                        {
                            visited.insert(t);
                            bfs.push(t);
                        }
                    }
                }
                sort(merged.begin(), merged.end());
                merged.insert(merged.begin(), name);
                ans.push_back(merged);
            }
        }
        return ans;
    }
};
