// 94. Binary Tree Inorder Traversal
// https://leetcode.com/problems/binary-tree-inorder-traversal/
// Easy | Java | Accepted 2026-08-26
// Runtime 0 ms | Memory 43.2 MB

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
    ArrayList<Integer> temp = new ArrayList<>();
    public List<Integer> inorderTraversal(TreeNode root) {
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
       temp.add(root.val);
       recurse(root.right);
    }
}
