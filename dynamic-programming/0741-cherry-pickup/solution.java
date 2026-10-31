// 741. Cherry Pickup
// https://leetcode.com/problems/cherry-pickup/
// Hard | Java | Accepted 2026-10-30
// Runtime 16 ms | Memory 46.7 MB

class Solution {
    int[][][] dp;
    int m;
    int n;
    public int cherryPickup(int[][] grid) {
        //Intuition:
        //Can only go down or right (backtracking not needed)
        //Imagine simulating 2 people going from the top left grid to the bottom right grid
        //Add whatever value is in the cell (1 or 0) to the count and then try all 4 combinations for the 2 people to go next (Both people go down, Both people go right, P1 goes down/P2 goes right and vice versa)
        //Instead of keeping track of all 4 indices, you can just keep track of 3 and find the 4th one through algebraic isolation
        //DP cache keeps track of Person 1's x and y values and Person 2's x value. Person 2's y value is found by subtracting Person 2's x value from the summation of Person 1's x and y values. 
        //This is due to the fact that since person 1 and person 2 take the exact same number of steps at all times, so the sum of their indices will be the same
        //Try all 4 directions and if both people land on the same cell, only add that cell once, otherwise, add the cell value both people landed on
        m = grid.length; 
        n = grid[0].length;
        dp = new int[m][n][m]; //3D DP Cache
        for(int[][] d : dp)
        {
            for(int[] dd : d)
            {
                Arrays.fill(dd, -1);
            }
        }
        int res = recurse(grid, 0, 0, 0);
        return res < 0 ? 0 : res; //In the case where we never reach the bottom right cell, the resulting value we get from the dp function will be highly negative, so return 0. Else, return what we got.
    }

    public int recurse(int[][] grid, int r1, int c1, int r2)
    {
        int c2 = r1+c1-r2; //Calculate Person 2's y value
        if(r1<0 || c1<0 || r2<0 || c2<0 || r1>=m || r2>=m || c1>=n || c2>=n) //If the indices are out of bounds for either person, return a very small number (since we want to get the maximum a small number will ensure any out of bounds paths are neglected) 
        {
            return -9999;
        }
        if(grid[r1][c1]==-1||grid[r2][c2]==-1) //Same for paths we can't go on due to thorns
        {
            return -9999;
        }
        if(r1==m-1 && c1==n-1) //If both people arrive at the end (they must arrive at the end at the exact same time)
        {
            return grid[r1][c1]; //Return whatever value is in the bottom right cell (this is our return condition for success)
        }
        if(dp[r1][c1][r2]!=-1) //Check cache
        {
            return dp[r1][c1][r2];
        }
        int sum = 0; //This represents the value we add to the current path before embarking on the next 4 possibilities
        if(r1==r2&&c1==c2) //If the current cell is shared by both people, only add this cell's value once
        {
            sum+=grid[r1][c1];
        }
        else
        {
            sum+=grid[r1][c1]+grid[r2][c2]; //Otherwise add both cells' values
        } 
        int d1 = sum +  recurse(grid, r1+1, c1, r2+1); //Then explore all 4 directions
        int d2 = sum + recurse(grid, r1+1, c1, r2);
        int d3 = sum + recurse(grid, r1, c1+1, r2+1);
        int d4 = sum + recurse(grid, r1, c1+1, r2);
        return dp[r1][c1][r2] = Math.max(d1, Math.max(d2, Math.max(d3, d4))); //Get the maximum of the 4 directions
    }
}
