// 1489. Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree
// https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/
// Hard | Java | Accepted 2026-09-06
// Runtime 162 ms | Memory 47.5 MB

class Solution {
    int[] parent;
    int[] size;
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        //Kruskal's Algorithm
        PriorityQueue<int[]> weights = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i<n; i++)
        {
            parent[i] = i;
            size[i] = 1;
        }
        for(int[] e : edges)
        {
            weights.add(new int[]{e[2], e[0], e[1]});
        }
        int mstWeight = 0;
        while(!weights.isEmpty())
        {
            int[] e = weights.poll();
            if(find(e[1])==find(e[2]))
            {
                continue;
            }
            union(e[1], e[2]);
            mstWeight+=e[0];
        }
        List<Integer> crit = new ArrayList<>();
        List<Integer> pseudo = new ArrayList<>();
        for(int i = 0; i<edges.length; i++)
        {
            for(int k = 0; k<n; k++)
            {
                parent[k] = k;
                size[k] = 1;
            }
            for(int j = 0; j<edges.length; j++)
            {
                int[] edge = edges[j];
                if(i==j)
                {
                    continue;
                }
                weights.add(new int[]{edge[2], edge[0], edge[1]});
            }
            int weight = 0;
            int unionE = 0;
            while(!weights.isEmpty())
            {
                int[] e = weights.poll();
                if(find(e[1])==find(e[2]))
                {
                    continue;
                }
                union(e[1], e[2]);
                unionE++;
                weight+=e[0];
            }
            if(weight!=mstWeight || unionE<n-1)
            {
                crit.add(i);
            }
            else
            {
                for(int k = 0; k<n; k++)
                {
                    parent[k] = k;
                    size[k] = 1;
                }
                union(edges[i][0], edges[i][1]);
                for(int j = 0; j<edges.length; j++)
                {
                    int[] edge = edges[j];
                    if(i==j)
                    {
                        continue;
                    }
                    weights.add(new int[]{edge[2], edge[0], edge[1]});
                }
                weight = edges[i][2];
                while(!weights.isEmpty())
                {
                    int[] e = weights.poll();
                    if(find(e[1])==find(e[2]))
                    {
                        continue;
                    }
                    union(e[1], e[2]);
                    weight+=e[0];
                }
                if(weight==mstWeight)
                {
                    pseudo.add(i);
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(crit);
        ans.add(pseudo);
        return ans;
    }

    public int find(int node)
    {
        if(parent[node]==node)
        {
            return node;
        }
        return parent[node] = find(parent[node]);
    }
    public void union(int node1, int node2)
    {
        int par1 = find(node1);
        int par2 = find(node2);
        if(size[par2]>size[par1])
        {
            int temp = par1;
            par1 = par2;
            par2 = temp;
        }
        parent[par2] = par1;
        size[par1]+=size[par2];
    }
}
