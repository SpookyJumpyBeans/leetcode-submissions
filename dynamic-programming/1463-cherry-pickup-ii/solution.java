// 1463. Cherry Pickup II
// https://leetcode.com/problems/cherry-pickup-ii/
// Hard | Java | Accepted 2026-10-30
// Runtime 26 ms | Memory 47.8 MB

class Solution {
    int[][][] dp;
    int m;
    int n;
    public int cherryPickup(int[][] grid) {
        //We only have to keep track of 3 states:
        //The row and the columns each robot is at
        //Since each robot can only go down one row every step, we know they will always be on the same row (which is why we don't have to keep track of both rows)
        //We simply try every path combination for both robots and return the max value we get
        m = grid.length;
        n = grid[0].length;
        dp = new int[m][n][n]; //3D Cache
        for(int[][] dd : dp)
        {
            for(int[] d : dd)
            {
                Arrays.fill(d, -1);
            }
        }
        return recurse(grid, 0, 0, n-1); //Start the first robot at column 0 and the second at column n-1
    }

    public int recurse(int[][] g, int r, int c1, int c2)
    {
        if(c1<0 || c2<0 || c1>=n || c2>=n || r>=m) //If we hit out of bounds, return 0 (Returning 0 will guarantee this path will lose compared to any path that has actual values in it)
        {
            return 0;
        }
        if(dp[r][c1][c2]!=-1) //Check the cache
        {
            return dp[r][c1][c2];
        }
        int curr = c1 == c2 ? g[r][c1] : g[r][c1]+g[r][c2]; //If both robots hit the same cell, only add the cell once, else add both cells
        int[] d = {-1, 0, 1}; //The three possible directions for both robots
        int max = 0; //Max tracker for all paths
        for(int i = 0; i<d.length; i++)
        {
            for(int j = 0; j<d.length; j++)
            {  
                max = Math.max(max, curr+recurse(g, r+1, c1+d[i], c2+d[j])); //Try each path and see if it's larger than our current max
            }
        }
        return dp[r][c1][c2] = max; //Cache and return the max
    }
}
