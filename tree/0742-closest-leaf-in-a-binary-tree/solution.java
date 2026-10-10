// 742. Closest Leaf in a Binary Tree
// https://leetcode.com/problems/closest-leaf-in-a-binary-tree/
// Medium | Java | Accepted 2026-10-09
// Runtime 7 ms | Memory 46.2 MB

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public record Combined(TreeNode node, int dist){}
    Map<TreeNode, List<TreeNode>> graph = new HashMap<>();
    TreeNode start;
    public int findClosestLeaf(TreeNode root, int k) {
        //Turn the Tree into an undirected graph and then run BFS
        //Starting from the target node explore outwards
        //If we hit a leaf node, see if the distance to this leaf node from the target is smaller than the minimum value we currently store
        if(root.val==k) //If the root is the target, set the start node to the root
        {
            start = root;
        }
        recurse(root, null, k); //Traverse the graph through DFS and make all the necessary connections 
        //Keep track of the current node and the previous node for the child to parent connection
        Queue<Combined> bfs = new ArrayDeque<>();
        bfs.add(new Combined(start, 0));
        int min = Integer.MAX_VALUE;
        int ans = 0;
        Set<Integer> visited = new HashSet<>(); //Keep track of the visited values so we don't have an infinite loop
        while(!bfs.isEmpty()) //Start BFS
        {
            Combined curr = bfs.poll();
            visited.add(curr.node().val);
            List<TreeNode> neigh = graph.get(curr.node());
            if(curr.node().left==null && curr.node().right==null && curr.dist()+1<min) //If this node is a leaf node and its distance is smaller than our current best, update
            {
                min = curr.dist()+1;
                ans = curr.node().val;
                continue;
            }
            for(int i = 0; i<neigh.size(); i++) //Else, go through all of the node's neighbors and add them to the queue if they haven't been explored
            {
                TreeNode next = neigh.get(i);
                if(!visited.contains(next.val))
                {
                    bfs.add(new Combined(next, curr.dist()+1));
                }
            }
        }
        return ans;
    }

    public void recurse(TreeNode root, TreeNode prev, int targ)
    {
        if(root==null) 
        {
            return;
        }
        if(root.val==targ) //If we've found the target node, set the start node to the current node
        {
            start = root;
        }
        if(prev!=null) //If the parent exists, set the root to parent connection
        {
            graph.computeIfAbsent(root, k -> new ArrayList<>()).add(prev);
        }
        if(root.left!=null) //Set the root to left child connection and then explore the left child's subtree
        {
            graph.computeIfAbsent(root, k -> new ArrayList<>()).add(root.left);
            recurse(root.left, root, targ);
        }
        if(root.right!=null) //Set the root to right child connection and then explore the right child's subtree
        {
            graph.computeIfAbsent(root, k -> new ArrayList<>()).add(root.right);
            recurse(root.right, root, targ);
        }
    }
}
