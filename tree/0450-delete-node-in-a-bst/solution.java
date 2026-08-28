// 450. Delete Node in a BST
// https://leetcode.com/problems/delete-node-in-a-bst/
// Medium | Java | Accepted 2026-08-19
// Runtime 0 ms | Memory 47.7 MB

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
    public TreeNode deleteNode(TreeNode root, int key) {
       if(root==null)
       {
        return null;
       }
       if(key<root.val)
       {
        root.left = deleteNode(root.left, key);
       }
       else if(key>root.val)
       {
        root.right = deleteNode(root.right, key);
       }
       else
       {
            if(root.right==null && root.left==null)
            {
                return null;
            }
            if(root.right==null && root.left!=null)
            {
                return root.left;
            }
            if(root.right!=null && root.left==null)
            {
                return root.right;
            }
            if(root.right!=null && root.left!=null)
            {
                root.val = smallestRight(root.right);
                root.right = deleteNode(root.right, root.val);
                return root;
            }
       }
       return root;
    }
    public int smallestRight(TreeNode root)
    {
        if(root.left==null)
        {
            return root.val;
        }
        return smallestRight(root.left);
    }
}
