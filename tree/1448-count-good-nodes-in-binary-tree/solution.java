// 1448. Count Good Nodes in Binary Tree
// https://leetcode.com/problems/count-good-nodes-in-binary-tree/
// Medium | Java | Accepted 2025-11-28
// Runtime 2 ms | Memory 57.1 MB

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
    public int goodNodes(TreeNode root) {
        return recurse(root, -99999);
    }
    
    public int recurse(TreeNode root, int max)
    {
        if(root == null)
        {
            return 0;
        }
        max = Math.max(root.val, max);
        int count = 0;
        if(root.val >= max)
        {
            count = 1;
        }
        return recurse(root.left, max) + recurse(root.right, max) + count;
    }
}
