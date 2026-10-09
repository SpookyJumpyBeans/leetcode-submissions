// 221. Maximal Square
// https://leetcode.com/problems/maximal-square/
// Medium | Java | Accepted 2026-10-08
// Runtime 11 ms | Memory 70.6 MB

class Solution {
    int[][] dp;
    public int maximalSquare(char[][] matrix) {
        dp = new int[matrix.length][matrix[0].length];
        for(int[] r : dp)
        {
            Arrays.fill(r, -1);
        }
        int max = 0;
        for(int i = 0; i<matrix.length; i++)
        {
            for(int j = 0; j<matrix[0].length; j++)
            {
                if(matrix[i][j]=='1')
                {
                    max = Math.max(max, recurse(matrix, i, j));
                }
            }
        }
        return max*max;
    }
    public int recurse(char[][] m, int i, int j)
    {
        if(dp[i][j]!=-1)
        {
            return dp[i][j];
        }
        int left = 0;
        int up = 0;
        int diag = 0;
        if(i-1>=0 && m[i-1][j]=='1')
        {
            up = recurse(m, i-1, j);
        }
        if(j-1>=0 && m[i][j-1]=='1')
        {
            left = recurse(m, i, j-1);
        }
        if(i-1>=0 && j-1>=0 && m[i-1][j-1]=='1')
        {
            diag = recurse(m, i-1, j-1);
        }
        return dp[i][j] = Math.min(left, Math.min(up, diag))+1;
    }
}
