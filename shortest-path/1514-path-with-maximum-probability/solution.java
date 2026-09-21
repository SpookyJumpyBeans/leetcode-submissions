// 1514. Path with Maximum Probability
// https://leetcode.com/problems/path-with-maximum-probability/
// Medium | Java | Accepted 2026-09-20
// Runtime 28 ms | Memory 63.6 MB

class Solution {
    record Edge(int b, double prob) {}
    record Pair(double prob, int node) {}
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        ArrayList<Edge>[] graph = new ArrayList[n]; //Create adjacency list, tying every start node with the probability of traversing to all its neighbors
        //Run Dijkstra's starting at the start_node
        for(int i = 0; i<graph.length; i++)
        {
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i<edges.length; i++)
        {
            int[] e = edges[i];
            graph[e[0]].add(new Edge(e[1], succProb[i]));
            graph[e[1]].add(new Edge(e[0], succProb[i])); //Since this graph is undirected, every edge goes both ways
        }
        double[] maxProb = new double[n]; //This is our tracker for the maximum probabilities found to traverse to every node
        Arrays.fill(maxProb, 0.0); //Default everything to 0.0
        PriorityQueue<Pair> dij = new PriorityQueue<>((a, b) -> Double.compare(b.prob(), a.prob())); 
        //Have a max heap that stores pairs of the current probability of the path and the node we're curretnly at
        maxProb[start_node] = 1.0; //Set the probability of the starting node to 1.0 since it's guaranteed we can get to this node
        dij.add(new Pair(1.0, start_node)); //Add the starting node to the pq
        while(!dij.isEmpty())
        {
            Pair tempNode = dij.poll(); //Poll the current node we're at
            double prob = tempNode.prob();
            int node = tempNode.node();
            if(node==end_node) //If this node is the end node, return the probability stored of getting to this node
            {
                return prob;
            }
            if(prob<maxProb[node]) //If this probability is lower than what we have stored in the tracker, this is an outdated value, so continue
            {
                continue;
            }
            List<Edge> neigh = graph[node]; //Get all the node's neighbors
            for(int i = 0; i<neigh.size(); i++)
            {
                double newProb = prob * neigh.get(i).prob(); //Get the probability of moving to this neighbor by multiplying our current probability with the edge weight as well as the neighbors node id
                int neighN = neigh.get(i).b();
                if(newProb>maxProb[neighN]) //If the probability of moving to this node is greater than what we currently have stored, this is the most optimal path of getting to this node
                { 
                    maxProb[neighN] = newProb; //Store the new highest probability path to this node in the tracker
                    dij.add(new Pair(newProb, neighN)); //Add this to the priority queue as a possible maximum probability path to the end_node 
                }
            }
        }
        //If it's impossible to get to the end node, return 0.0
        return 0.0;
    }
}
