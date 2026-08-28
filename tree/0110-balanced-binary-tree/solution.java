// 110. Balanced Binary Tree
// https://leetcode.com/problems/balanced-binary-tree/
// Easy | Java | Accepted 2025-11-18
// Runtime 0 ms | Memory 45.5 MB

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
    public boolean isBalanced(TreeNode root) {
        if(root==null)
        {
            return true;
        }
        return recurse(root) > 0 ? true : false;
    }

    public int recurse(TreeNode root)
    {
        if(root == null)
        {
            return 0;
        }
        int left = recurse(root.left);
        int right = recurse(root.right);

        if(left==-1 || right==-1 || (Math.abs(left-right)>1))
        {
            return -1;
        }
        return 1 + Math.max(left, right);
    }

}
