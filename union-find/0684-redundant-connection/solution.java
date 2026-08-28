// 684. Redundant Connection
// https://leetcode.com/problems/redundant-connection/
// Medium | Java | Accepted 2026-01-05
// Runtime 0 ms | Memory 44.7 MB

class Solution {
    int[] unionFind;
    public int[] findRedundantConnection(int[][] edges) {
        unionFind = new int[edges.length+1];
        for(int i = 1; i<=edges.length; i++)
        {
            unionFind[i] = i;
        }
        for(int j = 0; j<edges.length; j++)
        {
            int servant = edges[j][0];
            int boss = edges[j][1];
            int bossServ = find(servant);
            int bossBoss = find(boss);
            if(bossServ == bossBoss)
            {
                return edges[j];
            }
            else
            {
                unionFind[bossServ] = bossBoss;
            }
        }
        return null;
    }

    public int find(int start)
    {
        if(unionFind[start]==start)
        {
            return start;
        }
        return unionFind[start] = find(unionFind[start]);
    }
}
