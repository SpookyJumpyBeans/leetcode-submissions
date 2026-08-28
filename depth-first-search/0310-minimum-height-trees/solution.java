// 310. Minimum Height Trees
// https://leetcode.com/problems/minimum-height-trees/
// Medium | Java | Accepted 2026-08-27
// Runtime 15 ms | Memory 63.7 MB

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if(n==1)
        {
            return new ArrayList<>(Arrays.asList(0));
        }
        int[] frequency = new int[n];
        List<Integer>[] graph = new ArrayList[n];
        for(int[] e : edges)
        {
            if(graph[e[0]]==null)
            {
                graph[e[0]] = new ArrayList<>();
            }
            if(graph[e[1]]==null)
            {
                graph[e[1]] = new ArrayList<>();
            }
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
            frequency[e[1]]++;
            frequency[e[0]]++;
        }
        Queue<Integer> bfs = new LinkedList<>();
        for(int i = 0; i<frequency.length; i++)
        {
            if(frequency[i]==1)
            {
                bfs.add(i);
            }
        }
        // [5, 0, 1, 2]
        int numberNodes = n;
        while(numberNodes>2)
        {
            int size = bfs.size();
            for(int j = 0; j<size; j++)
            {
                int temp = bfs.poll();
                List<Integer> neighbors = graph[temp];
                for(int i = 0; i<neighbors.size(); i++)
                {
                    int node = neighbors.get(i); //1
                    frequency[node]--;
                    if(frequency[node]==1)
                    {
                        bfs.add(node);
                    }
                }
            }
            numberNodes-=size;
        }
        List<Integer> temp = new ArrayList<>(bfs);
        return temp;
    }
}
