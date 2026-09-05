// 72. Edit Distance
// https://leetcode.com/problems/edit-distance/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
    
vector<vector<int>> dp;
string word1;
string word2;
public:
    int minDistance(string word1, string word2) {
        int m = word1.length();
        int n = word2.length();
        dp.assign(m+1, vector<int>(n+1, -1));
        this->word1 = word1;
        this->word2 = word2;
        return recurse(0, 0);
    }

    int recurse(int w1, int w2)
    {
        if(dp[w1][w2]!=-1)
        {
            return dp[w1][w2];
        }
        if(w1==word1.length())
        {
            return word2.length()-w2;
        }
        if(w2==word2.length())
        {
            return word1.length()-w1;
        }
        if(word1[w1] == word2[w2])
        {
            dp[w1][w2] = recurse(w1+1, w2+1);
            return dp[w1][w2];
        }
        else
        {
            int incr = recurse(w1, w2+1);
            int del = recurse(w1+1, w2);
            int rep = recurse(w1+1, w2+1);
            dp[w1][w2] = 1+min({incr, del, rep});
        }
        return dp[w1][w2];
    }
};
