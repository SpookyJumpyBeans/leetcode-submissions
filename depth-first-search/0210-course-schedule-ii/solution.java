// 210. Course Schedule II
// https://leetcode.com/problems/course-schedule-ii/
// Medium | Java | Accepted 2026-01-05
// Runtime 7 ms | Memory 48 MB

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] count = new int[numCourses];
        for(int i = 0; i<prerequisites.length; i++)
        {
            int course = prerequisites[i][0];
            int prereq = prerequisites[i][1];
            if(!map.containsKey(prereq))
            {
                map.put(prereq, new ArrayList<>());
            }
            map.get(prereq).add(course);
            count[course]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int j = 0; j<numCourses; j++)
        {
            if(count[j]==0)
            {
                queue.add(j);
            }
        }
        int[] ans = new int[numCourses];
        int ind = 0;
        while(!queue.isEmpty())
        {
            int node = queue.poll();
            ans[ind] = node;
            if(map.get(node)!=null)
            {
            for(int neigh : map.get(node))
            {
                count[neigh]--;
                if(count[neigh]==0)
                {
                    queue.add(neigh);
                }
            }
            }
            ind++;
        }
        for(int check : count)
        {
            if(check!=0)
            {
            return new int[0];
            }
        }
        return ans;
    }
}
