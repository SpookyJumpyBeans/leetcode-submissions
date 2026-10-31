// 2359. Find Closest Node to Given Two Nodes
// https://leetcode.com/problems/find-closest-node-to-given-two-nodes/
// Medium | Java | Accepted 2026-10-30
// Runtime 11 ms | Memory 101.6 MB

class Solution {
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        //Since we have directed edges, there is no branching, which means we don't have to run BFS
        int n = edges.length;
        int[] dist1 = new int[n]; //Keep two distance arrays which tracks the distance from node1/node2 to every reachable node 
        int[] dist2 = new int[n];
        Arrays.fill(dist1, -1); //Fill both with -1 so we can distinguish nodes they can't reach
        Arrays.fill(dist2, -1);
        int curr = 0; //Tracks the distance thus far
        //Since every step is the same weight (1) and there is only one node per level (since each node has at most one outgoing edge) we can just increment curr every visit
        while(true)
        {
            dist1[node1] = curr++; //Set the distance to get to this node and increment curr
            node1 = edges[node1]; //Update the next node we traverse to
            if(node1==-1 || dist1[node1] != -1) //If this next node is -1 or we've already traversed to this node before, break
            {
                break;
            }
        }
        curr = 0;
        while(true)
        {
            dist2[node2] = curr++;
            node2 = edges[node2];
            if(node2==-1 || dist2[node2] != -1)
            {
                break;
            }
        }
        int min = Integer.MAX_VALUE;
        int ans = -1;
        for(int i = 0; i<n; i++) //Go through both distance arrays and only look at nodes that are reachable by both nodes
        {
            if(dist1[i]!=-1 && dist2[i]!=-1)
            {
                int dist = Integer.max(dist1[i], dist2[i]);
                if(dist<min) //If the maximum of the two distances is less than our current min, update our min and our answer node
                {
                    min = dist;
                    ans = i;
                }
            }
        }
        return ans;
    }
}
