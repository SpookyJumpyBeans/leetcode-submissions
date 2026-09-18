// 2812. Find the Safest Path in a Grid
// https://leetcode.com/problems/find-the-safest-path-in-a-grid/
// Medium | Java | Accepted 2026-09-18
// Runtime 157 ms | Memory 126.5 MB

class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        //First, use BFS to find the safe factor of every square (finding the nearest thief from every non thief square)
        //Do this by adding all the thieves to a queue and performing bfs from each thief
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] safeness = new int[m][n];
        Queue<int[]> bfs = new ArrayDeque<>();
        for(int i = 0; i<m; i++)
        {
            for(int j = 0; j<n; j++)
            {
                safeness[i][j] = Integer.MAX_VALUE; //The safeness 2D array keeps track of the safe factor for every possible non thief square
                //Default set to Integer.MAX_VALUE
                if(grid.get(i).get(j)==1)
                {
                    bfs.add(new int[]{i, j, 0});
                    safeness[i][j] = 0;
                }
            }
        }
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while(!bfs.isEmpty()) //BFS to find safe factor for every square
        {
            int[] ind = bfs.poll();
            for(int[] d : dir)
            {
                int xx = ind[0] + d[0];
                int yy = ind[1] + d[1];
                if(xx>=0 && xx<m && yy>=0 && yy<n && safeness[xx][yy]==Integer.MAX_VALUE) //If the next coordinates are in bounds and hasn't been visited yet
                {
                    safeness[xx][yy] = ind[2]+1; //Set the safe factor to the current safe factor plus 1, since every layer further away from the original thief adds 1 to the safe factor (squares containing the thief start at 0)
                    bfs.add(new int[]{xx, yy, ind[2]+1}); //Add this new coordinate to the queue
                }
            }
        }
        int[][] maxDij = new int[m][n]; //This is the grid which we keep track of the maximum safe factors
        //We are performing Dijkstra's Algorithm now
        //Dijkstra's allows us to find the maximum safe factor of a path (Instead of finding the minimum weight, we are finding the maximum safe factor, which is maximizing the minimum)
        for(int[] r : maxDij)
        {
            Arrays.fill(r, -1);
        }
        PriorityQueue<int[]> dij = new PriorityQueue<>((a, b)->Integer.compare(b[0], a[0])); //We want to use a max heap instead of a min heap because we want the larger safe factors to be evaluated first
        dij.add(new int[]{safeness[0][0], 0, 0}); //Add the starting point
        while(!dij.isEmpty())
        {
            int[] coor = dij.poll();
            if(coor[0]<maxDij[coor[1]][coor[2]]) //If this square was reached earlier and the safe factor stored is larger than the current safe factor of this path, we can skip this entry since it's outdated
            //This is a runtime optimzation 
            {
                continue;
            }
            if(coor[1]==m-1 && coor[2]==n-1) //If we've reached the bottom right corner, return whatever safe factor is attached
            {
                return coor[0];
            }
            for(int[] d : dir)
            {
                int xx = coor[1] + d[0];
                int yy = coor[2] + d[1];
                if(xx>=0 && xx<m && yy>=0 && yy<n) //Check the next possible coordinate is in bounds
                {
                    int newSafeness = Math.min(safeness[xx][yy], coor[0]); //Get the new safe factor if we traverse to this square, which is the minimum of the safe factor of this new square and the current safe factor
                    if(newSafeness>maxDij[xx][yy]) //If this new safe factor is greater than the max safe factor we have stored for all paths including this next coordinate thus far
                    {                    
                        maxDij[xx][yy] = newSafeness; //Update the max safe factor for this new coordinate
                        dij.add(new int[]{maxDij[xx][yy], xx, yy}); //Push it to the pq
                    }
                }
            }
        }
        return -1;
    }
}
