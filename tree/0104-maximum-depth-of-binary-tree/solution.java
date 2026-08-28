// 104. Maximum Depth of Binary Tree
// https://leetcode.com/problems/maximum-depth-of-binary-tree/
// Easy | Java | Accepted 2025-11-12
// Runtime 0 ms | Memory 44.2 MB

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
    public int maxDepth(TreeNode root) {
        int count = 0;
        return recurse(root, count);
    }
    public int recurse(TreeNode node, int count)
    {
        if(node==null)
        {
            return count;
        }
        return Math.max(recurse(node.right, count+1), recurse(node.left, count+1));
    }
}
