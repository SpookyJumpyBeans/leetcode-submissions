// 2267.  Check if There Is a Valid Parentheses String Path
// https://leetcode.com/problems/check-if-there-is-a-valid-parentheses-string-path/
// Hard | Java | Accepted 2026-09-29
// Runtime 130 ms | Memory 157.8 MB

class Solution {
    int[][][] dp;
    public boolean hasValidPath(char[][] grid) {
        //3D DP
        //Instead of tracking the string itself (since we don't know what path was taken to get to the current indices, so 2D isn't enough) track the balance (which is the absolute difference between the number of open and closed parentheses)
        //The balance needs to be greater than or equal to 0 for any path to be valid and needs to be 0 when reaching the bottom right cell for us to return true
        int r = grid.length;
        int c = grid[0].length;
        if(grid[0][0]==')' || grid[r-1][c-1]=='(') //If the starting cell or ending cell is invalid, return false immediately
        {
            return false;
        }
        dp = new int[r][c][r+c]; //Instantiate the DP
        return recurse(0, 0, 1, grid); //Recurse and return the result, the balance is initially 1 since every open parentheses we add 1 and every closed parentheses we subtract 1 and the starting cell must be open for any path to be explored
    }

    public boolean recurse(int i, int j, int balance, char[][] grid)
    {
        if(balance<0) //The second there are more closed parentheses than open, it's impossible from here on out to make a valid path, so return false immediately
        {
            return false;
        }
        if(balance==0 && i==grid.length-1 && j==grid[0].length-1) //If we reach the ending cell with a balance of 0, return true
        {
            return true;
        }
        if(dp[i][j][balance]!=0) //Check the cahce 
        //0 means uninitialized result, 1 means valid path, and 2 means invalid path
        {
            return dp[i][j][balance] == 1 ? true : false;
        }
        boolean res = false; //Set initial result to false
        if(i+1<grid.length) //If we can go right
        {
            res |= recurse(i+1, j, balance+(grid[i+1][j] == '(' ? 1 : -1), grid); //Or the result of the recursion after going right
            //Additionally, update the balance based on whether the right cell is an open/closed parentheses
        }
        if(j+1<grid[0].length)
        {
            res |= recurse(i, j+1, balance+(grid[i][j+1] == '(' ? 1 : -1), grid); //Same with going down
        }
        dp[i][j][balance] = res ? 1 : 2; //Check if we found a valid path and store it 
        return dp[i][j][balance] == 1 ? true : false; //Return whether we found a valid path or not
    }
}
