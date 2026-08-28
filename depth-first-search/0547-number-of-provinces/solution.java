// 547. Number of Provinces
// https://leetcode.com/problems/number-of-provinces/
// Medium | Java | Accepted 2026-08-17
// Runtime 2 ms | Memory 47.3 MB

class Solution {
    int[] parent; //Stores the roots of every node
    int[] size; //Stores the size of the group each node belongs to
    int count; //Number of groups
    public int findCircleNum(int[][] isConnected) {
        parent = new int[isConnected.length];
        size = new int[isConnected.length];
        count = isConnected.length;
        for(int i = 0; i<isConnected.length; i++)
        {
            parent[i] = i;
            size[i] = 1;
        }
        for(int i = 0; i<isConnected.length; i++)
        {
            int[] temp = isConnected[i];
            for(int j = 0; j<isConnected.length; j++)
            {
                if(i==j || temp[j]==0)
                {
                    continue;
                }
                union(i, j);
            }
        }
        return count;
    }

    public int find(int node)
    {
        if(parent[node]==node)
        {
            return node;
        }
        return parent[node] = find(parent[node]);
    }

    public void union(int group1, int group2)
    {
        int root1 = find(group1);
        int root2 = find(group2);
        if(root1==root2)
        {
            return;
        }
        if(size[root1]>=size[root2])
        {
            int temp = group1;
            group1 = group2;
            group2 = temp;
        }
        parent[root1] = root2;
        size[root2]+=size[root1];
        count--;
    }


}
