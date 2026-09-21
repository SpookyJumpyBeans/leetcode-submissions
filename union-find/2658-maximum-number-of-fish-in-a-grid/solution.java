// 2658. Maximum Number of Fish in a Grid
// https://leetcode.com/problems/maximum-number-of-fish-in-a-grid/
// Medium | Java | Accepted 2026-09-20
// Runtime 21 ms | Memory 49.6 MB

class Solution {
    public int findMaxFish(int[][] grid) {
        int ans = 0;
        //BFS way of finding the answer
        //Can use DFS too
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {   
                if(grid[i][j]!=0) //If the cell is water, then we run bfs to see what the largest contiguous pool of water is using BFS
                {
                    Queue<int[]> bfs = new ArrayDeque<>();
                    bfs.add(new int[]{i, j}); //Add initial water cell
                    int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                    int temp = 0;
                    while(!bfs.isEmpty())
                    {
                        int[] curr = bfs.poll();
                        temp+=grid[curr[0]][curr[1]];
                        grid[curr[0]][curr[1]] = 0;
                        for(int[] d : dir)
                        {
                            int x = curr[0] + d[0];
                            int y = curr[1] + d[1];
                            if(x>=0 && x<grid.length && y>=0 && y<grid[0].length && grid[x][y]>0) //Go to adjacent water cells 
                            {
                                bfs.add(new int[]{x, y});
                            }
                        }
                    }
                    ans = Math.max(ans, temp); //See if the number caught in this pool is bigger than the max thus far
                }
            }   
        }
        return ans;
    }
}
