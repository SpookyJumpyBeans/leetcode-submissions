// 576. Out of Boundary Paths
// https://leetcode.com/problems/out-of-boundary-paths/
// Medium | Java | Accepted 2026-09-16
// Runtime 3 ms | Memory 43.7 MB

class Solution {
    int[][][] dp;
    int startR;
    int startC;
    int rows;
    int cols;
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        rows = m;
        cols = n;
        startR = startRow;
        startC = startColumn;
        dp = new int[m][n][maxMove+1];
        //3D DP
        //Since the indices are changing as well as the number of moves, we have to keep track of 3 different states
        //Reasoning: Getting to coordinates (2, 0) is different whne the number of moves left is 4 compared to 2
        for(int[][] dd : dp)
        {
            for(int[] d : dd)
            {
                Arrays.fill(d, -1); //Make the 3D Cache
            }
        }
        return recurse(maxMove, startR, startC);
    }

    public int recurse(int movesLeft, int r, int c)
    {
        if(r<0 || r>=rows || c<0 || c>=cols) //If this index is out of bounds, return 1
        {
            return 1;
        }
        if(movesLeft==0) //Else if there are no more moves left, return 0
        {
            return 0;
        }
        if(dp[r][c][movesLeft]!=-1) //If we've already been at this state before, return the cached value
        {
            return dp[r][c][movesLeft];
        }
        int MOD = 1000000007;
        long count = (recurse(movesLeft-1, r-1, c) + recurse(movesLeft-1, r+1, c)) % MOD;
        count = (count + recurse(movesLeft-1, r, c-1)) % MOD;
        count = (count + recurse(movesLeft-1, r, c+1)) % MOD; 
        //Else, explore all 4 directions and sum them together
        return dp[r][c][movesLeft] = (int) count; //Store it in the cache and return it
    }
}
