// 329. Longest Increasing Path in a Matrix
// https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
// Hard | Java | Accepted 2026-08-10
// Runtime 8 ms | Memory 46.8 MB

class Solution {
    int[][] m;
    int[][] dp;
    public int longestIncreasingPath(int[][] matrix) {
        m = new int[matrix.length][matrix[0].length];
        dp = new int[matrix.length][matrix[0].length];
        for(int k = 0; k<matrix.length; k++)
        {
            for(int l = 0; l<matrix[0].length; l++)
            {
                m[k][l] = matrix[k][l];
                dp[k][l] = -1;
            }
        }
        int max = 0;
        for(int i = 0; i<matrix.length; i++)
        {
            for(int j = 0; j<matrix[0].length; j++)
            {
                max = Math.max(max, recurse(i, j)+1);
            }
        }
        return max;
    }

    public int recurse(int i, int j)
    {
        if(i<0 || j<0 || i>=m.length || j>=m[0].length)
        {
            return 0;
        }
        if(dp[i][j]!=-1)
        {
            return dp[i][j];
        }
        int path1 = 0;
        int path2 = 0;
        int path3 = 0;
        int path4 = 0;
        if(i+1<m.length && m[i+1][j]>m[i][j])
        {
            path1 = 1 + recurse(i+1, j);
        }
        if(j+1<m[0].length && m[i][j+1]>m[i][j])
        {
            path2 = 1 + recurse(i, j+1);
        }
        if(i-1>=0 && m[i-1][j]>m[i][j])
        {
            path3 = 1 + recurse(i-1, j);
        }
        if(j-1>=0 && m[i][j-1]>m[i][j])
        {
            path4 = 1 + recurse(i, j-1);
        }
        dp[i][j] = Math.max(path1, Math.max(path2, Math.max(path3, path4)));
        return dp[i][j];
    }
}
