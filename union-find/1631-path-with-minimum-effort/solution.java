// 1631. Path With Minimum Effort
// https://leetcode.com/problems/path-with-minimum-effort/
// Medium | Java | Accepted 2026-08-23
// Runtime 46 ms | Memory 47.4 MB

class Solution {
    public int minimumEffortPath(int[][] heights) {
       int[][] tracker = new int[heights.length][heights[0].length];
       for(int i = 0; i<heights.length; i++)
       {
        for(int j = 0; j<heights[0].length; j++)
        {
            tracker[i][j] = Integer.MAX_VALUE;
        }
       }
       PriorityQueue<int[]> dij = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
       dij.add(new int[]{0, 0, 0});
       while(!dij.isEmpty())
       {
        int[] temp = dij.poll();
        if(temp[0]>tracker[temp[1]][temp[2]])
        {
            continue;
        }
        if(temp[1]==heights.length-1&&temp[2]==heights[0].length-1)
        {
            return temp[0];
        }
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for(int[] d : dir)
        {
            int ni = temp[1] + d[0];
            int nj = temp[2] + d[1];
            if(ni>=0 && nj>=0 && ni<heights.length && nj<heights[0].length)
            {
                int edgeEffort = Math.abs(heights[temp[1]][temp[2]]-heights[ni][nj]);
                int pathEffort = Math.max(edgeEffort, temp[0]);
                if(pathEffort<tracker[ni][nj])
                {
                    tracker[ni][nj] = pathEffort;
                    dij.add(new int[]{pathEffort, ni, nj});
                }
            }
        }
       }
       return -1;
    }
}
