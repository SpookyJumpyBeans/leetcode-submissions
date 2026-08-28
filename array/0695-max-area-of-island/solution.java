// 695. Max Area of Island
// https://leetcode.com/problems/max-area-of-island/
// Medium | Java | Accepted 2026-01-04
// Runtime 2 ms | Memory 46.5 MB

class Solution {
    int max = 0;
    public int maxAreaOfIsland(int[][] grid) {
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]==1)
                {
                recurse(grid, i, j, 0);
                }
            }
        }
        return max;
    }

    public int recurse(int[][] grid, int r, int c, int count)
    {
        if(r<0 || c<0 || r>=grid.length || c>=grid[0].length)
        {
            return 0;
        }
        if(grid[r][c]==0)
        {
            return 0;
        }
        if(grid[r][c]==1)
        {
            grid[r][c] = '0';
            count+= 1 + recurse(grid, r+1, c, count) + recurse(grid, r, c+1, count) + recurse(grid, r, c-1, count) + recurse(grid, r-1, c, count);
            max = Math.max(count, max);
        }
        return count;
    }
}
