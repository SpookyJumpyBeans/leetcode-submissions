// 200. Number of Islands
// https://leetcode.com/problems/number-of-islands/
// Medium | Java | Accepted 2026-01-04
// Runtime 3 ms | Memory 52.5 MB

class Solution {
    int count = 0;
    public int numIslands(char[][] grid) {
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]=='1')
                {
                    count++;
                    recurse(grid, i, j);
                }
            }
        }
        return count;
    }

    public void recurse(char[][] grid, int r, int c)
    {
        if(r>=grid.length || c>=grid[0].length || r < 0 || c < 0)
        {
            return;
        }
        if(grid[r][c]=='0')
        {
            return;
        }
        if(grid[r][c]=='1')
        {
            grid[r][c]='0';
            recurse(grid, r+1,c);
            recurse(grid, r-1,c);
            recurse(grid, r, c+1);
            recurse(grid, r,c-1);
        }
    }

}
