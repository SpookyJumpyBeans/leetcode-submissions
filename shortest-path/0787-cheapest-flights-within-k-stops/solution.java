// 787. Cheapest Flights Within K Stops
// https://leetcode.com/problems/cheapest-flights-within-k-stops/
// Medium | Java | Accepted 2026-01-07
// Runtime 6 ms | Memory 46.3 MB

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<int[]>[] graph = new ArrayList[n];
        int[] best = new int[n];
        Arrays.fill(best, 10000000);
        for(int i = 0; i<n; i++)
        {
            graph[i] = new ArrayList<>();
        }
        for(int j = 0; j<flights.length; j++)
        {
            int source = flights[j][0];
            int dest = flights[j][1];
            int price = flights[j][2];
            graph[source].add(new int[]{dest, price});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        pq.add(new int[]{0,src, 0});
        while(!pq.isEmpty())
        {
            int[] temp = pq.poll();
            if(temp[2]>k+1)
            {
                continue;
            }
            if(temp[1]==dst)
            {
                return temp[0];
            }
            if(temp[2]>=best[temp[1]])
            {
                continue;
            }
            best[temp[1]] = temp[2];
            for(int[] a : graph[temp[1]])
            {
                int weight = a[1];
                int node = a[0];
                pq.add(new int[]{weight+temp[0], node, temp[2]+1});
            }
        }   
        return -1;
        // 
    }
}
