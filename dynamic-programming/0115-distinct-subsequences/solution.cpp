// 115. Distinct Subsequences
// https://leetcode.com/problems/distinct-subsequences/
// Hard | C++ | Accepted on NeetCode 2026-09-04

class Solution {
    string string1;
    string string2;
    vector<vector<int>> memo;
public:
    int numDistinct(string s, string t) {
        string1 = s;
        string2 = t;
        memo.assign(s.length(), vector<int>(t.length(), -1));
        return recurse(0, 0);
    }
    int recurse(int ind1, int ind2)
    {
        if(ind2>=string2.length())
        {
            return 1;
        }
        if(ind1>=string1.length())
        {
            return 0;
        }
        if(memo[ind1][ind2]>-1)
        {
            return memo[ind1][ind2];
        }
        if(string1[ind1]!=string2[ind2])
        {
            memo[ind1][ind2] = recurse(ind1+1, ind2);
            return memo[ind1][ind2];
        }
        else
        {
            int skip = recurse(ind1+1, ind2);
            int take = recurse(ind1+1, ind2+1);
            memo[ind1][ind2] = skip+take;
        }
        return memo[ind1][ind2];
    }
};
