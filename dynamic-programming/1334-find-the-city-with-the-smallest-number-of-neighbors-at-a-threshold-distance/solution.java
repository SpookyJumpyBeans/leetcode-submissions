// 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
// Medium | Java | Accepted 2026-08-17
// Runtime 29 ms | Memory 47.3 MB

class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {

        List<int[]>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>(); // Initialize upfront!
        }

        for(int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
            graph[e[1]].add(new int[]{e[0], e[2]});
        }
        int min = Integer.MAX_VALUE;
        int city = 0;
        for(int k = 0; k<n; k++)
        {
        int[] temp = new int[n];
        for(int l = 0; l<n; l++)
        {
            temp[l] = Integer.MAX_VALUE;
        }
        temp[k] = 0;
        PriorityQueue<int[]> dijkstra = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        dijkstra.add(new int[]{k, 0});
        while(!dijkstra.isEmpty())
        {
            int[] node = dijkstra.poll();
            if(node[1]>temp[node[0]])
            {
                continue;
            }
            List<int[]> neigh = graph[node[0]];
            for(int i = 0; i<neigh.size(); i++)
            {
                int weight = node[1]+neigh.get(i)[1];
                if(weight<temp[neigh.get(i)[0]] && weight<=distanceThreshold)
                {
                    temp[neigh.get(i)[0]] = weight;
                    dijkstra.add(new int[]{neigh.get(i)[0], weight});
                }
            }
        }
        int count = 0;
        for(int j = 0; j<temp.length; j++)
        {
            if(temp[j]<=distanceThreshold)
            {
                count++;
            }
        }
        if(count<=min)
        {
            min = count;
            city = k;
        }
        }
        return city;
    }
}
