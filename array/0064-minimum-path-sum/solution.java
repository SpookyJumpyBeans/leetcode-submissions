// 64. Minimum Path Sum
// https://leetcode.com/problems/minimum-path-sum/
// Medium | Java | Accepted 2026-08-15
// Runtime 4 ms | Memory 50.2 MB

class Solution {
    int[][] g;
    int[][] dp;
    public int minPathSum(int[][] grid) {
        g = new int[grid.length][grid[0].length];
        dp = new int[grid.length][grid[0].length];
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                g[i][j] = grid[i][j];
                dp[i][j] = -1;
            }
        }
        return recurse(0, 0);
    }
    public int recurse(int i, int j)
    {
        if(i<0 || j<0 || i>=g.length || j>=g[0].length)
        {
            return 0;
        }
        if(i==g.length-1 && j==g[0].length-1)
        {
            return g[i][j];
        }
        if(dp[i][j]!=-1)
        {
            return dp[i][j];
        }
        int right = Integer.MAX_VALUE;
        int down = Integer.MAX_VALUE;
        if(j+1<g[0].length)
        {
            right = g[i][j]+recurse(i, j+1);
        }
        if(i+1<g.length)
        {
            down = g[i][j]+recurse(i+1, j);
        }
        dp[i][j] = Math.min(right, down);
        return dp[i][j];
    }
}
