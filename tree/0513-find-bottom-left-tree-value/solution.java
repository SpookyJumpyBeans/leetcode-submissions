// 513. Find Bottom Left Tree Value
// https://leetcode.com/problems/find-bottom-left-tree-value/
// Medium | Java | Accepted 2026-11-05
// Runtime 4 ms | Memory 46 MB

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
    public class levelNode
    {
        int level;
        TreeNode node;
        public levelNode(TreeNode node, int lev)
        {
            this.node = node;
            level = lev;
        }
    }
    public int findBottomLeftValue(TreeNode root) {
        //Run BFS and keep track of the level we're currently at
        //If we reach a level greater than our current max level, update our max level and the first value associated with this new max level 
        //At the end, that first value of the max level is the answer
        Queue<levelNode> bfs = new ArrayDeque<>();
        bfs.add(new levelNode(root, 0));
        int maxLev = -1;
        int maxVal = 0;
        while(!bfs.isEmpty())
        {
            levelNode temp = bfs.poll();
            if(temp.level>maxLev)
            {
                maxLev = temp.level;
                maxVal = temp.node.val;
            }
            if(temp.node.left!=null)
            {
                bfs.add(new levelNode(temp.node.left, temp.level+1));
            }
            if(temp.node.right!=null)
            {
                bfs.add(new levelNode(temp.node.right, temp.level+1));
            }
        }
        return maxVal;

    }
}
