// 77. Combinations
// https://leetcode.com/problems/combinations/
// Medium | C++ | Accepted 2026-07-16
// Runtime 74 ms | Memory 82.5 MB

class Solution {
public:
    vector<vector<int>> res;
    int nn;
    int kk;
    vector<vector<int>> combine(int n, int k) {
        nn = n;
        kk = k;
        vector<int> vec;
        backtrack(vec, 1);
        return res;
    }

    void backtrack(vector<int>& temp, int i)
    {
        if(temp.size()==kk)
        {
            res.push_back(temp);
            return;
        }
        for(int j = i; j<=nn; j++)
        {
            temp.push_back(j);
            backtrack(temp, j+1);
            temp.pop_back();
        }
    }
};
