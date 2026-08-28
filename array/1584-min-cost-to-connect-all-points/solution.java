// 1584. Min Cost to Connect All Points
// https://leetcode.com/problems/min-cost-to-connect-all-points/
// Medium | Java | Accepted 2026-01-07
// Runtime 67 ms | Memory 46.9 MB

class Solution {
    public int minCostConnectPoints(int[][] points) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]);
        int[] best = new int[points.length];
        Arrays.fill(best, 100000000);
        pq.add(new int[]{0, 0});
        boolean[] visited = new boolean[points.length];
        int ans = 0;
        // weight to get to that node and node #
        while(!pq.isEmpty())
        {
            int[] temp = pq.poll();
            if(visited[temp[1]])
            {
                continue;
            }
            visited[temp[1]] = true;
            ans+=temp[0];
            for(int i = 0; i<points.length; i++)
            {
                if(i==temp[1])
                {
                    continue;
                }
                int mand = Math.abs(points[temp[1]][0]-points[i][0]) + Math.abs(points[temp[1]][1]-points[i][1]);   
                if(!visited[i])
                {
                    if(best[i]>mand)
                    {
                    best[i] = mand;
                    pq.add(new int[]{mand, i});
                    }
                }
            }
        }
        return ans;
    }
}
