// 1443. Minimum Time to Collect All Apples in a Tree
// https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/
// Medium | Java | Accepted 2026-11-07
// Runtime 13 ms | Memory 82.3 MB

class Solution {
    ArrayList<Integer>[] graph;
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        graph = new ArrayList[n];
        for(int i = 0; i<n; i++)
        {
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i<edges.length; i++)
        {
            int[] e = edges[i];
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        return recurse(0, -1, hasApple);
    }

    public int recurse(int curr, int parent, List<Boolean> hasApple)
    {
        ArrayList<Integer> neigh = graph[curr];
        int tot = 0;
        for(int i = 0; i<neigh.size(); i++)
        {
            if(neigh.get(i)!=parent)
            {
                int res = recurse(neigh.get(i), curr, hasApple);
                if(res>0)
                {
                    tot += res+2;
                }
                else if(hasApple.get(neigh.get(i)))
                {
                    tot += 2;
                }
            }
        }
        return tot;
    }
}
