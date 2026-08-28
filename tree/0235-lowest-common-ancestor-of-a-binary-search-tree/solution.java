// 235. Lowest Common Ancestor of a Binary Search Tree
// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
// Medium | Java | Accepted 2025-11-23
// Runtime 6 ms | Memory 47.7 MB

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q)
        {
            return root;
        }
        if(root.val < p.val && root.val < q.val)
        {
            return lowestCommonAncestor(root.right, p, q);
        }
     if(root.val > p.val && root.val > q.val)
        {
            return lowestCommonAncestor(root.left, p, q);
        }
        return root;
    }
}
