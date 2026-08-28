// 124. Binary Tree Maximum Path Sum
// https://leetcode.com/problems/binary-tree-maximum-path-sum/
// Hard | Java | Accepted 2025-12-02
// Runtime 0 ms | Memory 46.7 MB

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
    int max = -9999999;
    public int maxPathSum(TreeNode root) {
     int temp = recurse(root);
     return max;
    }

    public int recurse(TreeNode root)
    {
        if(root == null)
        {
            return 0;
        }
        int left =  Math.max(recurse(root.left), 0);
        int right = Math.max(recurse(root.right), 0); 
        max = Math.max(left + right + root.val, max);
        return root.val + Math.max(left, right);
    }
}
