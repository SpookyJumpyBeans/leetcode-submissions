// 1197. Minimum Knight Moves
// https://leetcode.com/problems/minimum-knight-moves/
// Medium | Java | Accepted 2026-10-08
// Runtime 310 ms | Memory 123.2 MB

class Solution {
    public int minKnightMoves(int x, int y) {
        //Basic BFS
        //Since the indices can be negative, we want to offset by 300 so everything is >= 0
        //Then, keep a visited array so we don't get into a cycle
        //Return the count of the first indices that hits the target indices
        int[][] dir = {{1, 2}, {-1, 2}, {-1, -2}, {1, -2}, {2, 1}, {-2, 1}, {-2, -1}, {2, -1}};
        Queue<int[]> bfs = new ArrayDeque<>();
        bfs.add(new int[]{300, 300, 0}); //Start by adding (0, 0) -> (300, 300) to the bfs queue
        int targx = x+300; //Offset the target x and y by 300
        int targy = y+300;
        boolean[][] used = new boolean[601][601];
        while(!bfs.isEmpty())
        {
            int[] curr = bfs.poll();
            if(used[curr[0]][curr[1]]) //If we've already visited this indices before, continue
            {
                continue;
            }
            used[curr[0]][curr[1]] = true; //Mark this indices as visited
            if(curr[0]==targx && curr[1]==targy) //If this indices is the target indices, return the count of jumps to get here
            {
                return curr[2];
            } 
            for(int[] d: dir) //Go through all possible directions
            {
                bfs.add(new int[]{curr[0]+d[0], curr[1]+d[1], curr[2]+1});
            }
        }
        return -1;
    }
}
