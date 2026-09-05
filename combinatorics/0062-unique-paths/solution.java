// 62. Unique Paths
// https://leetcode.com/problems/unique-paths/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public int uniquePaths(int m, int n) {
        int[][] ans = new int[m][n];
        ans[0][0] = 1;
        for(int i = 0; i<m; i++)
        {
            for(int j = 0; j<n; j++)
            {
                if(i-1<0 && j-1>=0)
                {
                    ans[i][j] = ans[i][j-1];
                }
                if(j-1<0 && i-1>=0)
                {
                    ans[i][j] = ans[i-1][j];
                }   
                else if(j-1>=0 && i-1>=0)
                {
                    ans[i][j] = ans[i-1][j] + ans[i][j-1];
                }
            }
        }
        return ans[m-1][n-1];
    }
}
