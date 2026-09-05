// 1834. Single-Threaded CPU
// https://leetcode.com/problems/single-threaded-cpu/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<int> getOrder(vector<vector<int>>& tasks) {
      for(int i = 0; i<tasks.size(); i++)
      {
        tasks[i].push_back(i);
      }
      sort(tasks.begin(), tasks.end());
      priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
      long long currT = 0;
      int ind = 0;
      vector<int> ans;
      while(ind<tasks.size() || !pq.empty())
      {
        while(ind<tasks.size() && tasks[ind][0]<=currT)
        {
            pq.push(pair<int, int>{tasks[ind][1], tasks[ind][2]});
            ind++;
        }
        if(!pq.empty())
        {
            pair<int, int> temp = pq.top();
            pq.pop();
            ans.push_back(temp.second);
            currT+=temp.first;
        }
        else if(pq.empty() && ind<tasks.size())
        {
            currT = tasks[ind][0];
        }
      }
        return ans;
    }
};
