// 1091. Shortest Path in Binary Matrix
// https://leetcode.com/problems/shortest-path-in-binary-matrix/
// Medium | Java | Accepted 2026-08-12
// Runtime 21 ms | Memory 47.4 MB

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        if(grid[0][0]==1 || grid[grid.length-1][grid[0].length-1]==1)
        {
            return -1;
        }
        Queue<int[]> bfs = new LinkedList<>();
        bfs.add(new int[]{0, 0, 1});
        while(!bfs.isEmpty())
        {
            int[] temp = bfs.poll();
            if(temp[0]==grid.length-1 && temp[1]==grid[0].length-1)
            {
                return temp[2];
            }
            int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
            for(int[] d : dir)
            {
                int ni = temp[0] + d[0];
                int nj = temp[1] + d[1];
                if(ni>=0 && nj>=0 && ni<grid.length && nj<grid[0].length && grid[ni][nj]==0)
                {
                    grid[ni][nj] = 2;
                    bfs.add(new int[]{ni, nj, temp[2]+1});
                }
            }
        }
        return -1;
    }
}
