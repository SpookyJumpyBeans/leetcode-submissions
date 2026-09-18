// 2924. Find Champion II
// https://leetcode.com/problems/find-champion-ii/
// Medium | Java | Accepted 2026-09-17
// Runtime 1 ms | Memory 48.5 MB

class Solution {
    public int findChampion(int n, int[][] edges) {
        //This problem is like implementing the first half of Kahn's algorithm, which is finding the number of incoming edges for every node
        //A champion is defined as a node with no incoming edges
        //Simply count all the incoming edges for a node 
        //If there's only one node with no incoming edges, then that node is the champion
        //Else, return -1
        int[] numIncomingEdges = new int[n];
        for(int[] e : edges)
        {
            numIncomingEdges[e[1]]++; //Get number of incoming edges for every node
        }
        int count = 0;
        int champ = -1;
        for(int k = 0; k<numIncomingEdges.length; k++)
        {
            if(numIncomingEdges[k]==0) //Possible champion
            {
                count++; //Increment the count of champions
                champ = k; //Set the champ to the current node
            }
            if(count>1) //The second there are more than 1 possible champ, return -1
            {
                return -1;
            }
        }
        return champ; //Return the champ
    }
}
