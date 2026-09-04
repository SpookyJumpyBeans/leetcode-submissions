// 1325. Delete Leaves With a Given Value
// https://leetcode.com/problems/delete-leaves-with-a-given-value/
// Medium | Java | Accepted 2026-09-04
// Runtime 0 ms | Memory 46.1 MB

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
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if(root==null)
        {
            return null;
        }
        if(root.val==target && root.left==null && root.right==null)
        {
            return null;
        }
        root.left = removeLeafNodes(root.left, target);   
        root.right = removeLeafNodes(root.right, target);
        if(root.val==target && root.left==null && root.right==null)
        {
            return null;
        }
        return root;
    }
}
