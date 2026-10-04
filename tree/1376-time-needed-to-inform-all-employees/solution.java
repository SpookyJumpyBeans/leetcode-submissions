// 1376. Time Needed to Inform All Employees
// https://leetcode.com/problems/time-needed-to-inform-all-employees/
// Medium | Java | Accepted 2026-10-03
// Runtime 42 ms | Memory 110.6 MB

class Solution {
    public record Emp(int id, int informTime){}

    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        //Make a graph where the index is the manager and the arraylist is the subordinates of that manager
        //All the subordinates will have the same inform time
        //Run BFS until the queue is empty
        //Compare the time to get to that node with the max time found thus far
        //Return the max time at the end
        ArrayList<Emp>[] graph = new ArrayList[n];
        graph[headID] = new ArrayList<>(); //Instantiate the head ceo arraylist intiially since it doesn't have a manager
        for(int i = 0; i<manager.length; i++)
        {
            if(i==headID) //Continue if we hit the -1
            {
                continue;
            }
             if(graph[manager[i]]==null) //Else we want to instantiate this manager's subordinate list
            {
                graph[manager[i]] = new ArrayList<>();
            }
            graph[manager[i]].add(new Emp(i, informTime[manager[i]])); //Add the subordinate to the manager's list
        }
        Queue<int[]> bfs = new ArrayDeque<>(); //BFS
        bfs.add(new int[]{headID, 0}); //Start at the head ceo
        //Keep track of the current id of the node as well as the current time it's taken to get to this node
        int maxTime = 0; //Track the max time found
        //If we did DFS, we can just get the max of every full root to leaf path we find
        while(!bfs.isEmpty())
        {
            int[] curr = bfs.poll();
            maxTime = Math.max(curr[1], maxTime); //Update the max for this current employee
            ArrayList<Emp> children = graph[curr[0]]; //Get its children
            if(children == null) //If the children are null, continue (this is a leaf node)
            {
                continue;
            }
            for(int i = 0; i<children.size(); i++)
            {                
                bfs.add(new int[]{children.get(i).id(), curr[1]+children.get(i).informTime()}); //Add the time it takes to get to this child node and add it to the queue
            }
        }
        return maxTime;
    }
}
