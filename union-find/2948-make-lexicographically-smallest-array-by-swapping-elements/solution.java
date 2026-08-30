// 2948. Make Lexicographically Smallest Array by Swapping Elements
// https://leetcode.com/problems/make-lexicographically-smallest-array-by-swapping-elements/
// Medium | Java | Accepted 2026-08-29
// Runtime 227 ms | Memory 238.3 MB

class Solution {
    Map<Integer, Integer> parents = new HashMap<>();
    Map<Integer, Integer> size = new HashMap<>();
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        //I USED A UNION FIND APPROACH
        //Basically union all the nodes that are within limit's reach of each other
        //This makes all of the node's that are swappable and within a limit's reach of each other in the same group
        //Go through this 
        int[] copy = nums.clone();
        for(int i = 0; i<nums.length; i++)
        {
            parents.put(nums[i], nums[i]);
            size.put(nums[i], 1);
        }
        Arrays.sort(nums);
        for(int i = 0; i<nums.length-1; i++)
        {
            if(nums[i+1]-nums[i]<=limit)
            {
               union(nums[i], nums[i+1]);
            }
        }
        Map<Integer, Queue<Integer>> queue = new HashMap<>();
        for(int i : nums)
        {   
            queue.computeIfAbsent(parents.get(i), k -> new LinkedList<>()).add(i);
        }
        for(int i = 0; i<nums.length; i++)
        {
            copy[i] = queue.get(parents.get(copy[i])).poll();
        }
        return copy;
    }

    public int find(int node)
    {
        if(parents.get(node)==node)
        {
            return node;
        }
        parents.put(node, find(parents.get(node)));
        return parents.get(node);
    }
    
    public void union(int node1, int node2)
    {
        int parent1 = find(node1);
        int parent2 = find(node2);
        int tempp = node2;
        if(parent1 == parent2)
        {
            return;
        }
        if(size.get(parent1)>size.get(parent2))
        {
            int temp = parent1;
            node1 = node2;
            node2 = temp;
        }   
        parents.put(node1, node2);
        int combinedSize = size.get(parent1) + size.get(parent2);
        size.put(tempp, combinedSize);
    }
}
