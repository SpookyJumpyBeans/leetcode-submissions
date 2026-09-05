// 767. Reorganize String
// https://leetcode.com/problems/reorganize-string/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    string reorganizeString(string s) {
        priority_queue<pair<int, char>> pq;
        vector<int> count(26);
        for(char c : s)
        {
            count[c-'a']++;
        }
        for(int i = 0; i<count.size(); i++)
        {
            if(count[i]>(s.size()+1)/2)
            {
                return "";
            }
            if(count[i]>0)
            {
                pq.push(pair<int, char>{count[i], i+'a'});
            }
        }
        string ans;
        while(!pq.empty())
        {
                pair<int, char> t1 = pq.top();
                cout << t1.first;
                pq.pop();
                ans+=t1.second;
                t1.first--;
                if(!pq.empty())
                {
                pair<int, char> t2 = pq.top();
                cout<<t2.first;
                pq.pop();
                ans+=t2.second;
                t2.first--;
                 if(t2.first>0)
                {
                    pq.push(t2);
                }
                }
                if(t1.first>0)
                {
                    pq.push(t1);
                
                }
        }
        return ans;
    }
};
