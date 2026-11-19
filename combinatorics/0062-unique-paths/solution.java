// 62. Unique Paths
// https://leetcode.com/problems/unique-paths/
// Medium | Java | Accepted 2026-11-15
// Runtime 0 ms | Memory 41.8 MB

class Solution {
    int[][] dp;
    int m;
    int n;
    public int uniquePaths(int m, int n) {
        dp = new int[m][n];
        this.m = m-1;
        this.n = n-1;
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        return recurse(0, 0);
    }

    public int recurse(int i, int j)
    {
        if(i==m && j==n)
        {
            return 1;
        }
        if(i<0 || j<0 || i>m || j>n)
        {
            return 0;
        }
        if(dp[i][j]!=-1)
        {
            return dp[i][j];
        }
        int sum = recurse(i+1, j) + recurse(i, j+1);
        return dp[i][j] = sum;
    }
}
