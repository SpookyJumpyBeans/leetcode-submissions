// 3459. Length of Longest V-Shaped Diagonal Segment
// https://leetcode.com/problems/length-of-longest-v-shaped-diagonal-segment/
// Hard | Java | Accepted 2026-11-04
// Runtime 479 ms | Memory 285 MB

class Solution {
    int[][][][] dp;
    int[][] direc = {{1, 1}, {-1, -1}, {1, -1}, { -1, 1}};
    //2 to 1 (1, -1) to (-1, -1)
    //1 to 3 (-1, -1) to (-1, 1)
    //3 to 0(-1, 1) to (1, 1)
    //0 to 2(1, 1) to (1, -1)
    int m;
    int n;
    public int lenOfVDiagonal(int[][] grid) {
        //Directions: 0 (TL to BR), 1 (BR to TL), 2 (TR to BL), and 3 (BL to TR)
        //2 options at every cell, either make a 90 degree clockwise turn or go in the same direction
        //The DP state needs 4 different values to distinguish between states
        //The indices i and j as well as the current direction we took to get to this cell as well as whether we've made a 90 degree turn yet
        m = grid.length;
        n = grid[0].length;
        dp = new int[m][n][4][2]; //4 Directions to go in and 1 possible turn available
        for(int[][][] ddd : dp)
        {
            for(int[][] dd : ddd)
            {
                for(int[] d : dd)
                {
                    Arrays.fill(d, -1);
                } 
            }
        }
        int max = 0;
        for(int i = 0; i<m; i++)
        {
            for(int j = 0; j<n; j++)
            {
                if(grid[i][j]==1) //If we hit a 1
                {
                    for(int k = 0; k<4; k++) //Go in the 4 possible directions starting at this 1
                    {
                        max = Math.max(max, recurse(grid, i, j, k, 1, 1)); //Find the max of every possible v segment
                    }
                }
            }
        }
        return max;
    }

    public int recurse(int[][] grid, int i, int j, int dir, int turns, int next)
    {
        if(turns<0 || i<0 || i>=m || j<0 || j>=n) //If we run out of turns or if we're out of bounds, return 0
        {
            return 0;
        }
        if(grid[i][j]!=next) //If the current cell doesn't match the pattern, return 0
        {
            return 0;
        }
        if(dp[i][j][dir][turns]!=-1) //Check the cache
        {
            return dp[i][j][dir][turns];
        }
        int max = 0;
        if(next==1 || next==0) //If current next is 1 or 0, then the next value in the pattern must be 2
        {
            next = 2;
        }
        else //If current next is 2, then the next value in the pattern must be 0
        {
            next = 0;
        }
        int sameI = i + direc[dir][0]; //Get the indices for the next cell if we keep going in the same direction
        int sameJ = j + direc[dir][1];
        int newD = 0;
        int ni = 0;
        int nj = 0;
        if(dir==0) //Get the indices for the next cell if we make a 90 degree turn
        {
            ni = i+direc[2][0];
            nj = j+direc[2][1];
            newD = 2;
        }
        if(dir==1)
        {
            ni = i+direc[3][0];
            nj = j+direc[3][1];
            newD = 3;
        }
        if(dir==2)
        {
            ni = i+direc[1][0];
            nj = j+direc[1][1];
            newD = 1;
        }
        if(dir==3)
        {
            ni = i+direc[0][0];
            nj = j+direc[0][1];
            newD = 0;
        }
        int sameDir = 1 + recurse(grid, sameI, sameJ, dir, turns, next); //Try both paths and store and return the max of them
        int turn = 1 + recurse(grid, ni, nj, newD, turns-1, next);
        return dp[i][j][dir][turns] = Math.max(max, Math.max(sameDir, turn));
    }
}
