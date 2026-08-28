// 222. Count Complete Tree Nodes
// https://leetcode.com/problems/count-complete-tree-nodes/
// Medium | Java | Accepted 2026-08-15
// Runtime 0 ms | Memory 49.6 MB

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
    TreeNode left;
    TreeNode right;
    public int countNodes(TreeNode root) {
        if(root==null)
        {
            return 0;
        }
        int l = recurseLeft(root);
        int r = recurseRight(root);
        if(l==r)
        {
            return (1 << l)-1;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    public int recurseLeft(TreeNode root)
    {
       int height = 0;
       while(root!=null)
       {
        height++;
        root = root.left;
       }
       return height;
    }
     public int recurseRight(TreeNode root)
    {
        int height = 0;
       while(root!=null)
       {
        height++;
        root = root.right;
       }
       return height;
    }
}
