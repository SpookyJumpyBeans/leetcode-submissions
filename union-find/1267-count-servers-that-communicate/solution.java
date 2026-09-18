// 1267. Count Servers that Communicate
// https://leetcode.com/problems/count-servers-that-communicate/
// Medium | Java | Accepted 2026-09-17
// Runtime 4 ms | Memory 55.5 MB

class Solution {
    public int countServers(int[][] grid) {
        //Can implement this using BFS but neighbors aren't determined by adjacency, but by whether there's another server in the current server's entire row/col
        //I did it using a two pass 
        int[] rowC = new int[grid.length];
        int[] colC = new int[grid[0].length];
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                rowC[i] += grid[i][j] == 1 ? 1 : 0; //Count the number of servers in the current row
                colC[j] += grid[i][j] == 1 ? 1 : 0; //Count the number of servers in the current col
            }
        }
        int count = 0;
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]==1 && (rowC[i]>1 || colC[j]>1)) //Go through the entire grid again
                //If the current row/col that the server rests on has another server (the server isn't alone), then we can increment count by 1 since this server has a neighbor it can communicate with
                {
                    count++;
                }
            }
        }
        return count;
    }
}
