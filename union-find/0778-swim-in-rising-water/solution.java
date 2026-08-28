// 778. Swim in Rising Water
// https://leetcode.com/problems/swim-in-rising-water/
// Hard | Java | Accepted 2026-01-07
// Runtime 9 ms | Memory 46.4 MB

class Solution {
    public int swimInWater(int[][] grid) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        boolean[][]visited = new boolean[grid.length][grid[0].length];
        visited[0][0] = true;
        pq.add(new int[] {grid[0][0], 0, 0});
        while(!pq.isEmpty())
        {
            int[] temp = pq.poll();
            if(temp[1] == grid.length-1 && temp[2] == grid.length-1)
            {
                return Math.max(temp[0], grid[temp[1]][temp[2]]);
            }
            int rp = temp[1]+1;
            int rb = temp[1]-1;
            int r = temp[1];
            int cp = temp[2]+1;
            int cb = temp[2]-1;
            int c = temp[2];
            if(rp<grid.length  && !visited[rp][c])
            {
               int  max = Math.max(temp[0], grid[rp][c]);
                pq.add(new int[]{max, rp, c});
                visited[rp][c] = true;
            }
            if(rb>=0 && !visited[rb][c])
            {
               int max = Math.max(temp[0], grid[rb][c]);
                pq.add(new int[]{max, rb, c});
                visited[rb][c] = true;
            }
            if(cp<grid.length && !visited[r][cp])
            {
               int max = Math.max(temp[0], grid[r][cp]);
                pq.add(new int[] {max, r, cp});
                visited[r][cp] = true;
            }
            if(cb>=0 && !visited[r][cb])
            {
               int max = Math.max(temp[0], grid[r][cb]);
                pq.add(new int[] {max, r, cb});
                visited[r][cb] = true;
            };
        }
        return -1;
    }
}
