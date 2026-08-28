// 226. Invert Binary Tree
// https://leetcode.com/problems/invert-binary-tree/
// Easy | Java | Accepted 2025-11-12
// Runtime 0 ms | Memory 42.9 MB

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
    public TreeNode invertTree(TreeNode root) {
        traverse(root);
        return root;
    }
    
    public void traverse(TreeNode node)
    {
        if(node==null)
        {
            return;
        }
        TreeNode rightN = node.right;
        TreeNode leftN = node.left;
        node.left = rightN;
        node.right = leftN;
        traverse(node.right);
        traverse(node.left);
    }
}
