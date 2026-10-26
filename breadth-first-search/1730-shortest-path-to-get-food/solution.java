// 1730. Shortest Path to Get Food
// https://leetcode.com/problems/shortest-path-to-get-food/
// Medium | Java | Accepted 2026-10-25
// Runtime 5 ms | Memory 55.5 MB

class Solution {
    public int getFood(char[][] grid) {
        int si = 0;
        int sj = 0;
        Queue<int[]> bfs = new ArrayDeque<>();
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]=='*')
                {
                    si = i;
                    sj = j;
                    break;
                }
            }
        }
        bfs.add(new int[]{si, sj, 0});
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[si][sj] = true;
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        while(!bfs.isEmpty())
        {
            int[] curr = bfs.poll();
            if(grid[curr[0]][curr[1]]=='#')
            {
                return curr[2];
            }
            for(int[] d : dir)
            {
                int x = curr[0] + d[0];
                int y = curr[1] + d[1];
                if(x>=0 && y>=0 && x<grid.length && y<grid[0].length && grid[x][y]!='X' && !visited[x][y])
                {
                    visited[x][y] = true;
                    bfs.add(new int[]{x, y, curr[2]+1});
                }
            }
        }
        return -1;
    }
}
