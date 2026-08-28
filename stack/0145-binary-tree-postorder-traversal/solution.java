// 145. Binary Tree Postorder Traversal
// https://leetcode.com/problems/binary-tree-postorder-traversal/
// Easy | Java | Accepted 2026-08-26
// Runtime 0 ms | Memory 43 MB

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
    List<Integer> temp = new ArrayList<>();
    public List<Integer> postorderTraversal(TreeNode root) {
        recurse(root);
        return temp;
    }
      public void recurse(TreeNode root)
    {
       if(root==null) 
       {
        return;
       }
       recurse(root.left); 
       recurse(root.right);
       temp.add(root.val);
    }
}
