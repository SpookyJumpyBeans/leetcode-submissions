// 994. Rotting Oranges
// https://leetcode.com/problems/rotting-oranges/
// Medium | Java | Accepted 2026-08-13
// Runtime 2 ms | Memory 44.8 MB

class Solution {

    public int orangesRotting(int[][] grid) {
        Queue<int[]> bfs = new LinkedList<>(); //USE BFS, since this is a minimization problem, BFS is better than DFS
        int countO = 0; //Keep a count of all the fresh oranges to check if all oranges at the end are infected
        for(int i = 0; i<grid.length; i++) 
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]==2)
                {
                    bfs.add(new int[]{i, j , 0}); //If an orange starts off infected at time 0, then add it into the queue
                }
                if(grid[i][j]==1) //If the orange is fresh, add to the count of oranges
                {
                    countO++;
                }
            }
        }
        int min = 0; //Keeping track of the minutes that have gone by
        while(!bfs.isEmpty()) 
        {
            int[] curr = bfs.poll(); //Pop the rotten orange 
            int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
            min = curr[2]; //Immediately set the minutes to how many minutes it's taken to get to this rotten orange, use Math.max as older rotten oranges may be in the queue
            for(int[] d : dir)
            {
                int ni = curr[0] + d[0];
                int nj = curr[1] + d[1];
                if(ni>=0 && nj>=0 && ni<grid.length && nj<grid[0].length && grid[ni][nj]==1)
                {
                    grid[ni][nj] = 2;
                    countO--;
                    bfs.add(new int[]{ni, nj, curr[2]+1});
                }
            }
        }
        return countO == 0 ? min : -1;
        
    }
}
