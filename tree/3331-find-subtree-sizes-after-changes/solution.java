// 3331. Find Subtree Sizes After Changes
// https://leetcode.com/problems/find-subtree-sizes-after-changes/
// Medium | Java | Accepted 2026-11-21
// Runtime 121 ms | Memory 230.1 MB

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        ArrayList<Integer>[] adj = new ArrayList[parent.length];
        for(int i = 0; i<parent.length; i++) //Create the adjacency list from parent to children (top down)
        //The parent array only gives child to parent (bottom up) 
        {
            adj[i] = new ArrayList<>();
        }
        for(int i = 0; i<parent.length; i++)
        {
            if(parent[i]!=-1)
            {
                adj[parent[i]].add(i);
            }
        }
        int[] ans = new int[parent.length]; //This stores the sizes of the subtrees
        int[] lastSeen = new int[26]; //This keeps track of the last seen node that contains a specific character 'a' to 'z' (0 - 25)
        Arrays.fill(lastSeen, -1); //Fill it by default to -1
        recurse(0, adj, parent, s, ans, lastSeen); //DFS
        return ans;
    }

    public void recurse(int curr, ArrayList<Integer>[] adj, int[] parent, String s, int[] ans, int[] lastSeen)
    {
        if(lastSeen[s.charAt(curr)-'a']!=-1) //Check if an ancestor on the current path has the same character as curr
        //If yes, then that means we need to set the parent of curr to that ancestor
        {
            parent[curr] = lastSeen[s.charAt(curr)-'a']; //Overwrite parent to the ancestor
        }
        int temp = lastSeen[s.charAt(curr)-'a']; //Save the previous ancestor for this letter (Need to do this because we're backtracking)
        lastSeen[s.charAt(curr)-'a'] = curr; //Put curr into the last seen so curr's descendants see curr as their closest ancestor for this letter
        ArrayList<Integer> neigh = adj[curr]; //Get curr's children
        ans[curr] = 1; //Initialize curr's subtree to 1 (counting curr itself) before visiting its children
        //Important because when we add its children's counts to the parent, we need the parent's size to already by initialized
        if(neigh!=null)
        {
            for(int child : neigh)
            {
                recurse(child, adj, parent, s, ans, lastSeen); //Explore all of curr's children
            }
        }
        lastSeen[s.charAt(curr)-'a'] = temp; //After exploring, restore the original last seen for the character before we hit the parent node (we are backtracking and recursing back up the tree)
        if(parent[curr]!=-1) //If this node isn't the root, add the child's subtree size to the parents
        {
            ans[parent[curr]] += ans[curr];
        }
    }
}
