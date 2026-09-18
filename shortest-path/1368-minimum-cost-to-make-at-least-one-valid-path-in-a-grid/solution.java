// 1368. Minimum Cost to Make at Least One Valid Path in a Grid
// https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/
// Hard | Java | Accepted 2026-09-16
// Runtime 27 ms | Memory 47 MB

class Solution {
    public int minCost(int[][] grid) {
        //Since cycles can occur, there isn't a DAG and therefore we can't use DP
        //Use BFS (Dijkstra's instead)
        int m = grid.length; 
        int n = grid[0].length;
        int[][] minDist = new int[m][n]; //Since it's a 2D matrix, use a 2D array to store our minDistances
        for(int[] r : minDist)
        {
            Arrays.fill(r, Integer.MAX_VALUE); //Fill them all with the max possible value at the start
        } 
        PriorityQueue<int[]> dij = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0])); //Min heap that stores the weight and the indices of each grid square
        dij.add(new int[]{0, 0, 0}); //Initially we start from the top left square and the weight to get there is 0
        minDist[0][0] = 0; //Set the minDistance for the starting square to be 0
        while(!dij.isEmpty()) //While the pq isn't empty
        {
            int[] node = dij.poll(); //Poll the current grid
            if(node[0]>minDist[node[1]][node[2]]) //Check if this is an outdated entry
            //If the current minimum distance from the top left square to this square is less than the distance associated with this entry, we can skip this entry
            {
                continue;
            }
            int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; //Go in all four directions
            //Each index matches whatever arrow is stored in the current square
            for(int i = 0; i<dir.length; i++)
            {
                int[] d = dir[i];
                int nextCoorX = node[1]+d[0];
                int nextCoorY = node[2]+d[1]; //Get next coordinates
                int cost = i+1==grid[node[1]][node[2]] ? 0 : 1; //If the next coordinate matches the arrow in this current square, then the cost is 0, otherwise it's 1
                if(nextCoorX>=0 && nextCoorX<m && nextCoorY>=0 && nextCoorY<n) //If this next coordinate is in bounds
                {
                    if(node[0]+cost<minDist[nextCoorX][nextCoorY]) //And going to this square is going to be the minimum possible cost 
                    {
                        minDist[nextCoorX][nextCoorY] = node[0]+cost; //Then we set the minimum distance for this next coordinate to the new minimum
                        dij.add(new int[]{node[0]+cost, nextCoorX, nextCoorY}); //And push this next coordinate along with its new cost it to the pq
                    }
                }
            }
        }
        return minDist[m-1][n-1]; //Return the value stored for the bottom right corner
    }
}
