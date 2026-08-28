// 1928. Minimum Cost to Reach Destination in Time
// https://leetcode.com/problems/minimum-cost-to-reach-destination-in-time/
// Hard | Java | Accepted 2026-08-12
// Runtime 938 ms | Memory 105.3 MB

class Solution {
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        PriorityQueue<int[]> dij = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        List<int[]>[] graph = new ArrayList[passingFees.length];
        for(int i = 0; i<graph.length; i++)
        {
            graph[i] = new ArrayList<>();
        }
        for(int[] e : edges)
        {
            int u = e[0];
            int v = e[1];
            int time = e[2];
            graph[u].add(new int[]{v, time});
            graph[v].add(new int[]{u, time});
        }
        int[][] tracker = new int[passingFees.length][maxTime+1];
        for(int l = 0; l<tracker.length; l++)
        {
            for(int m = 0; m<tracker[0].length; m++)
            {
                tracker[l][m] = Integer.MAX_VALUE;
            }
        }
        dij.add(new int[]{passingFees[0], 0, 0});
        while(!dij.isEmpty())
        {
            int[] node = dij.poll();
            if(node[1]==passingFees.length-1)
            {
                return node[0];
            }
            if(node[0] > tracker[node[1]][node[2]])
            {
                continue;
            }
            for(int j = 0; j<graph[node[1]].size(); j++)
            {
                int[] t = graph[node[1]].get(j);
                int newCost = node[0] + passingFees[t[0]];
                int newTime = node[2] + t[1];
                if(newTime<=maxTime && newCost<tracker[t[0]][newTime])
                {
                    dij.add(new int[]{newCost, t[0], newTime});
                    tracker[t[0]][newTime] = newCost;
                }
            }
        }
        return -1;


    }
}
