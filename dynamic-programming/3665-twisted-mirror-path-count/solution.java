// 3665. Twisted Mirror Path Count
// https://leetcode.com/problems/twisted-mirror-path-count/
// Medium | Java | Accepted 2026-11-01
// Runtime 73 ms | Memory 283.7 MB

class Solution {
    int[][] dp;
    public int uniquePaths(int[][] grid) {
        //Intuition:
        //Use standard DP and traverse the grid
        //If we hit a mirror, find where the ending cell is (what non mirror cell the mirrors ultimately reflect us to)
        //Recurse from there
        dp = new int[grid.length][grid[0].length];
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        return recurse(grid, 0, 0);
    }

    public int recurse(int[][] grid, int i, int j)
    {
        if(i<0 || j<0 || i>=grid.length || j>=grid[0].length) //If out of bounds, return 0
        {
            return 0;
        }
        if(i==grid.length-1 && j==grid[0].length-1) //If we hit the target bottom right cell, return 1 since it's a valid path
        { 
            return 1;
        }
        if(dp[i][j]!=-1) //Check cache
        {
            return dp[i][j];
        }
        int totVal = 0;
        int[] down = nextCoor(grid, i+1, j, 0); //Get the indices we end up at if we try going down and right 
        int[] right = nextCoor(grid, i, j+1, 1);
        totVal+=(recurse(grid, down[0], down[1])+recurse(grid, right[0], right[1]))%1000000007; //Recurse from there 
        return dp[i][j] = totVal;
    }

    public int[] nextCoor(int[][] grid, int i, int j, int dir)
    {
        while(i>=0 && j>=0 && i<grid.length && j<grid[0].length) //While the cell we're in has a value of 1 (it means we're getting reflected to another cell)
        {
            if(grid[i][j]==0) //If we reach a non mirror cell, return the coordinates immediately
            {
                return new int[]{i, j};
            }
            if(dir==0) //Else, based on whether we entered this cell going down or right, reflect the path differently
            {
                if(grid[i][j]==1)
                {
                    j++;
                    dir = 1;
                }
            }
            else if(dir==1)
            {
                if(grid[i][j]==1)
                {
                    i++;
                    dir = 0;
                }
            }
        }
        return new int[]{i, j}; //This condition is only hit if we go out of bounds (our recursion cases handle out of bounds params so this is fine)
    }
}
