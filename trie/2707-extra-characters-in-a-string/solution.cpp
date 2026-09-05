// 2707. Extra Characters in a String
// https://leetcode.com/problems/extra-characters-in-a-string/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
    unordered_set<string> dict;
    vector<int> dp;
    string ss;
public:
    int minExtraChar(string s, vector<string>& dictionary) {
       dict = unordered_set<string>(dictionary.begin(), dictionary.end());
        dp = vector<int>(s.size(), -1);
        ss = s;
        return recurse(0);
    }
    int recurse(int ind)
    {
        if(ind>=ss.size())
        {
            return 0;
        }
        if(dp[ind]!=-1)
        {
            return dp[ind];
        }
        int m = 1 + recurse(ind+1);
        for(int j = ind; j<ss.size(); j++)
        {
            string temp = ss.substr(ind, j-ind+1);
            if(dict.contains(temp))
            {
                m = min(m, recurse(j+1));
            }
        }
        dp[ind] = m;
        return dp[ind];
    }
};
