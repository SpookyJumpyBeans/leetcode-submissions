// 980. Unique Paths III
// https://leetcode.com/problems/unique-paths-iii/
// Hard | Java | Accepted 2026-10-12
// Runtime 2 ms | Memory 46.2 MB

class Solution {
    int count = 0;
    public int uniquePathsIII(int[][] grid) {
        int startx = 0;
        int starty = 0;
        int totalSquares = grid.length*grid[0].length;
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]==-1)
                {
                    totalSquares--;
                }
                if(grid[i][j]==1)
                {
                    startx = i; starty = j;
                }
            }
        }
        recurse(grid, startx, starty, totalSquares);
        return count;
    }

    public void recurse(int[][] grid, int startx, int starty, int total)
    {
        if(startx<0 || starty<0 || startx>=grid.length || starty>=grid[0].length || grid[startx][starty]==-1)
        {
            return;
        }
        if(total==1 && grid[startx][starty]==2)
        {
            count++;
            return;
        }
        int temp = grid[startx][starty];
        grid[startx][starty] = -1;
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for(int[] d : dir)
        {
            int xx = startx + d[0];
            int yy = starty + d[1];
            recurse(grid, xx, yy, total-1);
        }
        grid[startx][starty] = temp;
    }
}
