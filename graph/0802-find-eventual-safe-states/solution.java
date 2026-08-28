// 802. Find Eventual Safe States
// https://leetcode.com/problems/find-eventual-safe-states/
// Medium | Java | Accepted 2026-08-17
// Runtime 24 ms | Memory 64.1 MB

class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer>[] g = new ArrayList[graph.length];
        for(int i = 0; i<g.length; i++)
        {
            g[i] = new ArrayList<>();
        }
        int[] counts = new int[graph.length];
        for(int i = 0; i<graph.length; i++)
        {
            counts[i] = graph[i].length;
            for(int j = 0; j<graph[i].length; j++)
            {
                g[graph[i][j]].add(i);
            }
        }
        Queue<Integer> kahn = new LinkedList<>();
        for(int i = 0; i<counts.length; i++)
        {
            if(counts[i]==0)
            {
                kahn.add(i);
            }
        }
        List<Integer> ans = new ArrayList<>();
        while(!kahn.isEmpty())
        {

            int node = kahn.poll();
            ans.add(node);
            List<Integer> neigh = g[node];
            for(int i = 0; i<neigh.size(); i++)
            {
                counts[neigh.get(i)]--;
                if(counts[neigh.get(i)]==0)
                {
                    kahn.add(neigh.get(i));
                }
            }
        }
        Collections.sort(ans);
        return ans;
    }
}
