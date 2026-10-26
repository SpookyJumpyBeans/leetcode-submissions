// 2685. Count the Number of Complete Components
// https://leetcode.com/problems/count-the-number-of-complete-components/
// Medium | Java | Accepted 2026-10-25
// Runtime 18 ms | Memory 47.5 MB

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        //Use BFS to explore all the graphs
        //Keep a visited array so that for separate components of the graph, we only explore each component once
        for(int i = 0; i<n; i++)
        {
            graph[i] = new ArrayList<>();
        }
        for(int[] e : edges) //Since the graph is undirected, add the edge both ways
        { 
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        Set<Integer> visited = new HashSet<>();
        int count = 0; //Counts the number of complete connected components of the graph
        for(int i = 0; i<n; i++)
        {
            if(visited.add(i)) //Check if we haven't already explored this node
            {
                Queue<Integer> bfs = new ArrayDeque<>();
                bfs.add(i); //Add the initial node
                int countNode = 1; //At the start, we will have one node and zero edges
                int countEdge = 0;
                while(!bfs.isEmpty())
                {
                    int curr = bfs.poll();
                    ArrayList<Integer> neigh = graph[curr]; //Get the node's neighbors
                    countEdge+=neigh.size(); //Add the number of neighbors this node has to the edges counter 
                    //Essentially, we are overcounting every edge twice 
                    for(int j = 0; j<neigh.size(); j++)
                    {
                        if(visited.add(neigh.get(j))) //If we haven't already explored this neighbor node
                        {
                            countNode++; //Increment the count of nodes
                            bfs.add(neigh.get(j)); //Add the node to the queue
                        }
                    }
                }
                if(countNode*(countNode-1)/2 == countEdge/2) //The formula for a completely connected graph is numNodes(numNodes-1)/2 == numEdges
                //Since we counted every edge twice, we simply divide it by 2 and check
                {
                    count++; //If this statement holds, increment the counter of completely connected components
                }
            }
        }
        return count;
    }
}
