// 743. Network Delay Time
// https://leetcode.com/problems/network-delay-time/
// Medium | Java | Accepted 2026-01-06
// Runtime 9 ms | Memory 48.7 MB

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        List<int[]>[] adj = new ArrayList[n+1];
        for(int l = 1; l<=n; l++)
        {
            adj[l] = new ArrayList<>();
        }
        int[] time = new int[n+1];
        Arrays.fill(time, 100000000);
        time[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for(int i = 0; i<times.length; i++)
        {
            int source = times[i][0];
            int dest = times[i][1];
            int weight = times[i][2];
            adj[source].add(new int[]{dest, weight});
        }
        pq.add(new int[]{0, k});
        int ans = 0;
        while(!pq.isEmpty())
        {
            int[] temp = pq.poll();
            if(temp[0] > time[temp[1]])
            {
                continue;
            }
            for(int[] arr : adj[temp[1]])
            {
                int neigh = arr[0];
                int weight = arr[1];
                if(temp[0]+weight<time[arr[0]])
                {
                    time[arr[0]] = temp[0]+weight;
                    pq.add(new int[]{time[arr[0]], neigh});
                }
            }
            ans = temp[0];
        }
        for(int j = 1; j<time.length; j++)
        {
            if(time[j]==100000000)
            {
                return -1;
            }
        }
        return ans;
    }
}
