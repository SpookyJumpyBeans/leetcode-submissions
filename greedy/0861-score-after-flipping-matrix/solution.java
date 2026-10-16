// 861. Score After Flipping Matrix
// https://leetcode.com/problems/score-after-flipping-matrix/
// Medium | Java | Accepted 2026-10-15
// Runtime 1 ms | Memory 42.9 MB

class Solution {
    public int matrixScore(int[][] grid) {
        //First, the most significant bit (the first index of every row) matters more to the total sum than every other bit combined in that row
        //We want to ensure that every row starts off with a bit of 1, so if a row doesn't, flip all its bits in that row
        for(int i = 0; i<grid.length; i++)
        {
            if(grid[i][0]==0) //If the row starts with a 0
            {
                for(int j = 0; j<grid[0].length; j++)
                {
                    grid[i][j] ^= 1; //Flip all the bits for the row to ensure the first bit is 1
                }
            }
        }
        //For the columns, every bit in that column contributes the same so we only want to flip all the bits in that column if there are more 0s than 1s
        //Flipping the column ensures we maximize the number of 1s in that specific position
        for(int i = 0; i<grid[0].length; i++)
        {
            int count = 0; //Keeps track of the difference of 0s and 1s
            for(int j = 0; j<grid.length; j++)
            {
                count += grid[j][i] == 0 ? 1 : -1;
            }
            if(count>0) //If the difference is positive, there are more 0s than 1s, so flip all the bits in that column
            {
                for(int k = 0; k<grid.length; k++)
                {
                    grid[k][i] ^= 1;
                }
            }
        }
        int sum = 0; 
        for(int[] r : grid)
        {
            StringBuilder temp = new StringBuilder();
            for(int rr : r)
            {
                temp.append(rr);
            }
            sum+=Integer.parseInt(temp.toString(), 2); //Sum up every row as an binary number
        }
        return sum;
    }
}
