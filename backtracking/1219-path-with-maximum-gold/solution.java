// 1219. Path with Maximum Gold
// https://leetcode.com/problems/path-with-maximum-gold/
// Medium | Java | Accepted 2026-09-29
// Runtime 35 ms | Memory 43.1 MB

class Solution {
    public int getMaximumGold(int[][] grid) {
        //Backtracking solution
        //Go through the grid and start backtracking at any cell that isn't 0 to find the max gold in a path
        int maxGold = 0;
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]!=0) //If the cell has gold, start backtracking
                {
                    maxGold = Math.max(maxGold, recurse(i, j, grid)); //Backtracking will return the max gold for a path starting at this cell
                }
            }
        }
        return maxGold;
    }

    public int recurse(int i, int j, int[][] grid)
    {
        int max = grid[i][j]; //Global max tracker for a path
        int temp = grid[i][j]; //This is for restoring after backtracking
        grid[i][j] = 0; //Mark this cell as visited
        if(i+1<grid.length && grid[i+1][j]!=0) //Go all 4 directions only if that cell has gold
        {
            max = Math.max(max, temp + recurse(i+1, j, grid)); //See if going in this direction produces more gold compared to the current max we have
        }
        if(i-1>=0 && grid[i-1][j]!=0)
        {
           max = Math.max(max, temp + recurse(i-1, j, grid));
        }
        if(j+1<grid[0].length && grid[i][j+1]!=0)
        {
            max = Math.max(max, temp + recurse(i, j+1, grid));
        }
        if(j-1>=0 && grid[i][j-1]!=0)
        {
            max = Math.max(max, temp + recurse(i, j-1, grid));
        }
        grid[i][j] = temp; //Backtrack
        return max; //Return the max of the results of going in the 4 directions
    }
}
