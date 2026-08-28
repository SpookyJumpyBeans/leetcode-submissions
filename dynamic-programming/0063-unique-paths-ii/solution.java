// 63. Unique Paths II
// https://leetcode.com/problems/unique-paths-ii/
// Medium | Java | Accepted 2026-08-28
// Runtime 0 ms | Memory 43.7 MB

class Solution {
    int[][] dp;
    int[][] grid;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        grid = obstacleGrid;
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        if(obstacleGrid[0][0]==1)
        {
            return 0;
        }
        dp = new int[m][n];
        for(int i = 0; i<m; i++)
        {
            for(int j = 0; j<n; j++)
            {
                dp[i][j] = -1;
            }
        }
        return recurse(0, 0);
    }

    public int recurse(int i, int j)
    {
        if(i==grid.length-1 && j==grid[0].length-1)
        {
            return 1;
        }
        if(dp[i][j]!=-1)
        {
            return dp[i][j];
        }
        int track = 0;
        if(i+1<grid.length && grid[i+1][j]!=1)
        {
            track+=recurse(i+1, j);
        }
        if(j+1<grid[0].length && grid[i][j+1]!=1)
        {
            track+=recurse(i, j+1);
        }
        return dp[i][j] = track;
    }
}
