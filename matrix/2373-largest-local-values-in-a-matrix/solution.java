// 2373. Largest Local Values in a Matrix
// https://leetcode.com/problems/largest-local-values-in-a-matrix/
// Easy | Java | Accepted 2026-11-04
// Runtime 3 ms | Memory 47 MB

class Solution {
    public int[][] largestLocal(int[][] grid) {
        int n = grid.length;
        int[][] res = new int[n-2][n-2];
        for(int i = 0; i<n-2; i++) //Explore all the possible starting left corners of each 3x3 matrix
        {
            for(int j = 0; j<n-2; j++)
            {
                int max = 0;
                for(int k = i; k<i+3; k++) //Iterate through the 9 squares in every possible 3x3 matrix
                {
                    for(int l = j; l<j+3; l++)
                    {
                        max = Math.max(grid[k][l], max); //Find the max value out of the 9 squares
                    }
                }
                res[i][j] = max; //Store it in the resulting array
            }
        }
        return res;
    }
}
