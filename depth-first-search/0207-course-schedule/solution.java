// 207. Course Schedule
// https://leetcode.com/problems/course-schedule/
// Medium | Java | Accepted 2026-08-12
// Runtime 10 ms | Memory 46.9 MB

/* 
Method using cycle detection and DFS

class Solution {
    boolean[] visited;
     Map<Integer, List<Integer>> map = new HashMap<>();
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        for(int i = 0; i<prerequisites.length; i++)
        {
               int course = prerequisites[i][0];
               int prereq = prerequisites[i][1];
               if(!map.containsKey(course))
               {
                map.put(course, new ArrayList<>());
               }
               map.get(course).add(prereq);
        }
        visited = new boolean[numCourses];
        
        for(int j = 0; j<numCourses; j++)
        {
            boolean flag = recurse(new boolean[numCourses], j);
            if(!flag)
            {
                return false;
            }
        }
        return true;
    }

    public boolean recurse(boolean[] currPath, int node)
    {
        if(map.containsKey(node))
        {
        visited[node] = true;
        currPath[node] = true;
        for(int neighbor : map.get(node))
        {
            if(!visited[neighbor])
            {
                if(!recurse(currPath, neighbor))
                {
                    return false;
                }
            }
            if(currPath[neighbor])
            {
                return false;
            }
        }
        currPath[node] = false;
        }
        return true;
    }
}
*/

class Solution {
     Map<Integer, List<Integer>> map = new HashMap<>();
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] counts = new int[numCourses];
        for(int[] i : prerequisites)
        {
            map.computeIfAbsent(i[0], k -> new ArrayList<>()).add(i[1]);
            counts[i[1]]++;
        }
        Queue<Integer> bfs = new LinkedList<>();
        for(int j = 0; j<numCourses; j++)
        {
            if(counts[j]==0)
            {
                bfs.add(j);
            }
        }
        int count = 0;
        while(!bfs.isEmpty())
        {
            int front = bfs.poll();
            List<Integer> neighbors = map.get(front);
            count++;
            if(neighbors!=null)
            {
            for(int k = 0; k<neighbors.size(); k++)
            {
                counts[neighbors.get(k)]--;
                if(counts[neighbors.get(k)]==0)
                {
                    bfs.add(neighbors.get(k));
                }
            }
            }
        }
        return count == numCourses ? true : false;
    }
}
