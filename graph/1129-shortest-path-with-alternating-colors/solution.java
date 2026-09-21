// 1129. Shortest Path with Alternating Colors
// https://leetcode.com/problems/shortest-path-with-alternating-colors/
// Medium | Java | Accepted 2026-09-20
// Runtime 5 ms | Memory 46.6 MB

class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        // This is secretly a 2D BFS Problem, because not only do we have to keep track of the current distance (every edge is a weight of 1) but also whether the previous edge we traversed was red or blue
        // First create the adjacency list
        ArrayList<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < redEdges.length; i++) {
            int[] e = redEdges[i];
            graph[e[0]].add(new int[]{e[1], 0}); // Throw in all the red edges that this current node can travel to
        }
        for (int i = 0; i < blueEdges.length; i++) {
            int[] e = blueEdges[i];
            graph[e[0]].add(new int[]{e[1], 1}); // Throw in all the blue edges that this current node can travel to 
        }
        
        int[][] minDist = new int[n][2]; // This tracks the minimum distance to reach each node, split into two states based on whether the incoming edge was red or blue.
        for (int[] d : minDist) {
            Arrays.fill(d, Integer.MAX_VALUE); // Default everything to the max integer value
        }
        
        minDist[0][0] = 0;
        minDist[0][1] = 0; // The initial node 0 will always have a minimum of 0
        
        Queue<int[]> q = new ArrayDeque<>(); // We store 3 values in the queue: the current distance, the current node, and the color of the previous edge taken to reach this node.
        q.add(new int[]{0, 0, -1}); // The initial node can explore any path since it doesn't have a previous color
        
        while (!q.isEmpty()) {
            int[] node = q.poll();
            
            // If this node isn't the initial node and if the current distance and color combo we have stored for this node is less than the distance we just polled, we continue since this is an outdated entry
            if (node[2] != -1 && node[0] > minDist[node[1]][node[2]]) {
                continue;
            }
            
            List<int[]> neigh = graph[node[1]]; // Get all the neighbors of this node
            for (int i = 0; i < neigh.size(); i++) {
                int[] possible = neigh.get(i); 
                
                // If the color of the next edge is different from the previous edge (the initial state of -1 naturally passes this check since it doesn't equal 0 or 1)
                if (possible[1] != node[2]) {
                    int newDist = node[0] + 1; // Get the new distance to travel to the next possible node
                    
                    if (newDist < minDist[possible[0]][possible[1]]) { // If this distance is a new best
                        minDist[possible[0]][possible[1]] = newDist; // Store this new minimum distance and color combo
                        q.add(new int[]{newDist, possible[0], possible[1]}); // Add the entry to the queue
                    }
                }
            }
        }
        
        int[] ans = new int[n]; // We return the final array
        for (int i = 0; i < n; i++) {
            // If both incoming edge colors still hold the max value, the node is unreachable, so default to -1.
            if (minDist[i][0] == Integer.MAX_VALUE && minDist[i][0] == minDist[i][1]) { 
                ans[i] = -1;
            } else {
                ans[i] = Math.min(minDist[i][0], minDist[i][1]); // Else get the minimum value of both color combos to get to this node 
            }
        }
        
        return ans;
    }
}
