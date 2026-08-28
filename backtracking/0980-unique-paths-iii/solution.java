// 980. Unique Paths III
// https://leetcode.com/problems/unique-paths-iii/
// Hard | Java | Accepted 2026-08-11
// Runtime 1 ms | Memory 45 MB

class Solution {
    int count = 0;
    int[][] g;
    public int uniquePathsIII(int[][] grid) {
        int total = grid.length*grid[0].length;
        g = new int[grid.length][grid[0].length];
        int si = 0;
        int sj = 0;
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                g[i][j] = grid[i][j];
                if(g[i][j]==-1)
                {
                    total--;
                }
                if(g[i][j]==1)
                {
                    si = i;
                    sj = j;
                }
            }
        }
        dfs(si, sj, total);
        return count;
    }

    public void dfs(int i, int j, int remaining)
    {
        if(i>=g.length || j >= g[0].length || i<0 || j<0)
        {
            return;
        }
        if(g[i][j]==2 && remaining>1)
        {
            return;
        }
        if(g[i][j]==-1)
        {
            return;
        }
        if(g[i][j]==2 && remaining==1)
        {
            count++;
            return;
        }
        g[i][j] = -1;
        int[][] path = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for(int[] p : path)
        {
            int ni = i + p[0];
            int nj = j + p[1];
            if(ni >=0 && nj>=0 && ni<g.length && nj <g[0].length)
            {
                dfs(ni, nj, remaining-1);
            }
        }
        g[i][j] = 0;
    }
}
