// 10. Regular Expression Matching
// https://leetcode.com/problems/regular-expression-matching/
// Hard | C++ | Accepted on NeetCode 2026-09-04

class Solution {
    string st;
    string pp; 
    vector<vector<int>> dp;
public:
    bool isMatch(string s, string p) {
        st = s;
        pp = p;
        dp.assign(s.length()+1, vector<int>(p.length()+1, -1));
        int res = recurse(0, 0);
        return res == 1 ? true : false;
    }

    int recurse(int i, int j)
    {
        if(j==pp.length())
        {
            return i==st.length() ? 1 : 0;
        }
        if(dp[i][j]!=-1)
        {
            return dp[i][j];
        }
         if(j+1<pp.length() && pp[j+1]=='*')
        {

           int skip = recurse(i, j+2);
           int match = 0;
           if(i<st.length() && (st[i]==pp[j]||pp[j]=='.'))
           {
            match = recurse(i+1, j);
           }
           if(skip == 1 || match == 1)
           {
            dp[i][j] = 1;
           }
           else
           {
            dp[i][j] = 0;
           }
            return dp[i][j];
        }
        if(i<st.length() &&( st[i]==pp[j]||pp[j]=='.'))
        {
           return dp[i][j] = recurse(i+1, j+1);
        }
        return dp[i][j] = 0;
    }
};
