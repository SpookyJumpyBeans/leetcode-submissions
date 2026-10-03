// 827. Making A Large Island
// https://leetcode.com/problems/making-a-large-island/
// Hard | Java | Accepted 2026-10-03
// Runtime 86 ms | Memory 152.8 MB

class Solution {
    int[][] uniqueIslands;
    Map<Integer, Integer> size = new HashMap<>();
    public int largestIsland(int[][] grid) {
        //Intuition: Find all the unique isolated islands and mark all cells belonging to that island with their unique island ID
        //Do this by doing a pass and running BFS on all 1 marked cells 
        //Mark those cells as 0 to ensure you don't traverse them again
        //Go through all 0 cells and look in all 4 directions to see if there's a unique island id associated with any of these 4 cells
        //If there is, add the size of that island to the count
        //Make sure to only add islands once
        int max = 0;    
        int defCount = 0;
        int id = 1;
        uniqueIslands = new int[grid.length][grid[0].length];
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]==1)
                {
                    defCount++;
                }
            }
        }
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(grid[i][j]==1)
                {
                    size.put(id, bfs(grid, i, j, id));
                    id++;
                }
            }
        }
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for(int i = 0; i<grid.length; i++)
        {
            for(int j = 0; j<grid[0].length; j++)
            {
                if(uniqueIslands[i][j]==0)
                {
                    int currSize = 1;
                    Set<Integer> used = new HashSet<>();
                    for(int[] d : dir)
                    {
                        int x = i+d[0];
                        int y = j+d[1];
                        if(x>=0 && x<grid.length && y>=0 && y<grid[0].length && uniqueIslands[x][y]!=0 && !used.contains(uniqueIslands[x][y]))
                        {
                            currSize+=size.get(uniqueIslands[x][y]);
                            used.add(uniqueIslands[x][y]);
                        }
                    }
                    max = Math.max(max, currSize);
                }
            }
        }
        return max == 0 ? defCount : max;
    }

    public int bfs(int[][] grid, int sR, int sC, int id)
    {
        grid[sR][sC] = 0;
        int count = 1;
        Queue<int[]> bfs = new ArrayDeque<>();
        bfs.add(new int[]{sR, sC});
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while(!bfs.isEmpty())
        {
            int[] curr = bfs.poll();
            int x = curr[0];
            int y = curr[1];
            uniqueIslands[x][y] = id;
            for(int[] d : dir)
            {
                int newX = x + d[0];
                int newY = y + d[1];
                if(newX>=0 && newX<grid.length && newY>=0 && newY<grid[0].length && grid[newX][newY]==1)
                {
                    count++;
                    grid[newX][newY] = 0;
                    bfs.add(new int[]{newX, newY});
                }
            }
        }
        return count;
    }
}
