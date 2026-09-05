// 73. Set Matrix Zeroes
// https://leetcode.com/problems/set-matrix-zeroes/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    void setZeroes(vector<vector<int>>& matrix) {
        vector<bool> rows(matrix.size(), false);
        vector<bool> col(matrix[0].size(), false);
        for(int i = 0; i<matrix.size(); i++)
        {
            for(int j = 0; j<matrix[0].size(); j++)
            {
                if(matrix[i][j]==0)
                {
                    rows[i] = true;
                    col[j] = true;
                }
            }
        }
        for(int k = 0; k<matrix.size(); k++)
        {
            for(int l = 0; l<matrix[0].size(); l++)
            {
                if(rows[k] || col[l])
                {
                    cout << k << " " << l << "\n";
                    matrix[k][l] = 0;
                }
            }
        }
    }
};
